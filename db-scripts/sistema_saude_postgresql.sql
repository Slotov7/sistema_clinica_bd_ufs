-- Criação do schema
CREATE SCHEMA IF NOT EXISTS sistema_saude;

-- Define o schema padrão para as próximas operações
SET search_path TO sistema_saude;

-- -----------------------------------------------------
-- Tabela sistema_saude.usuario
-- -----------------------------------------------------
CREATE TYPE tipo_usuario_enum AS ENUM ('ADMIN', 'RECEPCIONISTA', 'MEDICO', 'PACIENTE');

CREATE TABLE IF NOT EXISTS sistema_saude.usuario (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) DEFAULT NULL,
    dataNascimento DATE DEFAULT NULL,
    tipoDeUsuario tipo_usuario_enum NOT NULL
);

-- -----------------------------------------------------
-- Tabela sistema_saude.administrador
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.administrador (
    id INT PRIMARY KEY,
    CONSTRAINT administrador_ibfk_1
        FOREIGN KEY (id)
        REFERENCES sistema_saude.usuario (id)
);

-- -----------------------------------------------------
-- Tabela sistema_saude.agenda
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.agenda (
    id SERIAL PRIMARY KEY,
    status VARCHAR(50) DEFAULT NULL,
    horario TIME DEFAULT NULL,
    data DATE DEFAULT NULL
);

