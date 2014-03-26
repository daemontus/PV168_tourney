CREATE TABLE "KNIGHTS" (
    "ID" BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "NAME" VARCHAR(255) NOT NULL,
    "BORN" DATE NOT NULL,
    "CASTLE" VARCHAR(255) NOT NULL,
    "HERALDRY" VARCHAR(255) NOT NULL
);
