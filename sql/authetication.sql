create database duan_1_db;

use duan_1_db;

CREATE TABLE permissions (
    id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(255) NOT NULL,
    description TEXT
);


CREATE TABLE roles (
    id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE roles_permissions (
    role_id INT NOT NULL,
    permission_id INT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id)
);

CREATE TABLE users (
    id VARCHAR(255) PRIMARY KEY ,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),
    address NVARCHAR(255),
    avatar VARCHAR(255),
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);

CREATE TABLE users_roles (
    user_id VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE invalidated_tokens(
    jwt_id VARCHAR(255) PRIMARY KEY,
    expired_at DATETIME DEFAULT GETDATE()
)


drop table if exists invalidated_tokens;


INSERT INTO permissions (name, description) VALUES
('READ_PRODUCT', 'Permission to read product information'),
('CREATE_PRODUCT', 'Permission to create a new product'),
('UPDATE_PRODUCT', 'Permission to update product information'),
('DELETE_PRODUCT', 'Permission to delete a product'),
('CREATE_USER', 'Permission to create a new user'),
('READ_USER', 'Permission to read user information'),
('UPDATE_USER', 'Permission to update user information'),
('DELETE_USER', 'Permission to delete a user');

INSERT INTO roles (name, description) VALUES
('ADMIN', 'Administrator role with full permissions'),
('USER', 'Regular user role with limited permissions');

INSERT INTO roles_permissions (role_id, permission_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(2, 2),
(2, 3);




select * from permissions;
select * from roles;
select * from roles_permissions;
select * from users;
select * from users_roles;


USE master;

DROP DATABASE  duan_1_db;


SELECT r.* FROM roles r 
JOIN users_roles ur ON r.id = ur.role_id 
JOIN users u ON ur.user_id = u.id
WHERE u.username = 'lovanky'; // Dừng xử lý logic phía dưới


create table categories(
    id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(255) NOT NULL,
    description NVARCHAR(255)
)

