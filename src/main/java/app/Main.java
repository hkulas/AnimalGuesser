package app;

import app.game.AnimalGame;
import app.util.InteractionManager;

public class Main {
    public static void main(String[] args) {
        InteractionManager.greetUser();
        AnimalGame animalGame = new AnimalGame();
        animalGame.runGame();
        InteractionManager.sayGoodbye();
    }
}
