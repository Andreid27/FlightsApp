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


INSERT INTO aeronave VALUES
(null,"Airbus","A330",260),
(null,"Boeing ","747",524),
(null,"Embraer ","E-Jet",124);

INSERT INTO aeronave VALUES
(null,"Antonov","An-24/26",44),
(null,"Airbus","A320 Family",170),
(null,"Boeing ","777",312);

select * from aeronave;



CREATE TABLE zboruri(
	id int NOT NULL auto_increment,
	FlightNumber varchar(15) NOT NULL,
    Companie varchar(30) NOT NULL,
    aeronava int NOT NULL,
    departure_timestamp timestamp NOT NULL,
    landing_timestamp timestamp NOT NULL DEFAULT "1990-01-01 00:00:01",
    departure_location varchar(50),
    landing_location varchar(50),
    issued_by int NOT NULL,
    pret float NOT NULL,
    primary key(id),
	FOREIGN KEY(aeronava) REFERENCES aeronave(id),
    FOREIGN KEY(issued_by) REFERENCES Users(id)
);

ALTER TABLE zboruri
ADD column status_CIN enum("closed","open") DEFAULT "closed";


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
	locuri tinyint,
	id_zbor int NOT NULL,
    pret float NOT NULL,
	transaction_number varchar(20) DEFAULT null,
    primary key(id),
	FOREIGN KEY(id_user) REFERENCES users(id),
	FOREIGN KEY(id_zbor) REFERENCES zboruri(id)
);


ALTER TABLE rezervari
ADD COLUMN pret float NOT NULL,
ADD COLUMN transaction_number varchar(20) DEFAULT null;


INSERT INTO rezervari VALUES
(null,2,"01B",5);

select * from rezervari;
select * from rezervari
WHERE id_user=2 AND locuri=16 AND id_zbor=1;



CREATE TABLE persoane_CIN(
	id int NOT NULL auto_increment,
    nume varchar(30) NOT NULL,
    prenume varchar(30) NOT NULL,
    loc varchar(5),
    primary key(id)
);

ALTER TABLE persoane_CIN
ADD COLUMN id_rezervare int NOT NULL;


ALTER TABLE persoane_CIN
ADD constraint FOREIGN KEY(id_rezervare) REFERENCES rezervari(id);

select * from persoane_CIN;




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
  rezervare.id as rezerare_id,rezervare.locuri,rezervare.pret,zbor.id as zbor_id,zbor.FlightNumber,
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
        
        
        
        
	





CREATE TABLE istoric_zboruri(
	id int NOT NULL,
	FlightNumber varchar(15) NOT NULL,
    Companie varchar(30) NOT NULL,
    departure_timestamp timestamp NOT NULL,
    landing_timestamp timestamp NOT NULL DEFAULT "1990-01-01 00:00:01",
    departure_location varchar(50),
    landing_location varchar(50),
    issued_by int NOT NULL,
    primary key(id),
    FOREIGN KEY(issued_by) REFERENCES Users(id)
);

SELECT * FROM istoric_zboruri;


CREATE TABLE istoric_rezervari(
	id int NOT NULL,
	id_user  int NOT NULL,
	locuri tinyint,
	id_zbor int NOT NULL,
    pret float NOT NULL,
	transaction_number varchar(20),
    primary key(id),
	FOREIGN KEY(id_user) REFERENCES users(id),
	FOREIGN KEY(id_zbor) REFERENCES istoric_zboruri(id)
); 

SELECT * FROM istoric_rezervari;



SELECT
  zbor.id as zbor_id,zbor.FlightNumber,
  zbor.aeronava as aeronava_id,aeronava.Producator, aeronava.Model,aeronava.nr_max_locuri,
  zbor.Companie,zbor.departure_timestamp,zbor.landing_timestamp, zbor.departure_location,zbor.landing_location
FROM zboruri zbor 
JOIN aeronave aeronava
    ON aeronava.id=zbor.aeronava WHERE departure_location=Bucharest(OTP) AND landing_location=Tel-Aviv(TLV);

	


