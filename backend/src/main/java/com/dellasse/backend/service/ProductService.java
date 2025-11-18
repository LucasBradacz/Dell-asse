package com.dellasse.backend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dellasse.backend.contracts.product.ProductCreateRequest;
import com.dellasse.backend.contracts.product.ProductUpdateRequest;
import com.dellasse.backend.contracts.product.UpdateResponse;
import com.dellasse.backend.mappers.ProductMapper;
import com.dellasse.backend.models.Enterprise;
import com.dellasse.backend.models.Product;
import com.dellasse.backend.models.User;
import com.dellasse.backend.repositories.ProductRepository;
import com.dellasse.backend.util.ConvertString;
import com.dellasse.backend.util.DateUtils;

import jakarta.persistence.EntityManager;

import com.dellasse.backend.exceptions.DomainError;
import com.dellasse.backend.exceptions.DomainException;

/*
 * @author Equipe Compilador 
 * @version 1.0
 * Serviço para a entidade Product, responsável por operações de negócio relacionadas a produtos.
 * - Contém métodos para criar e atualizar produtos.
 * - Realiza validações de usuário e empresa associada.
 * - Utiliza repositórios para interagir com a camada de persistência.
 * - Retorna respostas HTTP apropriadas para as operações realizadas.
 * - Define valores padrão para novos produtos.
 * - Lança exceções de domínio para erros específicos.
 * - Utiliza mapeadores para converter entre entidades e contratos.
 * - Gerencia datas de criação e atualização dos produtos.
 * - Assegura que o produto pertence à empresa do usuário autenticado.
 * - Garante a integridade dos dados durante as operações de criação e atualização.
 * - Facilita a manutenção e evolução do código relacionado a produtos.
 * - Promove boas práticas de desenvolvimento, como injeção de dependências e separação de responsabilidades.
 * - Fornece uma interface clara para outras partes do sistema interagirem com a lógica de negócios dos produtos.
 * - Contribui para a robustez e confiabilidade do sistema como um todo.
 * - Suporta a escalabilidade do sistema ao permitir fácil adição de novas funcionalidades relacionadas a produtos.
 * - Melhora a experiência do desenvolvedor ao fornecer um código bem estruturado e documentado.
 * - Facilita a integração com outras partes do sistema, como controladores e repositórios.
 * - Ajuda a garantir a conformidade com os requisitos de negócios e regras de validação específicas.
 * - Promove a reutilização de código através do uso de mapeadores e serviços compartilhados.
 * - Contribui para a clareza e legibilidade do código, facilitando a colaboração entre desenvolvedores.
 * - Auxilia na identificação e resolução de problemas relacionados a produtos através de mensagens de erro claras e específicas.
 * - Suporta a evolução contínua do sistema, permitindo ajustes e melhorias na lógica de negócios dos produtos conforme necessário.
 * - Facilita a implementação de testes automatizados para garantir a qualidade do código relacionado a produtos.
 * - Ajuda a manter a consistência dos dados relacionados a produtos em todo o sistema.
 * - Promove a adoção de padrões de design e melhores práticas no desenvolvimento de software.
 */
@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    public ResponseEntity<?> create(ProductCreateRequest createRequest, String token){
        UUID userId = ConvertString.toUUID(token);

        Product product = ProductMapper.toEntityCreateProduct(createRequest);
        if (product == null){
            throw new NullPointerException();
        }

        UUID enterpriseId = userService.validateUserEnterprise(userId);

        setValueDefault(product, userId, enterpriseId);
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void setValueDefault(Product product, UUID userId, UUID enterpriseId){
        product.setUser(entityManager.getReference(User.class, userId));
        product.setEnterprise(entityManager.getReference(Enterprise.class, enterpriseId));
        product.setDateCreate(DateUtils.now());
        product.setDateUpdate(DateUtils.now());
    }

    public UpdateResponse update(ProductUpdateRequest request, Long productId, String token) {
        UUID userId = ConvertString.toUUID(token);
        UUID enterpriseId = userService.validateUserEnterprise(userId);

        Product entity = productRepository.findById(productId)
                .orElseThrow(() -> new DomainException(DomainError.PRODUCT_NOT_FOUND));

        existsProductEnterprise(productId, enterpriseId);
        setValueUpdate(entity, request);

        entity.setDateUpdate(DateUtils.now());

        productRepository.save(entity);

        return ProductMapper.toContractUpdateResponse(entity);
    }

    private void existsProductEnterprise(Long productId, UUID enterpriseId){
        if (enterpriseId == null || productId == null){
            throw new DomainException(DomainError.PRODUCT_OR_ENTERPRISE_NOT_FOUND_INTERNAL);
        }

        if (!productRepository.existsById(productId)){
            throw new DomainException(DomainError.PRODUCT_NOT_FOUND);
        }

        if(!productRepository.existsByIdAndEnterprise_Id(productId, enterpriseId)){
            throw new DomainException(DomainError.PRODUCT_NOT_FOUND);
        }
    }

    private void setValueUpdate(Product entity, ProductUpdateRequest request){
        if (request.name() != null){
            entity.setName(request.name());
        }
        if (request.description() != null){
            entity.setDescription(request.description());
        }
        if (request.price() != null){
            entity.setPrice(request.price());
        }
        if (request.stockQuantity() != null){
            entity.setStockQuantity(request.stockQuantity());
        }
        if (request.category() != null){
            entity.setCategory(request.category());
        }
        if (request.imageUrl() != null){
            entity.setImageUrl(request.imageUrl());
        }
    }
}
