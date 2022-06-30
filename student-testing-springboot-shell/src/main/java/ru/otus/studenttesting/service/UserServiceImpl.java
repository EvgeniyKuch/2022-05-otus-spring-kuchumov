package ru.otus.studenttesting.service;

import org.springframework.stereotype.Service;
import ru.otus.studenttesting.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Map<Long, User> users;

    private final MessageService messageService;

    public UserServiceImpl(MessageService messageService) {
        this.messageService = messageService;
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

    @Override
    public String results() {
        return users.values().stream().map(user ->
                resultById(user.getId()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String resultById(Long id) {
        return new StringBuilder()
                .append(users.get(id).getFirstName()).append(" ").append(users.get(id).getLastName())
                .append(users.get(id).getGrade()
                        ? messageService.getMessage("pass") : messageService.getMessage("fail")).toString();
    }
}
