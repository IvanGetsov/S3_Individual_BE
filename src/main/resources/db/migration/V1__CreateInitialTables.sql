CREATE TABLE app_user (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(50),
    password VARCHAR(255),
    name VARCHAR(50),
    age INT NOT NULL,
    role VARCHAR(255) NOT NULL,
    picture LONGBLOB,
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE car (
    id INT NOT NULL AUTO_INCREMENT,
    brand VARCHAR(50),
    model VARCHAR(50),
    colour VARCHAR(50),
    year_Of_Construction INT NOT NULL,
    kilometers INT NOT NULL,
    PRIMARY KEY (id)
);
