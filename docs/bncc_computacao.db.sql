BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "competencia_especifica" (
	"codigo"	TEXT NOT NULL,
	"descricao"	TEXT NOT NULL,
	"etapa_codigo"	TEXT NOT NULL,
	PRIMARY KEY("codigo"),
	FOREIGN KEY("etapa_codigo") REFERENCES "etapa"("codigo")
);
CREATE TABLE IF NOT EXISTS "conceito_habilidade" (
	"codigo"	TEXT NOT NULL,
	"descricao"	TEXT NOT NULL,
	PRIMARY KEY("codigo")
);
CREATE TABLE IF NOT EXISTS "eixo" (
	"codigo"	TEXT NOT NULL,
	"descricao"	TEXT NOT NULL,
	PRIMARY KEY("codigo")
);
CREATE TABLE IF NOT EXISTS "etapa" (
	"codigo"	TEXT NOT NULL,
	"nome"	TEXT NOT NULL,
	PRIMARY KEY("codigo")
);
CREATE TABLE IF NOT EXISTS "habilidade" (
	"codigo"	TEXT NOT NULL,
	"descricao"	TEXT NOT NULL,
	"explicacao"	TEXT,
	"exemplo"	TEXT,
	"eixo_codigo"	TEXT,
	"objeto_conhecimento_id"	INTEGER,
	"conceito_codigo"	TEXT,
	"competencia_codigo"	TEXT,
	PRIMARY KEY("codigo"),
	FOREIGN KEY("competencia_codigo") REFERENCES "competencia_especifica"("codigo"),
	FOREIGN KEY("conceito_codigo") REFERENCES "conceito_habilidade"("codigo"),
	FOREIGN KEY("eixo_codigo") REFERENCES "eixo"("codigo"),
	FOREIGN KEY("objeto_conhecimento_id") REFERENCES "objeto_conhecimento"("id")
);
CREATE TABLE IF NOT EXISTS "importacao_bncc" (
	"id"	INTEGER,
	"origem_aba"	TEXT,
	"etapa"	TEXT,
	"serie"	TEXT,
	"eixo"	TEXT,
	"objeto_conhecimento"	TEXT,
	"objeto_conhecimento_filho"	TEXT,
	"conceito_habilidade"	TEXT,
	"competencia_especifica"	TEXT,
	"codigo"	TEXT,
	"descricao"	TEXT,
	"explicacao"	TEXT,
	"exemplo"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "objeto_conhecimento" (
	"id"	INTEGER,
	"nome"	TEXT NOT NULL,
	"objeto_pai_id"	INTEGER,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("objeto_pai_id") REFERENCES "objeto_conhecimento"("id")
);
CREATE TABLE IF NOT EXISTS "serie" (
	"codigo"	TEXT NOT NULL,
	"nome"	TEXT NOT NULL,
	"etapa_codigo"	TEXT NOT NULL,
	PRIMARY KEY("codigo"),
	FOREIGN KEY("etapa_codigo") REFERENCES "etapa"("codigo")
);
CREATE TABLE IF NOT EXISTS "serie_eixo" (
	"codigo_serie"	TEXT NOT NULL,
	"codigo_eixo"	TEXT NOT NULL,
	PRIMARY KEY("codigo_serie","codigo_eixo"),
	FOREIGN KEY("codigo_eixo") REFERENCES "eixo"("codigo"),
	FOREIGN KEY("codigo_serie") REFERENCES "serie"("codigo")
);
COMMIT;
