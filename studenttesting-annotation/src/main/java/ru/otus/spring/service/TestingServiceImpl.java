package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class TestingServiceImpl implements TestingService {

    private final PrintService printService;

    private final QuestionService questionService;

    private final Scanner sc = new Scanner(System.in);

    private final int threshold;

    private String firstName;

    private String lastName;

    public TestingServiceImpl(PrintService printService,
                              QuestionService questionService,
                              @Value("${threshold}") int threshold) {
        this.printService = printService;
        this.questionService = questionService;
        this.threshold = threshold;
    }

    @Override
    public void testing() {
        int numberCorrectAnswers = 0;
        try {
            queryFirstLastName();
            for (int i = 0; i < questionService.getNumberQuestions(); i++) {
                printService.printQuestion(i);
                int j = Integer.parseInt(sc.nextLine());
                if (j < 1 || j > questionService.getNumberAnswersForQuestion(i)) {
                    System.out.println("Number out of bound, please try again");
                    i--;
                    continue;
                }
                if (j == questionService.getCorrectAnswerIndexForQuestion(i) + 1) {
                    numberCorrectAnswers++;
                    System.out.println("Correct");
                } else {
                    System.out.println("Incorrect");
                }
            }
            System.out.println("Number correct answers: " + numberCorrectAnswers);
            String grade = numberCorrectAnswers >= threshold ? ", your grade: pass" : ", your grade: fail";
            System.out.println(firstName + " " + lastName + grade);
        } catch (NumberFormatException e) {
            System.out.println("Number input error");
        }
    }

    private void queryFirstLastName() {
        System.out.println("Enter first name");
        firstName = sc.nextLine();
        System.out.println("Enter last name");
        lastName = sc.nextLine();
    }
}
