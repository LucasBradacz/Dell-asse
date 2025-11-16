package com.dellasse.backend.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String observations;
    private String lastAtualization;
    private String status;
    private String imgExample;
    private Double generateBudget;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @ManyToMany
    @JoinTable(
        name = "party_products",
        joinColumns = @JoinColumn(name = "party_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;

    public Party(
            Long id,
            String title,
            String description,
            String observations,
            String lastAtualization,
            String status,
            List<Long> products,
            String imgExample,
            Double generateBudget
    ){
            this.id = id;
            this.title = title;
            this.description = description;
            this.observations = observations;
            this.lastAtualization = lastAtualization;
            this.productList = products.stream().map(productId -> new Product(productId)).toList();
            this.status = status;
            this.imgExample = imgExample;
            this.generateBudget = generateBudget;
    }

    public Party(String title,
            String description, List<Product> list,
            Double budget,
            String observations2, String imageURL) 
    {
            this.title = title;
            this.description = description;
            this.productList = list;
            this.generateBudget = budget;
            this.observations = observations2;
            this.imgExample = imageURL;
    }
}
