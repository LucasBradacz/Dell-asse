package com.dellasse.backend.service;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dellasse.backend.contracts.user.CreateRequest;
import com.dellasse.backend.contracts.user.LoginRequest;
import com.dellasse.backend.contracts.user.UpdateRequest;
import com.dellasse.backend.exceptions.DomainError;
import com.dellasse.backend.exceptions.DomainException;
import com.dellasse.backend.mappers.UserMapper;
import com.dellasse.backend.models.Role;
import com.dellasse.backend.models.User;
import com.dellasse.backend.repositories.RoleRepository;
import com.dellasse.backend.repositories.UserRepository;
import com.dellasse.backend.util.ConvertString;

/*
 * @author Equipe Compilador 
 * @version 1.0
 * Serviço para a entidade User, responsável por operações de negócio relacionadas a usuários.
 * - Contém métodos para criar, autenticar e atualizar usuários.
 * - Realiza validações de existência e permissões de usuários.
 * - Utiliza repositórios para interagir com a camada de persistência.
 * - Retorna respostas HTTP apropriadas para as operações realizadas.
 * - Gera tokens JWT para autenticação de usuários.
 * - Define valores padrão para novos usuários.
 * - Lança exceções de domínio para erros específicos.
 * - Utiliza mapeadores para converter entre entidades e contratos.
 * - Gerencia papéis associados aos usuários.
 * - Assegura a integridade dos dados durante as operações de criação e atualização.
 * - Facilita a manutenção e evolução do código relacionado a usuários.
 * - Promove boas práticas de desenvolvimento, como injeção de dependências e separação de responsabilidades.
 * - Fornece uma interface clara para outras partes do sistema interagirem com a lógica de negócios dos usuários.
 * - Contribui para a robustez e confiabilidade do sistema como um todo.
 * - Suporta a escalabilidade do sistema ao permitir fácil adição de novas funcionalidades relacionadas a usuários.
 * - Melhora a experiência do desenvolvedor ao fornecer um código bem estruturado e documentado.
 * - Facilita a integração com outras partes do sistema, como controladores e repositórios.
 * - Ajuda a garantir a conformidade com os requisitos de negócios e regras de validação específicas.
 * - Promove a reutilização de código através do uso de mapeadores e serviços compartilhados.
 * - Contribui para a clareza e legibilidade do código, facilitando a colaboração entre desenvolvedores.
 * - Auxilia na identificação e resolução de problemas relacionados a usuários através de mensagens de erro claras e específicas.
 * - Suporta a evolução contínua do sistema, permitindo ajustes e melhorias na lógica de negócios dos usuários conforme necessário.
 * - Facilita a implementação de testes automatizados para garantir a qualidade do código relacionado a usuários.
 * - Ajuda a manter a consistência dos dados relacionados a usuários em todo o sistema.
 * - Promove a adoção de padrões de design e melhores práticas no desenvolvimento de software.
 */

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtEncoder jwtEncoder;

    public ResponseEntity<?> createUser(CreateRequest request){

        if (userRepository.existsByUsername(request.username())) {
            throw new DomainException(DomainError.USER_ALREADY_EXISTS);
        }

        if (userRepository.existsByEmail(request.email())){
            throw new DomainException(DomainError.USER_ALREADY_EXISTS);
        }

        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        defaultValues(user);
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
        boolean userExists = userRepository.existsByUsername(loginRequest.username());
        if (!userExists){
            throw new DomainException(DomainError.USER_NOT_FOUND);
        }

        var user = userRepository.findByUsername(loginRequest.username())
            .orElseThrow(() -> new DomainException(DomainError.USER_NOT_FOUND));
        
        if (!user.isLoginCorret(loginRequest, passwordEncoder)){
            throw new DomainException(DomainError.USER_INVALID_PASSWORD);
        }

        var enterprise = user.getEnterprise();
        if (enterprise == null){
            throw new DomainException(DomainError.USER_NOT_FOUND_ENTERPRISE);
        }
        var expiration = enterprise.getDateExpiration();
        if (expiration != null && expiration.isBefore(LocalDateTime.now())){
            throw new DomainException(DomainError.ENTERPRISE_EXPIRED);
        }

        var now = Instant.now();
        var expiresIn = 30000L;  // em segundos (8h20min)

        var scope = user.getRoles()
            .stream()
            .map(Role::getName)
            .collect(Collectors.joining(" "));

        List<String> rolesList = user.getRoles()
            .stream()
            .map(Role::getName)
            .toList();

        var claims = JwtClaimsSet.builder()
            .issuer("dellasse.com")
            .expiresAt(now.plusSeconds(expiresIn))
            .subject(user.getUuid().toString())
            .claim("scope", scope)
            .build();

        var jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims));
        var jwtValue = jwt.getTokenValue();

        Optional<User> userOptional = userRepository.findByUsername(loginRequest.username());

        if (userOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtValue);
        response.put("roles", rolesList);
        return ResponseEntity.ok(response);
    }

    private void defaultValues(User user){
        user.setActive(true);
        Role role = roleRepository.findById(Role.Values.BASIC.getRoleId())
            .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRoles(List.of(role));

    }

    public  ResponseEntity<?>  updateUser(UpdateRequest request, UUID userId, String token){
        UUID userUUID = ConvertString.toUUID(token);
        
        if (userUUID == null){
            throw new DomainException(DomainError.USER_NOT_FOUND_INTERNAL);
        }

        User user = userRepository.findById(userUUID)
            .orElseThrow(() -> new DomainException(DomainError.USER_NOT_FOUND));

        if (!user.getUuid().equals(userUUID)){
            throw new DomainException(DomainError.USER_CANNOT_UPDATE_DATA);
        }

        user = setValuesUpdate(request, user);
        if (user == null){
            throw new DomainException(DomainError.USER_NOT_FOUND_INTERNAL);
        }

        User userSaved = userRepository.save(user);

        return ResponseEntity.ok(UserMapper.toResponse(userSaved));
    }

    public UUID validateUserEnterprise(UUID userId){

        if (userId == null){
            throw new DomainException(DomainError.USER_NOT_FOUND_INTERNAL);
        }

        if (!userRepository.existsById(userId)){
            throw new DomainException(DomainError.USER_NOT_FOUND);
        }

        UUID enterpriseId = userRepository.findEnterpriseIdByUuid(userId)
                                    .orElseThrow(() -> new DomainException(DomainError.USER_NOT_FOUND_ENTERPRISE));

        if (!userRepository.existsByUuidAndEnterprise_Id(userId, enterpriseId)){
            throw new DomainException(DomainError.ENTERPRISE_FORBIDDEN);
        }

        return enterpriseId;
    }

    public List<Role> getRoles(UUID userId){
        if (userId == null){
            throw new DomainException(DomainError.USER_NOT_FOUND_INTERNAL);
        }
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new DomainException(DomainError.USER_NOT_FOUND));

        return user.getRoles()
            .stream()
            .toList();
    }

    private User setValuesUpdate(UpdateRequest request, User user){
        if(request.username() != null){
            if (request.username().isBlank()){
                throw new DomainException(DomainError.USER_REQUIRE_USERNAME);
            }
            if (userRepository.existsByUsername(request.username())) {
                throw new DomainException(DomainError.USER_ALREADY_EXISTS);
            }
            user.setName(request.name());
        }
     
        if (request.email() != null){
            if (userRepository.existsByEmail(request.email())) {
                throw new DomainException(DomainError.USER_EMAIL_ALREADY_EXISTS);
            }
            user.setEmail(request.email());
        }
   
        if (request.username() != null) user.setUsername(request.username());
        if (request.password() != null) user.setPassword(passwordEncoder.encode(request.password()));

        Boolean active = request.active();
        if (active != null) user.setActive(request.active());

        return user;
    }

}
