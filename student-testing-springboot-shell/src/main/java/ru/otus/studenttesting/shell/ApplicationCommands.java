package ru.otus.studenttesting.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.studenttesting.service.TestingService;
import ru.otus.studenttesting.service.UserService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final TestingService testingService;

    private final UserService userService;

    @ShellMethod(value = "Start testing", key = {"s", "start"})
    public String startTesting() {
        testingService.testing();
        return "End of testing";
    }

    @ShellMethod(value = "Show results of all students", key = {"r", "result"})
    public String showResult() {
        return userService.results();
    }
}
