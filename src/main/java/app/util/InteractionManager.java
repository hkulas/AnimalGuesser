package app.util;


import java.time.LocalTime;
import java.util.List;
import java.util.Random;

public class InteractionManager {
    private static final List<String> goodbye = List.of("Bye.", "Goodbye", "See you later", "See ya!", "Catch you later",
            "Have a good day", "Take care", "Peace!", "Adios, amigos!");

    public static void greetUser() {
        LocalTime currentTime = LocalTime.now();
        String greetingText = "";
        if (currentTime.isAfter(LocalTime.of(0, 0)) && currentTime.isBefore(LocalTime.of(5, 0))) {
            greetingText = "Hi, Night Owl!";
        } else if (currentTime.isBefore(LocalTime.of(12, 0))) {
            greetingText = "Good morning!";
        } else if (currentTime.isBefore(LocalTime.of(18, 0))) {
            greetingText = "Good afternoon!";
        } else {
            greetingText = "Good evening!";
        }
        System.out.printf("%s\n\n", greetingText);
    }

    public static void sayGoodbye() {
        System.out.printf(goodbye.get(new Random().nextInt(goodbye.size())));
    }
}
