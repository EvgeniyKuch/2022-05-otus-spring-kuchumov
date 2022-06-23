package ru.otus.studenttesting.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input() {
        return scanner.nextLine();
    }

    @Override
    public void outputln(String out) {
        System.out.println(out);
    }

    @Override
    public void output(String out) {
        System.out.print(out);
    }
}
