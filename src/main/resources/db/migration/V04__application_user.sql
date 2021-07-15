create table application_user
(
    id bigserial not null,
    first_name varchar(20),
    last_name  varchar(20),
    email      varchar(50) not null,
    password   varchar(64) not null,
    locked     bool        not null,
    enabled    bool        not null
) ;

insert into application_user (first_name, last_name, email, password, locked, enabled)
values ('admin', 'admin', 'admin@admin.ua', '$2y$12$XyRsLIAcz3wEo8mfUXuSU.8lNVMaVPlWNUTRsPpCoNrg85OEFMIZ2', false, true);
