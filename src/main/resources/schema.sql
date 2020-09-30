DROP TABLE IF EXISTS connection;

CREATE TABLE connection (
    id INTEGER PRIMARY KEY,
    name VARCHAR,
    database_name VARCHAR,
    user_name VARCHAR,
    password VARCHAR
);