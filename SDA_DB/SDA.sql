DROP DATABASE SDA
CREATE DATABASE SDA
USE SDA

SELECT 
    session_id, 
    login_name, 
    host_name, 
    program_name
FROM 
    sys.dm_exec_sessions
WHERE 
    database_id = DB_ID('GCR');


ALTER TABLE PAYMENTS
DROP CONSTRAINT PAYMENT_PK, PAYMENT_FK
DROP TABLE PAYMENTS

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
	ID INT IDENTITY(1, 1),
	EMAIL VARCHAR(50) NOT NULL,
	PASSWORD VARCHAR(50) NOT NULL,
	DOB DATE NOT NULL, 
	COUNTRY VARCHAR(50) NOT NULL,
	PHONE_NUMBER VARCHAR(11) NOT NULL CHECK (PHONE_NUMBER LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	GENDER VARCHAR(6) NOT NULL CHECK (GENDER IN ('Male', 'Female')),

	CONSTRAINT PATIENTS_PK PRIMARY KEY(ID),
)

CREATE TABLE PATIENT_HISTORY
(
	--SID INT IDENTITY(1, 1),
	ID INT NOT NULL,
	TYPE VARCHAR(50) NOT NULL,
	DESCRIPTION VARCHAR(50) NOT NULL,

	CONSTRAINT PATIENT_HISTORY_PK PRIMARY KEY (ID, TYPE, DESCRIPTION),
	CONSTRAINT PATIENT_HISTORY_FK FOREIGN KEY (ID) REFERENCES PATIENTS (ID) ON UPDATE CASCADE ON DELETE CASCADE,
)

