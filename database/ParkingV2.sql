CREATE DATABASE  IF NOT EXISTS `parking` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `parking`;
-- MySQL dump 10.13  Distrib 8.0.45, for macos15 (arm64)
--
-- Host: localhost    Database: parking
-- ------------------------------------------------------
-- Server version	8.4.8

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `parkingRecord`
--

DROP TABLE IF EXISTS `parkingRecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parkingRecord` (
  `registerID` int NOT NULL AUTO_INCREMENT,
  `vehiclePlate` varchar(30) NOT NULL,
  `entryTime` datetime NOT NULL,
  `exitTime` datetime DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `status` enum('ACTIVE','FINISHED') NOT NULL,
  PRIMARY KEY (`registerID`),
  KEY `parkingrecord_ibfk_1` (`vehiclePlate`),
  CONSTRAINT `parkingrecord_ibfk_1` FOREIGN KEY (`vehiclePlate`) REFERENCES `vehicles` (`vehiclePlate`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkingRecord`
--

LOCK TABLES `parkingRecord` WRITE;
/*!40000 ALTER TABLE `parkingRecord` DISABLE KEYS */;
INSERT INTO `parkingRecord` VALUES (18,'LTO2K26','2026-04-17 23:33:19','2026-04-18 02:30:00',24.00,'FINISHED'),(19,'LTO2K26','2026-04-17 23:35:21','2026-04-18 08:00:00',72.00,'FINISHED'),(20,'HXK2P67','2026-04-18 14:09:46',NULL,NULL,'ACTIVE'),(21,'KXQ2I98','2026-04-18 14:19:40',NULL,NULL,'ACTIVE'),(22,'LTO2K26','2026-04-19 14:13:15',NULL,NULL,'ACTIVE'),(23,'JPK2K67','2026-04-19 14:17:24',NULL,NULL,'ACTIVE'),(24,'MNO2P98','2026-04-19 14:20:20',NULL,NULL,'ACTIVE'),(25,'HGK7N43','2026-04-19 14:22:37',NULL,NULL,'ACTIVE'),(26,'CDB2K25','2026-04-19 14:24:03','2026-04-19 17:00:00',24.00,'FINISHED'),(27,'CDB2K25','2026-04-19 14:28:50',NULL,NULL,'ACTIVE');
/*!40000 ALTER TABLE `parkingRecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicles` (
  `vehicleID` int NOT NULL AUTO_INCREMENT,
  `vehiclePlate` varchar(45) NOT NULL,
  `vehicleModel` varchar(45) NOT NULL,
  PRIMARY KEY (`vehicleID`),
  UNIQUE KEY `vehiclePlate_UNIQUE` (`vehiclePlate`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
/*!40000 ALTER TABLE `vehicles` DISABLE KEYS */;
INSERT INTO `vehicles` VALUES (19,'LTO2K26','Chevrolet Camaro'),(20,'HXK2P67','Toyota Etios'),(21,'KXQ2I98','Fiat Argo'),(22,'JPK2K67','Corolla Etios'),(23,'MNO2P98','Fiat Kronos'),(24,'HGK7N43','Peugeot 208'),(25,'CDB2K25','Renault Kwid');
/*!40000 ALTER TABLE `vehicles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-19 14:43:20
