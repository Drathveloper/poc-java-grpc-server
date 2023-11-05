CREATE TABLE address(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    country VARCHAR(100),
    city VARCHAR(100),
    state VARCHAR(100),
    address VARCHAR(200),
    postalCode VARCHAR(10)
);

CREATE TABLE client(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(200) NOT NULL,
    last_name VARCHAR(200) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(100) NOT NULL,
    birthDate DATE NOT NULL,
    address_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES address(id)
)
