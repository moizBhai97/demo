DROP DATABASE SDA
CREATE DATABASE SDA
USE SDA
use dbLab

ALTER TABLE APPOINTMENT_SLOTS
DROP CONSTRAINT APPOINTMENT_SLOTS_PK, APPOINTMENT_SLOTS_FK
DROP TABLE APPOINTMENT_SLOTS

ALTER TABLE APPOINTMENTS
DROP CONSTRAINT APPOINTMENTS_PK, APPOINTMENTS_DFK, APPOINTMENTS_PFK
DROP TABLE APPOINTMENTS

ALTER TABLE REVIEWS
DROP CONSTRAINT REVIEWS_PK, REVIEWS_DFK, REVIEWS_PFK
DROP TABLE REVIEWS

ALTER TABLE COMPLAINTS
DROP CONSTRAINT COMPLAINTS_PK, COMPLAINTS_DFK, COMPLAINTS_PFK
DROP TABLE COMPLAINTS

ALTER TABLE CERTIFICATIONS
DROP CONSTRAINT CERTIFICATIONS_PK, CERTIFICATIONS_FK
DROP TABLE CERTIFICATIONS

ALTER TABLE SERVICES
DROP CONSTRAINT SERVICES_PK, SERVICES_FK
DROP TABLE SERVICES

ALTER TABLE DOCTORS
DROP CONSTRAINT DOCTORS_PK
DROP TABLE DOCTORS

ALTER TABLE PATIENT_HISTORY
DROP CONSTRAINT PATIENT_HISTORY_PK, PATIENT_HISTORY_FK
DROP TABLE PATIENT_HISTORY

ALTER TABLE PATIENTS
DROP CONSTRAINT PATIENTS_PK
DROP TABLE PATIENTS

