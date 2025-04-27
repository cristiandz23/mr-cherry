Enter password: 
-- MySQL dump 10.13  Distrib 8.4.3, for Linux (x86_64)
--
-- Host: localhost    Database: mr_cherry
-- ------------------------------------------------------
-- Server version	8.4.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `department` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  `district` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  `floor` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  `number` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  `street` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `person_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKie7rkxg6fmtxx1aes55ox9yxd` (`person_id`),
  CONSTRAINT `FKnvxfigj5o3te7ig37cq7qo0bc` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_table`
--

DROP TABLE IF EXISTS `order_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_table` (
  `amount` float NOT NULL,
  `date` date DEFAULT NULL,
  `order_status` tinyint DEFAULT NULL,
  `address_id` bigint DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `payment_id` bigint DEFAULT NULL,
  `name` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  `status` enum('CANCELED','CREATED','DELIVERED','PROCESS','READY','REJECTED','SENT') COLLATE utf16_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3i3c669vu93q9wiblm0bpykdp` (`address_id`),
  UNIQUE KEY `UK29o52mnhk04rlqjf5w0p9tj51` (`payment_id`),
  KEY `FKdit09e676nqbguvt1k1w8mlj2` (`customer_id`),
  CONSTRAINT `FKan38srg49qpanv9u1hskl9m7p` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FKdit09e676nqbguvt1k1w8mlj2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FKt08pnd89wc3gpq06nqrrf6fcr` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`),
  CONSTRAINT `order_table_chk_1` CHECK ((`order_status` between 0 and 6))
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_table_products`
--

DROP TABLE IF EXISTS `order_table_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_table_products` (
  `order_id` bigint NOT NULL,
  `products_id` bigint NOT NULL,
  KEY `FK6euwqx7dke7cmgnqri6if86mw` (`products_id`),
  KEY `FKmymwasvh5ta655k9wk38d01v3` (`order_id`),
  CONSTRAINT `FK6euwqx7dke7cmgnqri6if86mw` FOREIGN KEY (`products_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKmymwasvh5ta655k9wk38d01v3` FOREIGN KEY (`order_id`) REFERENCES `order_table` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_payment_provider` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `payment_type` enum('CASH','CREDITCARD') COLLATE utf16_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKmf7n8wo2rwrxsd6f3t9ub2mep` (`order_id`),
  CONSTRAINT `FK8p50pxqlca5tru4wjbqjp4vtm` FOREIGN KEY (`order_id`) REFERENCES `order_table` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `address_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKo8tnkglv9n1eeqmo7de7em37n` (`address_id`),
  CONSTRAINT `FKk7rgn6djxsv2j2bv1mvuxd4m9` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `price` float NOT NULL,
  `stock` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_table`
--

DROP TABLE IF EXISTS `user_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_table` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `person_id` bigint DEFAULT NULL,
  `password` varchar(255) COLLATE utf16_spanish_ci DEFAULT NULL,
  `user_name` varchar(255) COLLATE utf16_spanish_ci NOT NULL,
  `role` enum('Admin','Customer','Delivery') COLLATE utf16_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKp50irg6kthpq3f33xu9r1kw4x` (`user_name`),
  UNIQUE KEY `UKo0l5sy9ohe6iy6fqutl68hc3l` (`person_id`),
  CONSTRAINT `FK6ej5gli1xp93e1cxv2li9jivs` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-27  4:34:23
