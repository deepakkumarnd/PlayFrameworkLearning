CREATE KEYSPACE eva WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 };

CREATE TABLE posts (id uuid, title text, content text, created_at timestamp, PRIMARY KEY (id, title)) ;


// Registration related tables

CREATE TABLE registrations (
    id UUID,
    name text,
    email text,
    password_salt text,
    created_at timestamp,
    PRIMARY KEY(id)
);

CREATE TABLE registrations_by_email (
    id UUID,
    email text,
    PRIMARY KEY(email)
);

