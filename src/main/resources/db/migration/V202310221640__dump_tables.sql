-- public.tb_address definition

-- Drop table

-- DROP TABLE tb_address;

CREATE TABLE IF NOT EXISTS tb_address
(
    id           uuid NOT NULL,
    city         varchar(100) NULL,
    complement   varchar(100) NULL,
    county       varchar(100) NULL,
    neighborhood varchar(100) NULL,
    "number"     varchar(100) NULL,
    state        varchar(100) NULL,
    street       varchar(255) NULL,
    CONSTRAINT tb_address_pkey PRIMARY KEY (id)
);


-- public.tb_answer_comments definition

-- Drop table

-- DROP TABLE tb_answer_comments;

CREATE TABLE IF NOT EXISTS tb_answer_comments
(
    id              uuid NOT NULL,
    comment_message varchar(255) NULL,
    id_comment      varchar(255) NULL,
    CONSTRAINT tb_answer_comments_pkey PRIMARY KEY (id)
);


-- public.tb_comment definition

-- Drop table

-- DROP TABLE tb_comment;

CREATE TABLE IF NOT EXISTS tb_comment
(
    created_at      timestamp(6) NULL,
    id              uuid NOT NULL,
    comment_message varchar(255) NULL,
    id_post         varchar(255) NULL,
    user_id         varchar(255) NULL,
    CONSTRAINT tb_comment_pkey PRIMARY KEY (id)
);

-- public.tb_event definition

-- Drop table

-- DROP TABLE tb_event;

CREATE TABLE IF NOT EXISTS tb_event
(
    xp            int4 NULL,
    "date"        timestamp(6) NULL,
    fk_address_id uuid NULL,
    fk_user_id    uuid NULL,
    id            uuid NOT NULL,
    description   varchar(1000) NULL,
    "name"        varchar(255) NULL,
    tag           varchar(255) NULL,
    CONSTRAINT tb_event_pkey PRIMARY KEY (id)
);

-- public.tb_role definition

-- Drop table

-- DROP TABLE tb_role;

CREATE TABLE IF NOT EXISTS tb_role
(
    id     bigserial NOT NULL,
    "role" varchar(255) NULL,
    CONSTRAINT tb_role_pkey PRIMARY KEY (id)
);


-- public.tb_squad definition

-- Drop table

-- DROP TABLE tb_squad;

CREATE TABLE IF NOT EXISTS tb_squad
(
    is_active bit(1) NOT NULL,
    xp        int4 NULL,
    id        uuid   NOT NULL,
    "name"    varchar(255) NULL,
    CONSTRAINT tb_squad_pkey PRIMARY KEY (id)
);


-- public.tb_training definition

-- Drop table

-- DROP TABLE tb_training;

CREATE TABLE IF NOT EXISTS tb_training
(
    duration       int4 NULL,
    unlock_xp      int8 NULL,
    xp             int8 NULL,
    training_id    uuid NOT NULL,
    category       varchar(255) NULL,
    classification varchar(255) NULL,
    description    varchar(255) NULL,
    "name"         varchar(255) NULL,
    CONSTRAINT tb_training_category_check CHECK (((category)::text = ANY ((ARRAY['AEROBIC':: character varying, 'ANAEROBIC':: character varying, 'FLEXIBILITY':: character varying, 'STRENGTH':: character varying, 'FIGHT':: character varying, 'HEALTH':: character varying])::text[])
) ),
	CONSTRAINT tb_training_pkey PRIMARY KEY (training_id)
);


-- public.tb_user definition

-- Drop table

-- DROP TABLE tb_user;

CREATE TABLE IF NOT EXISTS tb_user
(
    birth           date NULL,
    is_active       bool NULL DEFAULT false,
    xp              int8 NULL DEFAULT 0,
    user_id         uuid NOT NULL,
    biography       varchar(255) NULL,
    cell_phone      varchar(255) NULL,
    email           varchar(255) NULL,
    full_name       varchar(255) NULL,
    last_name       varchar(255) NULL,
    "name"          varchar(255) NULL,
    "password"      varchar(255) NULL,
    profile_picture varchar(255) NULL,
    username        varchar(255) NULL,
    CONSTRAINT tb_user_pkey PRIMARY KEY (user_id)
);
CREATE INDEX idx_username ON public.tb_user USING btree (username);


