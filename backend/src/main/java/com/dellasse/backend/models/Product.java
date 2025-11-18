package com.dellasse.backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Entidade que representa um produto (Product) no sistema.
 * - Contém informações como nome, descrição, preço, quantidade em estoque, categoria, URL da imagem, data de criação e data de atualização.
 * - Está associada a uma empresa e a um usuário.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    private LocalDateTime dateCreate;
    private LocalDateTime dateUpdate;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Product(Long id) {
        this.id = id;
    }
    
    public Product(
        String name,
        String description,
        Double price,
        Integer stockQuantity,
        String category,
        String imageUrl ) 
    {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.imageUrl = imageUrl;
    }

}