-- -----------------------------------------------------
-- Tabela sistema_saude.medico
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.medico (
    id INT PRIMARY KEY,
    crm INT NOT NULL,
    disponibilidade VARCHAR(100) DEFAULT NULL,
    agenda_id INT NOT NULL,
    CONSTRAINT medico_ibfk_1
        FOREIGN KEY (id)
        REFERENCES sistema_saude.usuario (id),
    CONSTRAINT fk_medico_agenda1
        FOREIGN KEY (agenda_id)
        REFERENCES sistema_saude.agenda (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Tabela sistema_saude.paciente
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.paciente (
    id INT PRIMARY KEY,
    prioridade INT NOT NULL DEFAULT 1,
    CONSTRAINT paciente_ibfk_1
        FOREIGN KEY (id)
        REFERENCES sistema_saude.usuario (id)
);

-- -----------------------------------------------------
-- Tabela sistema_saude.Preescrição
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.Preescrição (
    idpreescricao SERIAL PRIMARY KEY
);

-- -----------------------------------------------------
-- Tabela sistema_saude.consulta
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.consulta (
    id SERIAL PRIMARY KEY,
    medico_id INT NOT NULL,
    paciente_id INT NOT NULL,
    status VARCHAR(50) DEFAULT NULL,
    diagnostico TEXT DEFAULT NULL,
    data TIMESTAMP NOT NULL, -- MySQL DATETIME é equivalente a TIMESTAMP no PostgreSQL
    Preescrição_id INT NOT NULL,
    CONSTRAINT consulta_ibfk_1
        FOREIGN KEY (medico_id)
        REFERENCES sistema_saude.medico (id),
    CONSTRAINT consulta_ibfk_3
        FOREIGN KEY (paciente_id)
        REFERENCES sistema_saude.paciente (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_consulta_Preescrição1
        FOREIGN KEY (Preescrição_id)
        REFERENCES sistema_saude.Preescrição (idpreescricao)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- Criação de índices para a tabela consulta
CREATE INDEX medico_id ON sistema_saude.consulta (medico_id);
CREATE INDEX paciente_id ON sistema_saude.consulta (paciente_id);
CREATE INDEX fk_consulta_Preescrição1_idx ON sistema_saude.consulta (Preescrição_id);


-- -----------------------------------------------------
-- Tabela sistema_saude.especialidade
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.especialidade (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT DEFAULT NULL,
    cod_cbo VARCHAR(20) DEFAULT NULL
);

-- -----------------------------------------------------
-- Tabela sistema_saude.historicopaciente
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.historicopaciente (
    paciente_id INT NOT NULL UNIQUE,
    data_entrada DATE NOT NULL,
    descricao TEXT DEFAULT NULL,
    idhistoricopaciente VARCHAR(45) DEFAULT NULL,
    historicopacientecol VARCHAR(45) NOT NULL PRIMARY KEY,
    CONSTRAINT historicopaciente_ibfk_1
        FOREIGN KEY (paciente_id)
        REFERENCES sistema_saude.paciente (id)
);

-- -----------------------------------------------------
-- Tabela sistema_saude.exame
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.exame (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(100) DEFAULT NULL,
    data DATE DEFAULT NULL,
    resultado TEXT DEFAULT NULL,
    historicopaciente_historicopacientecol VARCHAR(45) NOT NULL,
    Preescrição_idpreescricao INT NOT NULL,
    CONSTRAINT fk_exame_historicopaciente1
        FOREIGN KEY (historicopaciente_historicopacientecol)
        REFERENCES sistema_saude.historicopaciente (historicopacientecol)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT fk_exame_Preescrição1
        FOREIGN KEY (Preescrição_idpreescricao)
        REFERENCES sistema_saude.Preescrição (idpreescricao)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- Criação de índices para a tabela exame
CREATE INDEX fk_exame_historicopaciente1_idx ON sistema_saude.exame (historicopaciente_historicopacientecol);
CREATE INDEX fk_exame_Preescrição1_idx ON sistema_saude.exame (Preescrição_idpreescricao);

-- -----------------------------------------------------
-- Tabela sistema_saude.Especializado
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.Especializado (
    medico_id INT NOT NULL,
    especialidade_id INT NOT NULL,
    PRIMARY KEY (medico_id, especialidade_id),
    CONSTRAINT medico_especialidade_ibfk_1
        FOREIGN KEY (medico_id)
        REFERENCES sistema_saude.medico (id),
    CONSTRAINT medico_especialidade_ibfk_2
        FOREIGN KEY (especialidade_id)
        REFERENCES sistema_saude.especialidade (id)
);

-- Criação de índice para a tabela Especializado
CREATE INDEX especialidade_id ON sistema_saude.Especializado (especialidade_id);

-- -----------------------------------------------------
-- Tabela sistema_saude.planodesaude
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.planodesaude (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) DEFAULT NULL,
    cobertura TEXT DEFAULT NULL
);

-- -----------------------------------------------------
-- Tabela sistema_saude.Assina
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.Assina (
    paciente_id INT NOT NULL,
    plano_id INT NOT NULL,
    PRIMARY KEY (paciente_id, plano_id),
    CONSTRAINT paciente_assina_plano_ibfk_1
        FOREIGN KEY (paciente_id)
        REFERENCES sistema_saude.paciente (id),
    CONSTRAINT paciente_assina_plano_ibfk_2
        FOREIGN KEY (plano_id)
        REFERENCES sistema_saude.planodesaude (id)
);

-- Criação de índice para a tabela Assina
CREATE INDEX plano_id ON sistema_saude.Assina (plano_id);

-- -----------------------------------------------------
-- Tabela sistema_saude.pagamento
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.pagamento (
    id SERIAL PRIMARY KEY,
    paciente_id INT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    forma_pagamento VARCHAR(50) NOT NULL,
    data DATE NOT NULL,
    consulta_id INT NOT NULL,
    CONSTRAINT pagamento_valor_check CHECK (valor >= 0), -- AQUI ESTÁ A NOVA VERIFICAÇÃO!
    CONSTRAINT pagamento_ibfk_1
        FOREIGN KEY (paciente_id)
        REFERENCES sistema_saude.paciente (id),
    CONSTRAINT fk_pagamento_consulta1
        FOREIGN KEY (consulta_id)
        REFERENCES sistema_saude.consulta (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- Criação de índices para a tabela pagamento
CREATE INDEX paciente_id_pagamento ON sistema_saude.pagamento (paciente_id);
CREATE INDEX fk_pagamento_consulta1_idx ON sistema_saude.pagamento (consulta_id);


-- -----------------------------------------------------
-- Tabela sistema_saude.procedimento
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.procedimento (
    nome VARCHAR(100) PRIMARY KEY,
    descricao TEXT DEFAULT NULL,
    historicopaciente_historicopacientecol VARCHAR(45) NOT NULL,
    Preescrição_idpreescricao INT NOT NULL,
    CONSTRAINT fk_procedimento_historicopaciente1
        FOREIGN KEY (historicopaciente_historicopacientecol)
        REFERENCES sistema_saude.historicopaciente (historicopacientecol)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT fk_procedimento_Preescrição1
        FOREIGN KEY (Preescrição_idpreescricao)
        REFERENCES sistema_saude.Preescrição (idpreescricao)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- Criação de índices para a tabela procedimento
CREATE INDEX fk_procedimento_historicopaciente1_idx ON sistema_saude.procedimento (historicopaciente_historicopacientecol);
CREATE INDEX fk_procedimento_Preescrição1_idx ON sistema_saude.procedimento (Preescrição_idpreescricao);


-- -----------------------------------------------------
-- Tabela sistema_saude.recepcionista
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.recepcionista (
    id INT PRIMARY KEY,
    consulta_id INT NOT NULL,
    CONSTRAINT recepcionista_ibfk_1
        FOREIGN KEY (id)
        REFERENCES sistema_saude.usuario (id),
    CONSTRAINT fk_recepcionista_consulta1
        FOREIGN KEY (consulta_id)
        REFERENCES sistema_saude.consulta (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- Criação de índice para a tabela recepcionista
CREATE INDEX fk_recepcionista_consulta1_idx ON sistema_saude.recepcionista (consulta_id);

-- -----------------------------------------------------
-- Tabela sistema_saude.remedio
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.remedio (
    nome VARCHAR(100) PRIMARY KEY,
    miligramas VARCHAR(50) DEFAULT NULL,
    tarja VARCHAR(50) DEFAULT NULL,
    historicopaciente_historicopacientecol VARCHAR(45) NOT NULL,
    Preescrição_idpreescricao INT NOT NULL,
    CONSTRAINT fk_remedio_historicopaciente1
        FOREIGN KEY (historicopaciente_historicopacientecol)
        REFERENCES sistema_saude.historicopaciente (historicopacientecol)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT fk_remedio_Preescrição1
        FOREIGN KEY (Preescrição_idpreescricao)
        REFERENCES sistema_saude.Preescrição (idpreescricao)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- Criação de índices para a tabela remedio
CREATE INDEX fk_remedio_historicopaciente1_idx ON sistema_saude.remedio (historicopaciente_historicopacientecol);
CREATE INDEX fk_remedio_Preescrição1_idx ON sistema_saude.remedio (Preescrição_idpreescricao);


-- -----------------------------------------------------
-- Tabela sistema_saude.unidadedeatendimento
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.unidadedeatendimento (
    id SERIAL PRIMARY KEY,
    numero VARCHAR(20) DEFAULT NULL,
    bairro VARCHAR(100) DEFAULT NULL,
    rua VARCHAR(150) DEFAULT NULL,
    cidade VARCHAR(100) DEFAULT NULL,
    CEP VARCHAR(20) NOT NULL
);

-- -----------------------------------------------------
-- Tabela sistema_saude.Frequenta
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.Frequenta (
    usuario_id INT NOT NULL,
    unidade_id INT NOT NULL,
    PRIMARY KEY (usuario_id, unidade_id),
    CONSTRAINT usuario_frequenta_unidade_ibfk_1
        FOREIGN KEY (usuario_id)
        REFERENCES sistema_saude.usuario (id),
    CONSTRAINT usuario_frequenta_unidade_ibfk_2
        FOREIGN KEY (unidade_id)
        REFERENCES sistema_saude.unidadedeatendimento (id)
);

-- Criação de índice para a tabela Frequenta
CREATE INDEX unidade_id ON sistema_saude.Frequenta (unidade_id);

-- -----------------------------------------------------
-- Tabela sistema_saude.Marca
-- Esta tabela foi duplicada no seu script MySQL.
-- Mantendo apenas uma ocorrência para evitar redundância.
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sistema_saude.Marca (
    recepcionista_id INT NOT NULL,
    paciente_id INT NOT NULL,
    PRIMARY KEY (recepcionista_id, paciente_id),
    CONSTRAINT fk_recepcionista_has_paciente_recepcionista1
        FOREIGN KEY (recepcionista_id)
        REFERENCES sistema_saude.recepcionista (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT fk_recepcionista_has_paciente_paciente1
        FOREIGN KEY (paciente_id)
        REFERENCES sistema_saude.paciente (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- Criação de índices para a tabela Marca
CREATE INDEX fk_recepcionista_has_paciente_paciente1_idx ON sistema_saude.Marca (paciente_id);
CREATE INDEX fk_recepcionista_has_paciente_recepcionista1_idx ON sistema_saude.Marca (recepcionista_id);