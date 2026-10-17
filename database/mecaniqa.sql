-- =====================================================================
-- MecaniQA - Script do banco de dados (MySQL 8+)
-- DDL (criação das tabelas) + DML (dados iniciais)
-- Execução:  mysql -u root -p < database/mecaniqa.sql
-- =====================================================================

CREATE DATABASE IF NOT EXISTS mecaniqa
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE mecaniqa;

-- ---------------------------------------------------------------------
-- DDL
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS item_pedido;
DROP TABLE IF EXISTS pedido_pecas;
DROP TABLE IF EXISTS ordem_servico;
DROP TABLE IF EXISTS servico;
DROP TABLE IF EXISTS peca;

CREATE TABLE peca (
    codigo             VARCHAR(36)    NOT NULL,
    codigo_barras      VARCHAR(50),
    nome               VARCHAR(150)   NOT NULL,
    fornecedor         VARCHAR(150),
    quantidade_estoque INT            NOT NULL DEFAULT 0,
    preco_custo        DECIMAL(10, 2) NOT NULL,
    preco_venda        DECIMAL(10, 2) NOT NULL,
    data_cadastro      DATETIME       NOT NULL,
    data_atualizacao   DATETIME       NOT NULL,
    tamanho            VARCHAR(30),
    cor                VARCHAR(30),
    categoria          VARCHAR(30)    NOT NULL,
    PRIMARY KEY (codigo),
    CONSTRAINT ck_peca_estoque CHECK (quantidade_estoque >= 0),
    CONSTRAINT ck_peca_precos  CHECK (preco_custo >= 0 AND preco_venda >= 0)
) ENGINE = InnoDB;

CREATE TABLE servico (
    codigo                   VARCHAR(36)    NOT NULL,
    nome                     VARCHAR(150)   NOT NULL,
    duracao_estimada_minutos INT            NOT NULL,
    custo_tabela             DECIMAL(10, 2) NOT NULL,
    data_cadastro            DATETIME       NOT NULL,
    data_atualizacao         DATETIME       NOT NULL,
    PRIMARY KEY (codigo),
    CONSTRAINT ck_servico_duracao CHECK (duracao_estimada_minutos > 0),
    CONSTRAINT ck_servico_custo   CHECK (custo_tabela >= 0)
) ENGINE = InnoDB;

CREATE TABLE ordem_servico (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    descricao    VARCHAR(255) NOT NULL,
    nome_cliente VARCHAR(150) NOT NULL,
    status       VARCHAR(30)  NOT NULL DEFAULT 'ABERTO',
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE pedido_pecas (
    id     BIGINT      NOT NULL AUTO_INCREMENT,
    status VARCHAR(30) NOT NULL DEFAULT 'ORCANDO',
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE item_pedido (
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    pedido_id   BIGINT      NOT NULL,
    peca_codigo VARCHAR(36) NOT NULL,
    quantidade  INT         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_item_pedido FOREIGN KEY (pedido_id)
        REFERENCES pedido_pecas (id) ON DELETE CASCADE,
    CONSTRAINT fk_item_peca FOREIGN KEY (peca_codigo)
        REFERENCES peca (codigo) ON DELETE RESTRICT,
    CONSTRAINT ck_item_quantidade CHECK (quantidade > 0)
) ENGINE = InnoDB;

-- ---------------------------------------------------------------------
-- DML (dados iniciais)
-- ---------------------------------------------------------------------
INSERT INTO peca (codigo, codigo_barras, nome, fornecedor, quantidade_estoque,
                  preco_custo, preco_venda, data_cadastro, data_atualizacao,
                  tamanho, cor, categoria)
VALUES
    ('11111111-1111-1111-1111-111111111111', '7890000000011', 'Filtro de óleo',
     'AutoPeças Brasil', 40, 18.50, 32.90, NOW(), NOW(), NULL, NULL, 'FILTROS'),
    ('22222222-2222-2222-2222-222222222222', '7890000000028', 'Pastilha de freio dianteira',
     'FreioMax', 25, 55.00, 94.90, NOW(), NOW(), NULL, NULL, 'FREIO'),
    ('33333333-3333-3333-3333-333333333333', '7890000000035', 'Vela de ignição',
     'Ignição Total', 100, 12.00, 24.90, NOW(), NOW(), NULL, NULL, 'MOTOR');

INSERT INTO servico (codigo, nome, duracao_estimada_minutos, custo_tabela,
                     data_cadastro, data_atualizacao)
VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Troca de óleo', 30, 80.00, NOW(), NOW()),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Alinhamento e balanceamento', 60, 120.00, NOW(), NOW());

INSERT INTO ordem_servico (descricao, nome_cliente, status)
VALUES ('Troca de óleo e filtro', 'João Silva', 'ABERTO');

INSERT INTO pedido_pecas (status) VALUES ('ORCANDO');

INSERT INTO item_pedido (pedido_id, peca_codigo, quantidade)
VALUES (1, '11111111-1111-1111-1111-111111111111', 2);