-- public.tb_exercise definition

-- Drop table

-- DROP TABLE tb_exercise;

CREATE TABLE IF NOT EXISTS tb_exercise
(
    id                   uuid         NOT NULL,
    training_training_id uuid NULL,
    exercise             varchar(255) NOT NULL,
    CONSTRAINT tb_exercise_pkey PRIMARY KEY (id),
    CONSTRAINT fkg94hiwwaa2pa3hw03lb7vx8w8 FOREIGN KEY (training_training_id) REFERENCES tb_training (training_id)
);


-- public.tb_friends definition

-- Drop table

-- DROP TABLE tb_friends;

CREATE TABLE IF NOT EXISTS tb_friends
(
    accepted         bool NULL,
    accepted_at      date NULL,
    sent_at          date NULL,
    id               uuid NOT NULL,
    user_receiver_id uuid NULL,
    user_sender_id   uuid NULL,
    CONSTRAINT tb_friends_pkey PRIMARY KEY (id),
    CONSTRAINT fkh380it5kx88t7822i8ds57va6 FOREIGN KEY (user_receiver_id) REFERENCES tb_user (user_id),
    CONSTRAINT fkowsxl0rhdfswd588qf207lhck FOREIGN KEY (user_sender_id) REFERENCES tb_user (user_id)
);


-- public.tb_post definition

-- Drop table

-- DROP TABLE tb_post;

CREATE TABLE IF NOT EXISTS tb_post
(
    created_at  timestamp NULL,
    fk_user_id  uuid NULL,
    id          uuid NOT NULL,
    description varchar(1000) NULL,
    CONSTRAINT tb_post_pkey PRIMARY KEY (id),
    CONSTRAINT fk1yfgg9pt58i7sufryxd3o4as4 FOREIGN KEY (fk_user_id) REFERENCES tb_user (user_id)
);


-- public.tb_public_event definition

-- Drop table

-- DROP TABLE tb_public_event;

CREATE TABLE IF NOT EXISTS tb_public_event
(
    xp            int4 NULL,
    "date"        timestamp(6) NULL,
    fk_address_id uuid NULL,
    fk_user_id    uuid NULL,
    id            uuid NOT NULL,
    description   varchar(1000) NULL,
    "name"        varchar(255) NULL,
    tag           varchar(255) NULL,
    CONSTRAINT tb_public_event_pkey PRIMARY KEY (id),
    CONSTRAINT fk4r1w2x5bev20lh9giuvm1m35c FOREIGN KEY (fk_address_id) REFERENCES tb_address (id),
    CONSTRAINT fkg8ixwysj7gj58i67omrr501e8 FOREIGN KEY (fk_user_id) REFERENCES tb_user (user_id)
);


-- public.tb_squad_event definition

-- Drop table

-- DROP TABLE tb_squad_event;

CREATE TABLE IF NOT EXISTS tb_squad_event
(
    "date"        timestamp(6) NULL,
    fk_address_id uuid NULL,
    fk_squad_id   uuid NULL,
    id            uuid NOT NULL,
    description   varchar(1000) NULL,
    "name"        varchar(255) NULL,
    tag           varchar(255) NULL,
    CONSTRAINT tb_squad_event_pkey PRIMARY KEY (id),
    CONSTRAINT fkpxv397xp4hug8697qgjsm0wja FOREIGN KEY (fk_squad_id) REFERENCES tb_squad (id),
    CONSTRAINT fksfeet7x16hybwoi1e8ofl73uj FOREIGN KEY (fk_address_id) REFERENCES tb_address (id)
);


-- public.tb_squad_members definition

-- Drop table

-- DROP TABLE tb_squad_members;

