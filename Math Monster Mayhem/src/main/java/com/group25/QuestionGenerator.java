package com.group25;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * This class generates arithmetic questions with multiple-choice answers.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class QuestionGenerator {
    private int difficulty;
    private int[] range = {1, 10};

    /**
     * Constructs a QuestionGenerator with specified difficulty and range.
     * @param difficulty The difficulty level of the questions.
     * @param range The range within which the numbers in questions will be generated.
     */
    public QuestionGenerator(int difficulty, int[] range) {
        this.difficulty = difficulty;
        this.range = range;
    }

    /**
     * Sets the difficulty level.
     * @param difficulty The new difficulty level.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the difficulty level.
     * @return The difficulty level.
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Sets the range for number generation.
     * @param range The new range for number generation.
     */
    public void setRange(int[] range) {
        this.range = range;
    }

    /**
     * Gets the range for number generation.
     * @return The range for number generation.
     */
    public int[] getRange() {
        return this.range;
    }

    /**
     * Generates a random arithmetic question.
     * @return The generated question.
     */
    public Question generateQuestion() {
        Random random = new Random();
        int adjustedRangeMin = range[0] * difficulty;
        int adjustedRangeMax = range[1] * difficulty;
        int num1 = random.nextInt(adjustedRangeMax - adjustedRangeMin + 1) + adjustedRangeMin;
        int num2 = random.nextInt(adjustedRangeMax - adjustedRangeMin + 1) + adjustedRangeMin;

        // Choose a random operand (+, -, *, /)
        String[] operands = {"+", "-", "*", "/"};
        String operand = operands[random.nextInt(operands.length)];

        // Calculate the correct answer based on the chosen operand
        int correctAnswer;
        switch (operand) {
            case "+":
                correctAnswer = num1 + num2;
                break;
            case "-":
                correctAnswer = num1 - num2;
                break;
            case "*":
                correctAnswer = num1 * num2;
                break;
            case "/":
                if (num2 <= 0) {
                    num2 = 1;
                }

                int temp = random.nextInt(adjustedRangeMax - adjustedRangeMin + 1);
                num1 = num2 * temp;
                num2 = temp;
                if (num2 <= 0) {
                    num2 = 1;
                }
                correctAnswer = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Invalid operand");
        }

        String questionText = num1 + " " + operand + " " + num2;

        // Populate the answers array
        Answer[] answers = new Answer[4];
        int correctAnswerIndex = random.nextInt(4);
        answers[correctAnswerIndex] = new Answer(String.valueOf(correctAnswer), true);

        // Generate incorrect answers that are unique
        Set<Integer> generatedAnswers = new HashSet<>();
        generatedAnswers.add(correctAnswer);

        for (int i = 0; i < 4; i++) {
            if (i != correctAnswerIndex) {
                int incorrectAnswer;
                do {
                    incorrectAnswer = generateIncorrectAnswer(random, correctAnswer);
                } while (generatedAnswers.contains(incorrectAnswer));
                generatedAnswers.add(incorrectAnswer);
                answers[i] = new Answer(String.valueOf(incorrectAnswer), false);
            }
        }

        // Create and return the Question object
        return new Question(questionText, answers);
    }

    /**
     * Generates an incorrect answer for a given correct answer.
     * @param random Random number generator.
     * @param correctAnswer The correct answer.
     * @return An incorrect answer.
     */
    private int generateIncorrectAnswer(Random random, int correctAnswer) {
        int incorrectAnswer;
        do {
            incorrectAnswer = correctAnswer + random.nextInt(5) - 2;
        } while (incorrectAnswer == correctAnswer);
        return incorrectAnswer;
    }

    /**
     * Main method to demonstrate the functionality of the QuestionGenerator.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuestionGenerator questionGenerator = new QuestionGenerator(1, new int[]{1, 10});
        Question question = questionGenerator.generateQuestion();

        System.out.println("Question: " + question.getQuestion());
        Answer[] answers = question.getAnswers();
        for (int i = 0; i < answers.length; i++) {
            System.out.println("Choice " + (char) ('A' + i) + ": " + answers[i].getText());
        }

        System.out.print("Enter your choice (A, B, C, D): ");
        String response = scanner.nextLine().toUpperCase();

        boolean isCorrect = question.checkAnswer(response);

        if (isCorrect) {
            System.out.println("Your response is correct!");
        } else {
            System.out.println("Your response is incorrect.");
        }

        scanner.close();
    }
}
