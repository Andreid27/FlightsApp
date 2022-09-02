CREATE database FlightsApp;
USE FlightsApp;

CREATE TABLE Users(
	id int NOT NULL auto_increment,
    nume varchar(30),
    mail varchar(50) NOT NULL,
    password varchar(50)NOT NULL,
    primary key(id)
);

SELECT * FROM Users;

ALTER TABLE Users
ADD CONSTRAINT mail_used UNIQUE(mail);


SELECT * FROM users WHERE users.nume="andrei" AND users.mail="andrei@dinca.one" AND users.password="123456"; 
SELECT * FROM users WHERE users.id=21; 

INSERT INTO Users
VALUES(null,"danny","danny@flightapp.com","thisisdanny");


CREATE TABLE aeronave(
	id int NOT NULL auto_increment,
    Producator varchar(30) NOT NULL,
    Model varchar(15) NOT NULL,
    primary key(id)
);
ALTER TABLE aeronave 
ADD COLUMN nr_max_locuri int(4);

CREATE TABLE zboruri(
	id int NOT NULL auto_increment,
	FlightNumber varchar(15) NOT NULL,
    Companie varchar(30) NOT NULL,
    aeronava int NOT NULL,
    departure_timestamp timestamp NOT NULL,
    landing_timestamp timestamp NOT NULL DEFAULT "1990-01-01 00:00:01",
    departure_location varchar(50),
    landing_location varchar(50),
    primary key(id),
	FOREIGN KEY(aeronava) REFERENCES aeronave(id)
);


CREATE TABLE rezervari(
	id int NOT NULL auto_increment,
	id_user  int NOT NULL,
	lucuri varchar(150),
	id_zbor int NOT NULL,
    primary key(id),
	FOREIGN KEY(id_user) REFERENCES users(id),
	FOREIGN KEY(id_zbor) REFERENCES zboruri(id)
);



INSERT INTO aeronave VALUES
(null,"Airbus","A330",260),
(null,"Boeing ","747",524),
(null,"Embraer ","E-Jet",124);

INSERT INTO aeronave VALUES
(null,"Antonov","An-24/26",44),
(null,"Airbus","A320 Family",170),
(null,"Boeing ","777",312);

select * from aeronave;


INSERT INTO zboruri VALUES
(null,"W63257","BlueAir",2,"2022-09-15 07:35:00","2022-09-15 11:11:00","Bucharest(OTP)","Tel-Aviv(TLV)");
INSERT INTO zboruri VALUES
(null,"H64532","Tarom",4,"2022-09-23 8:25:00","2022-09-23 12:05:00","Bucharest(OTP)","Brussels Charleroi(CRL)");
INSERT INTO zboruri VALUES
(null,"M77392","Tarom",2,"2022-08-31 8:25:00","2022-09-23 12:05:00","Bucharest(OTP)","Madrid(MAD)"),
(null,"M77392","Tarom",2,"2022-08-31 8:25:00","2022-08-31 10:27:00","Bucharest(OTP)","Paris Beauvais(BVA)");

select * from zboruri;

INSERT INTO rezervari VALUES
(null,2,"01B",5);

select * from rezervari;

-- LISTA TOATE REZERVARILE COMPLETE sau CU WHERE CU ID USER PT REZERVARILE UNUI UTILIZATOR;
SELECT
  rezervare.id,rezervare.id_user, u.nume,
  zbor.Companie, zbor.departure_location,zbor.departure_timestamp,zbor.landing_location,zbor.landing_timestamp,zbor.FlightNumber, zbor.aeronava,
  aeronava.Producator, aeronava.Model,aeronava.nr_max_locuri
FROM rezervari rezervare 
JOIN users u
  ON u.id=rezervare.id_user
JOIN zboruri zbor
  ON zbor.id=rezervare.id_zbor
JOIN aeronave aeronava
    ON aeronava.id=zbor.aeronava;
-- WHERE u.id=2;
    
    
-- LISTA TOATE  REZERVARILE UNUI UTILIZATOR;
SELECT
  rezervare.id as rezerare_id,rezervare.lucuri,zbor.id as zbor_id,zbor.FlightNumber,
  zbor.aeronava as aeronava_id,aeronava.Producator, aeronava.Model,aeronava.nr_max_locuri,
  zbor.Companie,zbor.departure_timestamp,zbor.landing_timestamp, zbor.departure_location,zbor.landing_location
FROM rezervari rezervare 
JOIN users u
  ON u.id=rezervare.id_user
JOIN zboruri zbor
  ON zbor.id=rezervare.id_zbor
JOIN aeronave aeronava
    ON aeronava.id=zbor.aeronava
WHERE u.id=2;
    
    
    
    
    
    
    
    