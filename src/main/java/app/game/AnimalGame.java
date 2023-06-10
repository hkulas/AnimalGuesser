package app.game;

import app.model.Animal;

import java.util.*;

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


    private List<Animal> animals;
    private Scanner scanner;

    public AnimalGame() {
        scanner = new Scanner(System.in);
    }

    public void runGame() {
        animals = new ArrayList<>();
        askForAnimals();
        animals.replaceAll(this::formatAnimal);
        String fact = specifyFacts();
        generateAnimalFacts(fact);


    }

    private void generateAnimalFacts(String fact) {
        Map<String, String> verbMap = new HashMap<>();
        verbMap.put("can", "can't");
        verbMap.put("has", "doesn't have");
        verbMap.put("is", "isn't");

        Animal firstAnimal = animals.get(0);
        Animal secondAnimal = animals.get(1);
        System.out.printf("Is it correct for %s?\n", secondAnimal.getName());
        String response = scanner.nextLine().toLowerCase();
        while (!validateYesOrNo(response)) {
            System.out.println("Please answer yes or no.");
            response = scanner.nextLine().toLowerCase();
        }
        String verb = fact.split(" ")[1];
        String factEnd = prepareFactEnd(fact, verb);
        String fact1;
        String fact2;

        if (positiveResponses.contains(response)) {
            fact1 = generateFact(firstAnimal, verbMap.get(verb), factEnd);
            fact2 = generateFact(secondAnimal, verb, factEnd);
        } else if (negativeResponses.contains(response)) {
            fact1 = generateFact(firstAnimal, verb, factEnd);
            fact2 = generateFact(secondAnimal, verbMap.get(verb), factEnd);
        } else {
            throw new IllegalArgumentException("Unexpected response: " + response);
        }

        firstAnimal.setFact(fact1);
        secondAnimal.setFact(fact2);

        System.out.println("I have learned the following facts about animals:");
        String dash = "-";
        System.out.printf("%s %s\n", dash, fact1);
        System.out.printf("%s %s\n", dash, fact2);

        System.out.println("I can distinguish these animals by asking the question: ");
        System.out.println("- " + generateQuestion(fact));
    }

    private String generateFact(Animal animal, String verb, String factEnd) {
        return String.format("%s %s %s", swapForDefinedArticle(animal.getName()), verb, factEnd);
    }
    private String prepareFactEnd(String fact, String verb) {
        String dot = ".";
        String factEnd = fact.substring(fact.indexOf(verb) + verb.length()).replace("?", "").trim();  // get the fact without 'It can/has/is'
        if (!factEnd.endsWith(dot)) {
            factEnd += dot;
        }
        return factEnd;
    }

    private boolean validateYesOrNo(String input) {

        return positiveResponses.contains(input) || negativeResponses.contains(input);
    }

    private String generateQuestion(String fact) {
        String verb = fact.split(" ")[1];
        String factEnd = fact.substring(fact.indexOf(verb) + verb.length()).replace(".", "")
                .replace("?", "").trim();  // get the fact without 'It can/has/is'

        return switch (verb) {
            case "can" -> "Can it " + factEnd + "?";
            case "has" -> "Does it have " + factEnd + "?";
            case "is" -> "Is it " + factEnd + "?";
            default -> throw new IllegalArgumentException("Unexpected value: " + verb);
        };
    }

    private String swapForDefinedArticle(String name) {
        return name.replaceFirst("^a |^an ", "The ");
    }

    private String specifyFacts() {
        String fact = "";
        while (true) {
            System.out.printf("Specify a fact that distinguishes %s from %s.\n", animals.get(0).getName(), animals.get(1).getName());
            System.out.println("The sentence should be of the format: 'It can/has/is ...'");
            fact = scanner.nextLine();
            if (checkFactFormat(fact.toLowerCase())) {
                break;
            } else {
                printStatementExamples();
            }
        }
        return fact;
    }

    private void printStatementExamples() {
        System.out.println("The examples of a statement:");
        System.out.println("-It can fly");
        System.out.println("-It has horn");
        System.out.println("-It is a mammal");
    }

    private boolean checkFactFormat(String fact) {
        return fact.startsWith("it can") || fact.startsWith("it has") || fact.startsWith("it is");
    }

    private void askForAnimals() {
        System.out.println("Enter the first animal: ");
        animals.add(askForAnimal());
        System.out.println("Enter the second animal: ");
        animals.add(askForAnimal());
    }

    private Animal askForAnimal() {
        return new Animal(scanner.nextLine().toLowerCase().trim());
    }

    private Animal formatAnimal(Animal animal) {
        String animalName = animal.getName();
        String[] articleAndName = animalName.split(" ");

        if (animalName.startsWith("a ") || animalName.startsWith("an ")) {
        } else if (animalName.startsWith("the ")) {
            animalName = returnCorrectArticle(articleAndName[1]) + animalName.substring(3);
        } else if (animalName.matches("^[aeiou].*")) {
            animalName = "an " + animalName;
        } else {
            animalName = "a " + animalName;
        }
        animal.setName(animalName.trim());
        return animal;
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

    private void formulateQuestion(Animal animal) {
        System.out.printf("Is it %s?\n", animal.getName());
    }

    private String returnCorrectArticle(String animal) {
        if (animal.matches("^[aeiou].*")) {
            return "an";
        } else {
            return "a";
        }
    }
}