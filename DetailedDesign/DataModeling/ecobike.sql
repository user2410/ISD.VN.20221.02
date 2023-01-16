CREATE TABLE Bike (
	id integer PRIMARY KEY,
	licensePlate text not null,
	nSaddles integer not null,
	nPedals integer not null,
	nRearSeats integer not null,
	price integer not null
);

PRAGMA foreign_keys = ON;

CREATE TABLE StandardBike (
	id integer PRIMARY KEY REFERENCES Bike(id)
);

CREATE TABLE EBike (
	id integer PRIMARY KEY REFERENCES Bike(id),
	batteryLife float not null
);

CREATE TABLE TwinBike (
	id integer PRIMARY KEY REFERENCES Bike(id)
);

CREATE TABLE Dock (
	id integer PRIMARY KEY,
	name text NOT NULL,
	address text NOT NULL,
	area float NOT NULL
);

CREATE TABLE Lock (
	barCode text PRIMARY KEY,
	bikeId integer REFERENCES Bike(id),
	dockId integer NOT NULL REFERENCES Dock(id),
	status text NOT NULL default 'RELEASED'
);

CREATE TABLE Rental(
	id integer PRIMARY KEY,
	bikeId integer NOT NULL REFERENCES Bike(id),
	deposit integer NOT NULL,
	startTime integer NOT NULL,
	returnTime integer
);

CREATE TABLE Invoice(
	id integer PRIMARY KEY,
	rentalId integer NOT NULL REFERENCES Rental(id),
	totalAmount integer NOT NULL
);

CREATE TABLE Card(
	id integer PRIMARY KEY,
	cardCode text NOT NULL,
	owner text NOT NULL,
	cvvCode text NOT NULL,
	dateExpired integer NOT NULL
);

CREATE TABLE PaymentTransaction(
	id integer PRIMARY KEY,
	content text NOT NULL,
	method text NOT NULL DEFAULT 'CARD',
	createdAt integer NOT NULL,
	cardId 
);
