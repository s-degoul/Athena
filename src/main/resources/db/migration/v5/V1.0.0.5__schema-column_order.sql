ALTER TABLE drug_strength DROP COLUMN VALID_START_DATE;
ALTER TABLE drug_strength DROP COLUMN VALID_END_DATE;
ALTER TABLE drug_strength DROP COLUMN INVALID_REASON;

ALTER TABLE drug_strength ADD COLUMN VALID_START_DATE DATE NOT NULL;
ALTER TABLE drug_strength ADD COLUMN VALID_END_DATE DATE NOT NULL;
ALTER TABLE drug_strength ADD COLUMN INVALID_REASON VARCHAR(1);