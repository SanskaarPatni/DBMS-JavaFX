ALTER SESSION SET NLS_DATE_FORMAT='YYYY-MM-DD';


INSERT INTO	Person VALUES('368192', 'bob.builder@gmail.com', 'Bobby', '1998-11-30', '9179179170', '2018-11-23', 'N');
INSERT INTO	Person VALUES('251059', 'billy.cat@gmail.com', 'Billy', '1996-03-11', '9179179171', '2018-01-24', 'N');
INSERT INTO	Person VALUES('592134', 'sam.televi@gmail.com', 'James', '1992-05-14', '9179179172', '2017-09-01', 'N');
INSERT INTO	Person VALUES('845523', 'AppleID@apple.com', 'Apple', '2001-11-25', '9179179173', '2017-03-26', 'Y');
INSERT INTO	Person VALUES('359803', 'AdidasID@adidas.com', 'Adidas', '1967-10-12', '9179179174', '2018-01-27', 'Y');
INSERT INTO	Person VALUES('432591', 'NikeID@nike.com', 'Nike', '1912-02-07', '4545454545', '2015-05-15', 'Y');
INSERT INTO Person VALUES('256458', 'CKID@calvin.com', 'Calvin Klein', '1999-01-20', '1234567890', '2019-05-01', 'Y');


INSERT INTO Customer VALUES('368192', 'pbuvsaltxqusalte', 'N');
--password
INSERT INTO Customer VALUES('251059', 'wptgsaltqcvsaltt', 'N');
--wordpass
INSERT INTO Customer VALUES('592134', 'sfeuisaltukyisalt', 'Y');
--secretive


INSERT INTO Seller VALUES('432591', 'SFNOIsaltS334salt');
--SELLER100
INSERT INTO Seller VALUES('845523', 'SFNOIsaltS434salt');
--SELLER200
INSERT INTO Seller VALUES('359803', 'SFNOIsaltS534salt');
--SELLER300
INSERT INTO Seller VALUES('256458', 'SFNOIsaltS634salt');
--SELLER400


INSERT INTO Product VALUES('1233', '500', '123.00', 'IPhone X', '845523', 'A mobile device that helps in long distance calls.', 'Electronics');
INSERT INTO Product VALUES('18332', '90', '90.00', 'Soccer Ball', '432591', 'An awesome Soccer Ball for an energy filled sport.', 'Sports');
INSERT INTO Product VALUES('81234', '200', '10.00', 'Chinese Collar Shirt', '256458', 'A stylish Mens Shirt.', 'Fashion');
INSERT INTO Product VALUES('25645', '20', '50.00', 'Sweater', '359803', 'A gray sweater that will keep you warm and stylish', 'Fashion');
INSERT INTO Product VALUES('22222', '8', '80.00', 'Bayern Jersey', '359803', 'A Bayern Munich home jersey from 2017-2018 season', 'Sports');
INSERT INTO Product VALUES('33333', '5', '120.00', 'Soccer Cleets', '432591', 'Black and gold soccer cleets that are durable', 'Sports');
INSERT INTO Product VALUES('44444', '30', '30.00', 'Jeans', '256458', 'Awesome light blue jeans.', 'Fashion');
INSERT INTO Product VALUES('55555', '20', '40.00', 'Shirt', '432591', 'A nice white shirt with a black logo, just do it.', 'Fashion');
INSERT INTO Product VALUES('66666', '3', '180.00', 'Ultra Boost', '359803', 'Gray ultraboost that are good for running and style', 'Sports');


INSERT INTO ShoppingCart VALUES('368192', '1233', '5');
INSERT INTO ShoppingCart VALUES('368192', '25645', '1');
INSERT INTO ShoppingCart VALUES('251059', '18332', '2');
INSERT INTO ShoppingCart VALUES('251059', '22222', '1');
INSERT INTO ShoppingCart VALUES('592134', '81234', '50');
INSERT INTO ShoppingCart VALUES('592134', '33333', '5');


INSERT INTO WishList VALUES('368192', '1233');
INSERT INTO WishList VALUES('368192', '22222');
INSERT INTO WishList VALUES('251059', '18332');
INSERT INTO WishList VALUES('251059', '44444');
INSERT INTO WishList VALUES('592134', '81234');
INSERT INTO WishList VALUES('592134', '55555');


INSERT INTO Discount VALUES('7878', '0.10', 'Y');
INSERT INTO Discount VALUES('5454', '0.05', 'N');


INSERT INTO Orders VALUES('4444', '368192', '2018-11-25', 'Y');
INSERT INTO Orders VALUES('21344', '251059', '2019-04-08', 'N');
INSERT INTO Orders VALUES('332', '592134', '2012-08-10', 'Y');
INSERT INTO Orders VALUES('12345', '368192', '2012-09-15', 'N');
INSERT INTO Orders VALUES('23456', '251059', '2020-01-15', 'N');
INSERT INTO Orders VALUES('23458', '592134', '2020-02-15', 'N');


