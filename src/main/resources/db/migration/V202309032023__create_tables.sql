-- public.tb_address definition

-- Drop table

-- DROP TABLE public.tb_address;

CREATE TABLE public.tb_address
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

-- DROP TABLE public.tb_answer_comments;

CREATE TABLE public.tb_answer_comments
(
    id              uuid NOT NULL,
    comment_message varchar(255) NULL,
    id_comment      varchar(255) NULL,
    CONSTRAINT tb_answer_comments_pkey PRIMARY KEY (id)
);


-- public.tb_comment definition

-- Drop table

-- DROP TABLE public.tb_comment;

CREATE TABLE public.tb_comment
(
    id              uuid NOT NULL,
    comment_message varchar(255) NULL,
    created_at      timestamp(6) NULL,
    id_post         varchar(255) NULL,
    user_id         varchar(255) NULL,
    CONSTRAINT tb_comment_pkey PRIMARY KEY (id)
);


-- public.tb_role definition

-- Drop table

-- DROP TABLE public.tb_role;

CREATE TABLE public.tb_role
(
    id     bigserial NOT NULL,
    "role" varchar(255) NULL,
    CONSTRAINT tb_role_pkey PRIMARY KEY (id)
);


-- public.tb_squad definition

-- Drop table

-- DROP TABLE public.tb_squad;

CREATE TABLE public.tb_squad
(
    id        uuid   NOT NULL,
    is_active bit(1) NOT NULL,
    "name"    varchar(255) NULL,
    xp        int4 NULL,
    CONSTRAINT tb_squad_pkey PRIMARY KEY (id)
);


-- public.tb_training definition

-- Drop table

-- DROP TABLE public.tb_training;

CREATE TABLE public.tb_training
(
    training_id    uuid NOT NULL,
    category       varchar(255) NULL,
    classification varchar(255) NULL,
    description    varchar(255) NULL,
    duration       int4 NULL,
    "name"         varchar(255) NULL,
    unlock_xp      int8 NULL,
    xp             int8 NULL,
    CONSTRAINT tb_training_category_check CHECK (((category)::text = ANY ((ARRAY['AEROBIC':: character varying, 'ANAEROBIC':: character varying, 'FLEXIBILITY':: character varying, 'STRENGTH':: character varying, 'FIGHT':: character varying, 'HEALTH':: character varying])::text[])
) ),
	CONSTRAINT tb_training_pkey PRIMARY KEY (training_id)
);


-- public.tb_user definition

-- Drop table

-- DROP TABLE public.tb_user;

CREATE TABLE public.tb_user
(
    user_id         uuid NOT NULL,
    biography       varchar(255) NULL,
    birth           date NULL,
    cell_phone      varchar(255) NULL,
    email           varchar(255) NULL,
    full_name       varchar(255) NULL,
    is_active       bool NULL DEFAULT true,
    last_name       varchar(255) NULL,
    "name"          varchar(255) NULL,
    "password"      varchar(255) NULL,
    profile_picture varchar(255) NULL,
    username        varchar(255) NULL,
    xp              int8 NULL DEFAULT 0,
    CONSTRAINT tb_user_pkey PRIMARY KEY (user_id)
);
CREATE INDEX idx_username ON public.tb_user USING btree (username);


-- public.tb_exercise definition

-- Drop table

-- DROP TABLE public.tb_exercise;

CREATE TABLE public.tb_exercise
(
    id                   uuid         NOT NULL,
    exercise             varchar(255) NOT NULL,
    training_training_id uuid NULL,
    CONSTRAINT tb_exercise_pkey PRIMARY KEY (id),
    CONSTRAINT fkg94hiwwaa2pa3hw03lb7vx8w8 FOREIGN KEY (training_training_id) REFERENCES public.tb_training (training_id)
);


-- public.tb_friends definition

-- Drop table

-- DROP TABLE public.tb_friends;

CREATE TABLE public.tb_friends
(
    id               uuid NOT NULL,
    accepted         bool NULL,
    accepted_at      date NULL,
    sent_at          date NULL,
    user_receiver_id uuid NULL,
    user_sender_id   uuid NULL,
    CONSTRAINT tb_friends_pkey PRIMARY KEY (id),
    CONSTRAINT fkh380it5kx88t7822i8ds57va6 FOREIGN KEY (user_receiver_id) REFERENCES public.tb_user (user_id),
    CONSTRAINT fkowsxl0rhdfswd588qf207lhck FOREIGN KEY (user_sender_id) REFERENCES public.tb_user (user_id)
);


-- public.tb_post definition

-- Drop table

-- DROP TABLE public.tb_post;

CREATE TABLE public.tb_post
(
    id          uuid NOT NULL,
    created_at  timestamp NULL,
    description varchar(1000) NULL,
    fk_user_id  uuid NULL,
    CONSTRAINT tb_post_pkey PRIMARY KEY (id),
    CONSTRAINT fk1yfgg9pt58i7sufryxd3o4as4 FOREIGN KEY (fk_user_id) REFERENCES public.tb_user (user_id)
);


-- public.tb_public_event definition

-- Drop table

-- DROP TABLE public.tb_public_event;

