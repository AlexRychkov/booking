create table if not exists device (
    id serial primary key,
    public_id varchar(36) unique not null,
    name varchar(50) not null,
    technology text not null default '',
    second_gen text not null default '',
    third_gen text not null default '',
    fourth_gen text not null default ''
);

create index if not exists device_pub_idx_idx on device (public_id);