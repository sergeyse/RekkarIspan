CREATE TABLE tabOrder  (id INT NOT NULL AUTO_INCREMENT, 
    Navn VARCHAR(30) NOT NULL,
    Gateadresse VARCHAR(30), 
    Poststed VARCHAR(50) NOT NULL, 
    Telefon VARCHAR(12) NOT NULL,
    Ordrenummer VARCHAR(12) NOT NULL,
    PRIMARY KEY (ID));

INSERT INTO tabOrder values (default, 'Þorkell Pétur Ólafsson', 'Goðatúni 14','GARÐABÆR', '848 5444', '177903'); 

SELECT Navn,Gateadresse,Poststed,Telefon  FROM taborder  WHERE Ordrenummer =389934;

##// convert a column with a date-string to date:

update crud2.javacrud2 set dagsetning = str_to_date(dagsetning.'%d.%m.%Y');