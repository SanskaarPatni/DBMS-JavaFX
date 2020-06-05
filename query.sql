/*Quesries For DBMS Project on Shopping Cart Data-Base*/

/*Simple Queries*/

/*1. Show ID, Name and E-mail of only the customers.*/
SELECT Person.ID,Person.NAME,Person.EMAIL FROM Person,Customer WHERE Person.ID=Customer.CUSTOMER_ID;
/*
        ID NAME                           EMAIL
---------- ------------------------------ --------------------------------------------------
    368192 Bobby                          bob.builder@gmail.com
    251059 Billy                          billy.cat@gmail.com
    592134 James                          sam.televi@gmail.com
*/

/*2. Show ID, Name and Email of only the sellers.*/
SELECT Person.ID,Person.NAME,Person.EMAIL FROM Person,Seller WHERE Person.ID=Seller.SELLER_ID;
/*
        ID NAME                           EMAIL
---------- ------------------------------ --------------------------------------------------
    845523 Apple                          AppleID@apple.com
    359803 Adidas                         AdidasID@adidas.com
    432591 Nike                           NikeID@nike.com
    256458 Calvin Klein                   CKID@calvin.com
*/

/*3. List all the Products Sold by the Seller with ID 432591*/
SELECT PRODUCT_ID,PRODUCT_NAME,QUANTITY,PRICE FROM Product WHERE SELLER_ID='432591';
/*
PRODUCT_ID PRODUCT_NAME                     QUANTITY      PRICE
---------- ------------------------------ ---------- ----------
     18332 Soccer Ball                            90         90
     33333 Soccer Cleets                           5        120
     55555 Shirt                                  20         40
*/


/*Average Queries*/

/*4. Find the name of customers who have bought at least one of the items in their cart.*/
SELECT UNIQUE(Per.ID),Per.NAME FROM Person Per,ShoppingCart Sc WHERE Per.ID=Sc.CUSTOMER_ID AND Sc.PRODUCT_ID in (SELECT Op.PRODUCT_ID FROM Orders O,OrderedProducts Op WHERE O.ORDER_ID=Op.ORDER_ID);
/*
        ID NAME
---------- ------------------------------
    251059 Billy
    368192 Bobby
    592134 James
*/

/*5. List the Total Cost of a customer's Shopping Cart.*/
SELECT Per.ID, SUM(Sc.QUANTITY*Pro.PRICE) TOTAL_COST FROM Person Per,ShoppingCart Sc,Product Pro WHERE Per.ID=Sc.CUSTOMER_ID AND Pro.PRODUCT_ID=Sc.PRODUCT_ID GROUP BY Per.ID;
/*
        ID TOTAL_COST
---------- ----------
    368192        665
    251059        260
    592134       1100
*/

/*6. List all the products that have been delivered to Bangalore.*/
SELECT Pro.PRODUCT_NAME FROM Shipment Sh,Product Pro,OrderedProducts Op WHERE Sh.ORDER_ID=Op.ORDER_ID AND Op.PRODUCT_ID=Pro.PRODUCT_ID AND Sh.TO_ADDRESS='Bangalore';
/*
PRODUCT_NAME
------------------------------
Soccer Ball
Sweater
Soccer Cleets
*/

/*7. List the name of all the customers and sellers' IDs that have more than one address.*/
SELECT Nq.IDs FROM (SELECT Per.ID IDs,COUNT(*) Adc FROM Person Per,Addresses Ad WHERE Per.ID=Ad.ID GROUP BY Per.ID) Nq WHERE Nq.Adc>1;
/*
       IDS
----------
    251059
    256458
    359803
    368192
    432591
    592134
    845523
*/


/*Complex Queries*/

/*8. List all the name of products along with the customer who paid with a VISA card.*/
SELECT Per.ID,Per.NAME,Op.PRODUCT_ID,Pro.PRODUCT_NAME 
FROM Person Per,OrderedProducts Op,Payment P,Product Pro 
WHERE Per.ID=P.Customer_ID AND P.ORDER_ID=Op.ORDER_ID AND Op.PRODUCT_ID=Pro.PRODUCT_ID AND P.CARD_COMP='VISA';
/*
        ID NAME                           PRODUCT_ID PRODUCT_NAME
---------- ------------------------------ ---------- ------------------------------
    368192 Bobby                                1233 IPhone X
    368192 Bobby                               18332 Soccer Ball
    251059 Billy                               25645 Sweater
    251059 Billy                               33333 Soccer Cleets
    592134 James                               66666 Ultra Boost
*/

/*9. List the total spending of all the customers on past orders.*/
WITH P2(Val1,Val2) AS (SELECT Per.IDs,SUM(Per.Indi_Spent) FROM (SELECT P.ID IDs,(Pro.PRICE*Op.Quantity) Indi_Spent FROM Person P,Product Pro,Orders O,OrderedProducts Op WHERE P.ID=O.CUSTOMER_ID AND O.ORDER_ID=Op.ORDER_ID AND Op.Product_ID=Pro.PRODUCT_ID) Per GROUP BY Per.IDs)
SELECT P2.Val1,P1.NAME,P2.Val2 FROM P2,Person P1 WHERE P2.Val1=P1.ID;
/*
        ID NAME                           TOTAL_SPENT
---------- ------------------------------ -----------
    368192 Bobby                                 1761
    251059 Billy                                 1170
    592134 James                                 3060
*/

/*10. List the products which are in a user's shopping cart as well as the wish list.*/
SELECT Per.NAME,Pro.PRODUCT_NAME
FROM Person Per,ShoppingCart Sc,WishList Wl,Product Pro
WHERE Sc.CUSTOMER_ID=Per.ID AND Wl.CUSTOMER_ID=Per.ID AND Wl.CUSTOMER_ID=Sc.CUSTOMER_ID AND Sc.PRODUCT_ID=Wl.PRODUCT_ID AND Pro.PRODUCT_ID=Sc.PRODUCT_ID;
/*
NAME                           PRODUCT_NAME
------------------------------ ------------------------------
Bobby                          IPhone X
Billy                          Soccer Ball
James                          Chinese Collar Shirt
*/