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
    image text,
    role text,
    username text,
    password text,
    enabled BOOLEAN
    );

CREATE TABLE ads(
   pk serial primary key,
   price integer,
   title text,
   description text,
   user_id serial references users (id) on delete cascade
   );

CREATE TABLE comments(
  pk serial primary key,
  created_at timestamp not null,
  text text not null,
  user_id serial references users (id) on delete cascade,
  ads_pk serial references ads(pk)on delete cascade
  );

-- changeset elenazmeeva:2
CREATE TABLE image(
    id serial primary key,
    image bytea,
    ads_pk serial references ads(pk) on delete cascade,
    user_id serial references users (id) on delete cascade
);

-- changeset elenazmeeva:3
CREATE TABLE authorities(
    username text not null,
    authority text not null,
    FOREIGN KEY (username) REFERENCES users(username) on delete cascade
);
-- changeset elenazmeeva:4
ALTER TABLE ads ADD COLUMN image text



