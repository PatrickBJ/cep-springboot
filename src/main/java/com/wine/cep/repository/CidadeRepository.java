package com.wine.cep.repository;

import com.wine.cep.model.Cidade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, String> {
    @Query("SELECT c FROM Cidade c where c.ibge = ?1 and c.uf like ?2")
    Cidade findCidadeIbgeUf(String ibge, String uf);
}