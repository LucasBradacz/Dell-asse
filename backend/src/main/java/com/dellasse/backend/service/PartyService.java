package com.dellasse.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dellasse.backend.contracts.party.PartyCreateRequest;
import com.dellasse.backend.mappers.PartyMapper;
import com.dellasse.backend.models.Enterprise;
import com.dellasse.backend.models.Party;
import com.dellasse.backend.models.User;
import com.dellasse.backend.repositories.PartyRepository;
import com.dellasse.backend.repositories.UserRepository;
import com.dellasse.backend.util.ConvertString;
import com.dellasse.backend.util.DateUtils;
import com.dellasse.backend.util.StatusUtils;

import jakarta.persistence.EntityManager;

/*
 * @author Equipe Compilador 
 * @version 1.0
 * Serviço para a entidade Party, responsável por operações de negócio relacionadas a festas.
 * - Contém método para criar festas.
 * - Realiza validações de usuário e empresa associada.
 * - Utiliza repositórios para interagir com a camada de persistência.
 * - Define valores padrão para novas festas.
 * - Retorna a entidade Party criada.
 * - Utiliza EntityManager para buscar entidades relacionadas.
 * - Anotada com @Service para indicar que é um componente de serviço do Spring.
 * - Depende de UserService para validação de usuários e empresas.
 * - Utiliza mapeador PartyMapper para converter entre contratos e entidades.
 * - Define método privado setValueDefault para inicializar valores padrão da festa.
 * - Utiliza utilitários DateUtils e StatusUtils para manipulação de datas e status.
 * - Interage com PartyRepository para persistir a entidade Party.
 * - Realiza conversão de token de string para UUID usando ConvertString.
 * - Garante que a festa esteja associada ao usuário e empresa corretos.
 * - Facilita a manutenção e evolução do código relacionado a festas.
 * - Promove boas práticas de desenvolvimento, como injeção de dependências e separação de responsabilidades.
 * - Fornece uma interface clara para outras partes do sistema interagirem com a lógica de negócios das festas.
 * - Contribui para a robustez e confiabilidade do sistema como um todo.
 * - Suporta a escalabilidade do sistema ao permitir fácil adição de novas funcionalidades relacionadas a festas.
 * - Melhora a experiência do desenvolvedor ao fornecer um código bem estruturado e documentado.
 * - Facilita a integração com outras partes do sistema, como controladores e repositórios.
 * - Ajuda a garantir a conformidade com os requisitos de negócios e regras de validação específicas.
 * - Promove a reutilização de código através do uso de mapeadores e serviços compartilhados.
 * - Contribui para a clareza e legibilidade do código, facilitando a colaboração entre desenvolvedores.
 * - Auxilia na identificação e resolução de problemas relacionados a festas através de mensagens de erro claras e específicas.
 * - Suporta a evolução contínua do sistema, permitindo ajustes e melhorias na lógica de negócios das festas conforme necessário.
 * - Facilita a implementação de testes automatizados para garantir a qualidade do código relacionado a festas.
 * - Ajuda a manter a consistência dos dados relacionados a festas em todo o sistema.
 * - Promove a adoção de padrões de design e melhores práticas no desenvolvimento de software.
 */
@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    public Party create(PartyCreateRequest request, String token){
        UUID userId = ConvertString.toUUID(token);
        UUID enterpriseId = userService.validateUserEnterprise(userId);
        Party party = PartyMapper.toEntity(request);
        setValueDefault(party, userId, enterpriseId);
        return partyRepository.save(party);
    }

    private void setValueDefault(Party party, UUID userId, UUID enterpriseId){
        party.setUser(entityManager.find(User.class, userId));
        party.setEnterprise(entityManager.find(Enterprise.class, enterpriseId));
        party.setLastAtualization(DateUtils.now());
        party.setStatus(StatusUtils.PENDING.getValue());
    }
    
}