CREATE TABLE PATIENTS
(
	NAME VARCHAR(50) NOT NULL,
	ID INT NOT NULL,
	EMAIL VARCHAR(50) NOT NULL,
	PASSWORD VARCHAR(50) NOT NULL,
	DOB DATE NOT NULL, 
	PHONE_NUMBER VARCHAR(11) NOT NULL CHECK (PHONE_NUMBER LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	GENDER VARCHAR(6) NOT NULL CHECK (GENDER IN ('Male', 'Female')),

	CONSTRAINT PATIENTS_PK PRIMARY KEY(ID),
)

CREATE TABLE PATIENT_HISTORY
(
	ID INT NOT NULL,
	TYPE VARCHAR(50) NOT NULL,
	DESCRIPTION VARCHAR(50) NOT NULL,

	CONSTRAINT PATIENT_HISTORY_PK PRIMARY KEY (ID),
	CONSTRAINT PATIENT_HISTORY_FK FOREIGN KEY (ID) REFERENCES PATIENTS (ID) ON UPDATE CASCADE ON DELETE CASCADE,
)

CREATE TABLE DOCTORS
(
	NAME VARCHAR(50) NOT NULL,
	ID INT NOT NULL,
	EMAIL VARCHAR(50) NOT NULL,
	PASSWORD VARCHAR(50) NOT NULL,
	DOB DATE NOT NULL, 
	PHONE_NUMBER VARCHAR(11) NOT NULL CHECK (PHONE_NUMBER LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	GENDER VARCHAR(6) NOT NULL CHECK (GENDER IN ('Male', 'Female')),
	
	-- DOCTOR DETAILS --
	SPECIALIZATION VARCHAR(50) NOT NULL,
	DESCRIPTION VARCHAR(255) NOT NULL,
	LOCATION VARCHAR(50) NOT NULL,
	STATS DECIMAL(5, 2) NOT NULL CHECK (STATS BETWEEN 0 AND 100),
	PATIENTS_TREATED INT NOT NULL,
	EXPERIENCE INT NOT NULL,
	RATING DECIMAL(2, 1) NOT NULL CHECK (RATING BETWEEN 0 AND 5),
	WORKING_HOURS VARCHAR(50) NOT NULL,
	FEE INT NOT NULL,
	AVAILABILITY VARCHAR(50),

	CONSTRAINT DOCTORS_PK PRIMARY KEY(ID),
)

CREATE TABLE SERVICES
(
	DOCTOR_ID INT NOT NULL,
	DESCRIPTION VARCHAR(50) NOT NULL,

	CONSTRAINT SERVICES_PK PRIMARY KEY(DOCTOR_ID, DESCRIPTION),
	CONSTRAINT SERVICES_FK FOREIGN KEY(DOCTOR_ID) REFERENCES DOCTORS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
)

CREATE TABLE CERTIFICATIONS
(
	ID INT IDENTITY(1, 1),
	DOCTOR_ID INT NOT NULL,
	TYPE VARCHAR(50) NOT NULL,
	DESCRIPTION VARCHAR(50) NOT NULL,
	APPROVED_STATUS VARCHAR(8) NOT NULL CHECK (APPROVED_STATUS IN ('Approved', 'Pending', 'Rejected')),
	ISSUE_DATE DATE NOT NULL,
	EXPIRY_DATE DATE NOT NULL,

	CONSTRAINT CERTIFICATIONS_PK PRIMARY KEY(ID),
	CONSTRAINT CERTIFICATIONS_FK FOREIGN KEY(DOCTOR_ID) REFERENCES DOCTORS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
)

CREATE TABLE COMPLAINTS
(
	ID INT IDENTITY(1, 1),
	DOCTOR_ID INT NOT NULL,
	PATIENT_ID INT NOT NULL,
	REASON VARCHAR(50) NOT NULL,
	DESCRIPTION VARCHAR(255),

	CONSTRAINT COMPLAINTS_PK PRIMARY KEY(ID),
	CONSTRAINT COMPLAINTS_DFK FOREIGN KEY(DOCTOR_ID) REFERENCES DOCTORS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT COMPLAINTS_PFK FOREIGN KEY(PATIENT_ID) REFERENCES PATIENTS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
)

CREATE TABLE REVIEWS
(
	ID INT IDENTITY(1, 1),
	DOCTOR_ID INT NOT NULL,
	PATIENT_ID INT NOT NULL,
	COMMENT VARCHAR(255) NOT NULL,
	EXPERIENCE Float NOT NULL CHECK (EXPERIENCE BETWEEN 0 AND 5),
	checkupRating Float NOT NULL CHECK (checkupRating BETWEEN 0 AND 5),
	environmentRating Float NOT NULL CHECK (environmentRating BETWEEN 0 AND 5),
	staffRating Float NOT NULL CHECK (staffRating BETWEEN 0 AND 5),
	RECOMMEND bit not null,

	CONSTRAINT REVIEWS_PK PRIMARY KEY(ID),
	CONSTRAINT REVIEWS_DFK FOREIGN KEY(DOCTOR_ID) REFERENCES DOCTORS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT REVIEWS_PFK FOREIGN KEY(PATIENT_ID) REFERENCES PATIENTS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
)

CREATE TABLE APPOINTMENTS
(
	ID INT IDENTITY(1, 1),
	DOCTOR_ID INT NOT NULL,
	PATIENT_ID INT NOT NULL,
	DATE DATE NOT NULL,
	TIME TIME NOT NULL,
	PROBLEM VARCHAR(300) NOT NULL,
	UPDATE_REASON VARCHAR(300),
	STATUS VARCHAR(10) NOT NULL CHECK(STATUS IN ('Booked', 'Completed', 'Cancelled')),

	CONSTRAINT APPOINTMENTS_PK PRIMARY KEY(ID),
	CONSTRAINT APPOINTMENTS_DFK FOREIGN KEY(DOCTOR_ID) REFERENCES DOCTORS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT APPOINTMENTS_PFK FOREIGN KEY(PATIENT_ID) REFERENCES PATIENTS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
)

CREATE TABLE APPOINTMENT_SLOTS
(
	DOCTOR_ID INT NOT NULL,
	DATE DATE NOT NULL,
	TIME TIME NOT NULL,
	AVAILABLE BIT NOT NULL,

	CONSTRAINT APPOINTMENT_SLOTS_PK PRIMARY KEY(DOCTOR_ID, DATE, TIME),
	CONSTRAINT APPOINTMENT_SLOTS_FK FOREIGN KEY(DOCTOR_ID)REFERENCES DOCTORS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
)

-- Insert data into PATIENTS table
INSERT INTO PATIENTS (NAME, ID, EMAIL, PASSWORD, DOB, PHONE_NUMBER, GENDER)
VALUES ('Ali Ahmed', 1, 'ali.ahmed@example.com', 'password123', '1990-01-01', '03001234567', 'Male'),
       ('Fatima Khan', 2, 'fatima.khan@example.com', 'password456', '1992-02-02', '03007654321', 'Female'),
       ('Zainab Raza', 3, 'zainab.raza@example.com', 'password789', '1994-03-03', '03009876543', 'Female'),
       ('Usman Ali', 4, 'usman.ali@example.com', 'password321', '1996-04-04', '03005678901', 'Male'),
       ('Ayesha Tariq', 5, 'ayesha.tariq@example.com', 'password654', '1998-05-05', '03003456789', 'Female');

-- Insert data into PATIENT_HISTORY table
INSERT INTO PATIENT_HISTORY (ID, TYPE, DESCRIPTION)
VALUES (1, 'Allergy', 'Peanut allergy'),
       (2, 'Diabetes', 'Type 2 diabetes'),
       (3, 'Asthma', 'Mild asthma'),
       (4, 'Hypertension', 'High blood pressure'),
       (5, 'Anemia', 'Iron deficiency anemia');

-- Insert data into DOCTORS table
INSERT INTO DOCTORS (NAME, ID, EMAIL, PASSWORD, DOB, PHONE_NUMBER, GENDER, SPECIALIZATION, DESCRIPTION, LOCATION, STATS, PATIENTS_TREATED, EXPERIENCE, RATING, WORKING_HOURS, FEE, AVAILABILITY)
VALUES ('Dr. Asim Malik', 101, 'dr.asim@example.com', 'drpassword123', '1980-06-06', '03001231234', 'Male', 'Cardiologist', 'Expert in treating heart diseases', 'Karachi', 98.5, 1200, 15, 4.5, '9:00-17:00', 2000, 'Available'),
       ('Dr. Saima Iqbal', 102, 'dr.saima@example.com', 'drpassword456', '1982-07-07', '03002342345', 'Female', 'Dermatologist', 'Skin care specialist', 'Lahore', 95.0, 1500, 10, 4.7, '10:00-18:00', 1500, 'Available'),
       ('Dr. Tariq Mahmood', 103, 'dr.tariq@example.com', 'drpassword789', '1978-08-08', '03003453456', 'Male', 'Orthopedic Surgeon', 'Expert in bone and joint surgeries', 'Islamabad', 99.0, 2000, 20, 4.9, '8:00-16:00', 2500, 'Available'),
       ('Dr. Amina Zafar', 104, 'dr.amina@example.com', 'drpassword321', '1985-09-09', '03004564567', 'Female', 'Pediatrician', 'Child health specialist', 'Karachi', 97.0, 1000, 8, 4.6, '11:00-19:00', 1200, 'Available'),
       ('Dr. Kamran Ahmed', 105, 'dr.kamran@example.com', 'drpassword654', '1983-10-10', '03005675678', 'Male', 'Dentist', 'Expert in dental care', 'Lahore', 96.5, 800, 12, 4.8, '9:00-17:00', 1000, 'Available');

-- Insert data into SERVICES table
INSERT INTO SERVICES (DOCTOR_ID, DESCRIPTION)
VALUES (101, 'Heart checkup'),
       (102, 'Skin treatment'),
       (103, 'Joint replacement surgery'),
       (104, 'Child vaccination'),
       (105, 'Dental cleaning');

-- Insert data into CERTIFICATIONS table
INSERT INTO CERTIFICATIONS (DOCTOR_ID, TYPE, DESCRIPTION, APPROVED_STATUS, ISSUE_DATE, EXPIRY_DATE)
VALUES (101, 'MBBS', 'Bachelor of Medicine, Bachelor of Surgery', 'Approved', '2000-01-01', '2030-01-01'),
       (102, 'MBBS', 'Bachelor of Medicine, Bachelor of Surgery', 'Approved', '2002-01-01', '2032-01-01'),
       (103, 'MBBS', 'Bachelor of Medicine, Bachelor of Surgery', 'Approved', '1998-01-01', '2028-01-01'),
       (104, 'MBBS', 'Bachelor of Medicine, Bachelor of Surgery', 'Approved', '2005-01-01', '2035-01-01'),
       (105, 'MBBS', 'Bachelor of Medicine, Bachelor of Surgery', 'Approved', '2003-01-01', '2033-01-01');

-- Insert data into COMPLAINTS table
INSERT INTO COMPLAINTS (DOCTOR_ID, PATIENT_ID, REASON, DESCRIPTION)
VALUES (101, 1, 'Long waiting time', 'Had to wait for more than an hour for the appointment'),
       (102, 2, 'Misdiagnosis', 'The initial diagnosis was incorrect, causing unnecessary stress'),
       (103, 3, 'Rude staff', 'The staff at the clinic were unprofessional and rude'),
       (104, 4, 'Overcharging', 'The fee charged was higher than what was mentioned earlier'),
       (105, 5, 'Unhygienic conditions', 'The clinic was not clean and hygienic');

-- Insert data into REVIEWS table
INSERT INTO REVIEWS (DOCTOR_ID, PATIENT_ID, COMMENT, EXPERIENCE, checkupRating, environmentRating, staffRating, RECOMMEND)
VALUES (101, 1, 'Overall good experience, but the waiting time was too long.', 4.0, 4.5, 3.5, 4.0, 1),
       (102, 2, 'The doctor was knowledgeable, but the initial diagnosis was incorrect.', 3.5, 4.0, 4.0, 3.5, 1),
       (103, 3, 'Great doctor, but the staff at the clinic were rude.', 4.5, 5.0, 4.0, 3.0, 0),
       (104, 4, 'Good pediatrician, but the fee charged was higher than expected.', 3.5, 4.0, 3.5, 4.0, 1),
       (105, 5, 'The dentist was skilled, but the clinic was unhygienic.', 4.0, 4.5, 3.0, 4.0, 0);

-- Insert data into APPOINTMENTS table
INSERT INTO APPOINTMENTS (DOCTOR_ID, PATIENT_ID, DATE, TIME, STATUS, PROBLEM)
VALUES (101, 1, '2023-06-01', '10:00:00', 'Booked', 'My Heart is aching.'),
       (102, 2, '2023-06-02', '11:00:00', 'Completed', 'My skin is dry.'),
       (103, 3, '2023-06-03', '12:00:00', 'Cancelled', 'My bones are weak.'),
       (104, 4, '2023-06-04', '13:00:00', 'Booked', 'The kid cant walk no more.'),
       (105, 5, '2023-06-05', '14:00:00', 'Completed', 'My teeth are falling');

-- Insert data into APPOINTMENT_SLOTS table
INSERT INTO APPOINTMENT_SLOTS (DOCTOR_ID, DATE, TIME, AVAILABLE)
VALUES (101, '2023-06-01', '10:00:00', 0),
       (102, '2023-06-02', '11:00:00', 0),
       (103, '2023-06-03', '12:00:00', 1),
       (104, '2023-06-04', '13:00:00', 0),
       (105, '2023-06-05', '14:00:00', 0);

Select * from APPOINTMENTS;

SELECT (SELECT COUNT(ID) FROM Appointments WHERE DOCTOR_ID = 101 AND STATUS = 'Completed') as Patients, NAME, SPECIALIZATION, DESCRIPTION, LOCATION, EXPERIENCE, WORKING_HOURS, FEE, AVAILABILITY FROM Doctors WHERE ID = 101;

Select * from REVIEWS;

Select * from APPOINTMENT_SLOTS;

SElect count(*) from APPOINTMENTS;
