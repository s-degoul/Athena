CREATE TABLE RELATIONSHIP(
  RELATIONSHIP_ID BIGINT NOT NULL,
	RELATIONSHIP_NAME VARCHAR(256) NOT NULL,
	IS_HIERARCHICAL INT NOT NULL,
	DEFINES_ANCESTRY INT NOT NULL,
	REVERSE_RELATIONSHIP BIGINT
);

CREATE TABLE VOCABULARY(
  VOCABULARY_ID	BIGINT NOT NULL,
	VOCABULARY_NAME VARCHAR(256) NOT NULL
);
ALTER TABLE VOCABULARY ADD PRIMARY KEY (VOCABULARY_ID);

CREATE TABLE CONCEPT(
  CONCEPT_ID BIGINT NOT NULL,
	CONCEPT_NAME VARCHAR(255) NOT NULL,
	CONCEPT_LEVEL BIGINT,
	CONCEPT_CLASS VARCHAR(50),
	VOCABULARY_ID BIGINT,
	CONCEPT_CODE VARCHAR(50) NOT NULL,
	VALID_START_DATE DATE NOT NULL,
	VALID_END_DATE DATE NOT NULL,
	INVALID_REASON VARCHAR(1)
);
ALTER TABLE CONCEPT ADD PRIMARY KEY (concept_id);

CREATE TABLE CONCEPT_ANCESTOR(
  ANCESTOR_CONCEPT_ID BIGINT NOT NULL,
  DESCENDANT_CONCEPT_ID BIGINT NOT NULL,
  MAX_LEVELS_OF_SEPARATION BIGINT NOT NULL,
  MIN_LEVELS_OF_SEPARATION BIGINT NOT NULL
);

CREATE TABLE DRUG_STRENGTH(
	DRUG_CONCEPT_ID BIGINT NOT NULL,
	INGREDIENT_CONCEPT_ID BIGINT NOT NULL,
	AMOUNT_VALUE 	DOUBLE PRECISION,
	AMOUNT_UNIT VARCHAR(60),
	CONCENTRATION_VALUE 	DOUBLE PRECISION,
	CONCENTRATION_ENUM_UNIT VARCHAR(60),
	CONCENTRATION_DENOM_UNIT VARCHAR(60),
	BOX_SIZE INTEGER,
	VALID_START_DATE DATE NOT NULL,
	VALID_END_DATE DATE NOT NULL,
	INVALID_REASON VARCHAR(1)
);

CREATE TABLE CONCEPT_SYNONYM(
    CONCEPT_SYNONYM_ID BIGINT,
    CONCEPT_ID BIGINT NOT NULL,
    CONCEPT_SYNONYM_NAME VARCHAR(1000) NOT NULL
);

CREATE TABLE SOURCE_TO_CONCEPT_MAP(
  SOURCE_CODE VARCHAR(50) NOT NULL,
  SOURCE_VOCABULARY_ID BIGINT,
  SOURCE_CODE_DESCRIPTION VARCHAR(255) NOT NULL,
  TARGET_CONCEPT_ID BIGINT NOT NULL,
  TARGET_VOCABULARY_ID BIGINT,
  MAPPING_TYPE VARCHAR(20) NOT NULL,
  PRIMARY_MAP CHAR(1),
  VALID_START_DATE DATE NOT NULL,
  VALID_END_DATE DATE NOT NULL,
  INVALID_REASON VARCHAR(1)
);

CREATE TABLE CONCEPT_RELATIONSHIP(
   CONCEPT_ID_1 BIGINT NOT NULL,
   CONCEPT_ID_2 BIGINT NOT NULL,
   RELATIONSHIP_ID VARCHAR(20) NOT NULL,
   VALID_START_DATE DATE NOT NULL,
   VALID_END_DATE DATE NOT NULL,
   INVALID_REASON VARCHAR(1)
);

CREATE INDEX CONCEPT_RELATIONSHIP_C_1 ON CONCEPT_RELATIONSHIP (CONCEPT_ID_1);
CREATE INDEX CONCEPT_RELATIONSHIP_C_2 ON CONCEPT_RELATIONSHIP (CONCEPT_ID_2);