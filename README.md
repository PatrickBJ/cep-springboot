# cep-springboot

API Rest em Java de consulta de CEP.

Tecnologias:
  Java 11
  Spring Boot
  Hibernate
  PostgreSQL

Busca CEP

  {contexto}/cep/ {cep}
  Caso o CEP não exista, consultar o site http://viacep.com.br/ e realizar o cadastro do cep e da cidade.

Obter a lista de CEPs de uma cidade

  {contexto}/ceps?ibge=123&uf=es
  ibge (obrigatório) e uf
