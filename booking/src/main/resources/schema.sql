create table if not exists device (
    id serial primary key,
    public_id varchar(36) unique not null,
    name varchar(50) not null
);

create table if not exists booking (
    id serial primary key,
    public_id varchar(36) unique not null,
    device_id varchar(36) not null references device (public_id),
    status varchar(10) not null default 'free',
    time_at timestamp,
    user_id varchar(36)
);

create index if not exists device_pub_idx_idx on device (public_id);
create index if not exists booking_pub_id_idx on booking (public_id);
create index if not exists booking_device_id_idx on booking (device_id);
