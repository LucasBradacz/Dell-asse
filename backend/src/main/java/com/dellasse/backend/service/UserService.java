package com.dellasse.backend.service;


import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.dellasse.backend.exceptions.UserExeception;
import com.dellasse.backend.mappers.UserMapper;
import com.dellasse.backend.models.Role;
import com.dellasse.backend.models.User;
import com.dellasse.backend.repositories.RoleRepository;
import com.dellasse.backend.repositories.UserRepository;
import com.dellasse.backend.util.ConvertString;

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
            throw new UserExeception("Username already exists");
        }

        if (userRepository.existsByEmail(request.email())){
            throw new UserExeception("Email already exists");
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
            throw new UserExeception("User not found");
        }

        var user = userRepository.findByUsername(loginRequest.username())
            .orElseThrow(() -> new UserExeception("User not found"));
        
        if (!user.isLoginCorret(loginRequest, passwordEncoder)){
            throw new UserExeception("Invalid password");
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
        
        if (!userRepository.existsById(userUUID)){
            throw new UserExeception("User not found");
        }

        User user = userRepository.findById(userUUID)
            .orElseThrow(() -> new UserExeception("User not found"));

        if (!user.getUuid().equals(userUUID)){
            throw new UserExeception("The user does not have permission to change the data.");
        }
        
        if (request.name() != null) user.setName(request.name());
        if (request.email() != null) user.setEmail(request.email());
        if (request.username() != null) user.setUsername(request.username());
        if (request.password() != null) user.setPassword(passwordEncoder.encode(request.password()));

        Boolean active = request.active();
        if (active != null) user.setActive(request.active());

        return ResponseEntity.ok(UserMapper.toResponse(userRepository.save(user)));
    }
}
