CREATE TABLE IF NOT EXISTS BULLET (
                                       ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                       NAME VARCHAR(50) NOT NULL,
                                       PRODUCT_ID INT NOT NULL,
                                       FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(ID)
    )