CREATE TABLE IF NOT EXISTS OPTIONVALUE (
                             ID INT AUTO_INCREMENT PRIMARY KEY,
                             VALUE VARCHAR(255),
                             OPTION_ID INT,
                             FOREIGN KEY (OPTION_ID) REFERENCES OPTION_TABLE(ID)


);