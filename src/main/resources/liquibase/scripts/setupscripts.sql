-- liquibase formatted sql

-- changeset nzubovskiy:1
CREATE TABLE users(
    id serial primary key,
    email text,
    first_name text,
    last_name text,
    phone text,
    reg_date timestamp,
    city text,
    media_type text,
    image bytea,
    role text,
    login text,
    password text
    );

CREATE TABLE ads(
   pk serial primary key,
   media_type text,
   image bytea,
   price integer,
   title text,
   user_id serial references users (id)
   );

CREATE TABLE comments(
  pk serial primary key,
  created_at timestamp,
  text text,
  user_id serial references users (id)
  );


