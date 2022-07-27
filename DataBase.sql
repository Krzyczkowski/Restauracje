create table employee
(
    id       integer     not null
        constraint employee_pk
            primary key,
    name     varchar(15) not null,
    lastname varchar(25) not null
);

create table logins
(
    id             integer not null
        constraint logins_pk
            primary key
        constraint logins_fk0
            references employee,
    login          varchar not null,
    password       varchar not null,
    levelaccess    integer not null,
    pesel          integer,
    salary         real    not null,
    restaurantname varchar
);

create table clients
(
    phone   varchar(9) not null
        constraint client_pk
            primary key,
    address varchar
);

create table orders
(
    id         integer    not null
        constraint orders_pk
            primary key,
    idemployee integer    not null
        constraint orders_fk0
            references employee,
    idclient   varchar(9) not null
        constraint orders_fk1
            references clients,
    totalprice real       not null,
    dates      date,
    restaurant varchar
);

create table storage
(
    id         integer     not null
        constraint storage_pk
            primary key,
    amount     integer     not null,
    name       varchar(25) not null,
    restaurant varchar
);

create table earnings
(
    dates      date not null
        constraint earnings_pk
            primary key,
    earning    real not null,
    restaurant varchar
);

create table restaurants
(
    name varchar not null
        primary key
);

create table products
(
    id         integer     not null
        constraint products_pk
            primary key,
    name       varchar(25) not null,
    price      real        not null,
    category   varchar     not null,
    restaurant varchar
);

create table positions
(
    idorder   integer not null
        constraint positions_fk0
            references orders,
    idproduct integer not null
        constraint positions_fk1
            references products,
    amount    integer not null,
    id        integer not null
        primary key
);

create table compositions
(
    idproduct integer not null
        constraint compositions_fk0
            references products,
    iditem    integer not null
        constraint compositions_fk1
            references storage,
    amount    integer not null,
    id        integer not null
        primary key
);

create table categories
(
    id         varchar not null,
    restaurant varchar not null,
    primary key (id, restaurant)
);

