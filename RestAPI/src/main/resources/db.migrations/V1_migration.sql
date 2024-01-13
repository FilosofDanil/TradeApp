create table if not exists telegram_users
(
    id         bigserial
        primary key,
    username   varchar(100) not null unique,
    tg_name    varchar(100) not null,
    tg_surname varchar(100) not null,
    chat_id    bigint       not null
);

create table if not exists items
(
    id              bigserial
        primary key,
    item_name       varchar(100) not null,
    description     text,
    start_price int not null,
    bid_price int,
    placement_date  timestamp(6) not null,
    expiration_date timestamp(6) not null,
    user_id         bigserial    not null
        constraint user_id
            unique
        constraint userid
            references telegram_users
);

create table if not exists item_attachments
(
    id bigserial
        primary key,
    item_id         bigserial    not null
        constraint item_id
            unique
        constraint itemId
            references items,
    item_type varchar(100) not null,
    item_data varchar(256) not null
);

create table if not exists bids
(
    id bigserial
        primary key,
    user_id         bigserial    not null
        constraint b_user_id
            unique
        constraint userid
            references telegram_users,
    item_id         bigserial    not null
        constraint b_item_id
            unique
        constraint itemId
            references items,
    comment varchar(256),
    bid_price int not null
);