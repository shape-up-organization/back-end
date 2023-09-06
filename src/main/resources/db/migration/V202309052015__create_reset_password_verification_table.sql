CREATE TABLE tb_reset_password_verification
(
    id         uuid         NOT NULL,
    code       VARCHAR(6)   NOT NULL,
    user_id    uuid         NOT NULL,
    status     VARCHAR(60)  NOT NULL,
    verified   BOOLEAN      NOT NULL,
    email      VARCHAR(200) NOT NULL,
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    expires_in TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NULL,
    CONSTRAINT tb_reset_password_verification_pkey PRIMARY KEY (id)
);

CREATE UNIQUE INDEX tb_reset_password_verification_user_id_uindex
    ON tb_reset_password_verification (user_id);