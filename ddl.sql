CREATE TABLE Person
(
    ID INT,
    EMAIL VARCHAR(50) NOT NULL,
    NAME VARCHAR(30) NOT NULL,
    DOB VARCHAR(10) NOT NULL,
    PHONE VARCHAR(15) NOT NULL,
    DOJ DATE NOT NULL,
    IsSeller CHAR(1) DEFAULT 'N',
    PRIMARY KEY (ID)
);


CREATE TABLE Customer
(
    CUSTOMER_ID INT,
    PASSWORD VARCHAR(30) NOT NULL,
    MEMBERSHIP CHAR(1) DEFAULT 'N',
    PRIMARY KEY (CUSTOMER_ID),
    FOREIGN KEY (CUSTOMER_ID) REFERENCES Person (ID) ON DELETE CASCADE
);


CREATE TABLE Seller
(
    SELLER_ID INT NOT NULL,
    PASSWORD VARCHAR(30) NOT NULL,
    PRIMARY KEY (SELLER_ID),
    FOREIGN KEY (SELLER_ID) REFERENCES Person (ID) ON DELETE CASCADE,
    CHECK (length(PASSWORD)>8)
);


CREATE TABLE Product
(
    PRODUCT_ID INT,
    QUANTITY INT NOT NULL,
    PRICE NUMBER(9,2) NOT NULL,
    PRODUCT_NAME VARCHAR(30) NOT NULL,
    SELLER_ID INT NOT NULL,
    DESCRIPTION VARCHAR(200),
    CATEGORY VARCHAR(30) NOT NULL,
    PRIMARY KEY (PRODUCT_ID),
    FOREIGN KEY (SELLER_ID) REFERENCES Seller (SELLER_ID) ON DELETE CASCADE
);


CREATE TABLE ShoppingCart
(
    CUSTOMER_ID INT,
    PRODUCT_ID INT,
    QUANTITY INT NOT NULL,
    PRIMARY KEY (CUSTOMER_ID , PRODUCT_ID),
    FOREIGN KEY (CUSTOMER_ID) REFERENCES Customer (CUSTOMER_ID) ON DELETE CASCADE,
    FOREIGN KEY (PRODUCT_ID) REFERENCES Product (PRODUCT_ID) ON DELETE CASCADE
);


CREATE TABLE WishList
(
    CUSTOMER_ID INT,
    PRODUCT_ID INT,
    PRIMARY KEY (CUSTOMER_ID , PRODUCT_ID),
    FOREIGN KEY (CUSTOMER_ID) REFERENCES Customer (CUSTOMER_ID) ON DELETE CASCADE,
    FOREIGN KEY (PRODUCT_ID) REFERENCES Product (PRODUCT_ID) ON DELETE CASCADE
);


CREATE TABLE Discount
(
    DISCOUNT_ID INT,
    PERCENT DEC (2,2),
    VALIDITY CHAR (1) DEFAULT 'N',
    PRIMARY KEY (DISCOUNT_ID)
);


CREATE TABLE Orders
(
    ORDER_ID INT,
    CUSTOMER_ID INT,
    ORDER_DATE DATE NOT NULL,
    STATUS CHAR(1) DEFAULT 'N',
    PRIMARY KEY (ORDER_ID),
    FOREIGN KEY (CUSTOMER_ID) REFERENCES Customer (CUSTOMER_ID) ON DELETE CASCADE
);


CREATE TABLE OrderedProducts
(
    ORDER_ID INT,
    PRODUCT_ID INT,
    QUANTITY INT,
    PRIMARY KEY (ORDER_ID,PRODUCT_ID),
    FOREIGN KEY (ORDER_ID) REFERENCES Orders (ORDER_ID) ON DELETE CASCADE,
    FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (PRODUCT_ID) ON DELETE CASCADE
);


CREATE TABLE Payment
(
    ORDER_ID INT,
    CUSTOMER_ID INT,
    CARD_NAME VARCHAR(20) NOT NULL,
    CARD_NUM VARCHAR(16) NOT NULL,
    CARD_COMP VARCHAR(20) NOT NULL,
    CARD_EXP DATE NOT NULL,
    PRIMARY KEY (ORDER_ID),
    FOREIGN KEY (ORDER_ID) REFERENCES Orders (ORDER_ID) ON DELETE CASCADE,
    FOREIGN KEY (CUSTOMER_ID) REFERENCES Customer (CUSTOMER_ID) ON DELETE CASCADE
);


CREATE TABLE Shipment
(
    TRACK_ID INT,
    ORDER_ID INT,
    FROM_ADDRESS VARCHAR(200) NOT NULL,
    TO_ADDRESS VARCHAR(200) NOT NULL,
    FEES INT NOT NULL,
    SHIP_COMPANY VARCHAR(20) NOT NULL,
    PRIMARY KEY (ORDER_ID,TRACK_ID),
    FOREIGN KEY (ORDER_ID) REFERENCES Orders (ORDER_ID) ON DELETE CASCADE
);


CREATE TABLE Returnings
(
    ORDER_ID INT,
    PRODUCT_ID INT,
    QUANTITY INT,
    COMMENTS VARCHAR(200) NOT NULL,
    APPROVAL CHAR(1) DEFAULT 'N',
    PRIMARY KEY (ORDER_ID , PRODUCT_ID),
    FOREIGN KEY (ORDER_ID,PRODUCT_ID) REFERENCES OrderedProducts (ORDER_ID,PRODUCT_ID) ON DELETE CASCADE
);


CREATE TABLE Reviews
(
    CUSTOMER_ID INT,
    PRODUCT_ID INT,
    RATING INT NOT NULL,
    COMMENTS VARCHAR(200) NOT NULL,
    PRIMARY KEY (CUSTOMER_ID, PRODUCT_ID),
    FOREIGN KEY (CUSTOMER_ID) REFERENCES Customer (CUSTOMER_ID) ON DELETE CASCADE,
    FOREIGN KEY (PRODUCT_ID) REFERENCES Product (PRODUCT_ID) ON DELETE CASCADE
);


CREATE TABLE Addresses
(
    ID INT,
    ADDRESS1 VARCHAR(200) NOT NULL,
    STATE VARCHAR(50) NOT NULL,
    COUNTRY VARCHAR(50) NOT NULL,
    ZIP VARCHAR(10) NOT NULL,
    PRIMARY KEY (ID, ADDRESS1, STATE, COUNTRY, ZIP),
    FOREIGN KEY (ID) REFERENCES Person (ID) ON DELETE CASCADE
);


CREATE TABLE Cards
(
    CUSTOMER_ID INT,
    CARD_NAME VARCHAR(20) NOT NULL,
    CARD_NUM VARCHAR(16) NOT NULL,
    CARD_COMP VARCHAR(20) NOT NULL,
    CARD_EXP DATE NOT NULL,
    PRIMARY KEY (CUSTOMER_ID, CARD_NAME, CARD_NUM, CARD_COMP, CARD_EXP),
    FOREIGN KEY (CUSTOMER_ID) REFERENCES Customer (CUSTOMER_ID) ON DELETE CASCADE
);


CREATE TABLE Person_Image
(
    ID INT PRIMARY KEY,
    IMAGE BLOB,
    FOREIGN KEY(ID) REFERENCES Person(ID) on DELETE CASCADE
);


CREATE TABLE Product_Image
(
    PRODUCT_ID INT,
    PRODUCT_IMAGE BLOB,
    FOREIGN KEY(PRODUCT_ID) REFERENCES Product(PRODUCT_ID) on DELETE CASCADE
);