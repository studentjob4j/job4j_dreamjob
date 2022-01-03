CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   des TEXT,
   data TIMESTAMP
);

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT
)