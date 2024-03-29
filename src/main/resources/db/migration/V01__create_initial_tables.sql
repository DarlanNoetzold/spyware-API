CREATE TABLE IF NOT EXISTS alert
(
    id bigint NOT NULL,
    data_cadastro date NOT NULL,
    image bytea,
    pc_id character varying(255) COLLATE pg_catalog."default",
    processos text COLLATE pg_catalog."default",
    CONSTRAINT alert_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS image
(
    id bigint NOT NULL,
    base64img bytea,
    product_img character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT image_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bad_language
(
    id bigint NOT NULL,
    word character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT bad_language_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS malicious_port
(
    id bigint NOT NULL,
    vulnarable_banners character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT malicious_port_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS malicious_process
(
    id bigint NOT NULL,
    name_exe character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT malicious_process_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS malicious_website
(
    id bigint NOT NULL,
    url character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT malicious_website_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS usuario
(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    login character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT usuario_pkey PRIMARY KEY (id),
    CONSTRAINT uk_pm3f4m4fqv89oeeeac4tbe2f4 UNIQUE (login)
);

INSERT INTO usuario(
	id, login, password)
	VALUES (1, 'stringa', '$2a$10$PHsn/jNlKZrQeoCrkyLhMO7Dd5wgYgsiTbuV8bCqeaZmZ0JoTjSFq');

INSERT INTO usuario(
	id, login, password)
	VALUES (2, 'string', '$2a$10$mtI8dtloqyRywjibeQFkZuMKs.b18zYIOXjR/8tilhn01NZJYviDa');

INSERT INTO usuario(
	id, login, password)
	VALUES (3, 'darlan', '$2a$10$8KSslhLc1DDfb/pT2Tesve5bG8Q1rkP1R/nenRLRw05BtkJQcUVne');