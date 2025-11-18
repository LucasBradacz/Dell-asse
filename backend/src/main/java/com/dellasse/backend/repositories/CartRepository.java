package com.dellasse.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dellasse.backend.models.Cart;

/*
 * @author Equipe Compilador
 * @version 1.0
 * Repositório para a entidade Cart, responsável por operações de persistência relacionadas a carrinhos de compras.
 * <EM CONSTRUÇÃO>
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
}