CREATE TABLE public.tb_public_event
(
    id            uuid NOT NULL,
    "date"        timestamp(6) NULL,
    description   varchar(1000) NULL,
    "name"        varchar(255) NULL,
    tag           varchar(255) NULL,
    xp            int4 NULL,
    fk_address_id uuid NULL,
    fk_user_id    uuid NULL,
    CONSTRAINT tb_public_event_pkey PRIMARY KEY (id),
    CONSTRAINT fk4r1w2x5bev20lh9giuvm1m35c FOREIGN KEY (fk_address_id) REFERENCES public.tb_address (id),
    CONSTRAINT fkg8ixwysj7gj58i67omrr501e8 FOREIGN KEY (fk_user_id) REFERENCES public.tb_user (user_id)
);


-- public.tb_squad_event definition

-- Drop table

-- DROP TABLE public.tb_squad_event;

CREATE TABLE public.tb_squad_event
(
    id            uuid NOT NULL,
    "date"        timestamp(6) NULL,
    description   varchar(1000) NULL,
    "name"        varchar(255) NULL,
    tag           varchar(255) NULL,
    fk_address_id uuid NULL,
    fk_squad_id   uuid NULL,
    CONSTRAINT tb_squad_event_pkey PRIMARY KEY (id),
    CONSTRAINT fkpxv397xp4hug8697qgjsm0wja FOREIGN KEY (fk_squad_id) REFERENCES public.tb_squad (id),
    CONSTRAINT fksfeet7x16hybwoi1e8ofl73uj FOREIGN KEY (fk_address_id) REFERENCES public.tb_address (id)
);


-- public.tb_squad_members definition

-- Drop table

-- DROP TABLE public.tb_squad_members;

CREATE TABLE public.tb_squad_members
(
    id          uuid NOT NULL,
    "position"  varchar(100) NULL,
    fk_squad_id uuid NULL,
    fk_user_id  uuid NULL,
    CONSTRAINT tb_squad_members_pkey PRIMARY KEY (id),
    CONSTRAINT fkaw4q7sgs618ltspt7gig0tam7 FOREIGN KEY (fk_user_id) REFERENCES public.tb_user (user_id),
    CONSTRAINT fkbektdnoxuavrcq9suswsvvohm FOREIGN KEY (fk_squad_id) REFERENCES public.tb_squad (id)
);


-- public.tb_training_days definition

-- Drop table

-- DROP TABLE public.tb_training_days;

CREATE TABLE public.tb_training_days
(
    id           uuid NOT NULL,
    completed_at timestamp(6) NULL,
    day_of_week  varchar(255) NULL,
    "period"     varchar(255) NULL,
    status       varchar(255) NULL,
    training_id  uuid NULL,
    user_id      uuid NULL,
    CONSTRAINT tb_training_days_pkey PRIMARY KEY (id),
    CONSTRAINT fkou3nq9f5stsij5x1dltgcwrgc FOREIGN KEY (training_id) REFERENCES public.tb_training (training_id),
    CONSTRAINT fkpcf7kc1m3737vuvcn439ghxr9 FOREIGN KEY (user_id) REFERENCES public.tb_user (user_id)
);


-- public.tb_training_user definition

-- Drop table

-- DROP TABLE public.tb_training_user;

CREATE TABLE public.tb_training_user
(
    user_id     uuid NOT NULL,
    training_id uuid NOT NULL,
    CONSTRAINT fk3h349gk5o4ry8lx3l8hlndjue FOREIGN KEY (user_id) REFERENCES public.tb_user (user_id),
    CONSTRAINT fkfwq0vnn7osaokmr54edrhwmh6 FOREIGN KEY (training_id) REFERENCES public.tb_training (training_id)
);


-- public.tb_user_notification definition

-- Drop table

-- DROP TABLE public.tb_user_notification;

CREATE TABLE public.tb_user_notification
(
    id                uuid NOT NULL,
    id_type           uuid NULL,
    message           varchar(255) NULL,
    title             varchar(255) NULL,
    "type"            varchar(255) NULL,
    fk_user_id        uuid NULL,
    fk_user_sender_id uuid NULL,
    CONSTRAINT tb_user_notification_pkey PRIMARY KEY (id),
    CONSTRAINT fkattphjwr27wy4lpdevtgjk2eg FOREIGN KEY (fk_user_sender_id) REFERENCES public.tb_user (user_id),
    CONSTRAINT fkgntf8r5xvq7nip1sdk2dtfbgc FOREIGN KEY (fk_user_id) REFERENCES public.tb_user (user_id)
);


-- public.tb_user_role definition

-- Drop table

-- DROP TABLE public.tb_user_role;

CREATE TABLE public.tb_user_role
(
    user_id uuid NOT NULL,
    role_id int8 NOT NULL,
    CONSTRAINT tb_user_role_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk7vn3h53d0tqdimm8cp45gc0kl FOREIGN KEY (user_id) REFERENCES public.tb_user (user_id),
    CONSTRAINT fkea2ootw6b6bb0xt3ptl28bymv FOREIGN KEY (role_id) REFERENCES public.tb_role (id)
);


-- public.tb_event_notification definition

-- Drop table

-- DROP TABLE public.tb_event_notification;

CREATE TABLE public.tb_event_notification
(
    id                 uuid NOT NULL,
    message            varchar(1000) NULL,
    title              varchar(255) NULL,
    fk_public_event_id uuid NULL,
    CONSTRAINT tb_event_notification_pkey PRIMARY KEY (id),
    CONSTRAINT fkqr40gs4x77ofjy891ffo2stvp FOREIGN KEY (fk_public_event_id) REFERENCES public.tb_public_event (id)
);