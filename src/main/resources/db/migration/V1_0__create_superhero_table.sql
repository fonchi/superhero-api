CREATE TABLE superhero
(
    id            NUMERIC,
    name          VARCHAR(50),
    creation_date TIMESTAMP WITH TIME ZONE,
    update_date   TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_superhero PRIMARY KEY (id)
);