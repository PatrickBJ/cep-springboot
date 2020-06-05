package com.wine.cep.service;

import java.util.ArrayList;
import java.util.List;

import com.wine.cep.model.Cidade;
import com.wine.cep.repository.CidadeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    public ResponseEntity<List<String>> findCepsByIbgeUf(String ibge, String uf){
        List<String> cepResponse = new ArrayList<String>();
        HttpStatus status = HttpStatus.OK;

        try{
            uf = uf == null ? "%" : uf.toUpperCase();
            Cidade cidade = repository.findCidadeIbgeUf(ibge, uf);
            
            if(cidade != null)
                cidade.getCeps().forEach(c -> cepResponse.add(c.getCep()));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(status).body(cepResponse);
    }
}