package com.wine.cep.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "TCIDADE")
@Data
public class Cidade {
  
  @Id
	@NotBlank
	@Column(columnDefinition = "IBGE")
	@Size(min = 1, max = 20)
  private String ibge;
  
	@NotBlank
	@Column(columnDefinition = "UF")
	@Size(min = 2, max = 2)
  private String uf;
  
  @NotBlank
	@Column(columnDefinition = "LOCALIDADE")
	@Size(min = 1, max = 100)
  private String localidade;
  
  @OneToMany(mappedBy="cidade")
	@JsonIgnore
  private List<Cep> ceps; 

  public String getIbge() {
    return ibge;
  }

  public void setIbge(String ibge) {
    this.ibge = ibge;
  }

  public String getUf() {
    return uf;
  }
  public void setUf(String uf) {
    this.uf = uf;
  }

  public String getLocalidade() {
    return localidade;
  }

  public void setLocalidade(String localidade) {
    this.localidade = localidade;
  }

  public List<Cep> getCeps() {
    return ceps;
  }
}
