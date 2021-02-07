create table book(
    id bigint primary key auto_increment,
    name varchar(255) not null ,
    author varchar(255) not null ,
    description text,
    publisher varchar(255),
    isbn varchar(25),
    year int
)