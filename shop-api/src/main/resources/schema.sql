create schema if not exists api;

create table api.shop (
    id bigserial primary key auto_increment,
    identifier varchar not null,
    status varchar not null,
    date_shop date
);

create table api.shop_item (
    id bigserial primary key auto_increment,
    product_identifier varchar(100) not null,
    amount int not null,
    price float not null,
    shop_id bigint references shop(id)    
)