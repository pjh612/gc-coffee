CREATE TABLE designers
(
    id        BINARY(16) PRIMARY KEY,
    name      VARCHAR(10) NOT NULL,
    position  VARCHAR(20),
    joined_at datetime(6) DEFAULT NULL
);

CREATE TABLE customers
(
    id         BINARY(16) PRIMARY KEY,
    name       VARCHAR(10) NOT NULL,
    email      VARCHAR(50),
    gender     VARCHAR(10) NOT NULL,
    birth      datetime(6) DEFAULT NULL,
    created_at datetime(6) NOT NULL,
    updated_at datetime(6) DEFAULT NULL,
    CONSTRAINT unq_customer_email UNIQUE (email)
);

CREATE TABLE menus
(
    id         BINARY(16) PRIMARY KEY,
    name       VARCHAR(20) NOT NULL,
    price      INT         NOT NULL,
    created_at datetime(6) NOT NULL,
    updated_at datetime(6) DEFAULT NULL
);

CREATE TABLE appointments
(
    appointment_id binary(16)  NOT NULL,
    menu_id        binary(16)  NOT NULL,
    customer_id    binary(16)  NOT NULL,
    designer_id    binary(16)  NOT NULL,
    appointed_at   datetime(6) NOT NULL,
    INDEX (designer_id),

    CONSTRAINT customer_id_appointed_at_unique UNIQUE (customer_id, appointed_at),
    CONSTRAINT fk_appointments_to_menu FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE,
    CONSTRAINT fk_appointments_to_customer FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE,
    CONSTRAINT fk_appointments_to_designer FOREIGN KEY (designer_id) REFERENCES designers (id) ON DELETE CASCADE
)