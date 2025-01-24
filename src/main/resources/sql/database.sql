create database yougurtprodution;

use yougurtprodution;

create table user(
    username varchar(300) primary key,
    password varchar(300),
    email varchar(300)
);


create table Customer(
     cust_id varchar(10) primary key ,
     name varchar(100),
     nic varchar(100),
     email varchar(100),
     phone varchar(20)
);


create table Employee (
    Emp_ID varchar(20)primary key,
    Emp_Name varchar(100),
    Emp_Nic varchar(20),
    Emp_Email varchar(255),
    Emp_Phone int
);

create table Supplier(
    Sup_ID varchar(100) primary key,
    Sup_Name varchar(100),
    Sup_Nic varchar(20),
    Sup_Email varchar(255),
    Sup_Phone int
);

create table Material(
    Mat_ID varchar(100) primary key,
    Mat_Name varchar(100),
    Qty int,
    Price int

);

create table Production_mix_recip(
    Prod_Name varchar(200) primary key,
    Milk_qty int,
    Sugur_qty int,
    Jeliy_qty int

);

create table Inventory(
    In_ID varchar(100) primary key,
    Item_Type varchar(50),
    Item_Description varchar(250),
    Qty int,
    Prod_ID VARCHAR(50)
);

create table Cash_Book(
    CB_No varchar(50) primary key ,
    Sup_ID varchar(50),
    Mat_ID varchar(50),
    In_ID varchar(100),
    Description varchar(50),
    Qty int,
    Amount decimal(10,0),
    Transaction_Date date,
    foreign key (Sup_ID) references Supplier(Sup_ID),
    foreign key (Mat_ID) references Material(Mat_ID)


);

create table Production(
    Prod_ID varchar(50) primary key,
    Pro_Name varchar(100),
    Qty decimal(10,1)

);

create table Production(

   Prod_ID varchar(50) primary key,
   Pro_Name varchar(100),
   Qty decimal(10,1),
   Prod_Name varchar(200)

);



create table Material_Usage(
    MatUs_ID varchar(20)primary key,
    Prod_ID varchar(50),
    Mat_Milk varchar(100),
    Mat_Suguer varchar(100),
    Mat_Gelatin varchar(100),
    foreign key (Prod_ID) references Production(Prod_ID)

);

create table Packing(
    Pac_ID varchar(50) primary key,
    Prod_ID varchar(50),
    Emp_ID varchar(50),
    Packing_Type  varchar(150),
    Packing_Description varchar(200),
    Pack_date date,
    Expire_date date,
    Qty decimal(10,2),
    foreign key  (Prod_ID) references Production(Prod_ID),
    foreign key  (Emp_ID) references  Employee(Emp_ID)

);

create table Stock(
    Stock_ID varchar(100) primary key,
    Pac_ID varchar(50),
    Product_Name varchar(255),
    Qty decimal (10,2),
    Manfac_date date,
    Expire_date date,
    Pack_Type varchar(50),
    Unit_Price VARCHAR(50),

    foreign key (Pac_ID) references Packing (Pac_ID)
);

create table Orders(
    order_id    varchar(10) primary key ,
    cust_id varchar(10),
    order_date  date,
    foreign key (cust_id) references Customer (cust_id)
);

create table OrderDetails(
    order_id varchar(10),
    stock_id varchar(10),
    quantity int,
    price  decimal(10, 2),
    foreign key (order_id) references Orders (order_id),
    foreign key (stock_id) references Stock (Stock_ID)
);

