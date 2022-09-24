$(function () {
    $.get('/allbook').done(function (books) {
        console.log(books);
        books.forEach(function (book) {
            $('tbody').append(`
                    <tr>
                        <td>${book.name}</td>
                        <td>${book.author && book.author.id ? book.author.firstName + ' ' + book.author.lastName : 'Не найден'}</td>
                        <td>${book.genre && book.genre.id ? book.genre.name : 'Не найден'}</td>
                        <td>${book.year}</td>
                        <td><a href="/book/${book.id}">Комментарии</a></td>
                        <td><a href="/book/edit/${book.id}">Править</a></td>
                        <td><a href="/book/delete/${book.id}">Удалить</a></td>
                    </tr>
                `)
        });
    })
});