INSERT INTO OrderedProducts VALUES('4444', '1233', '5');
INSERT INTO OrderedProducts VALUES('4444', '18332', '10');
INSERT INTO OrderedProducts VALUES('21344', '18332', '10');
INSERT INTO OrderedProducts VALUES('332', '18332', '20');
INSERT INTO OrderedProducts VALUES('12345', '1233', '2' );
INSERT INTO OrderedProducts VALUES('23456','25645', '3' );
INSERT INTO OrderedProducts VALUES('23456', '33333', '1' );
INSERT INTO OrderedProducts VALUES('23458','66666','7' );


INSERT INTO Payment VALUES('4444', '368192', 'Bobby','402356','VISA','2018-11-25');
INSERT INTO Payment VALUES('21344', '251059','Billy','402566','MASTERCARD','2018-11-25');
INSERT INTO Payment VALUES('332','592134', 'James', '612345', 'RUPAY', '2018-11-25');
INSERT INTO Payment VALUES('12345','368192', 'Bobby', '455455', 'MASTERCARD', '2019-11-25');
INSERT INTO Payment VALUES('23456','251059', 'Billy', '556123', 'VISA', '2022-11-25');
INSERT INTO Payment VALUES('23458','592134', 'James', '556448', 'VISA', '2022-11-25');


INSERT INTO Shipment VALUES('1234','4444', 'First City', 'Second City', '10', 'FED-EX');
INSERT INTO Shipment VALUES('2345','21344', 'Mumbai', 'Manipal','5', 'Blue Dart');
INSERT INTO Shipment VALUES('3456','332', 'Lucknow', 'Bangalore','3', 'Track Dart');
INSERT INTO Shipment VALUES('4567','12345', 'Delhi', 'Hyderabad','8', 'Blue Dart');
INSERT INTO Shipment VALUES('5678','23456', 'Kolkata', 'Bangalore','9', 'FED-EX');
INSERT INTO Shipment VALUES('6789','23458', 'Mumbai', 'Lucknow','8', 'Track Dart');


INSERT INTO Returnings VALUES('4444', '1233', '4', 'Screen Broken', NULL);


INSERT INTO Reviews VALUES('368192', '1233', '2', 'It would not turn on');
INSERT INTO Reviews VALUES('592134', '18332', '5', 'It is pretty good');
INSERT INTO Reviews VALUES('592134', '1233', '5', 'It is pretty a good and fast phone.');


INSERT INTO Addresses VALUES('368192','Second City','Uttar Pradesh','India','226010');
INSERT INTO Addresses VALUES('368192','Hyderabad','Telangana','India','606010');
INSERT INTO Addresses VALUES('251059','Manipal','Karnataka','India','576104');
INSERT INTO Addresses VALUES('251059','Bangalore','Karnataka','India','705010');
INSERT INTO Addresses VALUES('592134','Bangalore','Karnataka','India','556010');
INSERT INTO Addresses VALUES('592134','Lucknow','Uttar Pradesh','India','226010');

INSERT INTO Addresses VALUES('845523','Delhi','Delhi','India','225020');
INSERT INTO Addresses VALUES('359803','Delhi','Delhi','India','237020');
INSERT INTO Addresses VALUES('432591','Hyderabad','Telangana','India','656010');
INSERT INTO Addresses VALUES('256458','Bangalore','Karnataka','India','586010');
INSERT INTO Addresses VALUES('845523','Hyderabad','Telangana','India','656010');
INSERT INTO Addresses VALUES('359803','Bangalore','Karnataka','India','566010');
INSERT INTO Addresses VALUES('432591','Delhi','Karnataka','India','266010');
INSERT INTO Addresses VALUES('256458','Hyderabad','Karnataka','India','626010');
INSERT INTO Addresses VALUES('845523','Lucknow','Uttar Pradesh','India','226010');
INSERT INTO Addresses VALUES('359803','Hyderabad','Telangana','India','562322');
INSERT INTO Addresses VALUES('432591','Pune','Maharashtra','India','566010');
INSERT INTO Addresses VALUES('256458','Vihakhapatnam','Andhra Pradesh','India','458010');


INSERT INTO Cards VALUES('368192','Bobby','402356','VISA','2018-11-25');
INSERT INTO Cards VALUES('368192','Bobby','455455','MASTERCARD','2019-11-25');
INSERT INTO Cards VALUES('251059','Billy','402566','MASTERCARD','2018-11-25');
INSERT INTO Cards VALUES('251059','Billy','556123','VISA','2022-11-25');
INSERT INTO Cards VALUES('592134','James','612345','RUPAY','2018-11-25');
INSERT INTO Cards VALUES('592134','James','556448','VISA','2022-11-25');
