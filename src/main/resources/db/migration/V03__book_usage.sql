create unique index client_id_uindex
    on client (id);

create unique index book_id_uindex
    on book (id);

create table book_in_use
(
    id serial not null,
    client_id int not null
        constraint book_in_use_client_id_fk
        references client (id)
        on delete cascade,
    book_id int not null
        constraint book_in_use_book_id_fk
        references book (id)
        on delete cascade,
    in_use_from date not null
);

create unique index book_in_use_client_id_book_id_uindex
    on book_in_use (client_id, book_id);

create unique index book_in_use_id_uindex
    on book_in_use (id);

create table book_history
(
    id serial not null,
    client_id int not null
        constraint book_in_use_client_id_fk
        references client (id)
        on delete cascade,
    book_id int not null
        constraint book_in_use_book_id_fk
        references book (id)
        on delete cascade,
    in_use_from date not null,
    in_use_to date not null
);

create unique index book_history_id_uindex
    on book_in_use (id);

alter table book add column available bool not null default true;