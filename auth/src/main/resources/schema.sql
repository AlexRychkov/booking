create table if not exists users (
    id serial primary key,
    public_id varchar(36) unique not null,
    email varchar(100) unique not null,
    password text not null,
    name varchar(100) unique not null,
    role varchar(10) not null default 'developer',
    enabled boolean not null default true
);