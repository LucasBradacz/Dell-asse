package com.dellasse.backend.models;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enterprise {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String document;
    private String address;
    private String phoneNumber;
    private String email;
    private String urlImage;
    private LocalDateTime dateExpiration;

    @OneToMany(mappedBy = "enterprise")
    private Set<User> users;

    public Enterprise(String name, String address, String phoneNumber, String email, String urlImage) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.urlImage = urlImage;
    }
}
