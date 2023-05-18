
## Agendamento de consultas

## Insert de nova Migration:

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

## Novas anotações:

- @Future
- @Service
- @Query
- @Component
- @Configuration
- @Bean
- @SecurityRequirement(name = "bearer-key")
- @Test
- @DataJpaTest: usada para testar uma interface Repository;
- @SpringBootTest
- @AutoConfigureMockMvc
- @WithMockUser
- @AutoConfigureJsonTesters
- @MockBean
- @ActiveProfiles("test"): sobrescreve propriedades do arquivo ```application.properties``` carregado apenas ao executar os testes;
- @DisplayName("String")
- @JsonAlias: serve para mapear “apelidos” alternativos para os campos que serão recebidos do JSON, sendo possível atribuir múltiplos alias;
- @JsonFormat(pattern = "dd/MM/yyyy HH:mm"): indica ao Spring que esse será o formato ao qual a data/hora será recebida na API;

## Documentando minha API:

```For spring-boot v3 support, make sure you use springdoc-openapi v2```

```
<dependency>
	<groupId>org.springdoc</groupId>
	<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
	<version>2.1.0</version>
</dependency>
```

URL add: /swagger-ui.html && /v3/api-docs

## Testes

Controller (API)
Repository (Queries)

- Para executar testes que acessam o banco de dados, existem duas abordagens:

1. Por padrão, o Spring usa um banco de dados diferente do banco usado na aplicação. Dessa forma, é necessário adicionar um banco de dados em memória;
2. Usar o mesmo banco da aplicação utilizando a seguinte anotação: @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE);

### Add ```application-test.properties```:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/vollmed_api_test
```

### Preparando o Projeto para Build:

- Criando e configurando ```application-prod.properties```:

```
spring.datasource.url=${DATASOURCE_URL}
spring.datasource.username=${DATASOURCE_USERNAME}
spring.datasource.password=${DATASOURCE_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false
```

### Build com arquivo .war:

1. Adicionar a tag <packaging>war</packaging> no arquivo ```pom.xml``` do projeto e a seguinte dependência:

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

2. Alterar a classe main do projeto (ApiApplication) para herdar da classe SpringBootServletInitializer, além de sobrescrever o método configure:

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

### Executando o Build:

1. Eclipse: botão direito na pasta raiz -> Run As -> Maven Build -> preencha o campo ```Goals:```: package -> Run -> Build gerado na pasta ```target```;
2. Intellij: aba "Maven" no canto superior direito -> "Plugins" e a pasta "Lifecycle" -> "package" -> botão direito -> "Run Maven Build";
3. Terminal: na pasta raiz -> ```mvn clean package```

### Executando o projeto via terminal:

- ```java -jar -Dspring.profiles.active=prod -DDATASOURCE_URL=jdbc:postgresql://localhost:5432/vollmed_api -DDATASOURCE_USERNAME=postgres -DDATASOURCE_PASSWORD=lucarauj target/api-0.0.1-SNAPSHOT.jar``` (ativando perfil de produção)