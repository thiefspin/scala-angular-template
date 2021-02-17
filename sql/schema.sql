DROP SCHEMA IF EXISTS platform;
CREATE SCHEMA platform;

CREATE TABLE platform.users
(
    id         serial PRIMARY KEY,
    email      VARCHAR   NOT NULL,
    name       VARCHAR   NOT NULL,
    surname    VARCHAR   NOT NULL,
    password   VARCHAR   NOT NULL,
    created    TIMESTAMP NOT NULL,
    last_login TIMESTAMP
);

INSERT INTO platform.users(email, name, surname, password, created)
VALUES ('johndoe@gmail.com', 'John', 'Doe', '$2a$12$TVi4xhKYJqtMLR.Z2H5Tw.p/ItmpTtFzKAtkkDPy9Rw.RzwQrC5PO', CURRENT_TIMESTAMP);