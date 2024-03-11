CREATE TABLE advert (
    id INT NOT NULL AUTO_INCREMENT,
    car_owner_id INT NOT NULL,
    car_id INT NOT NULL,
    available_from DATE NOT NULL,
    maximum_Available_Days INT NOT NULL,
    price_Per_Day DOUBLE NOT NULL,
    picture LONGBLOB,
    PRIMARY KEY (id),
    FOREIGN KEY (car_owner_id) REFERENCES app_user(id),
    FOREIGN KEY (car_id) REFERENCES car(id)
);