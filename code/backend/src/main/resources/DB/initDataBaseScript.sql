CREATE EXTENSION IF NOT EXISTS "uuid-ossp" schema pg_catalog;

CREATE TABLE "user" (
	"uuid" UUID NOT NULL DEFAULT uuid_generate_v1mc(),
	"login" VARCHAR(255) NOT NULL UNIQUE,
	"password" VARCHAR(255) NOT NULL,
	"is_admin" BOOLEAN NOT NULL DEFAULT 'false',
	CONSTRAINT "user_pk" PRIMARY KEY ("uuid")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "scanword" (
	"uuid" UUID NOT NULL DEFAULT uuid_generate_v1mc(),
	"name" VARCHAR(255) NOT NULL,
	"width" integer NOT NULL,
	"height" integer NOT NULL,
	"dictionary_uuid" UUID NOT NULL,
	CONSTRAINT "scanword_pk" PRIMARY KEY ("uuid")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "dictionary" (
	"uuid" UUID NOT NULL DEFAULT uuid_generate_v1mc(),
	"name" VARCHAR(255) NOT NULL,
	"url" VARCHAR(255) NOT NULL,
	CONSTRAINT "dictionary_pk" PRIMARY KEY ("uuid")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "user_scanword" (
	"user_uuid" UUID NOT NULL,
	"scanword_uuid" UUID NOT NULL,
	"count_hints_used" integer NOT NULL,
	"score" integer NOT NULL,
	CONSTRAINT "user_scanword_pk" PRIMARY KEY ("user_uuid","scanword_uuid")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "question" (
	"uuid" UUID NOT NULL DEFAULT uuid_generate_v1mc(),
	"answer" VARCHAR(255) NOT NULL,
	"text" VARCHAR(255) NOT NULL,
	"url" VARCHAR(255),
  "type" VARCHAR(255) NOT NULL,
	CONSTRAINT "question_pk" PRIMARY KEY ("uuid")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "media" (
  "uuid" UUID NOT NULL DEFAULT uuid_generate_v1mc(),
  "is_image" BOOLEAN NOT NULL,
  "url" VARCHAR(255) NOT NULL,
  "name" VARCHAR(255) NOT NULL,
  CONSTRAINT "media_pk" PRIMARY KEY ("uuid")
) WITH (
OIDS=FALSE
);



CREATE TABLE "scanword_question" (
	"scanword_uuid" UUID NOT NULL,
	"question_uuid" UUID NOT NULL,
  "orientation" VARCHAR(255) NOT NULL,
  "location" VARCHAR(255) NOT NULL,
	CONSTRAINT "scanword_question_pk" PRIMARY KEY ("scanword_uuid","question_uuid")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "user_scanword_question" (
	"user_uuid" UUID NOT NULL,
	"scanword_uuid" UUID NOT NULL,
	"question_uuid" UUID NOT NULL,
	"is_passed" BOOLEAN DEFAULT 'false',
	"user_answer" VARCHAR(255) NOT NULL,
	CONSTRAINT "user_scanword_question_pk" PRIMARY KEY ("user_uuid","scanword_uuid","question_uuid")
) WITH (
  OIDS=FALSE
);




ALTER TABLE "scanword" ADD CONSTRAINT "scanword_fk0" FOREIGN KEY ("dictionary_uuid") REFERENCES "dictionary"("uuid") ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE "user_scanword" ADD CONSTRAINT "user_scanword_fk0" FOREIGN KEY ("user_uuid") REFERENCES "user"("uuid") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "user_scanword" ADD CONSTRAINT "user_scanword_fk1" FOREIGN KEY ("scanword_uuid") REFERENCES "scanword"("uuid") ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE "scanword_question" ADD CONSTRAINT "scanword_question_fk0" FOREIGN KEY ("scanword_uuid") REFERENCES "scanword"("uuid") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "scanword_question" ADD CONSTRAINT "scanword_question_fk1" FOREIGN KEY ("question_uuid") REFERENCES "question"("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "user_scanword_question" ADD CONSTRAINT "user_scanword_question_fk0" FOREIGN KEY ("user_uuid") REFERENCES "user"("uuid") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "user_scanword_question" ADD CONSTRAINT "user_scanword_question_fk1" FOREIGN KEY ("scanword_uuid") REFERENCES "scanword"("uuid") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "user_scanword_question" ADD CONSTRAINT "user_scanword_question_fk2" FOREIGN KEY ("question_uuid") REFERENCES "question"("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

