package com.dellasse.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dellasse.backend.models.Party;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

}
