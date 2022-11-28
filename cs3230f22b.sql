-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 160.10.217.6:3306
-- Generation Time: Nov 28, 2022 at 06:40 PM
-- Server version: 8.0.22
-- PHP Version: 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cs3230f22b`
--
CREATE DATABASE IF NOT EXISTS `cs3230f22b` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `cs3230f22b`;

DELIMITER $$
--
-- Procedures
--
DROP PROCEDURE IF EXISTS `uspEditPatient`$$
$$

DROP PROCEDURE IF EXISTS `uspRegisterPatient`$$
CREATE DEFINER=`jwagner2`@`%` PROCEDURE `uspRegisterPatient` (IN `ufname` VARCHAR(50), IN `ulname` VARCHAR(50), IN `ussn` VARCHAR(9), IN `udob` DATE, IN `uaddr1_street` VARCHAR(50), IN `uaddr2_street` VARCHAR(50), IN `uaddr_state` VARCHAR(2), IN `uaddr_zip` VARCHAR(5), IN `uphone` VARCHAR(10), IN `ugender` VARCHAR(1))   BEGIN
DECLARE sql_error tinyint default false;
DECLARE Continue HANDLER FOR SQLEXCEPTION
set sql_error = true;
start transaction;
insert into person (fname, lname, ssn, dob, addr1_street, addr2_street, addr_state, addr_zip, phone, gender)
values (ufname, ulname, ussn, udob, uaddr1_street, uaddr2_street, uaddr_state, uaddr_zip, uphone, ugender); 
SET @new_patient_pid = (select last_insert_id()); 
insert into patient (pid, isActive)
values (@new_patient_pid, 1);
if sql_error = false then
commit;
else
rollback;
end if;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int NOT NULL,
  `pid` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `pid`) VALUES
(1, 1),
(2, 3),
(3, 17),
(4, 22),
(5, 23);

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
  `doctor_id` int NOT NULL,
  `appt_datetime` datetime NOT NULL,
  `patient_id` int NOT NULL,
  `appt_reason` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`doctor_id`, `appt_datetime`, `patient_id`, `appt_reason`) VALUES
(1, '2021-11-25 17:00:00', 3, 'Thanksgiving Checkup (2023)'),
(1, '2022-11-01 09:00:00', 5, 'chronic nausea'),
(1, '2022-11-01 17:00:00', 5, 'chronic fatigue'),
(1, '2022-11-02 12:00:00', 5, 'chronic nausea'),
(1, '2022-11-04 15:00:00', 5, 'seeing doctor'),
(1, '2022-11-04 16:00:00', 5, 'Enter Reason for Appointment'),
(1, '2022-11-14 09:00:00', 3, 'BACK TO THE FUTURE'),
(1, '2022-11-14 14:00:00', 5, 'test edit'),
(1, '2022-11-15 20:00:00', 5, 'Enter Reason for Appointment'),
(1, '2022-11-16 11:00:00', 5, 'Demo'),
(1, '2022-11-20 10:00:00', 2, '11-20-22 @ 10a w/ Dr. Jekyll'),
(1, '2022-11-22 15:00:00', 6, 'testing'),
(1, '2022-11-25 17:00:00', 2, 'double-book Dr. J'),
(2, '2022-11-02 17:00:00', 5, 'Enter Reason for Appointment'),
(2, '2022-11-13 17:00:00', 5, 'Enter Reason for Appointment'),
(2, '2022-11-22 16:00:00', 6, 'Enter Reason for Appointment'),
(2, '2022-11-22 17:00:00', 6, 'Enter Reason for Appointment'),
(3, '2022-11-02 12:00:00', 3, 'Strange bumps on arm'),
(3, '2022-11-02 13:00:00', 3, 'Enter Reason for Appointment'),
(3, '2022-11-09 14:00:00', 5, 'Enter Reason for Appointment'),
(3, '2022-11-10 20:05:00', 3, 'routine checkup'),
(3, '2022-11-13 16:00:00', 5, 'testing'),
(3, '2022-11-22 16:00:00', 5, 'Enter Reason for Appointment'),
(3, '2022-11-22 17:00:00', 3, 'Enter Reason for Appointment');

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `doctor_id` int NOT NULL,
  `pid` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`doctor_id`, `pid`) VALUES
(1, 4),
(2, 14),
(3, 15),
(4, 22),
(5, 23);

-- --------------------------------------------------------

--
-- Table structure for table `has_specialty`
--

DROP TABLE IF EXISTS `has_specialty`;
CREATE TABLE `has_specialty` (
  `doctor_id` int NOT NULL,
  `specialty` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `has_specialty`
--

INSERT INTO `has_specialty` (`doctor_id`, `specialty`) VALUES
(1, 'pediatrics'),
(1, 'podiatry'),
(2, 'Internal medicine'),
(3, 'Internal medicine'),
(4, 'Allergy and immunology'),
(5, 'Family medicine');

-- --------------------------------------------------------

--
-- Table structure for table `lab_order`
--

DROP TABLE IF EXISTS `lab_order`;
CREATE TABLE `lab_order` (
  `order_id` int NOT NULL,
  `test_id` int NOT NULL,
  `visit_id` int NOT NULL,
  `results` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `order_datetime` datetime NOT NULL,
  `isAbnormal` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `lab_order`
--

INSERT INTO `lab_order` (`order_id`, `test_id`, `visit_id`, `results`, `order_datetime`, `isAbnormal`) VALUES
(5, 1, 17, 'bad', '2022-11-13 20:17:24', 1),
(6, 7, 17, 'bad', '2022-11-13 20:17:24', 1),
(7, 11, 17, 'good', '2022-11-13 20:17:24', 1),
(11, 3, 19, NULL, '2022-11-14 21:07:58', NULL),
(12, 5, 19, NULL, '2022-11-14 21:07:58', NULL),
(13, 7, 19, NULL, '2022-11-14 21:07:58', NULL),
(14, 7, 20, 'wrrwrw', '2022-11-16 10:32:43', 1),
(15, 3, 21, NULL, '2022-11-22 10:27:06', NULL),
(16, 5, 21, NULL, '2022-11-22 10:27:06', NULL),
(17, 5, 22, NULL, '2022-11-22 12:46:16', NULL),
(18, 9, 22, NULL, '2022-11-22 12:46:16', NULL),
(19, 3, 23, NULL, '2022-11-22 13:14:41', NULL),
(20, 9, 23, NULL, '2022-11-22 13:14:41', NULL),
(21, 1, 24, NULL, '2022-11-22 14:15:02', NULL),
(22, 3, 24, NULL, '2022-11-22 14:15:02', NULL),
(23, 7, 24, NULL, '2022-11-22 14:15:02', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `lab_test_type`
--

DROP TABLE IF EXISTS `lab_test_type`;
CREATE TABLE `lab_test_type` (
  `test_id` int NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `lab_test_type`
--

INSERT INTO `lab_test_type` (`test_id`, `name`, `description`) VALUES
(1, 'Blood Panel', 'General test for common maladies detected in blood. '),
(3, 'Viral Test', 'Tests for a variety of virus\''),
(5, 'Full CBC', 'Checks vitamin levels in patient.'),
(7, 'Hematocrit', 'Checks patient hematocrit'),
(9, 'Metabolic panel', 'Analyzes patients metabolic function'),
(11, 'Liver Panel', 'Analyzes patients liver function');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `username` varchar(20) NOT NULL,
  `password` varbinary(255) DEFAULT NULL,
  `pid` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`username`, `password`, `pid`) VALUES
('administrator', 0x17ff36f37520d536b0f13917f655df2b, 22),
('ba818', 0x98f7a2f870b03dc1d2874a25419676aa, 3),
('dennis12', 0x17ff36f37520d536b0f13917f655df2b, 23),
('test', 0x17ff36f37520d536b0f13917f655df2b, 17),
('testAdmin', 0xe009ea42ec573a7766a996bb751c7b1f, 1),
('testNurse', 0xe009ea42ec573a7766a996bb751c7b1f, 2);

-- --------------------------------------------------------

--
-- Table structure for table `nurse`
--

DROP TABLE IF EXISTS `nurse`;
CREATE TABLE `nurse` (
  `nurse_id` int NOT NULL,
  `pid` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `nurse`
--

INSERT INTO `nurse` (`nurse_id`, `pid`) VALUES
(1, 2),
(2, 3),
(3, 17),
(4, 20),
(5, 21);

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
  `patient_id` int NOT NULL,
  `pid` int NOT NULL,
  `isActive` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`patient_id`, `pid`, `isActive`) VALUES
(1, 9, 1),
(2, 10, 1),
(3, 12, 1),
(4, 13, 0),
(5, 18, 1),
(6, 19, 1),
(7, 20, 1),
(8, 21, 1);

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `pid` int NOT NULL,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `ssn` varchar(9) DEFAULT NULL,
  `dob` date NOT NULL,
  `addr1_street` varchar(50) NOT NULL,
  `addr2_street` varchar(50) DEFAULT NULL,
  `addr_state` varchar(2) NOT NULL,
  `addr_zip` varchar(5) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `gender` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`pid`, `fname`, `lname`, `ssn`, `dob`, `addr1_street`, `addr2_street`, `addr_state`, `addr_zip`, `phone`, `gender`) VALUES
(1, 'Test', 'Admin', '123456789', '2004-10-10', '1234 Made Up Road', NULL, 'TX', '12345', '1234567890', 'M'),
(2, 'Senor', 'Prueba', '992488290', '2002-10-02', '142 Calle St.', '', 'FL', '99302', '4928418829', 'M'),
(3, 'B', 'A', '192837465', '2006-11-05', '2634 E St.', NULL, 'NH', '78123', '5553238221', 'M'),
(4, 'Hans', 'Jekyll', '918273645', '1967-01-13', '5 Soho Square', NULL, 'GB', '82292', '8821329324', 'F'),
(5, 'Bobby', 'McTest', '123421232', '2015-10-12', '123 Fake St.', '', 'AK', '22481', '2354331234', 'M'),
(10, 'Senor', 'Prueba', '992488284', '2002-10-02', '142 Calle St.', NULL, 'FL', '99302', '4928418829', 'M'),
(12, 'Dona', 'Prueba', '999001234', '2002-11-01', '142 Calle St.', '', 'FL', '99302', '4928418829', 'F'),
(13, 'Nino', 'Prueba', '943882381', '2008-08-08', '537 Calle St', '', 'DE', '29428', '9938827738', 'M'),
(14, 'test', 'doctor', '222556666', '1998-08-05', '111 im a doctor for sure blvd', '', 'GA', '22217', '9938827738', 'M'),
(15, 'another', 'doctor', '222556688', '1988-08-11', '111 address st', '', 'GA', '55537', '2223334444', 'F'),
(17, 'test', 'test', '888558888', '1988-08-11', '111 address st', '', 'GA', '55537', '2223334444', 'F'),
(18, 'jordan', 'wagner', '111332222', '2022-09-25', '2354 ok st', '', 'FL', '22212', '1111111111', 'M'),
(19, 'Jordan', 'Wagner', '222990000', '2022-07-05', '4242 west st', '', 'CA', '22222', '2222221111', 'M'),
(20, 'tiny', 'tim', '933201192', '2022-11-01', 'testing 123', '', 'AZ', '13244', '3330002382', 'M'),
(21, 'testing', 'theTest', '999002222', '2023-01-06', '127 ok sure', '', 'CO', '33333', '9098762828', 'M'),
(22, 'Doctor', 'Tuesday', '000990999', '1979-08-01', '12 valid address', 'apt 2', 'GA', '00009', '1112223333', 'M'),
(23, 'Dennis', 'Soleil', '011990999', '1985-09-11', '883 Charleston Ln', '', 'GA', '00029', '1112223344', 'M');

-- --------------------------------------------------------

--
-- Table structure for table `visit`
--

DROP TABLE IF EXISTS `visit`;
CREATE TABLE `visit` (
  `visit_id` int NOT NULL,
  `is_final` tinyint(1) NOT NULL,
  `doctor_id` int NOT NULL,
  `appt_datetime` datetime NOT NULL,
  `nurse_id` int NOT NULL,
  `systolic_pressure` int NOT NULL,
  `diastolic_pressure` int NOT NULL,
  `body_temp_degreesF` decimal(4,1) NOT NULL,
  `height_inches` int NOT NULL,
  `weight_pounds` decimal(5,2) NOT NULL,
  `pulse_bpm` int NOT NULL,
  `symptoms` varchar(250) NOT NULL,
  `diagnosis` varchar(100) NOT NULL,
  `appt_save_time` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `visit`
--

INSERT INTO `visit` (`visit_id`, `is_final`, `doctor_id`, `appt_datetime`, `nurse_id`, `systolic_pressure`, `diastolic_pressure`, `body_temp_degreesF`, `height_inches`, `weight_pounds`, `pulse_bpm`, `symptoms`, `diagnosis`, `appt_save_time`) VALUES
(3, 0, 1, '2022-11-01 21:05:44', 2, 92, 82, '100.3', 90, '185.00', 200, '', '', '2022-11-01 12:43:11'),
(4, 0, 1, '2022-11-02 11:51:08', 2, 110, 80, '98.7', 60, '180.00', 95, 'no symptoms', 'none', '2022-11-03 12:43:29'),
(5, 1, 3, '2022-11-10 20:05:00', 2, 20, 20, '23.0', 23, '234.00', 193, '', 'test dx', '2022-11-10 12:43:44'),
(17, 1, 3, '2022-11-13 16:00:00', 3, 90, 90, '90.0', 90, '90.00', 90, 'test', 'final', '2022-11-18 12:43:57'),
(19, 1, 1, '2022-11-14 09:00:00', 2, 200, 200, '98.0', 20, '200.00', 200, 'Irregular blood pressure', 'Things n stuff', '2022-11-03 12:44:10'),
(20, 0, 1, '2022-11-16 11:00:00', 2, 100, 100, '100.0', 100, '100.00', 100, 'nonw', 'first', '2022-11-11 12:44:23'),
(21, 0, 3, '2022-11-22 16:00:00', 3, 90, 90, '90.0', 90, '90.00', 90, 'testing 			', 'testing', '2022-11-22 12:44:31'),
(22, 0, 1, '2022-11-22 15:00:00', 3, 90, 90, '90.0', 90, '90.00', 90, 'testing', 'ok', '2022-11-22 12:45:44'),
(23, 0, 2, '2022-11-22 16:00:00', 3, 90, 90, '90.0', 90, '90.00', 90, 'testing submit visit confirm', '', '2022-11-22 13:14:09'),
(24, 0, 2, '2022-11-22 17:00:00', 3, 90, 90, '90.0', 90, '90.00', 90, 'ok sure', '', '2022-11-22 14:14:30');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `pid` (`pid`);

--
-- Indexes for table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`doctor_id`,`appt_datetime`);

--
-- Indexes for table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`doctor_id`),
  ADD UNIQUE KEY `pid` (`pid`);

--
-- Indexes for table `has_specialty`
--
ALTER TABLE `has_specialty`
  ADD PRIMARY KEY (`doctor_id`,`specialty`);

--
-- Indexes for table `lab_order`
--
ALTER TABLE `lab_order`
  ADD PRIMARY KEY (`order_id`,`test_id`),
  ADD KEY `fk_visit_id` (`visit_id`);

--
-- Indexes for table `lab_test_type`
--
ALTER TABLE `lab_test_type`
  ADD PRIMARY KEY (`test_id`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`username`),
  ADD UNIQUE KEY `pid` (`pid`);

--
-- Indexes for table `nurse`
--
ALTER TABLE `nurse`
  ADD PRIMARY KEY (`nurse_id`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`patient_id`),
  ADD KEY `pid` (`pid`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`pid`),
  ADD UNIQUE KEY `ssn` (`ssn`);

--
-- Indexes for table `visit`
--
ALTER TABLE `visit`
  ADD PRIMARY KEY (`visit_id`),
  ADD UNIQUE KEY `fk_appointment` (`doctor_id`,`appt_datetime`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `doctor`
--
ALTER TABLE `doctor`
  MODIFY `doctor_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `lab_order`
--
ALTER TABLE `lab_order`
  MODIFY `order_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `lab_test_type`
--
ALTER TABLE `lab_test_type`
  MODIFY `test_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `nurse`
--
ALTER TABLE `nurse`
  MODIFY `nurse_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `patient_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `pid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `visit`
--
ALTER TABLE `visit`
  MODIFY `visit_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `lab_order`
--
ALTER TABLE `lab_order`
  ADD CONSTRAINT `fk_visit_id` FOREIGN KEY (`visit_id`) REFERENCES `visit` (`visit_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
