ALTER TABLE estimates ADD COLUMN quality_level VARCHAR(16) DEFAULT 'standard';
ALTER TABLE estimates ADD COLUMN status VARCHAR(32) DEFAULT 'Актуальный';
ALTER TABLE estimates ADD COLUMN parameters JSON;
ALTER TABLE estimates ADD COLUMN tasks JSON;

