--liquibase formatted sql

--changeset aponedelko:1
CREATE TABLE IF NOT EXISTS Users (
    ID SERIAL PRIMARY KEY,
    Login VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL
);
--rollback DROP TABLE Users

--changeset aponedelko:2
CREATE TABLE IF NOT EXISTS Locations (
    ID SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    UserId INT NOT NULL,
    Latitude DECIMAL(10, 8) NOT NULL,
    Longitude DECIMAL(11, 8) NOT NULL,

    FOREIGN KEY (UserId) REFERENCES Users(ID)
);
--rollback DROP TABLE Locations

--changeset aponedelko:3
CREATE TABLE IF NOT EXISTS Sessions (
    ID UUID PRIMARY KEY,
    UserId INT NOT NULL,
    ExpiresAt TIMESTAMP NOT NULL,

    FOREIGN KEY (UserId) REFERENCES Users(ID)
);
--rollback DROP TABLE Sessions