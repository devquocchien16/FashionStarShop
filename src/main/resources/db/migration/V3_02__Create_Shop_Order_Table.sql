CREATE TABLE IF NOT EXISTS SHOP_ORDER(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID INT,
    VARIANT_ID INT,
    ORDER_DATE VARCHAR(255),
    ADDRESS_ID INT,
    PAYMENT_METHOD_ID INT,
    SHIPPING_METHOD_ID INT,
    QUANTITY INT,
    ORDER_TOTAL DOUBLE,
    FOREIGN KEY (USER_ID) REFERENCES USER(ID),
    FOREIGN KEY (VARIANT_ID) REFERENCES VARIANT(ID),
    FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS(ID),
    FOREIGN KEY (PAYMENT_METHOD_ID) REFERENCES PAYMENT_METHOD(ID),
    FOREIGN KEY (SHIPPING_METHOD_ID) REFERENCES SHIPPING_METHOD(ID)
    );