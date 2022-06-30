##### Приложение, хранящее информацию о книгах в библиотеке

Использованы возможности Spring JDBC и spring-boot-starter-jdbc для подключения к реляционным базам данных.<br>
Интерфейс выполнен на Spring Shell.<br>

Хранение данных выполняется в БД MySQL. В БД необходимо создать schema library. Структуру и начальные данные создаёт liquibase.<br>

Описание Shell команд<br>
aa, allauthors: Показать всех авторов<br>
ab, allbooks: Показать все книги<br>
ag, allgenres: Показать все жанры<br>
bi, bookid: Показать книгу по id. bi --id 3<br>
db, deletebook: Удалить книгу из бибилиотеки. deletebook --id 1. db 1<br>
ib, insertbook: Добавить книгу в библиотеку. insertbook --name Каштанка --year 1887 --author 5 --genre 6. ib -N Каштанка -Y 1887 -A 5 -G 6<br>
ub, updatebook: Изменить книгу в библиотеке. updatebook --id 1 --name Каштанка --year 1887 --author 5 --genre 6. ub 1 -N Каштанка -Y 1887 -A 5 -G 6<br>
exit, quit: Выход
