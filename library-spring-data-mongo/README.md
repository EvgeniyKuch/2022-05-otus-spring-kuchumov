##### Приложение, хранящее информацию о книгах в библиотеке

Использованы возможности Spring Data для подключения к нереляционным базам данных.<br>
Интерфейс выполнен на Spring Shell.<br>

Хранение данных выполняется в Mongo DB. 
Для поддержки в MongoDB транзакций через набор реплик необходимо
mongod --replSet rs0
mongo --eval "rs.initiate()"
Структуру и начальные данные создаёт Mongock.<br>

Описание Shell команд<br>
aa, allauthors: Показать всех авторов<br>
ab, allbooks: Показать все книги<br>
ac, allcomments: Показать все комментарии<br>
ag, allgenres: Показать все жанры<br>
bi, bookid: Показать книгу с комментариями по id книги. bi --id 3<br>
cbi, commbookid: Показать комментарии к книге по её id. cbi --id 3<br>
ci, commentid: Показать комментарий по его id. ci --id 3<br>
db, deletebook: Удалить книгу из бибилиотеки. deletebook --id 1. db 1<br>
dc, deletecomm: Удалить комментарий к книге по его id. deletecomm --id 1. dc 1<br>
ib, insertbook: Добавить книгу в библиотеку. insertbook --name Каштанка --year 1887 --author 5 --genre 6. ib -N Каштанка -Y 1887 -A 5 -G 6<br>
ic, insertcomm: Добавить комментарий к книге. insertcomm --bookid 2 --content "Понравилось". iс -I 2 -C "Понравилось"<br>
ub, updatebook: Изменить книгу в библиотеке. updatebook --id 1 --name Каштанка --year 1887 --author 5 --genre 6. ub 1 -N Каштанка -Y 1887 -A 5 -G 6<br>
uc, updatecomm: Изменить текст комментария по его id. updatecomm --id 2 --content "Не понравилось". uc -I 2 -C "Не понравилось"<br>
exit, quit: Выход
