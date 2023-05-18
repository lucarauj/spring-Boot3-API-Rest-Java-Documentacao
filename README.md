[![NPM](https://img.shields.io/npm/l/react)](https://github.com/lucarauj/spring-Boot3-API-Rest-Java-Documentacao/blob/main/LICENSE)

<h1 align="center"> Curso Spring Boot 3 API Rest em Java </h1>
<h2 align="center"> Gerenciando uma clínica médica: </h2>
<h3 align="center"> Documente, Teste e Prepare uma API para o Deploy </h3>

<p align="center"><img width="200px" src="https://github.com/lucarauj/assets/blob/main/ApiJavaSpring.png" /></p>

### 👉 [LINK DO PROJETO BASE 🖱](https://github.com/lucarauj/spring-Boot3-API-Rest-Java-Spring-Security)

<br>

## 🛑 Nessa parte do projeto será implementado:

- ✔ Agendamento e cancelamento de consultas;
- ✔ Testes com JUnit;
- ✔ Documentação com Swagger;
- ✔ Preparação para deploy.

<br>

## ➕ Insert de nova Migration:

```
CREATE TABLE consultas (
    id BIGSERIAL PRIMARY KEY,
    medico_id BIGINT NOT NULL,
    paciente_id BIGINT NOT NULL,
    data TIMESTAMP NOT NULL,
    CONSTRAINT fk_consultas_medico_id FOREIGN KEY (medico_id) REFERENCES medicos (id),
    CONSTRAINT fk_consultas_paciente_id FOREIGN KEY (paciente_id) REFERENCES pacientes (id)
);
```

<br>

## 📝 Documentando minha API:

```"For spring-boot v3 support, make sure you use springdoc-openapi v2"``` 👉 [🖱](https://springdoc.org/)

```
<dependency>
	<groupId>org.springdoc</groupId>
	<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
	<version>2.1.0</version>
</dependency>
```

### ✔ Add URL's: 

```.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()```

<br>

## ⚙ Testes

- Controller (API)
- Repository (Queries)

<br>

### ❗ Para executar testes que acessam o banco de dados, existem duas abordagens:

1. Por padrão, o Spring usa um banco de dados diferente do banco usado na aplicação. Dessa forma, é necessário adicionar um banco de dados em memória;
2. Usar o mesmo banco da aplicação utilizando a seguinte anotação: @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE);

<br>

### ✔ Add ```application-test.properties```:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/vollmed_api_test
```

<br>

### 🗜 Preparando o Projeto para Build ```(application-prod.properties)```:

```
spring.datasource.url=${DATASOURCE_URL}
spring.datasource.username=${DATASOURCE_USERNAME}
spring.datasource.password=${DATASOURCE_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false
```

<br>

### 🗜 Build com arquivo .war:

#### 1. Adicionar a tag <packaging>war</packaging> no arquivo ```pom.xml``` do projeto e a seguinte dependência:

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.0.0</version>
    <relativePath/>
  </parent>
  <groupId>grupo.com</groupId>
  <artifactId>api</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <name>api</name>

  <packaging>war</packaging>
```
```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-tomcat</artifactId>
  <scope>provided</scope>
</dependency>
```

#### 2. Alterar a classe main do projeto (ApiApplication) para herdar da classe SpringBootServletInitializer, além de sobrescrever o método configure:

```
@SpringBootApplication
public class ApiApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ApiApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }
}

```

<br>

### 🗜 Executando o Build:

1. Eclipse: botão direito na pasta raiz -> Run As -> Maven Build -> preencha o campo ```Goals:```: package -> Run -> Build gerado na pasta ```target```;
2. Intellij: aba "Maven" no canto superior direito -> "Plugins" e a pasta "Lifecycle" -> "package" -> botão direito -> "Run Maven Build";
3. Terminal: na pasta raiz -> ```mvn clean package```

<br>

### ⚙ Executando o projeto via terminal:

```java -jar -Dspring.profiles.active=prod -DDATASOURCE_URL="link do banco" -DDATASOURCE_USERNAME="variavel" -DDATASOURCE_PASSWORD="variavel" target/api-0.0.1-SNAPSHOT.jar``` (ativando perfil de produção)

<br>

### 📝 Anotações utilizadas no projeto (Parte III):

- @AutoConfigureMockMvc: injeta o MockMvc no contexto da aplicação;
- @AutoConfigureJsonTesters: aplicada a uma classe de teste para habilitar e configurar configuração automática de testadores JSON;
- @ActiveProfiles("test"): sobrescreve propriedades do arquivo ```application.properties``` carregado apenas ao executar os testes;
- @DataJpaTest: usada para testar uma interface Repository;
- @DisplayName("String"): usada para fornecer um nome personalizado para a classe de teste e os métodos de teste;
- @Future: define que a variável receberá apenas data futura;
- @JsonAlias: serve para mapear “apelidos” alternativos para os campos que serão recebidos do JSON, sendo possível atribuir múltiplos alias;
- @JsonFormat(pattern = "dd/MM/yyyy HH:mm"): indica ao Spring que esse será o formato ao qual a data/hora será recebida na API;
- @MockBean: usada para criar objetos simulados (mocks) de classes ou interfaces e adicioná-los ao contexto de aplicação;
- @Query: permite a customização de queries em métodos de um repositório do Spring Data JPA;
- @SecurityRequirement(name = "bearer-key"): especifica um requisito de segurança para uma operação;
- @SpringBootTest: criará o contexto da aplicação Spring, permitindo a injeção de dependência e demais funcionalidades do framework;
- @Test: informa ao JUnit quais são os métodos de teste de uma classe;
- @WithMockUser: possibilita a execução de um teste utilizando um usuário autenticado fictício;

<br>

### 🚀 Principais tecnologias utilizadas no projeto:

<div style="display: inline_block"><br>
<img align="center" alt="Lucarauj-Java" height="30" width="40" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg">
<img align="center" alt="Lucarauj-Postman" height="50" width="90" src="https://github.com/lucarauj/assets/blob/main/postman.png">
<img align="center" alt="Lucarauj-Spring" height="30" width="40" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg">
<img align="center" alt="Lucarauj-SpringBoot" height="40" width="110" src="https://github.com/lucarauj/assets/blob/main/SpringBoot.jpeg">
<img align="center" alt="Lucarauj-Maven" height="50" width="60" src="https://github.com/lucarauj/assets/blob/main/Maven-Apache.svg">
<img align="center" alt="Lucarauj-Swagger" height="30" width="90" src="https://github.com/lucarauj/assets/blob/main/Swagger.png">
<img align="center" alt="Lucarauj-Postgresql" height="40" width="50" src="https://github.com/lucarauj/assets/blob/main/postgresql.svg">
</div>

<br>

## Aluno

#### Lucas Araujo

<a href="https://www.linkedin.com/in/lucarauj"><img alt="lucarauj | LinkdeIN" width="40px" src="https://user-images.githubusercontent.com/43545812/144035037-0f415fc7-9f96-4517-a370-ccc6e78a714b.png" /></a>
