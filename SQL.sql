-- T?o c? s? d? li?u
CREATE DATABASE test_spring_3;
GO

-- S? d?ng c? s? d? li?u v?a t?o
USE test_spring_3;
GO

-- T?o b?ng tbl_user
CREATE TABLE tbl_user (
    _id INT PRIMARY KEY IDENTITY(1,1),
    _name NVARCHAR(50),
    _uid VARCHAR(20),
    _pwd VARCHAR(200),
    _usr_type INT
);
GO

-- T?o b?ng tbl_customer
CREATE TABLE tbl_customer (
    _id INT PRIMARY KEY IDENTITY(1,1),
    _name NVARCHAR(50),
    _phone VARCHAR(15),
    _usrId INT,
    FOREIGN KEY (_usrId) REFERENCES tbl_user(_id)
);
GO
