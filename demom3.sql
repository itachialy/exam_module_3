create database demom3;
use demom3;

create table class
(class_id int primary key auto_increment,
class_name varchar(50) );

create table student
(id int primary key auto_increment,
name varchar(50),
gender bit,
email varchar(50),
class_id int,
foreign key (class_id) references class(class_id));

insert into class (class_name) values ('Backend-01'), ('Backend-02'),('Frontend-01'),('Fullstack');

insert into student (name,gender,email,class_id)
values 
('Lê Quang Việt',1,'viet@gmail.com',1),
('Huỳnh Anh Khoa',1,'khoa@gmail.com',4),
('Trần Thị Kim Thương',0,'thuong@gmail.com',2),
('Lê Itachi',1,'itachi@gmail.com',2),
('Lê Kim Băng',1,'itach@gmail.com',2),
('Lê Hoàng',1,'itac@gmail.com',2);

select s.id, s.name, s.gender,
            s.email, c.class_id, c.class_name  from student s
            join class c on s.class_id = c.class_id
            ORDER BY s.id ASC;
            select * from class