CREATE TABLE tb_notification_history
(
    id                        UUID PRIMARY KEY NOT NULL,
    notification_id           VARCHAR(255)     NOT NULL,
    type                      VARCHAR(100)     NOT NULL,
    notification_sending_date TIMESTAMP        NOT NULL
);

CREATE UNIQUE INDEX idx_notification_history_notification_id_type ON tb_notification_history (notification_id, type);
