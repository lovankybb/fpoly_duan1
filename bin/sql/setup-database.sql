create database duan_1_db;
GO
use duan_1_db;
GO

-- AUTHENTICATION AND AUTHORIZATION TABLES
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
);



INSERT INTO permissions (name, description)
VALUES
('READ_PRODUCT', 'Permission to read product information'),
('CREATE_PRODUCT', 'Permission to create a new product'),
('UPDATE_PRODUCT', 'Permission to update product information'),
('DELETE_PRODUCT', 'Permission to delete a product'),
('CREATE_USER', 'Permission to create a new user'),
('READ_USER', 'Permission to read user information'),
('UPDATE_USER', 'Permission to update user information'),
('DELETE_USER', 'Permission to delete a user');

INSERT INTO roles (name, description)
VALUES
('ADMIN', 'Administrator role with full permissions'),
('USER', 'Regular user role with limited permissions');

INSERT INTO roles_permissions (role_id, permission_id)
VALUES
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


-- PRODUCT AND PRODUCT VARIANTS
CREATE TABLE categories (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(255)
);

CREATE TABLE brands (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255) NOT NULL,
    image_url NVARCHAR(255),
    description NVARCHAR(255)
);

CREATE TABLE products (
    id INT PRIMARY KEY IDENTITY(1001,1),
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    sale_price DECIMAL(10, 2),
    status NVARCHAR(50) NOT NULL,
    category_id INT,
    brand_id INT,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (brand_id) REFERENCES brands(id)
);


CREATE TABLE product_images(
    id INT PRIMARY KEY IDENTITY(1, 1),
    product_id INT NOT NULL REFERENCES products(id),
    image_url VARCHAR(255) NOT NULL
)

CREATE TABLE colors (
    id INT PRIMARY KEY IDENTITY(1, 1),
    name NVARCHAR(255) NOT NULL,
    hex VARCHAR(255) NOT NULL
);

CREATE TABLE versions (
    id INT PRIMARY KEY IDENTITY(1, 1),
    name NVARCHAR(255) NOT NULL
);

CREATE TABLE product_variants (
    id INT PRIMARY KEY IDENTITY(1, 1),
    prod_id  INT NOT NULL REFERENCES products(id),
    color_id INT NOT NULL REFERENCES colors(id),
    version_id INT NOT NULL REFERENCES versions(id),
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
);


-- ORDER
CREATE TABLE orders(
    id BIGINT PRIMARY KEY IDENTITY(1001, 1),
    order_code VARCHAR(255) NOT NULL,

    customer_name NVARCHAR(255) NOT NULL,
    customer_address NVARCHAR(255) NOT NULL,
    customer_phone NVARCHAR(255) NOT NULL,
    customer_note NVARCHAR(255) NOT NULL,

    user_id NVARCHAR(255),

    sub_total DECIMAL(10, 2),
    tax_amount DECIMAL(10, 2),
    total_amount DECIMAL(10, 2),

    order_status VARCHAR(255) NOT NULL,
    payment_status VARCHAR(255) NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    paid_at DATETIME,


    created_at DATETIME,
    updated_at DATETIME,

    canceled_at DATETIME,
    cancel_reason DATETIME,
);


CREATE TABLE order_details (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    order_id INT NOT NULL REFERENCES orders(id),
    prod_id INT NOT NULL REFERENCES products(id),
    price DECIMAL(10, 2) NOT NULL,
    quantity int NOT NULL
);



-- PAYMENT
CREATE TABLE payment_attempts(

  id INT PRIMARY KEY IDENTITY,
  order_id INT NOT NULL REFERENCES orders(id),
  amount DECIMAL(10, 2) NOT NULL,
  currency VARCHAR(255),
  payment_status VARCHAR(255),
  gateway_txn_id VARCHAR(255),
  response_code INT,
  response_message NVARCHAR(255),

  redirect_url VARCHAR(255),
  client_ip_address VARCHAR(255),
  request_payload  VARCHAR(255),
  response_payload  VARCHAR(255),

  created_at DATETIME,
  updated_at DATETIME,
  paid_at DATETIME

);

