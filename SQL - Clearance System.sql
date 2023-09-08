select * from student
select * from course
select * from Employee 
select * from Department
select * from Clearifiers
select * from clearance

create table Student(
StudentID integer Primary Key,
FirstName varchar(60),
LastName  varchar(60),
StudentRank varchar(60),
StudentPassword varchar(60)
CID varchar(10) Foreign key references Course(CID)
)
-------------------------------
create table Department(
DID integer Primary Key,
Dname varchar(60)
)
--------------------------------
create table Course(
CID varchar(10) Primary key,
DID int FOREIGN KEY REFERENCES Department(DID)
)
--------------------------------
create table Employee(
EID integer primary key,
FirstName varchar(60),
LastName  varchar(60),
Position  varchar(60),
EmployeePassword varchar(60),
DID int FOREIGN KEY REFERENCES Department(DID)
)
-----------------------------------
create table clearance(
StudentID int FOREIGN KEY REFERENCES Student(StudentID),
EID int FOREIGN KEY REFERENCES Employee(EID),
CONSTRAINT PK_clearance PRIMARY KEY (StudentID,EID),
ClearanceStatus bit 
Comment varchar(100)
)
-------------------------------------
create table Clearifiers(
EID int FOREIGN KEY REFERENCES Employee(EID),
DID int FOREIGN KEY REFERENCES Department(DID),
CONSTRAINT PK_clearifiers PRIMARY KEY (DID,EID),
Clearancepriority integer
)

-----------------------------------------------

-- Trigger implementation For/After	implementation

create trigger priority_check
on clearifiers 
after insert , update
as
declare @pr int

BEGIN TRY  
     select @pr = ( select Clearancepriority from inserted)
END TRY  
BEGIN CATCH  
     print'Please Spicify Departement'
	 SELECT ERROR_MESSAGE()
	 rollback transaction
END CATCH

if(@pr>3 or @pr<1)
begin 
print'Priority must be between 1 and 3 '
rollback transaction
end







------------------------------------------------ 



create trigger clearance_not_allowed
on clearance
instead of insert 
as
print'Currently Clearance is not Allowed'
rollback transaction




------------------------------------------------------------

--- procedure to calculate clearifiers of a certain department

create procedure count_clearifiers @dept_id int
as
select count(EID) from Clearifiers where DID = @dept_id
go

EXEC count_clearifiers @dept_id = 1