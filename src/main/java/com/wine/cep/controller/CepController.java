package com.wine.cep.controller;

import java.util.List;

import com.wine.cep.model.Cep;

import com.wine.cep.service.CepService;
import com.wine.cep.service.CidadeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CepController {
	final String address = "http://viacep.com.br/ws/";

	@Autowired
	private CepService cepService;
	
	@Autowired
	private CidadeService cidadeService;

	@GetMapping("/")
    public String getDefault() {
		String linha1 = "<h3>Bem vindo ao teste do cep!<h3><br><br>";
		String linha2 = "<h4>Digite o '/cep/{numeroCep} e retornaremos o endereço<h4>";
		String linha3 = "<h4>Digite o '/ceps?ibge=4412&uf=UF' e retornaremos todos os ceps da cidade (uf é opcional)<h4><br>";
		String linha4 = "<h5>Feito por: Patrick Brison Januario<h5>";

		return linha1+linha2+linha3+linha4;
	}

	@GetMapping("/all")
    public ResponseEntity<List<Cep>> getAllCep() {
		return cepService.findAll();
	}

	@GetMapping("/cep/{cep}")
    public ResponseEntity<Cep> getCep(@PathVariable String cep) {
		try{
			return cepService.findCep(address, cep);
		}
		catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
	}

	@GetMapping("/ceps")
	public ResponseEntity<List<String>> getCeps(@RequestParam String ibge, @RequestParam(required = false) String uf) {
		return cidadeService.findCepsByIbgeUf(ibge, uf);
	}
}
