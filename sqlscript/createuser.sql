create table defaultdb.user(
	id int not null AUTO_INCREMENT,
	LastName varchar(255),
	FirstName varchar(255),
	email varchar(255),
	password varchar(255),
    CONSTRAINT PK_Person PRIMARY KEY (id)
); 

insert into defaultdb.user (LastName,FirstName,email,password) values ('admin','admin','admin@live','abc123');