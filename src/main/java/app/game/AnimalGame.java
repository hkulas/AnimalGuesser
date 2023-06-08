package app.game;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class AnimalGame {
    private final Set<String> positiveResponses = Set.of("y", "yes", "yeah", "yep", "sure", "right", "affirmative",
            "correct", "indeed", "you bet", "exactly", "you said it");
    private final Set<String> negativeResponses = Set.of("n", "no", "no way", "nah", "nope", "negative",
            "I don't think so", "yeah no");

    private final List<String> clarificationPhrases = List.of("I'm not sure I caught you: was it yes or no?",
            "Funny, I still don't understand, is it yes or no?",
            "Oh, it's too complicated for me: just tell me yes or no.",
            "Could you please simply say yes or no?",
            "Oh, no, don't try to confuse me: say yes or no.");


    private String animal;
    private Scanner scanner;

    public AnimalGame() {
        scanner = new Scanner(System.in);
    }

    public void runGame() {
        animal = askForAnimal();
        animal = formatAnimal(animal);
        formulateQuestion(animal);
        while (!confirmAnimal()) {

        }
    }

    private String askForAnimal() {
        System.out.println("Enter an animal:");
        return scanner.nextLine().toLowerCase().trim();
    }

    private String formatAnimal(String animal) {
        String[] articleAndName = animal.split(" ");

        if (animal.startsWith("a ") || animal.startsWith("an ")) {
            // No modification needed if article is already specified
        } else if (animal.startsWith("the ")) {
            animal = returnCorrectArticle(articleAndName[1]) + animal.substring(3);
        } else if (animal.matches("^[aeiou].*")) {
            animal = "an " + animal; // Add "an" as the article
        } else {
            animal = "a " + animal; // Add "a" as the article
        }

        return animal.trim();
    }

    private boolean confirmAnimal() {
        String response = scanner.nextLine().toLowerCase().trim();
        if (response.endsWith(".") || response.endsWith("!")) {
            response = response.substring(0, response.length() - 1);
        }
        if (positiveResponses.contains(response)) {
            System.out.println("You answered: Yes");
            return true;
        } else if (negativeResponses.contains(response)) {
            System.out.println("You answered: No");
            return true;
        } else {
            System.out.println(clarificationPhrases.get(new Random().nextInt(clarificationPhrases.size())));
            return false;
        }

    }

    private void formulateQuestion(String animal) {
        System.out.printf("Is it %s?\n", animal);
    }

    private String returnCorrectArticle(String animal) {
        if (animal.matches("^[aeiou].*")) {
            return "an";
        } else {
            return "a";
        }
    }
}
