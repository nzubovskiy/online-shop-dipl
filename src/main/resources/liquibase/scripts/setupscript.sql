-- liquibase formatted sql

-- changeset nzubovskiy:1
CREATE TABLE ads(
    pk serial primary key,
    mediaType text,
    image bytea,
    price integer,
    title text,
    user_id serial references users (id)
);

CREATE TABLE comments(
    pk serial primary key,
    createdAt text,
    text text,
    user_id serial references users (id)
);

CREATE TABLE users(
    id serial primary key,
    email text,
    firstName text,
    lastName text,
    phone text,
    regDate text,
    city text,
    mediaType text,
    image bytea,
    role text
);