CREATE TABLE IF NOT EXISTS tb_squad_members
(
    fk_squad_id uuid NULL,
    fk_user_id  uuid NULL,
    id          uuid NOT NULL,
    "position"  varchar(100) NULL,
    CONSTRAINT tb_squad_members_pkey PRIMARY KEY (id),
    CONSTRAINT fkaw4q7sgs618ltspt7gig0tam7 FOREIGN KEY (fk_user_id) REFERENCES tb_user (user_id),
    CONSTRAINT fkbektdnoxuavrcq9suswsvvohm FOREIGN KEY (fk_squad_id) REFERENCES tb_squad (id)
);


-- public.tb_training_days definition

-- Drop table

-- DROP TABLE tb_training_days;

CREATE TABLE IF NOT EXISTS tb_training_days
(
    completed_at timestamp(6) NULL,
    id           uuid NOT NULL,
    training_id  uuid NULL,
    user_id      uuid NULL,
    day_of_week  varchar(255) NULL,
    "period"     varchar(255) NULL,
    status       varchar(255) NULL,
    CONSTRAINT tb_training_days_pkey PRIMARY KEY (id),
    CONSTRAINT fkou3nq9f5stsij5x1dltgcwrgc FOREIGN KEY (training_id) REFERENCES tb_training (training_id),
    CONSTRAINT fkpcf7kc1m3737vuvcn439ghxr9 FOREIGN KEY (user_id) REFERENCES tb_user (user_id)
);


-- public.tb_training_user definition

-- Drop table

-- DROP TABLE tb_training_user;

CREATE TABLE IF NOT EXISTS tb_training_user
(
    training_id uuid NOT NULL,
    user_id     uuid NOT NULL,
    CONSTRAINT fk3h349gk5o4ry8lx3l8hlndjue FOREIGN KEY (user_id) REFERENCES tb_user (user_id),
    CONSTRAINT fkfwq0vnn7osaokmr54edrhwmh6 FOREIGN KEY (training_id) REFERENCES tb_training (training_id)
);


-- public.tb_user_notification definition

-- Drop table

-- DROP TABLE tb_user_notification;

CREATE TABLE IF NOT EXISTS tb_user_notification
(
    fk_user_id        uuid NULL,
    fk_user_sender_id uuid NULL,
    id                uuid NOT NULL,
    id_type           uuid NULL,
    message           varchar(255) NULL,
    title             varchar(255) NULL,
    "type"            varchar(255) NULL,
    CONSTRAINT tb_user_notification_pkey PRIMARY KEY (id),
    CONSTRAINT fkattphjwr27wy4lpdevtgjk2eg FOREIGN KEY (fk_user_sender_id) REFERENCES tb_user (user_id),
    CONSTRAINT fkgntf8r5xvq7nip1sdk2dtfbgc FOREIGN KEY (fk_user_id) REFERENCES tb_user (user_id)
);


-- public.tb_user_role definition

-- Drop table

-- DROP TABLE tb_user_role;

CREATE TABLE IF NOT EXISTS tb_user_role
(
    role_id int8 NOT NULL,
    user_id uuid NOT NULL,
    CONSTRAINT tb_user_role_pkey PRIMARY KEY (role_id, user_id),
    CONSTRAINT fk7vn3h53d0tqdimm8cp45gc0kl FOREIGN KEY (user_id) REFERENCES tb_user (user_id),
    CONSTRAINT fkea2ootw6b6bb0xt3ptl28bymv FOREIGN KEY (role_id) REFERENCES tb_role (id)
);


-- public.tb_event_notification definition

-- Drop table

-- DROP TABLE tb_event_notification;

CREATE TABLE IF NOT EXISTS tb_event_notification
(
    fk_public_event_id uuid NULL,
    id                 uuid NOT NULL,
    message            varchar(1000) NULL,
    title              varchar(255) NULL,
    CONSTRAINT tb_event_notification_pkey PRIMARY KEY (id),
    CONSTRAINT fkqr40gs4x77ofjy891ffo2stvp FOREIGN KEY (fk_public_event_id) REFERENCES tb_public_event (id)
);