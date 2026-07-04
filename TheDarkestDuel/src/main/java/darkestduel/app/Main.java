package darkestduel.app;

import darkestduel.classes.Archer;
import darkestduel.classes.Assassin;
import darkestduel.classes.CharacterClass;
import darkestduel.classes.SpearMaster;
import darkestduel.classes.Warrior;
import darkestduel.game.Arena;
import darkestduel.game.Game;
import darkestduel.game.HumanController;
import darkestduel.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static List<Supplier<CharacterClass>> availableClasses() {
        List<Supplier<CharacterClass>> classOptions = new ArrayList<>();

        classOptions.add(Warrior::new);
        classOptions.add(Archer::new);
        classOptions.add(Assassin::new);
        classOptions.add(SpearMaster::new);

        return classOptions;
    }

    private static CharacterClass chooseCharacterClass(String playerLabel) {
        List<Supplier<CharacterClass>> classOptions = availableClasses();

        System.out.println("\nEscolha a classe de " + playerLabel + ":");

        for (int i = 0; i < classOptions.size(); i++) {
            CharacterClass sample = classOptions.get(i).get();

            System.out.println((i + 1) + ". " + sample.getName() +
                    " | HP " + sample.getMaxHp() +
                    " | AC/turno " + sample.getAcPerTurn() +
                    " | Dano " + sample.getDiceAmount() + "d" + sample.getDiceSides() +
                    " | Movimento 1-" + sample.getMaxMovement());
        }

        while (true) {
            System.out.print("Número da classe: ");
            String choice = SCANNER.nextLine().trim();

            if (choice.matches("\\d+")) {
                int index = Integer.parseInt(choice) - 1;

                if (index >= 0 && index < classOptions.size()) {
                    return classOptions.get(index).get();
                }
            }

            System.out.println("Escolha inválida.");
        }
    }

    private static String safeInput(String prompt, String defaultValue) {
        System.out.print(prompt);

        String value = SCANNER.nextLine().trim();

        return value.isEmpty() ? defaultValue : value;
    }

    private static Game createManualGame() {
        System.out.println("\nConfiguração da partida manual");

        String arenaSizeText = safeInput("Tamanho da arena [12]: ", "12");

        int arenaSize = arenaSizeText.matches("\\d+")
                ? Integer.parseInt(arenaSizeText)
                : 12;

        if (arenaSize < 4) {
            System.out.println("Tamanho inválido. Usando arena com 12 casas.");
            arenaSize = 12;
        }

      Arena arena = new Arena(arenaSize);

        CharacterClass classA = chooseCharacterClass("Jogador A");
        CharacterClass classB = chooseCharacterClass("Jogador B");

        int defaultPosA = Math.max(0, arenaSize / 3);
        int defaultPosB = Math.min(arenaSize - 1, 2 * arenaSize / 3);

        Player playerA = new Player("Jogador A", "A", classA, defaultPosA);
        Player playerB = new Player("Jogador B", "B", classB, defaultPosB);

        HumanController controllerA = new HumanController(SCANNER);
        HumanController controllerB = new HumanController(SCANNER);

        return new Game(
                arena,
                playerA,
                playerB,
                controllerA,
                controllerB,
                80
        );
    }

    public static void main(String[] args) {
        System.out.println("The Darkest Duel");
        System.out.println("Modo manual para dois jogadores");

        Game game = createManualGame();

        game.run();
    }
}