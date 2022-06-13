package ru.otus.studenttesting.service;

import ru.otus.studenttesting.domain.User;

public interface UserService {
    User getNewUser();

    User saveUser(User user);
}
