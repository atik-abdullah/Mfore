-- MySQL dump 10.13  Distrib 5.6.11, for osx10.7 (x86_64)
--
-- Host: localhost    Database: mfore_schema
-- ------------------------------------------------------
-- Server version	5.6.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP DATABASE IF EXISTS `mfore_schema`;
CREATE DATABASE `mfore_schema`;
USE `mfore_schema`;
GRANT ALL ON mfore_schema.* TO 'mnyrsuser'@'localhost';
--
-- Table structure for table `enrollment`
--

DROP TABLE IF EXISTS `enrollment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enrollment` (
  `enrollment_date` date DEFAULT NULL,
  `fk_person_id` int(11) DEFAULT NULL,
  `fk_service_id` int(11) DEFAULT NULL,
  KEY `FK_personID` (`fk_person_id`),
  KEY `FK_serviceID` (`fk_service_id`),
  CONSTRAINT `enrollment_ibfk_1` FOREIGN KEY (`fk_person_id`) REFERENCES `person` (`person_id`),
  CONSTRAINT `enrollment_ibfk_2` FOREIGN KEY (`fk_service_id`) REFERENCES `service` (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollment`
--

LOCK TABLES `enrollment` WRITE;
/*!40000 ALTER TABLE `enrollment` DISABLE KEYS */;
/*!40000 ALTER TABLE `enrollment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `identification_type`
--

DROP TABLE IF EXISTS `identification_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `identification_type` (
  `identification_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `identification_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`identification_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `identification_type`
--

LOCK TABLES `identification_type` WRITE;
/*!40000 ALTER TABLE `identification_type` DISABLE KEYS */;
INSERT INTO `identification_type` VALUES (1,'National Identificaion'),(2,'Kela'),(3,'Driving License'),(4,'Birth Certificate');
/*!40000 ALTER TABLE `identification_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `intrusion_attempt`
--

DROP TABLE IF EXISTS `intrusion_attempt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `intrusion_attempt` (
  `a_pid` int(11) NOT NULL AUTO_INCREMENT,
  `reason` varchar(100) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `password` varchar(16) NOT NULL,
  `ipa` varchar(16) NOT NULL,
  `attempt_time` datetime NOT NULL,
  `user_agent` varchar(50) NOT NULL,
  `user_os` varchar(50) NOT NULL,
  `service_id` varchar(45) NOT NULL,
  PRIMARY KEY (`a_pid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intrusion_attempt`
--

LOCK TABLES `intrusion_attempt` WRITE;
/*!40000 ALTER TABLE `intrusion_attempt` DISABLE KEYS */;
/*!40000 ALTER TABLE `intrusion_attempt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medication_reminder_service`
--

DROP TABLE IF EXISTS `medication_reminder_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medication_reminder_service` (
  `medication_reminder_service_id` int(11) NOT NULL AUTO_INCREMENT,
  `medicine_desc` varchar(60) DEFAULT NULL,
  `reminding_time` varchar(15) DEFAULT NULL,
  `fk_person_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`medication_reminder_service_id`),
  KEY `FK_personID` (`fk_person_id`),
  CONSTRAINT `medication_reminder_service_ibfk_1` FOREIGN KEY (`fk_person_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medication_reminder_service`
--

LOCK TABLES `medication_reminder_service` WRITE;
/*!40000 ALTER TABLE `medication_reminder_service` DISABLE KEYS */;
INSERT INTO `medication_reminder_service` VALUES (4,'','? string: ?',1),(5,'','? string: ?',1),(6,'1342','0130',1);
/*!40000 ALTER TABLE `medication_reminder_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mnyrs_specific_detail`
--

DROP TABLE IF EXISTS `mnyrs_specific_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mnyrs_specific_detail` (
  `mnyrs_specific_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_person_id` int(11) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `height` int(255) unsigned DEFAULT NULL,
  `weight` int(255) unsigned DEFAULT NULL,
  `blood_group` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`mnyrs_specific_detail_id`),
  UNIQUE KEY `fk_person_id` (`fk_person_id`),
  CONSTRAINT `mnyrs_specific_detail_ibfk_1` FOREIGN KEY (`fk_person_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `person_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_user_id` int(10) unsigned DEFAULT NULL,
  `fk_identification_type_id` int(10) DEFAULT NULL,
  `fk_person_contact_detail_id` int(10) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `identification_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  KEY `FK_user_id` (`fk_user_id`),
  KEY `FK_identification_type_id` (`fk_identification_type_id`),
  KEY `FK_person_Contact_detail_id` (`fk_person_contact_detail_id`),
  CONSTRAINT `person_ibfk_1` FOREIGN KEY (`fk_user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `person_ibfk_2` FOREIGN KEY (`fk_identification_type_id`) REFERENCES `identification_type` (`identification_type_id`),
  CONSTRAINT `person_ibfk_3` FOREIGN KEY (`fk_person_contact_detail_id`) REFERENCES `person_contact_detail` (`person_contact_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `person_contact_detail`
--

DROP TABLE IF EXISTS `person_contact_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_contact_detail` (
  `person_contact_detail_id` int(10) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `street_address` varchar(120) DEFAULT NULL,
  `postal_code` varchar(120) DEFAULT NULL,
  `city` varchar(15) DEFAULT NULL,
  `country` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`person_contact_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `reading_profile_service`
--

DROP TABLE IF EXISTS `reading_profile_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reading_profile_service` (
  `reading_profile_service_id` int(11) NOT NULL AUTO_INCREMENT,
  `reading_days` varchar(20) DEFAULT NULL,
  `reading_type` int(4) DEFAULT NULL,
  `fk_person_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`reading_profile_service_id`),
  KEY `FK_personID` (`fk_person_id`),
  CONSTRAINT `reading_profile_service_ibfk_1` FOREIGN KEY (`fk_person_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;





--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `password_salt` varchar(30) DEFAULT NULL,
  `registered_on` datetime NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `user_session`
--

DROP TABLE IF EXISTS `user_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_session` (
  `session_pid` int(11) NOT NULL AUTO_INCREMENT,
  `session` varchar(64) NOT NULL,
  `session_end_reason` varchar(100) NOT NULL,
  `user_fid` int(11) NOT NULL,
  `ipa` varchar(16) NOT NULL,
  `in_time` datetime NOT NULL,
  `out_time` datetime DEFAULT NULL,
  `user_agent` varchar(50) DEFAULT NULL,
  `user_os` varchar(50) DEFAULT NULL,
  `service_id` varchar(45) NOT NULL,
  PRIMARY KEY (`session_pid`),
  UNIQUE KEY `session` (`session`),
  KEY `fk_sanchay_session_1_idx` (`user_fid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_session`
--


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-10 19:20:15
