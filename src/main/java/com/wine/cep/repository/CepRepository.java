package com.wine.cep.repository;

import com.wine.cep.model.Cep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CepRepository extends JpaRepository<Cep, String> {
}