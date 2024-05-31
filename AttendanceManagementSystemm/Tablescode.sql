DROP TABLE record_Attendance ;
DROP TABLE Attendance ;
DROP TABLE Teach_course ;
drop table record_Course;
drop table Student;
drop table Course ;
DROP TABLE  instructor ;
DROP TABLE periods ;
DROP TABLE adminn ;

drop SEQUENCE Student_SEQ ;
drop SEQUENCE instructor_SEQ;
drop SEQUENCE Attendance_SEQ;
drop SEQUENCE periods_SEQ;
drop SEQUENCE adminn_SEQ;

CREATE  SEQUENCE Student_SEQ
    start with 1
   ;
   
CREATE  SEQUENCE instructor_SEQ
    start with 1
   ;
Create SEQUENCE    Attendance_SEQ
start with 1
   ;
   Create SEQUENCE    periods_SEQ
start with 1
   ;
   Create SEQUENCE    adminn_SEQ
start with 1
   ;

CREATE TABLE Student (
  id_stud BIGINT PRIMARY KEY DEFAULT  NEXTVAL('Student_SEQ') ,
  FirstName VARCHAR(255) ,
  LastName VARCHAR(255) ,
  email VARCHAR(255) NOT NULL,
  username VARCHAR(255) ,
  Birthday DATE NOT NULL,
  RegisterDay DATE ,
  Password_stud VARCHAR(255) ,
  CurrentLevel VARCHAR(255),
 CONSTRAINT UC_student UNIQUE (id_stud,email,username)
	
);
create TABLE instructor(
	id_instr BIGINT PRIMARY KEY DEFAULT  NEXTVAL('instructor_SEQ') ,
  FirstName VARCHAR(255) ,
  LastName VARCHAR(255) ,
  email VARCHAR(255) NOT NULL,
  username VARCHAR(255) ,
  Birthday DATE NOT NULL,
  
  Password_instr VARCHAR(255) ,
	department VARCHAR(255),
	discription VARCHAR(255),
	CONSTRAINT UC_instructor UNIQUE (id_instr,email,username)
);




Create Table Course(
	id_Course VARCHAR(10) PRIMARY KEY ,
	name_c VARCHAR(255) NOT NULL,
	year_c VARCHAR(255) NOT NULL
);

Create Table record_Course(
     id_stud_record BIGINT ,
	id_Course_record VARCHAR(10) ,
	CONSTRAINT FK_id_stud_record FOREIGN key(id_stud_record ) REFERENCES Student(id_stud) ON DELETE CASCADE,
	CONSTRAINT FK_id_Course_record FOREIGN key(id_Course_record ) REFERENCES Course(id_Course) ON DELETE CASCADE
);
Create Table Teach_course(
	 id_instr_Teach BIGINT ,
	id_Course_Teach VARCHAR(10) ,
	CONSTRAINT FK_id_instr_Teach FOREIGN key(id_instr_Teach ) REFERENCES instructor(id_instr) ON DELETE CASCADE,
	CONSTRAINT FK_id_Course_Teach FOREIGN key(id_Course_Teach ) REFERENCES Course(id_Course) ON DELETE CASCADE

);
create Table Attendance(
	enroll_no BIGINT  PRIMARY KEY DEFAULT  NEXTVAL('Attendance_SEQ'),
	class_date varChar(255),
	Id_student BIGINT,
	ID_course VARCHAR(10),
	CONSTRAINT FK_id_stud_Attendance FOREIGN key(Id_student ) REFERENCES Student(id_stud) ON DELETE CASCADE,
	CONSTRAINT FK_id_Course_Attendance FOREIGN key(ID_course ) REFERENCES Course(id_Course) ON DELETE CASCADE

);

create Table periods (
id_periods BIGINT  PRIMARY KEY DEFAULT  NEXTVAL('periods_SEQ'),
week	int,
	state_Student char
);

create Table record_Attendance (
id_periods_rd BIGINT,
enroll_no_rd BIGINT ,
id_instr_rd BIGINT ,
	
CONSTRAINT FK_id_id_periods FOREIGN key(id_periods_rd ) REFERENCES periods(id_periods) ON DELETE CASCADE,
CONSTRAINT FK_enroll_no FOREIGN key(enroll_no_rd ) REFERENCES Attendance(enroll_no) ON DELETE CASCADE,
CONSTRAINT FK_id_instr_Teach FOREIGN key(id_instr_rd ) REFERENCES instructor(id_instr) ON DELETE CASCADE
);



create table  adminn(
id_admin int PRIMARY KEY DEFAULT  NEXTVAL('adminn_SEQ') ,
  FirstName VARCHAR(255) ,
  LastName VARCHAR(255) ,
  email VARCHAR(255) NOT NULL,
  username VARCHAR(255) ,
  RegisterDay DATE ,
  Password_admin VARCHAR(255) ,
 CONSTRAINT UC_admin UNIQUE (id_admin,email,username));
 





