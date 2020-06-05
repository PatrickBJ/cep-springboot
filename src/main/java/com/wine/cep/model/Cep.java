package com.wine.cep.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name = "TCEP")
@Data
public class Cep {

	@Id
	@NotBlank
	@Column(columnDefinition = "CEP", unique = true)
	@Size(min = 8, max = 10)
	private String cep;

	@NotBlank
	@Column(columnDefinition = "LOGRADOURO")
	@Size(min = 1, max = 255)
	private String logradouro;

	@Column(columnDefinition = "COMPLEMENTO")
	@Size(max = 255)
	private String complemento;

	@Column(columnDefinition = "BAIRRO")
	@Size(max = 50)
	private String bairro;

	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name="IBGE")
	private Cidade cidade;

	public String getCep(){
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
}
