CREATE TABLE chat (
                             id INT NOT NULL AUTO_INCREMENT,
                             advert_id INT,
                             PRIMARY KEY (id),
                             FOREIGN KEY (advert_id) REFERENCES advert(id)
);

CREATE TABLE message (
                          id INT NOT NULL AUTO_INCREMENT,
                          sender_id INT NOT NULL,
                          chat_id INT NOT NULL,
                          recipient_id INT NOT NULL,
                          text VARCHAR(255) NOT NULL,
                          time DATETIME NOT NULL,
                          PRIMARY KEY (id),
                          FOREIGN KEY (sender_id) REFERENCES app_user(id),
                          FOREIGN KEY (recipient_id) REFERENCES app_user(id),
                          FOREIGN KEY (chat_id) REFERENCES chat(id)
);


