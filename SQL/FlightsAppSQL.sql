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

ALTER TABLE users
ADD COLUMN userRole enum('admin','manager','user') DEFAULT 'user';


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

ALTER TABLE zboruri
ADD column issued_by int NOT NULL;

ALTER TABLE zboruri 
ADD CONSTRAINT issued_by_fk
FOREIGN KEY(issued_by) REFERENCES Users(id);


SELECT departure_location FROM zboruri
WHERE id IN
(SELECT MIN(id) FROM zboruri GROUP BY departure_location);

SELECT landing_location FROM zboruri
WHERE id IN
(SELECT MIN(id) FROM zboruri GROUP BY landing_location);

INSERT INTO zboruri VALUES
(null,"W63257","BlueAir",2,"2022-09-15 07:35:00","2022-09-15 11:11:00","Bucharest(OTP)","Tel-Aviv(TLV)");
INSERT INTO zboruri VALUES
(null,"H64532","Tarom",4,"2022-09-23 8:25:00","2022-09-23 12:05:00","Bucharest(OTP)","Brussels Charleroi(CRL)");

-- (null,"M77392","Tarom",2,"2022-08-31 8:25:00","2022-09-23 12:05:00","Bucharest(OTP)","Madrid(MAD)"),
-- (null,"M77392","Tarom",2,"2022-08-31 8:25:00","2022-08-31 10:27:00","Bucharest(OTP)","Paris Beauvais(BVA)");

select * from zboruri;


INSERT INTO zboruri VALUES
(null,"Y54168","Lufthansa",3,"2022-11-12 17:55:00","2022-12-20 23:38:00","Londra(LDA)","Lisabona(LSB)",1);

SELECT
  zbor.id as zbor_id,zbor.FlightNumber,
  zbor.aeronava as aeronava_id,aeronava.Producator, aeronava.Model,aeronava.nr_max_locuri,
  zbor.Companie,zbor.departure_timestamp,zbor.landing_timestamp, zbor.departure_location,zbor.landing_location
FROM zboruri zbor 
JOIN aeronave aeronava
    ON aeronava.id=zbor.aeronava
 WHERE departure_timestamp>=now();
 

CREATE TABLE rezervari(
	id int NOT NULL auto_increment,
	id_user  int NOT NULL,
	lucuri varchar(150),
	id_zbor int NOT NULL,
    primary key(id),
	FOREIGN KEY(id_user) REFERENCES users(id),
	FOREIGN KEY(id_zbor) REFERENCES zboruri(id)
);

ALTER TABLE rezervari
MODIFY COLUMN lucuri tinyint;

ALTER TABLE rezervari
CHANGE lucuri locuri tinyint;

INSERT INTO rezervari VALUES
(null,2,"01B",5);

select * from rezervari;
select * from rezervari
WHERE id_user=2 AND locuri=16 AND id_zbor=1;




INSERT INTO aeronave VALUES
(null,"Airbus","A330",260),
(null,"Boeing ","747",524),
(null,"Embraer ","E-Jet",124);

INSERT INTO aeronave VALUES
(null,"Antonov","An-24/26",44),
(null,"Airbus","A320 Family",170),
(null,"Boeing ","777",312);

select * from aeronave;








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
  rezervare.id as rezerare_id,rezervare.locuri,zbor.id as zbor_id,zbor.FlightNumber,
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
        
    
    
    

    