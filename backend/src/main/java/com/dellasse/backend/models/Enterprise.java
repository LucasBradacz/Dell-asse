package com.dellasse.backend.models;

import java.time.LocalDateTime;
import java.util.UUID;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Entidade que representa uma empresa (Enterprise) no sistema.
 * - Contém informações como nome, documento, endereço, telefone, email, URL da imagem e data de expiração.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enterprise {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String document;
    private String address;
    private String phoneNumber;
    private String email;
    private String urlImage;
    private LocalDateTime dateExpiration;

    public Enterprise(UUID id) {
        this.id = id;
    }

    public Enterprise(String name, String document, String address, String phoneNumber, String email, String urlImage, LocalDateTime dateExpiration){
        this.name = name;
        this.document = document;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.urlImage = urlImage;
        this.dateExpiration = dateExpiration;
    }

    public Enterprise(String name, String address, String phoneNumber, String email, String urlImage) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.urlImage = urlImage;
    }

    public Enterprise(String name, String document, String address, String phoneNumber, String email, String urlImage) {
        this.name = name;
        this.document = document;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.urlImage = urlImage;
    }
}