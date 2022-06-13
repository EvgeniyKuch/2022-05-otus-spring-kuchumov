package ru.otus.studenttesting.service;

import org.springframework.stereotype.Service;
import ru.otus.studenttesting.domain.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final Map<Long, User> users;

    public UserServiceImpl() {
        users = new HashMap<>();
    }

    @Override
    public User getNewUser() {
        User user = new User();
        user.setId((long) users.size());
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public User saveUser(User user) {
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

}
