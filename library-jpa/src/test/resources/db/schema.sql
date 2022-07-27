DROP TABLE IF EXISTS `authors`;
DROP TABLE IF EXISTS `genres`;
DROP TABLE IF EXISTS `books`;
DROP TABLE IF EXISTS `comments`;

CREATE TABLE `authors`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `first_name` varchar(255) NOT NULL,
    `last_name`  varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `genres`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `books`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `name`         varchar(255) NOT NULL,
    `release_year` int          NOT NULL,
    `author_id`    bigint       NOT NULL,
    `genre_id`     bigint       NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `comments`
(
    `id`      bigint        NOT NULL AUTO_INCREMENT,
    `book_id` bigint        NOT NULL,
    `content` varchar(1024) NOT NULL,
    PRIMARY KEY (`id`)
);


