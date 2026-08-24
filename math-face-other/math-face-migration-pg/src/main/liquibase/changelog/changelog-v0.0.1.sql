--liquibase formatted sql

--changeset geniyyc:1 labels:v0.0.1
CREATE TABLE "expressions" (
	"id" text primary key constraint expressions_id_length_ctr check (length("id") < 64),
	"value" text not null constraint expressions_value_length_ctr check (length("value") < 256),
	"complexity_id" int not null,
	"description" text constraint expressions_description_length_ctr check (length("description") < 4096),
	"owner_id" text not null constraint expressions_owner_id_length_ctr check (length("owner_id") < 64),
	"lock" text not null constraint expressions_lock_length_ctr check (length("lock") < 64)
);

CREATE INDEX expressions_complexity_id_idx on "expressions" using hash ("complexity_id");
