CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE tb_email_html_template
(
    id         UUID DEFAULT uuid_generate_v4() PRIMARY KEY NOT NULL,
    content    TEXT NOT NULL,
    type       VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NULL
);

CREATE UNIQUE INDEX idx_email_html_template_type ON tb_email_html_template (type);