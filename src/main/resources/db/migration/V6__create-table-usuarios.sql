CREATE TABLE usuarios (
    id bigserial PRIMARY KEY,
    login varchar(100) NOT NULL,
    senha varchar(255) NOT NULL
);
