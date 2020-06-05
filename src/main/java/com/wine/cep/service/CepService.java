package com.wine.cep.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wine.cep.model.Cep;
import com.wine.cep.model.Cidade;
import com.wine.cep.repository.CepRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.configurationprocessor.json.JSONTokener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CepService {

    @Autowired
    private CepRepository repository;

    public ResponseEntity<List<Cep>> findAll(){
        List<Cep> cepResponse = new ArrayList<Cep>();

        try{
            cepResponse = repository.findAll();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(cepResponse);
    }

    public ResponseEntity<Cep> findCep(String address, String cep){
        Cep cepResponse = null;
        HttpStatus status = HttpStatus.OK;

        try{
            cep = cep.replace("-", "");
            if(cep.length() < 8)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            
            //Verify in database
            Optional<Cep> optional = repository.findById(cep);
            
            if(optional.isPresent()) {
                cepResponse = optional.get();
            }
            else{
                cepResponse = findCepInSite(address, cep); 
                repository.save(cepResponse);
                status = cepResponse == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(status).body(cepResponse);
    }

    private Cep findCepInSite(String address, String cep){

        String type = "/json";
        Cep cepObj = new Cep();

        try{
            URL url = new URL(address+cep+type);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder jsonSb = new StringBuilder();
            br.lines().forEach(l -> jsonSb.append(l.trim()));
            JSONObject json = new JSONObject(new JSONTokener(jsonSb.toString()));
            
            Cidade cidadeObj = new Cidade();
            cidadeObj.setIbge(json.getString("ibge"));
            cidadeObj.setUf(json.getString("uf"));
            cidadeObj.setLocalidade(json.getString("localidade"));

            cepObj.setCep(cep);
            cepObj.setLogradouro(json.getString("logradouro"));
            cepObj.setComplemento(json.getString("complemento"));
            cepObj.setBairro(json.getString("bairro"));
            cepObj.setCidade(cidadeObj);

            br.close();
            con.disconnect();
        }
        catch(Exception e){
            return null;
        }

        return cepObj;
    }
    
}