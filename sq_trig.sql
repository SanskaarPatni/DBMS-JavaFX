CREATE SEQUENCE PERSON_ID
INCREMENT BY 1
START WITH 10000
ORDER;

create or replace package ADD_PERSON as
    procedure ADD_SELLER(pass seller.password%type, id seller.seller_id%type);
    procedure ADD_CUSTOMER(pass customer.password%type, id customer.customer_id%type);
END;
/

create or replace package body ADD_PERSON as
    procedure ADD_SELLER(pass seller.password%type, id seller.seller_id%type) is
    BEGIN
        update seller set PASSWORD=pass where SELLER_ID=id;
    END;
    procedure ADD_CUSTOMER(pass customer.password%type, id customer.customer_id%type) is
    BEGIN
        update customer set PASSWORD=pass where CUSTOMER_ID=id;
    END;
END ADD_PERSON;
/

create or replace trigger PERSON_ADD
after insert on Person
for each row
begin
    if(:NEW.IsSeller='N')then
        insert into Customer(Customer_ID,password) Values(:NEW.id,'BLANK1010');
    elsif(:NEW.IsSeller='Y')then
        insert into seller Values(:NEW.id,'BLANK1010');
    END IF;
END;
/

CREATE SEQUENCE PRODUCT_ID
INCREMENT BY 1
START WITH 10000
ORDER;