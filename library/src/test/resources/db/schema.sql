DROP SCHEMA IF EXISTS LIBRARY;
CREATE SCHEMA LIBRARY;
USE library;

DROP TABLE IF EXISTS `authors`;
DROP TABLE IF EXISTS `genres`;
DROP TABLE IF EXISTS `books`;

CREATE TABLE `library`.`authors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `library`.`genres` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `library`.`books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `year` int NOT NULL,
  `author_id` bigint NOT NULL,
  `genre_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
);

