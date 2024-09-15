CREATE TABLE trainer
(
    id             SERIAL PRIMARY KEY,
    user_id        BIGINT      NOT NULL,
    specialization VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id)
);