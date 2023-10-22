CREATE TABLE tb_email_verification
(
    id         UUID PRIMARY KEY                    NOT NULL,
    code       VARCHAR(6)                          NOT NULL,
    verified   BOOLEAN   DEFAULT true              NOT NULL,
    email      VARCHAR(200)                        NOT NULL,
    username   VARCHAR(50)                         NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    expires_in TIMESTAMP                           NOT NULL,
    updated_at TIMESTAMP NULL,
    CONSTRAINT unique_username UNIQUE (username),
    CONSTRAINT unique_email UNIQUE (email)
);

CREATE INDEX idx_email_verification_username ON tb_email_verification (username);
CREATE INDEX idx_email_verification_email ON tb_email_verification (email);
