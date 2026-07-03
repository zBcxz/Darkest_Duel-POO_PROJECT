package darkestduel.game;

import darkestduel.actions.Action;
import darkestduel.actions.AttackAction;
import darkestduel.actions.FireBombAction;
import darkestduel.actions.MovementAction;
import darkestduel.model.Player;
import darkestduel.util.MovementPreview;

import java.util.List;
import java.util.Scanner;

public class HumanController {
    private final Scanner scanner;

    public HumanController(Scanner scanner) {
        this.scanner = scanner;
    }
    public Action chooseAction(Player player, Player opponent, Arena arena) {
         List<Action> actions = player.getCharacterClass().getActions();

        while (true) {
              System.out.println("\nTurno de " + player.getName() + " (" + player.getCharacterClass().getName() + ")");
              System.out.println("HP: " + player.getHp() + "/" + player.getMaxHp() +
                      " | AC: " + player.getAc() +
                      " | Posição: " + player.getPosition());
              System.out.println("Status: " + player.statusSummary());
              System.out.println("\nAções disponíveis:");

              for (int i = 0; i < actions.size(); i++) {
                Action action = actions.get(i);
                String status = actionStatus(action, player, opponent, arena);
                String extra = "";

                if (action instanceof AttackAction) {
                    AttackAction attackAction = (AttackAction) action;
                    extra = " | Alcance: " + attackAction.getMinRange() + "-" + attackAction.getMaxRange();
                }

                if (action instanceof FireBombAction) {
                    FireBombAction fireBombAction = (FireBombAction) action;
                    extra = " | Alcance: " + fireBombAction.getMinRange() + "-" + fireBombAction.getMaxRange();
                }

                System.out.println((i + 1) + ". " + action.getName() +
                        " | Custo: " + action.getAcCost() + " AC" + extra +
                        " | " + status);
                System.out.println("   " + action.getDescription());
            }

            System.out.print("Escolha o número da ação: ");
            String choice = SCANNER.nextLine().trim();

            if (!choice.matches("\\d+")) {
                System.out.println("Entrada inválida.");
                continue;
            }

            int index = Integer.parseInt(choice) - 1;
            if (index < 0 || index >= actions.size()) {
                System.out.println("Ação inexistente.");
                continue;
            }

            return actions.get(index);
        }
    }

    private String actionStatus(Action action, Player player, Player opponent, Arena arena) {
        if (player.getAc() < action.getAcCost()) {
            return "AC insuficiente: precisa de " + action.getAcCost() + ", possui " + player.getAc() + ".";
        }

        int cooldown = player.cooldownRemaining(action.getKey());
        if (cooldown > 0) {
            return "Cooldown restante: consumir mais " + cooldown + " AC.";
        }

        if (action instanceof MovementAction) {
            MovementAction movementAction = (MovementAction) action;
            MovementPreview preview = arena.previewMovement(player, opponent, movementAction.getSteps());
            return preview.isValid() ? "OK" : preview.getMessage();
        }

        if (action instanceof AttackAction) {
            AttackAction attackAction = (AttackAction) action;
            if (!attackAction.isTargetInRange(player, opponent, arena)) {
                return "Alvo fora do alcance.";
            }
        }

        if (action instanceof FireBombAction) {
            FireBombAction fireBombAction = (FireBombAction) action;
            if (!fireBombAction.isTargetInRange(player, opponent, arena)) {
                return "Alvo fora do alcance.";
            }
        }

        return "OK";
    }
}