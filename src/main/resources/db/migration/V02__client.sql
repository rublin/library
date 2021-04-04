create table client
(
    id bigserial not null,
    first_name varchar(50)  not null,
    last_name  varchar(50)  not null,
    email     varchar(255) not null,
    phone     varchar(15)  not null
) ;

create unique index client_email_uindex
    on client (email);

create unique index client_phone_uindex
    on client (phone);