CREATE TABLE DOCTORS
(
	NAME VARCHAR(50) NOT NULL,
	ID INT IDENTITY(1, 1),
	EMAIL VARCHAR(50) NOT NULL,
	PASSWORD VARCHAR(50) NOT NULL,
	DOB DATE NOT NULL, 
	COUNTRY VARCHAR(50) NOT NULL,
	PHONE_NUMBER VARCHAR(11) NOT NULL CHECK (PHONE_NUMBER LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	GENDER VARCHAR(6) NOT NULL CHECK (GENDER IN ('Male', 'Female')),
	
	-- DOCTOR DETAILS --
	SPECIALIZATION VARCHAR(50) NOT NULL,
	DESCRIPTION VARCHAR(255) NOT NULL,
	LOCATION VARCHAR(50) NOT NULL,
	EXPERIENCE INT NOT NULL,
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
	--ID INT IDENTITY(1, 1),
	DOCTOR_ID INT NOT NULL,
	NAME VARCHAR(255) NOT NULL,
	APPROVED_STATUS VARCHAR(8) NOT NULL CHECK (APPROVED_STATUS IN ('Approved', 'Pending', 'Rejected')),
	ISSUE_DATE DATE NOT NULL,
	EXPIRY_DATE DATE NOT NULL,

	CONSTRAINT CERTIFICATIONS_PK PRIMARY KEY(DOCTOR_ID, NAME),
	CONSTRAINT CERTIFICATIONS_FK FOREIGN KEY(DOCTOR_ID) REFERENCES DOCTORS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
)

CREATE TABLE COMPLAINTS
(
	ID INT IDENTITY(1, 1),
	DOCTOR_ID INT NOT NULL,
	PATIENT_ID INT NOT NULL,
	REASON VARCHAR(255) NOT NULL,

	CONSTRAINT COMPLAINTS_PK PRIMARY KEY(ID),
	CONSTRAINT COMPLAINTS_DFK FOREIGN KEY(DOCTOR_ID) REFERENCES DOCTORS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT COMPLAINTS_PFK FOREIGN KEY(PATIENT_ID) REFERENCES PATIENTS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
)

CREATE TABLE REVIEWS
(
	ID INT IDENTITY(1, 1),
	DOCTOR_ID INT NOT NULL,
	PATIENT_ID INT NOT NULL,
	COMMENT VARCHAR(255),
	EXPERIENCE Float NOT NULL CHECK (EXPERIENCE BETWEEN 0 AND 5),
	checkupRating Float NOT NULL CHECK (checkupRating BETWEEN 0 AND 5),
	environmentRating Float NOT NULL CHECK (environmentRating BETWEEN 0 AND 5),
	staffRating Float NOT NULL CHECK (staffRating BETWEEN 0 AND 5),
	RECOMMEND bit,

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

CREATE TABLE PAYMENTS
(
	APPOINTMENT_ID int not null,
	DATE DATE not null,
	TIME TIME not null,
	AMOUNT FLOAT not null,
	STATUS BIT not null,

	CONSTRAINT PAYMENT_PK PRIMARY KEY(APPOINTMENT_ID),
	CONSTRAINT PAYMENT_FK FOREIGN KEY(APPOINTMENT_ID)REFERENCES APPOINTMENTS(ID) ON UPDATE CASCADE ON DELETE CASCADE,
)

-- Insert data into PATIENTS table
INSERT INTO PATIENTS (NAME, EMAIL, PASSWORD, DOB, COUNTRY, PHONE_NUMBER, GENDER)
VALUES ('Ali Ahmed', 'ali.ahmed@example.com', 'password123', '1990-01-01', 'Pakistan', '03001234567', 'Male'),
       ('Fatima Khan', 'fatima.khan@example.com', 'password456', '1992-02-02', 'Pakistan', '03007654321', 'Female'),
       ('Zainab Raza', 'zainab.raza@example.com', 'password789', '1994-03-03', 'Pakistan', '03009876543', 'Female'),
       ('Usman Ali', 'usman.ali@example.com', 'password321', '1996-04-04', 'Pakistan', '03005678901', 'Male'),
       ('Ayesha Tariq', 'ayesha.tariq@example.com', 'password654', '1998-05-05', 'Pakistan', '03003456789', 'Female');

INSERT INTO PATIENTS (NAME, EMAIL, PASSWORD, DOB, COUNTRY, PHONE_NUMBER, GENDER)
VALUES
('Ali Khan', 'alikhan@gmail.com', 'password.123', '1990-01-01', 'Pakistan', '03001234567', 'Male'),
('Sana Ahmed', 'sanaahmed@gmail.com', 'password.123', '1995-05-05', 'Pakistan', '03001234568', 'Female'),
('Ahmed Hassan', 'ahmedhassan@gmail.com', 'password.123', '1985-12-31', 'Pakistan', '03001234569', 'Male'),
('Fatima Ali', 'fatimaali@gmail.com', 'password.123', '1992-06-15', 'Pakistan', '03001234570', 'Female'),
('Hassan Raza', 'hassanraza@gmail.com', 'password.123', '1988-11-11', 'Pakistan', '03001234571', 'Male'),
('Ayesha Malik', 'ayeshamalik@gmail.com', 'password.123', '1997-03-20', 'Pakistan', '03001234572', 'Female'),
('Usman Khan', 'usmankhan@gmail.com', 'password.123', '1993-09-25', 'Pakistan', '03001234573', 'Male'),
('Sadia Ahmed', 'sadiaahmed@gmail.com', 'password.123', '1991-04-30', 'Pakistan', '03001234574', 'Female'),
('Imran Ali', 'imranali@gmail.com', 'password.123', '1987-08-08', 'Pakistan', '03001234575', 'Male'),
('Aisha Raza', 'aisharaza@gmail.com', 'password.123', '1994-02-14', 'Pakistan', '03001234576', 'Female'),
('Kamran Khan', 'kamrankhan@gmail.com', 'password.123', '1989-07-10', 'Pakistan', '03001234577', 'Male'),
('Saima Ahmed', 'saimaahmed@gmail.com', 'password.123', '1996-01-05', 'Pakistan', '03001234578', 'Female'),
('Naveed Hassan', 'naveedhassan@gmail.com', 'password.123', '1986-11-30', 'Pakistan', '03001234579', 'Male'),
('Sara Ali', 'saraali@gmail.com', 'password.123', '1993-05-25', 'Pakistan', '03001234580', 'Female'),
('Asad Raza', 'asadraza@gmail.com', 'password.123', '1988-10-21', 'Pakistan', '03001234581', 'Male'),
('Amina Malik', 'aminamalik@gmail.com', 'password.123', '1995-04-15', 'Pakistan', '03001234582', 'Female'),
('Tariq Khan', 'tariqkhan@gmail.com', 'password.123', '1991-09-10', 'Pakistan', '03001234583', 'Male'),
('Sana Ahmed', 'sanaahmed2@gmail.com', 'password.123', '1997-02-05', 'Pakistan', '03001234584', 'Female'),
('Ali Raza', 'aliraza@gmail.com', 'password.123', '1989-06-30', 'Pakistan', '03001234585', 'Male'),
('Sadia Malik', 'sadiamalik@gmail.com', 'password.123', '1994-12-25', 'Pakistan', '03001234586', 'Female'),
('Ahmed Khan', 'ahmedkhan@gmail.com', 'password.123', '1990-07-21', 'Pakistan', '03001234587', 'Male'),
('Fatima Ahmed', 'fatimaahmed@gmail.com', 'password.123', '1996-01-15', 'Pakistan', '03001234588', 'Female'),
('Hassan Ali', 'hassanali@gmail.com', 'password.123', '1987-05-10', 'Pakistan', '03001234589', 'Male'),
('Ayesha Raza', 'ayesharaza@gmail.com', 'password.123', '1992-11-05', 'Pakistan', '03001234590', 'Female'),
('Usman Ahmed', 'usmanahmed@gmail.com', 'password.123', '1998-03-01', 'Pakistan', '03001234591', 'Male'),
('Sadia Khan', 'sadiakhan@gmail.com', 'password.123', '1993-08-26', 'Pakistan', '03001234592', 'Female'),
('Imran Raza', 'imranraza@gmail.com', 'password.123', '1985-12-20', 'Pakistan', '03001234593', 'Male'),
('Aisha Malik', 'aishamalik2@gmail.com', 'password.123', '1990-06-15', 'Pakistan', '03001234594', 'Female'),
('Kamran Ahmed', 'kamranahmed@gmail.com', 'password.123', '1995-12-10', 'Pakistan', '03001234595', 'Male'),
('Saima Raza', 'saimaraza@gmail.com', 'password.123', '1991-05-05', 'Pakistan', '03001234596', 'Female');

-- Insert data into PATIENT_HISTORY table
INSERT INTO PATIENT_HISTORY (ID, TYPE, DESCRIPTION)
VALUES (1, 'Allergy', 'Peanut allergy'),
       (2, 'Diabetes', 'Type 2 diabetes'),
       (3, 'Asthma', 'Mild asthma'),
       (4, 'Hypertension', 'High blood pressure'),
       (5, 'Anemia', 'Iron deficiency anemia');
-- Insert data into DOCTORS table
INSERT INTO DOCTORS (NAME, EMAIL, PASSWORD, DOB, COUNTRY, PHONE_NUMBER, GENDER, SPECIALIZATION, DESCRIPTION, LOCATION, EXPERIENCE,  WORKING_HOURS, FEE, AVAILABILITY)
VALUES ('Dr. Asim Malik',  'dr.asim@example.com', 'drpassword123', '1980-06-06', 'Pakistan', '03001231234', 'Male', 'Cardiologist', 'Expert in treating heart diseases', 'Karachi', 15, '9:00-17:00', 2000, 'Available'),
       ('Dr. Saima Iqbal',  'dr.saima@example.com', 'drpassword456', '1982-07-07', 'Pakistan', '03002342345', 'Female', 'Dermatologist', 'Skin care specialist', 'Lahore', 10, '10:00-18:00', 1500, 'Available'),
       ('Dr. Tariq Mahmood',  'dr.tariq@example.com', 'drpassword789', '1978-08-08', 'Pakistan', '03003453456', 'Male', 'Orthopedic Surgeon', 'Expert in bone and joint surgeries', 'Islamabad', 20, '8:00-16:00', 2500, 'Available'),
       ('Dr. Amina Zafar',  'dr.amina@example.com', 'drpassword321', '1985-09-09', 'Pakistan', '03004564567', 'Female', 'Pediatrician', 'Child health specialist', 'Karachi', 8, '11:00-19:00', 1200, 'Available'),
       ('Dr. Kamran Ahmed',  'dr.kamran@example.com', 'drpassword654', '1983-10-10', 'Pakistan', '03005675678', 'Male', 'Dentist', 'Expert in dental care', 'Lahore', 12, '9:00-17:00', 1000, 'Available');

INSERT INTO DOCTORS (NAME, GENDER, SPECIALIZATION, DESCRIPTION, LOCATION, EXPERIENCE, WORKING_HOURS, FEE, AVAILABILITY, EMAIL, PASSWORD, DOB, PHONE_NUMBER,COUNTRY)
VALUES
('John Smith', 'Male', 'Cardiologist', 'Dr. John Smith is a highly experienced cardiologist with over 20 years of experience.', 'New York', 20, '9:00-5:00', 200, 'Available', 'john.smith@gmail.com', 'john.smith.123', '1990-05-05', '03456789123','Pakistan'),
('Jane Doe', 'Female', 'Cardiologist', 'Dr. Jane Doe is a board-certified dermatologist with a passion for helping patients achieve healthy skin.', 'Los Angeles', 10, '10:00-6:00', 150, 'Available', 'jane.doe@gmail.com', 'jane.doe.123', '1990-05-05', '03123456789','Pakistan'),
('David Lee', 'Male', 'Cardiologist', 'Dr. David Lee is a renowned oncologist with a track record of successful cancer treatments.', 'Chicago', 15, '8:00-4:00', 250, 'Available', 'david.lee@gmail.com', 'david.lee.123', '1990-05-05', '03456789012','Pakistan'),
('Emily Chen', 'Female', 'Cardiologist', 'Dr. Emily Chen is a caring pediatrician who loves working with children and their families.', 'San Francisco', 8, '9:00-7:00', 100, 'Available', 'emily.chen@gmail.com', 'emily.chen.123', '1990-05-05', '03456789012','Pakistan'),
('Michael Kim', 'Male', 'Cardiologist', 'Dr. Michael Kim is a skilled neurologist who specializes in treating patients with neurological disorders.', 'Houston', 12, '10:00-6:00', 180, 'Available', 'michael.kim@gmail.com', 'michael.kim.123', '1990-05-05', '03456789012','Pakistan'),
('Sarah Lee', 'Female', 'Cardiologist', 'Dr. Sarah Lee is a compassionate gynecologist who provides personalized care to women of all ages.', 'Miami', 5, '8:00-4:00', 300, 'Available', 'sarah.lee@gmail.com', 'sarah.lee.123', '1990-05-05', '03456789012','Pakistan'),
('Daniel Park', 'Male', 'Cardiologist', 'Dr. Daniel Park is a skilled orthopedic surgeon who specializes in joint replacement and sports medicine.', 'Seattle', 7, '9:00-5:00', 350, 'Available', 'daniel.park@gmail.com', 'daniel.park.123', '1990-05-05', '03456789012','Pakistan'),
('Jessica Kim', 'Female', 'Cardiologist', 'Dr. Jessica Kim is a licensed psychiatrist who provides compassionate care to patients with mental health issues.', 'Boston', 3, '10:00-8:00', 400, 'Available', 'jessica.kim@gmail.com', 'jessica.kim.123', '1990-05-05', '03456789012','Pakistan'),
('Andrew Lee', 'Male', 'Dentist', 'Dr. Andrew Lee is a board-certified urologist who provides comprehensive care to patients with urological conditions.', 'Dallas', 9, '8:00-4:00', 275, 'Available', 'andrew.lee@gmail.com', 'andrew.lee.123', '1990-05-05', '03456789012','Pakistan'),
('Rachel Kim', 'Female', 'Dentist', 'Dr. Rachel Kim is a skilled ophthalmologist who specializes in treating patients with eye diseases and disorders.', 'Atlanta', 11, '9:00-5:00', 225, 'Available', 'rachel.kim@gmail.com', 'rachel.kim.123', '1990-05-05', '03456789012','Pakistan'),
('Kevin Lee', 'Male', 'Dentist', 'Dr. Kevin Lee is a highly skilled dentist who provides a wide range of dental services to patients of all ages.', 'Washington D.C.', 6, '10:00-6:00', 150, 'Available', 'kevin.lee@gmail.com', 'kevin.lee.123', '1990-05-05', '03456789012','Pakistan'),
('Julia Park', 'Female', 'Dentist', 'Dr. Julia Park is a board-certified allergist and immunologist who provides personalized care to patients with allergies and immune system disorders.', 'Philadelphia', 4, '8:00-6:00', 275, 'Available', 'julia.park@gmail.com', 'julia.park.123', '1990-05-05', '03456789012','Pakistan'),
('Eric Kim', 'Male', 'Dentist', 'Dr. Eric Kim is a skilled gastroenterologist who specializes in diagnosing and treating patients with digestive system disorders.', 'San Diego', 8, '9:00-5:00', 225, 'Available', 'eric.kim@gmail.com', 'eric.kim.123', '1990-05-05', '03456789012','Pakistan'),
('Grace Lee', 'Female', 'Dentist', 'Dr. Grace Lee is a board-certified endocrinologist who provides comprehensive care to patients with hormonal imbalances and disorders.', 'Denver', 5, '10:00-6:00', 150, 'Available', 'grace.lee@gmail.com', 'grace.lee.123', '1990-05-05', '03456789012','Pakistan'),
('Brian Park', 'Male', 'Psychiatrist', 'Dr. Brian Park is a skilled rheumatologist who specializes in diagnosing and treating patients with rheumatic diseases.', 'Phoenix', 10, '8:00-4:00', 275, 'Available', 'brian.park@gmail.com', 'brian.park.123', '1990-05-05', '03456789012','Pakistan'),
('Sophia Kim', 'Female', 'Psychiatrist', 'Dr. Sophia Kim is a board-certified hematologist who provides personalized care to patients with blood disorders and cancers.', 'Las Vegas', 6, '9:00-7:00', 225, 'Available', 'sophia.kim@gmail.com', 'sophia.kim.123', '1990-05-05', '03456789012','Pakistan'),
('Jason Lee', 'Male', 'Psychiatrist', 'Dr. Jason Lee is a skilled infectious disease specialist who provides comprehensive care to patients with infectious diseases.', 'Portland', 7, '10:00-6:00', 150, 'Available', 'jason.lee@gmail.com', 'jason.lee.123', '1990-05-05', '03456789012','Pakistan'),
('Olivia Park', 'Female', 'Psychiatrist', 'Dr. Olivia Park is a board-certified nephrologist who provides personalized care to patients with kidney diseases and disorders.', 'San Antonio', 4, '8:00-4:00', 275, 'Available', 'olivia.park@gmail.com', 'olivia.park.123', '1990-05-05', '03456789012','Pakistan'),
('William Kim', 'Male', 'Psychiatrist', 'Dr. William Kim is a skilled pulmonologist who specializes in diagnosing and treating patients with lung diseases and disorders.', 'Minneapolis', 9, '9:00-5:00', 225, 'Available', 'william.kim@gmail.com', 'william.kim.123', '1990-05-05', '03456789012','Pakistan'),
('Ella Lee', 'Female', 'Dermatologist', 'Dr. Ella Lee is a board-certified rheumatologist who provides personalized care to patients with rheumatic diseases.', 'Kansas City', 8, '10:00-8:00', 150, 'Available', 'ella.lee@gmail.com', 'ella.lee.123', '1990-05-05', '03456789012','Pakistan'),
('Jacob Park', 'Male', 'Dermatologist', 'Dr. Jacob Park is a skilled oncologist who specializes in diagnosing and treating patients with cancer.', 'St. Louis', 11, '9:00-5:00', 275, 'Available', 'jacob.park@gmail.com', 'jacob.park.123', '1990-05-05', '03456789012','Pakistan'),
('Chloe Kim', 'Female', 'Dermatologist', 'Dr. Chloe Kim is a board-certified cardiologist who provides personalized care to patients with heart diseases and disorders.', 'Orlando', 7, '9:00-5:00', 225, 'Available', 'chloe.kim@gmail.com', 'chloe.kim.123', '1990-05-05', '03456789012','Pakistan'),
('Ryan Lee', 'Male', 'Dermatologist', 'Dr. Ryan Lee is a skilled gastroenterologist who specializes in diagnosing and treating patients with digestive system disorders.', 'St. Louis', 10, '9:00-5:00', 150, 'Available', 'ryan.lee@gmail.com', 'ryan.lee.123', '1990-05-05', '03456789012','Pakistan'),
('Ava Park', 'Female', 'Dermatologist', 'Dr. Ava Park is a board-certified endocrinologist who provides comprehensive care to patients with hormonal imbalances and disorders.', 'Salt Lake City', 9, '8:00-6:00', 275, 'Available', 'ava.park@gmail.com', 'ava.park.123', '1990-05-05', '03456789012','Pakistan'),
('Ethan Kim', 'Male', 'Dermatologist', 'Dr. Ethan Kim is a skilled neurologist who specializes in treating patients with neurological disorders.', 'Detroit', 12, '10:00-6:00', 225, 'Available', 'ethan.kim@gmail.com', 'ethan.kim.123', '1990-05-05', '03456789012','Pakistan'),
('Mia Lee', 'Female', 'Dermatologist', 'Dr. Mia Lee is a compassionate gynecologist who provides personalized care to women of all ages.', 'Charlotte', 5, '8:00-4:00', 150, 'Available', 'mia.lee@gmail.com', 'mia.lee.123', '1990-05-05', '03456789012','Pakistan');


-- Insert data into SERVICES table
INSERT INTO SERVICES (DOCTOR_ID, DESCRIPTION)
VALUES
  (1, 'General checkup'),
  (1, 'Blood test'),
  (1, 'Vaccination'),
  (2, 'Dental cleaning'),
  (2, 'Tooth extraction'),
  (2, 'Root canal treatment'),
  (3, 'Eye exam'),
  (3, 'Prescription for glasses'),
  (3, 'Treatment for eye infections'),
  (4, 'Physical therapy'),
  (4, 'Sports injury treatment'),
  (4, 'Post-surgery rehabilitation'),
  (5, 'Cancer screening'),
  (5, 'Chemotherapy'),
  (5, 'Radiation therapy'),
  (6, 'Prenatal care'),
  (6, 'Delivery'),
  (6, 'Postpartum care'),
  (7, 'Cardiac stress test'),
  (7, 'Echocardiogram'),
  (7, 'Angioplasty'),
  (8, 'Colonoscopy'),
  (8, 'Endoscopy'),
  (8, 'Hemorrhoid treatment'),
  (9, 'Allergy testing'),
  (9, 'Immunotherapy'),
  (9, 'Asthma treatment'),
  (10, 'Psychiatric evaluation'),
  (10, 'Cognitive behavioral therapy'),
  (10, 'Medication management'),
  (11, 'Physical exam'),
  (11, 'Immunizations'),
  (11, 'Minor injury treatment'),
  (12, 'Dermatology consultation'),
  (12, 'Acne treatment'),
  (12, 'Skin cancer screening'),
  (13, 'Gynecological exam'),
  (13, 'Pap smear'),
  (13, 'Birth control counseling'),
  (14, 'Orthopedic consultation'),
  (14, 'Fracture treatment'),
  (14, 'Joint replacement surgery'),
  (15, 'Neurological exam'),
  (15, 'MRI'),
  (15, 'Stroke treatment'),
  (16, 'ENT consultation'),
  (16, 'Hearing test'),
  (16, 'Sinus surgery'),
  (17, 'Urological exam'),
  (17, 'Prostate cancer screening'),
  (17, 'Erectile dysfunction treatment'),
  (18, 'Ophthalmology consultation'),
  (18, 'Cataract surgery'),
  (18, 'Glaucoma treatment'),
  (19, 'Pediatric checkup'),
  (19, 'Vaccinations'),
  (19, 'Developmental screening'),
  (20, 'Chiropractic adjustment'),
  (20, 'Massage therapy'),
  (20, 'Acupuncture'),
  (21, 'Podiatry consultation'),
  (21, 'Ingrown toenail treatment'),
  (21, 'Foot surgery'),
  (22, 'Nutrition counseling'),
  (22, 'Weight loss management'),
  (22, 'Diabetes education'),
  (23, 'Speech therapy'),
  (23, 'Language therapy'),
  (23, 'Swallowing therapy'),
  (24, 'Physical therapy'),
  (24, 'Sports injury treatment'),
  (24, 'Post-surgery rehabilitation'),
  (25, 'Cancer screening'),
  (25, 'Chemotherapy'),
  (25, 'Radiation therapy'),
  (26, 'Prenatal care'),
  (26, 'Delivery'),
  (26, 'Postpartum care'),
  (27, 'Cardiac stress test'),
  (27, 'Echocardiogram'),
  (27, 'Angioplasty'),
  (28, 'Colonoscopy'),
  (28, 'Endoscopy'),
  (28, 'Hemorrhoid treatment'),
  (29, 'Allergy testing'),
  (29, 'Immunotherapy'),
  (29, 'Asthma treatment'),
  (30, 'Psychiatric evaluation'),
  (30, 'Cognitive behavioral therapy'),
  (30, 'Medication management'),
  (31, 'Physical exam'),
  (31, 'Immunizations'),
  (31, 'Minor injury treatment');

-- Insert data into CERTIFICATIONS table
INSERT INTO CERTIFICATIONS (DOCTOR_ID, NAME, APPROVED_STATUS, ISSUE_DATE, EXPIRY_DATE)
VALUES
-- Certifications for Doctor ID 1
(1, 'Cardiology Board Certification', 'Approved', '2022-03-15', '2024-03-15'),
(1, 'Advanced Cardiac Life Support (ACLS)', 'Pending', '2022-06-30', '2023-12-31'),
(1, 'Echocardiography Certification', 'Rejected', '2021-09-10', '2023-06-30'),

-- Certifications for Doctor ID 2
(2, 'Orthopedic Surgery Board Certification', 'Approved', '2022-04-20', '2024-04-20'),
(2, 'Advanced Trauma Life Support (ATLS)', 'Pending', '2022-07-10', '2023-12-31'),
(2, 'Arthroscopy Certification', 'Rejected', '2021-08-25', '2023-06-30'),

-- Certifications for Doctor ID 3
(3, 'Ophthalmology Board Certification', 'Approved', '2022-05-05', '2024-05-05'),
(3, 'Basic Life Support (BLS)', 'Pending', '2022-08-15', '2023-12-31'),
(3, 'LASIK Certification', 'Rejected', '2021-10-01', '2023-06-30'),

-- Repeat the above pattern for each doctor ID...
-- Certifications for Doctor ID 4
(4, 'Nephrology Board Certification', 'Approved', '2022-06-10', '2024-06-10'),
(4, 'Renal Ultrasound Certification', 'Pending', '2022-09-25', '2023-12-31'),
(4, 'Peritoneal Dialysis Certification', 'Rejected', '2021-11-15', '2023-06-30'),

-- Certifications for Doctor ID 5
(5, 'Pulmonology Board Certification', 'Approved', '2022-07-20', '2024-07-20'),
(5, 'Critical Care Medicine Certification', 'Pending', '2022-10-10', '2023-12-31'),
(5, 'Pulmonary Function Testing Certification', 'Rejected', '2021-12-20', '2023-06-30'),

-- Certifications for Doctor ID 6
(6, 'Gastroenterology Board Certification', 'Approved', '2022-08-25', '2024-08-25'),
(6, 'Advanced Cardiovascular Life Support (ACLS)', 'Pending', '2022-11-15', '2023-12-31'),
(6, 'Endoscopy Certification', 'Rejected', '2022-01-05', '2023-06-30'),

-- Certifications for Doctor ID 7
(7, 'Dermatology Board Certification', 'Approved', '2022-09-30', '2024-09-30'),
(7, 'Basic Life Support (BLS)', 'Pending', '2022-12-20', '2023-12-31'),
(7, 'Mohs Surgery Certification', 'Rejected', '2022-02-10', '2023-06-30'),

-- Certifications for Doctor ID 8
(8, 'Neurology Board Certification', 'Approved', '2022-10-10', '2024-10-10'),
(8, 'Advanced Cardiovascular Life Support (ACLS)', 'Pending', '2023-01-25', '2023-12-31'),
(8, 'Neuroimaging Certification', 'Rejected', '2022-03-15', '2023-06-30'),

-- Certifications for Doctor ID 9
(9, 'Urology Board Certification', 'Approved', '2022-11-15', '2024-11-15'),
(9, 'Basic Life Support (BLS)', 'Pending', '2023-02-20', '2023-12-31'),
(9, 'Urologic Oncology Certification', 'Rejected', '2022-04-20', '2023-06-30'),

-- Certifications for Doctor ID 10
(10, 'Pediatrics Board Certification', 'Approved', '2022-12-20', '2024-12-20'),
(10, 'Advanced Cardiovascular Life Support (ACLS)', 'Pending', '2023-03-30', '2023-12-31'),
(10, 'Pediatric Advanced Life Support (PALS)', 'Rejected', '2022-05-05', '2023-06-30'),

-- Certifications for Doctor ID 11
(11, 'Radiology Board Certification', 'Approved', '2023-01-05', '2025-01-05'),
(11, 'Basic Life Support (BLS)', 'Pending', '2023-04-10', '2023-12-31'),
(11, 'Magnetic Resonance Imaging (MRI) Certification', 'Rejected', '2022-06-10', '2023-06-30'),

-- Certifications for Doctor ID 12
(12, 'Psychiatry Board Certification', 'Approved', '2023-02-10', '2025-02-10'),
(12, 'Advanced Cardiovascular Life Support (ACLS)', 'Pending', '2023-05-15', '2023-12-31'),
(12, 'Psychiatry Certification', 'Rejected', '2022-07-20', '2023-06-30'),

-- Certifications for Doctor ID 13
(13, 'Otolaryngology Board Certification', 'Approved', '2023-03-15', '2025-03-15'),
(13, 'Basic Life Support (BLS)', 'Pending', '2023-06-20', '2023-12-31'),
(13, 'Otolaryngology Certification', 'Rejected', '2022-08-25', '2023-06-30'),

-- Certifications for Doctor ID 14
(14, 'Anesthesiology Board Certification', 'Approved', '2023-04-20', '2025-04-20'),
(14, 'Advanced Cardiovascular Life Support (ACLS)', 'Pending', '2023-07-30', '2023-12-31'),
(14, 'Anesthesiology Certification', 'Rejected', '2022-09-30', '2023-06-30'),

-- Certifications for Doctor ID 15
(15, 'Orthopedic Surgery Board Certification', 'Approved', '2023-05-05', '2025-05-05'),
(15, 'Basic Life Support (BLS)', 'Pending', '2023-08-10', '2023-12-31'),
(15, 'Orthopedic Surgery Certification', 'Rejected', '2022-10-10', '2023-06-30'),

-- Certifications for Doctor ID 16
(16, 'Obstetrics and Gynecology Board Certification', 'Approved', '2023-06-10', '2025-06-10'),
(16, 'Advanced Cardiovascular Life Support (ACLS)', 'Pending', '2023-09-15', '2023-12-31'),
(16, 'Obstetrics and Gynecology Certification', 'Rejected', '2022-11-15', '2023-06-30'),

-- Certifications for Doctor ID 17
(17, 'Ophthalmology Board Certification', 'Approved', '2023-07-20', '2025-07-20'),
(17, 'Basic Life Support (BLS)', 'Pending', '2023-10-25', '2023-12-31'),
(17, 'Ophthalmology Certification', 'Rejected', '2022-12-20', '2023-06-30'),

-- Certifications for Doctor ID 18
(18, 'Emergency Medicine Board Certification', 'Approved', '2023-08-25', '2025-08-25'),
(18, 'Advanced Cardiovascular Life Support (ACLS)', 'Pending', '2023-11-30', '2023-12-31'),
(18, 'Emergency Medicine Certification', 'Rejected', '2023-01-05', '2023-06-30'),

-- Certifications for Doctor ID 19
(19, 'Family Medicine Board Certification', 'Approved', '2023-09-30', '2025-09-30'),
(19, 'Basic Life Support (BLS)', 'Pending', '2023-12-05', '2023-12-31'),
(19, 'Family Medicine Certification', 'Rejected', '2023-02-10', '2023-06-30'),

-- Certifications for Doctor ID 20
(20, 'Pathology Board Certification', 'Approved', '2023-10-10', '2025-10-10'),
(20, 'Advanced Cardiovascular Life Support (ACLS)', 'Pending', '2024-01-15', '2023-12-31'),
(20, 'Pathology Certification', 'Rejected', '2023-03-15', '2023-06-30'),

-- Certifications for Doctor ID 21
(21, 'Internal Medicine Board Certification', 'Approved', '2023-11-15', '2025-11-15'),
(21, 'Basic Life Support (BLS)', 'Pending', '2024-02-20', '2023-12-31'),
(21, 'Internal Medicine Certification', 'Rejected', '2023-04-20', '2023-06-30'),

-- Certifications for Doctor ID 22
(22, 'Neurology Board Certification', 'Approved', '2023-12-20', '2025-12-20'),
(22, 'Advanced Cardiovascular Life Support (ACLS)', 'Pending', '2024-03-25', '2023-12-31'),
(22, 'Neurology Certification', 'Rejected', '2023-05-05', '2023-06-30'),

-- Certifications for Doctor ID 22
(22, 'Dermatology Board Certification', 'Approved', '2022-03-15', '2024-03-15'),
(22, 'Cosmetic Dermatology Certification', 'Pending', '2022-06-30', '2023-12-31'),
(22, 'Mohs Micrographic Surgery Certification', 'Rejected', '2021-09-10', '2023-06-30'),

-- Certifications for Doctor ID 23
(23, 'Neurology Board Certification', 'Approved', '2022-04-20', '2024-04-20'),
(23, 'Electromyography (EMG) Certification', 'Pending', '2022-07-10', '2023-12-31'),
(23, 'Neurocritical Care Certification', 'Rejected', '2021-08-25', '2023-06-30'),

-- Certifications for Doctor ID 24
(24, 'Pediatrics Board Certification', 'Approved', '2022-05-05', '2024-05-05'),
(24, 'Pediatric Advanced Life Support (PALS)', 'Pending', '2022-08-15', '2023-12-31'),
(24, 'Pediatric Hematology-Oncology Certification', 'Rejected', '2021-10-01', '2023-06-30'),

-- Certifications for Doctor ID 25
(25, 'Radiology Board Certification', 'Approved', '2022-06-10', '2024-06-10'),
(25, 'Interventional Radiology Certification', 'Pending', '2022-09-25', '2023-12-31'),
(25, 'Neuroradiology Certification', 'Rejected', '2021-11-15', '2023-06-30'),

-- Certifications for Doctor ID 26
(26, 'Gastroenterology Board Certification', 'Approved', '2022-07-20', '2024-07-20'),
(26, 'Endoscopic Retrograde Cholangiopancreatography (ERCP) Certification', 'Pending', '2022-10-10', '2023-12-31'),
(26, 'Inflammatory Bowel Disease Certification', 'Rejected', '2021-12-20', '2023-06-30'),

-- Certifications for Doctor ID 27
(27, 'Obstetrics and Gynecology Board Certification', 'Approved', '2022-08-15', '2024-08-15'),
(27, 'Maternal-Fetal Medicine Certification', 'Pending', '2022-11-01', '2023-12-31'),
(27, 'Reproductive Endocrinology and Infertility Certification', 'Rejected', '2022-01-10', '2023-06-30'),

-- Certifications for Doctor ID 28
(28, 'Emergency Medicine Board Certification', 'Approved', '2022-09-01', '2024-09-01'),
(28, 'Advanced Cardiac Life Support (ACLS)', 'Pending', '2022-12-15', '2023-12-31'),
(28, 'Toxicology Certification', 'Rejected', '2022-02-05', '2023-06-30');


-- Insert data into COMPLAINTS table
INSERT INTO COMPLAINTS (DOCTOR_ID, PATIENT_ID, REASON)
VALUES (1, 1, 'Long waiting time'), 
       (2, 2, 'Impolite'),
       (3, 3, 'Rude '),
       (4, 4, 'Didnt shower'),
       (5, 5, 'Unhygienic conditions');

select * from PATIENTS;
SELECT * FROM REVIEWS;
-- Insert data into REVIEWS table
INSERT INTO REVIEWS (DOCTOR_ID, PATIENT_ID, COMMENT, EXPERIENCE, checkupRating, environmentRating, staffRating, RECOMMEND)
VALUES
-- Reviews for Doctor ID 1
(1, 1, 'Great doctor, explained everything clearly. mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm', 4.5, 4.5, 4.5, 4.5, 1),
(1, 2, 'Very satisfied with the treatment, would highly recommend.', 4.8, 4.8, 4.5, 4.7, 1),
(1, 3, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(1, 4, 'Excellent experience, the doctor was very attentive.', 5.0, 5.0, 4.5, 5.0, 1),
(1, 5, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),


-- Reviews for Doctor ID 2
(2, 6, 'The doctor was knowledgeable, but the appointment got delayed.', 4.2, 4.0, 4.5, 4.0, 1),
(2, 7, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
(2, 8, 'The diagnosis was accurate, but the doctor seemed hurried.', 3.8, 4.0, 3.5, 4.0, 1),
(2, 9, 'Not satisfied with the consultation, would not recommend.', 2.5, 3.0, 2.5, 2.5, 0),
(2, 10, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),

-- Repeat the above pattern for the remaining doctor IDs...
-- Reviews for Doctor ID 3

-- Reviews for Doctor ID 2
(2, 6, 'The doctor was knowledgeable, but the appointment got delayed.', 4.2, 4.0, 4.5, 4.0, 1),
(2, 7, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
(2, 8, 'The diagnosis was accurate, but the doctor seemed hurried.', 3.8, 4.0, 3.5, 4.0, 1),
(2, 9, 'Not satisfied with the consultation, would not recommend.', 2.5, 3.0, 2.5, 2.5, 0),
(2, 10, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),
(3, 11, 'The doctor was very thorough in the examination.', 4.7, 4.7, 4.5, 4.8, 1),
(3, 12, 'Satisfied with the treatment and the doctor''s approach.', 4.5, 4.5, 4.0, 4.5, 1),
(3, 13, 'The doctor was knowledgeable, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(3, 14, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(3, 15, 'The diagnosis was accurate, but the doctor seemed distant.', 3.8, 4.0, 3.5, 4.0, 1),

-- Reviews for Doctor ID 4
(4, 16, 'Very satisfied with the treatment, would highly recommend.', 4.8, 4.8, 4.5, 4.7, 1),
(4, 17, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(4, 18, 'Excellent experience, the doctor was very attentive.', 5.0, 5.0, 4.5, 5.0, 1),
(4, 19, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(4, 20, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),

-- Reviews for Doctor ID 5
(5, 21, 'The doctor was knowledgeable, but the appointment got delayed.', 4.2, 4.0, 4.5, 4.0, 1),
(5, 22, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(5, 23, 'Not satisfied with the consultation, would not recommend.', 2.5, 3.0, 2.5, 2.5, 0),
(5, 24, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),
(5, 25, 'Satisfied with the treatment and the doctor''s approach.', 4.5, 4.5, 4.0, 4.5, 1),

-- Reviews for Doctor ID 6
(6, 26, 'The doctor was very thorough in the examination.', 4.7, 4.7, 4.5, 4.8, 1),
(6, 27, 'The doctor was knowledgeable, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(6, 28, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(6, 29, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
(6, 30, 'The diagnosis was accurate, but the doctor seemed distant.', 3.8, 4.0, 3.5, 4.0, 1),

-- Reviews for Doctor ID 7
(7, 31, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(7, 32, 'Excellent experience, the doctor was very attentive.', 5.0, 5.0, 4.5, 5.0, 1),
(7, 33, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(7, 34, 'The doctor was knowledgeable, but the appointment got delayed.', 4.2, 4.0, 4.5, 4.0, 1),
(7, 35, 'Satisfied with the treatment and the doctor''s approach.', 4.5, 4.5, 4.0, 4.5, 1),

-- Reviews for Doctor ID 8
(8, 30, 'Not satisfied with the consultation, would not recommend.', 2.5, 3.0, 2.5, 2.5, 0),
(8, 31, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),
(8, 32, 'The doctor was very thorough in the examination.', 4.7, 4.7, 4.5, 4.8, 1),
(8, 33, 'The doctor was knowledgeable, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(8, 34, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),

-- Reviews for Doctor ID 9
(9, 1, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(9, 2, 'The diagnosis was accurate, but the doctor seemed distant.', 3.8, 4.0, 3.5, 4.0, 1),
(9, 3, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(9, 4, 'Excellent experience, the doctor was very attentive.', 5.0, 5.0, 4.5, 5.0, 1),
(9, 5, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),
-- Reviews for Doctor ID 10
(10, 6, 'Satisfied with the treatment and the doctor''s approach.', 4.5, 4.5, 4.0, 4.5, 1),
(10, 7, 'The doctor was knowledgeable, but the appointment got delayed.', 4.2, 4.0, 4.5, 4.0, 1),
(10, 8, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(10, 9, 'Not satisfied with the consultation, would not recommend.', 2.5, 3.0, 2.5, 2.5, 0),
(10, 10, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),

-- Reviews for Doctor ID 11
(11, 6, 'The doctor was very thorough in the examination.', 4.7, 4.7, 4.5, 4.8, 1),
(11, 7, 'The doctor was knowledgeable, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(11, 8, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(11, 9, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
(11, 10, 'The diagnosis was accurate, but the doctor seemed distant.', 3.8, 4.0, 3.5, 4.0, 1),
-- Reviews for Doctor ID 12
(12, 11, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(12, 12, 'Excellent experience, the doctor was very attentive.', 5.0, 5.0, 4.5, 5.0, 1),
(12, 13, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(12, 14, 'The doctor was knowledgeable, but the appointment got delayed.', 4.2, 4.0, 4.5, 4.0, 1),
(12, 15, 'Satisfied with the treatment and the doctor''s approach.', 4.5, 4.5, 4.0, 4.5, 1),
-- Reviews for Doctor ID 13
(13, 16, 'Not satisfied with the consultation, would not recommend.', 2.5, 3.0, 2.5, 2.5, 0),
(13, 17, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),
(13, 18, 'The doctor was very thorough in the examination.', 4.7, 4.7, 4.5, 4.8, 1),
(13, 19, 'The doctor was knowledgeable, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(13, 20, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
-- Reviews for Doctor ID 14
(14, 21, 'Satisfied with the treatment and the doctor''s approach.', 4.5, 4.5, 4.0, 4.5, 1),
(14, 22, 'The doctor was knowledgeable, but the appointment got delayed.', 4.2, 4.0, 4.5, 4.0, 1),
(14, 23, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(14, 24, 'Not satisfied with the consultation, would not recommend.', 2.5, 3.0, 2.5, 2.5, 0),
(14, 25, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),
-- Reviews for Doctor ID 15
(15, 26, 'The doctor was very thorough in the examination.', 4.7, 4.7, 4.5, 4.8, 1),
(15, 27, 'The doctor was knowledgeable, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(15, 28, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(15, 29, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
(15, 30, 'The diagnosis was accurate, but the doctor seemed distant.', 3.8, 4.0, 3.5, 4.0, 1),
-- Reviews for Doctor ID 16
(16, 31, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(16, 32, 'Excellent experience, the doctor was very attentive.', 5.0, 5.0, 4.5, 5.0, 1),
(16, 33, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(16, 34, 'The doctor was knowledgeable, but the appointment got delayed.', 4.2, 4.0, 4.5, 4.0, 1),
(16, 35, 'Satisfied with the treatment and the doctor''s approach.', 4.5, 4.5, 4.0, 4.5, 1),
-- Reviews for Doctor ID 17
(17, 1, 'Not satisfied with the consultation, would not recommend.', 2.5, 3.0, 2.5, 2.5, 0),
(17, 2, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),
(17, 3, 'The doctor was very thorough in the examination.', 4.7, 4.7, 4.5, 4.8, 1),
(17, 4, 'The doctor was knowledgeable, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(17, 5, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
-- Reviews for Doctor ID 18
(18, 6, 'Satisfied with the treatment and the doctor''s approach.', 4.5, 4.5, 4.0, 4.5, 1),
(18, 7, 'The doctor was knowledgeable, but the appointment got delayed.', 4.2, 4.0, 4.5, 4.0, 1),
(18, 8, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(18, 9, 'Not satisfied with the consultation, would not recommend.', 2.5, 3.0, 2.5, 2.5, 0),
(18, 10, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),
-- Reviews for Doctor ID 19
(19, 11, 'The doctor was very thorough in the examination.', 4.7, 4.7, 4.5, 4.8, 1),
(19, 12, 'The doctor was knowledgeable, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(19, 13, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(19, 14, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
(19, 15, 'The diagnosis was accurate, but the doctor seemed distant.', 3.8, 4.0, 3.5, 4.0, 1),
-- Reviews for Doctor ID 20
(20, 16, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(20, 17, 'Excellent experience, the doctor was very attentive.', 5.0, 5.0, 4.5, 5.0, 1),
(20, 18, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(20, 19, 'The doctor was knowledgeable, but the appointment got delayed.', 4.2, 4.0, 4.5, 4.0, 1),
(20, 20, 'Satisfied with the treatment and the doctor''s approach.', 4.5, 4.5, 4.0, 4.5, 1),

(21, 21, 'The doctor was very knowledgeable and explained everything clearly.', 4.8, 4.8, 4.5, 4.8, 1),
(21, 22, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(21, 23, 'Satisfied with the treatment, but the clinic was overcrowded.', 4.5, 4.5, 4.0, 4.0, 1),
(21, 24, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(21, 25, 'The diagnosis was accurate, but the doctor seemed rushed.', 4.2, 4.0, 4.0, 4.5, 1),
-- Reviews for Doctor ID 22
(22, 26, 'The doctor was knowledgeable and provided helpful advice.', 4.5, 4.5, 4.0, 4.5, 1),
(22, 27, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),
(22, 28, 'The doctor was friendly, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(22, 29, 'Satisfied with the treatment and the doctor''s approach.', 4.5, 4.5, 4.0, 4.5, 1),
(22, 30, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
-- Reviews for Doctor ID 23
(23, 31, 'The doctor was very thorough in the examination.', 4.7, 4.7, 4.5, 4.8, 1),
(23, 32, 'The doctor was knowledgeable, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(23, 33, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(23, 34, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
(23, 35, 'The diagnosis was accurate, but the doctor seemed distant.', 3.8, 4.0, 3.5, 4.0, 1),
-- Reviews for Doctor ID 24
(24, 1, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(24, 2, 'Excellent experience, the doctor was very attentive.', 5.0, 5.0, 4.5, 5.0, 1),
(24, 3, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(24, 4, 'The doctor was knowledgeable, but the clinic was crowded.', 4.2, 4.0, 4.0, 4.5, 1),
(24, 5, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
-- Reviews for Doctor ID 25
(25, 6, 'The doctor was very thorough and explained everything clearly.', 4.8, 4.8, 4.5, 4.8, 1),
(25, 7, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(25, 8, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
(25, 9, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(25, 10, 'The diagnosis was accurate, but the doctor seemed rushed.', 4.2, 4.0, 4.0, 4.5, 1),
-- Reviews for Doctor ID 26
(26, 11, 'The doctor was knowledgeable and provided helpful advice.', 4.5, 4.5, 4.0, 4.5, 1),
(26, 12, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),
(26, 13, 'The doctor was friendly, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(26, 14, 'Satisfied with the treatment and the doctor''s approach.', 4.5, 4.5, 4.0, 4.5, 1),
(26, 15, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
-- Reviews for Doctor ID 27
(27, 16, 'The doctor was very thorough in the examination.', 4.7, 4.7, 4.5, 4.8, 1),
(27, 17, 'The doctor was knowledgeable, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(27, 18, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(27, 19, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
(27, 20, 'The diagnosis was accurate, but the doctor seemed distant.', 3.8, 4.0, 3.5, 4.0, 1),
-- Reviews for Doctor ID 28
(28, 21, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(28, 22, 'Excellent experience, the doctor was very attentive.', 5.0, 5.0, 4.5, 5.0, 1),
(28, 23, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(28, 24, 'The doctor was knowledgeable, but the clinic was crowded.', 4.2, 4.0, 4.0, 4.5, 1),
(28, 25, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
-- Reviews for Doctor ID 29
(29, 26, 'The doctor was very thorough and explained everything clearly.', 4.8, 4.8, 4.5, 4.8, 1),
(29, 27, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(29, 28, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
(29, 29, 'Good doctor, but the waiting time was longer than expected.', 3.5, 4.0, 3.5, 3.0, 1),
(29, 30, 'The diagnosis was accurate, but the doctor seemed rushed.', 4.2, 4.0, 4.0, 4.5, 1),
-- Reviews for Doctor ID 30
(30, 31, 'The doctor was knowledgeable and provided helpful advice.', 4.5, 4.5, 4.0, 4.5, 1),
(30, 32, 'Average experience, nothing extraordinary.', 3.0, 3.5, 3.0, 3.0, 0),
(30, 33, 'The doctor was friendly, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(30, 34, 'Satisfied with the treatment and the doctor''s approach.', 4.5, 4.5, 4.0, 4.5, 1),
(30, 35, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
-- Reviews for Doctor ID 31
(31, 1, 'The doctor was very thorough in the examination.', 4.7, 4.7, 4.5, 4.8, 1),
(31, 2, 'The doctor was knowledgeable, but the waiting time was long.', 4.2, 4.0, 4.0, 4.5, 1),
(31, 3, 'Average experience, nothing exceptional.', 3.0, 3.5, 3.0, 3.0, 0),
(31, 4, 'Satisfied with the treatment, the doctor was friendly.', 4.5, 4.5, 4.0, 4.5, 1),
(31, 5, 'The diagnosis was accurate, but the doctor seemed distant.', 3.8, 4.0, 3.5, 4.0, 1);
-- Insert data into APPOINTMENTS table
INSERT INTO APPOINTMENTS (DOCTOR_ID, PATIENT_ID, DATE, TIME, STATUS, PROBLEM)
VALUES (101, 1, '2023-06-01', '10:00:00', 'Booked', 'My Heart is aching.'),
       (102, 2, '2023-06-02', '11:00:00', 'Completed', 'My skin is dry.'),
       (103, 3, '2023-06-03', '12:00:00', 'Cancelled', 'My bones are weak.'),
       (104, 4, '2023-06-04', '13:00:00', 'Booked', 'The kid cant walk no more.'),
       (105, 5, '2023-06-05', '14:00:00', 'Completed', 'My teeth are falling');

INSERT INTO PAYMENTS (APPOINTMENT_ID, DATE, TIME, STATUS, AMOUNT)
VALUES (1, '2023-05-01', '10:00:00', 1, 1200),
	   (2, '2023-05-02', '11:00:00', 0, 1500),
	   (3, '2023-05-03', '12:00:00', 1, 2000),
	   (4, '2023-05-04', '13:00:00', 1, 1000),
	   (5, '2023-05-05', '14:00:00', 1, 800)

-- Insert data into APPOINTMENT_SLOTS table
GO
DECLARE @startDate DATE = GETDATE();
DECLARE @endDate DATE = DATEADD(DAY, 10, @startDate);

DECLARE @doctorId INT = 1;

WHILE @doctorId <= 31
BEGIN
    DECLARE @date DATE = @startDate;

    WHILE @date <= @endDate
    BEGIN
        INSERT INTO APPOINTMENT_SLOTS (DOCTOR_ID, DATE, TIME, AVAILABLE)
        VALUES 
        (@doctorId, @date, '10:00:00', 1),
        (@doctorId, @date, '12:00:00', 1),
        (@doctorId, @date, '14:00:00', 1),
        (@doctorId, @date, '18:00:00', 1),
        (@doctorId, @date, '20:00:00', 1),
        (@doctorId, @date, '22:00:00', 1);

        SET @date = DATEADD(DAY, 1, @date);
    END

    SET @doctorId = @doctorId + 1;
END
GO
     


INSERT INTO APPOINTMENTS (DOCTOR_ID, PATIENT_ID, DATE, TIME, STATUS, PROBLEM)
VALUES 
  (101, 1, '2023-06-06', '10:00:00', 'Completed', 'My skin is dry.'),
  (102, 1, '2023-06-07', '11:00:00', 'Completed', 'My skin is dry.'),
  (103, 1, '2023-06-08', '12:00:00', 'Completed', 'My skin is dry.'),
  (104, 1, '2023-06-09', '13:00:00', 'Completed', 'My skin is dry.'),
  (105, 1, '2023-06-10', '14:00:00', 'Completed', 'My skin is dry.');

INSERT INTO PAYMENTS (APPOINTMENT_ID, DATE, TIME, STATUS, AMOUNT)
VALUES 
  (6, '2023-06-06', '10:00:00', 1, 1200),
  (7, '2023-06-07', '11:00:00', 1, 1500),
  (8, '2023-06-08', '12:00:00', 1, 2000),
  (9, '2023-06-09', '13:00:00', 1, 1000),
  (10, '2023-06-10', '14:00:00', 1, 800);

-- Select * from APPOINTMENTS;

-- Select * from PAYMENTS

-- SELECT (SELECT COUNT(ID) FROM Appointments WHERE DOCTOR_ID = 101 AND STATUS = 'Completed') as Patients, NAME, SPECIALIZATION, DESCRIPTION, LOCATION, EXPERIENCE, WORKING_HOURS, FEE, AVAILABILITY FROM Doctors WHERE ID = 101;

-- SELECT (SELECT COUNT(ID) FROM Appointments WHERE DOCTOR_ID = d.id AND STATUS = 'Completed') as Patients, id, NAME, SPECIALIZATION, DESCRIPTION, LOCATION, EXPERIENCE, WORKING_HOURS, FEE, AVAILABILITY FROM Doctors d where d.name LIKE '%A%'

-- Select * from REVIEWS;

 Select * from APPOINTMENT_SLOTS;

-- SElect count(*) from APPOINTMENTS;

-- SElect * from DOCTORS

-- SELECT (SELECT COUNT(Distinct PATIENT_ID) FROM Appointments WHERE DOCTOR_ID = d.id AND STATUS = 'Completed') as Patients, id, NAME, EMAIL, DOB, COUNTRY, PHONE_NUMBER, GENDER, SPECIALIZATION, DESCRIPTION, LOCATION, STATS, PATIENTS_TREATED, EXPERIENCE, RATING, WORKING_HOURS, FEE, AVAILABILITY FROM DOCTORS d where d.name LIKE '%a%';

-- Select * from COMPLAINTS;

-- SELECT TOP 4 *, (SELECT COUNT(Distinct PATIENT_ID) FROM Appointments WHERE DOCTOR_ID = d.id AND STATUS = 'Completed') as Patients FROM DOCTORS d ORDER BY ((RATING/5)*60 + STATS*.4) DESC;