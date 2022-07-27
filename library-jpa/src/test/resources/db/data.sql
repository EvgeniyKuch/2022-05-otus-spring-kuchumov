insert into `authors`(id, first_name, last_name) values (1, 'Александр', 'Пушкин');
insert into `genres`(id, name) values (1, 'Поэма');
insert into `books`(name, release_year, author_id, genre_id) VALUES ('Руслан и Людмила', 1820, 1, 1);
INSERT INTO `comments`(`book_id`,`content`) VALUES(1, 'Понравилось');
INSERT INTO `comments`(`book_id`,`content`) VALUES(1, 'Много букв');
