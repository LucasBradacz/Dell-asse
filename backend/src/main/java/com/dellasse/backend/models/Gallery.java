package com.dellasse.backend.models;

import java.util.List;
import java.util.stream.Collectors;

import com.dellasse.backend.contracts.image.ImageCreateRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;

    @OneToMany(mappedBy = "gallery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @ManyToOne
    private Enterprise enterprise;


    public Gallery(String name, String description, List<ImageCreateRequest> images) {
        this.name = name;
        this.description = description;
        if (images != null) {
            this.images = images.stream()
                                .map(dto -> new Image(dto.url(), dto.alt(), this))
                                .collect(Collectors.toList());
        }
    }
}