DELIMITER //
CREATE PROCEDURE update_istoric_zboruri()
BEGIN
	DECLARE id_i int;
	DECLARE FlightNumber_i varchar(15);
    DECLARE Companie_i varchar(30);
    DECLARE departure_timestamp_i timestamp;
    DECLARE landing_timestamp_i timestamp;
    DECLARE departure_location_i varchar(50);
    DECLARE landing_location_i varchar(50);
    DECLARE issued_by_i int ;
    
    -- variabila de tip semafor
    DECLARE exista_inregistrari_cursor TINYINT DEFAULT 1;
    
   -- declaram cursorul in care salvam setul de date dintr-un select
   DECLARE cursor_zboruri_trecut CURSOR FOR
		SELECT 
		id,FlightNumber,Companie,departure_timestamp,landing_timestamp,departure_location,landing_location,issued_by
        FROM zboruri
        WHERE landing_timestamp<CURDATE();
        
        -- declaram handler pt cand nu mai avem inregistrari in cursor
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET exista_inregistrari_cursor = 0;
        
        -- deschidem cursor cursor_zboruri_trecut
        OPEN cursor_zboruri_trecut;
        
        zboruri: LOOP
		-- salvam valorile din inregistrarea curenta in varibilele locale
        FETCH cursor_zboruri_trecut INTO id_i,FlightNumber_i,Companie_i,departure_timestamp_i,landing_timestamp_i,departure_location_i,landing_location_i,issued_by_i;
        
        IF exista_inregistrari_cursor = 0 THEN
			LEAVE zboruri;-- daca nu exista inregistrari iesi din bucla
		ELSE 
			INSERT IGNORE istoric_zboruri VALUES (id_i,FlightNumber_i,Companie_i,departure_timestamp_i,landing_timestamp_i,departure_location_i,landing_location_i,issued_by_i); -- daca exista insereaza datele din variabila
		END IF;
        END LOOP zboruri;
        CLOSE cursor_zboruri_trecut;
        
		CALL update_istoric_rezervari();
        
        
        DELETE FROM zboruri WHERE landing_timestamp<CURDATE();
        
END
//


CALL update_istoric_zboruri();


DELIMITER //
CREATE PROCEDURE update_istoric_rezervari()
BEGIN
	DECLARE id1 int;
	DECLARE id_user1 int;
    DECLARE locuri1 tinyint;
	DECLARE id_zbor1 int;
	DECLARE pret1 float;
	DECLARE transaction_number1 varchar(20);

    
    -- variabila de tip semafor
       -- variabila de tip semafor
    DECLARE exista_inregistrari_cursor TINYINT DEFAULT 1;
    
   -- declaram cursorul in care salvam setul de date dintr-un select
   DECLARE cursor_zboruri_trecut CURSOR FOR
		SELECT 
		id,
		id_user,
		locuri,
		id_zbor,
		pret,
		transaction_number
        FROM rezervari 
        WHERE id_zbor IN ( SELECT id from zboruri WHERE landing_timestamp<CURDATE()) ;
        
        -- declaram handler pt cand nu mai avem inregistrari in cursor
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET exista_inregistrari_cursor = 0;
        
        -- deschidem cursor cursor_zboruri_trecut
        OPEN cursor_zboruri_trecut;
        
        zboruri: LOOP
		-- salvam valorile din inregistrarea curenta in varibilele locale
        FETCH cursor_zboruri_trecut INTO  id1,id_user1,locuri1,id_zbor1,pret1,transaction_number1;
        
        IF exista_inregistrari_cursor = 0 THEN
			LEAVE zboruri;-- daca nu exista inregistrari iesi din bucla
		ELSE 
			INSERT IGNORE istoric_rezervari VALUES (id1,id_user1,locuri1,id_zbor1,pret1,transaction_number1); -- daca exista insereaza datele din variabila
		END IF;
        END LOOP zboruri;
        CLOSE cursor_zboruri_trecut;
        
        DELETE FROM rezervari WHERE id_zbor IN ( SELECT id from zboruri WHERE landing_timestamp<CURDATE()) ;
END
//
    