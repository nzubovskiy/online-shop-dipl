-- liquibase formatted sql

-- changeset nzubovskiy:1
CREATE TABLE users(
    id serial primary key,
    email text not null,
    first_name text not null,
    last_name text not null,
    phone text not null,
    reg_date timestamp not null,
    city text not null,
    image text not null,
    role text not null,
    username text not null,
    password text not null
    );

CREATE TABLE ads(
   pk serial primary key,
   image text not null,
   price integer not null,
   title text not null,
   description text not null,
   user_id serial references users (id)
   );

CREATE TABLE comments(
  pk serial primary key,
  created_at timestamp not null,
  text text not null,
  user_id serial references users (id),
  ads_pk serial references ads(pk)
  );

-- changeset elenazmeeva:2
CREATE TABLE image(
    id serial primary key,
    image bytea not null,
    ads_pk serial references ads(pk) not null,
    user_id serial references users (id) not null
);

