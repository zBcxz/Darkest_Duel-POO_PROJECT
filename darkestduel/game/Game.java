package darkestduel.game;

import darkestduel.actions.Action;
import darkestduel.actions.WaitAction;
import darkestduel.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private Arena arena;
    private Player playerA;
    private Player playerB;
    private HumanController controllerA;
    private HumanController controllerB;
    private int maxTurns;
    private int turnCounter;

    public Game(
            Arena arena,
            Player playerA,
            Player playerB,
            HumanController controllerA,
            HumanController controllerB,
            int maxTurns
    ) {
        this.arena = arena;
        this.playerA = playerA;
        this.playerB = playerB;
        this.controllerA = controllerA;
        this.controllerB = controllerB;
        this.maxTurns = maxTurns;
        this.turnCounter = 1;
    }

    public void run() {
        System.out.println("\n=== THE DARKEST DUEL ===");
        System.out.println("Legenda da arena: A/B = jogadores | * = fogo | _ = casa vazia\n");

        while (playerA.isAlive() && playerB.isAlive() && turnCounter <= maxTurns) {
            TurnData data = currentActorData();

            System.out.println("\n" + "=".repeat(60));
            System.out.println("Turno " + turnCounter);
            System.out.println("Arena: " + arena.render(playerA, playerB));
            printPlayerSummary();

            playTurn(data.getActor(), data.getOpponent(), data.getController());

            arena.tickHazards();
            turnCounter += 1;
        }

        showResult();
    }

    private TurnData currentActorData() {
        if (turnCounter % 2 == 1) {
            return new TurnData(playerA, playerB, controllerA);
        }
        return new TurnData(playerB, playerA, controllerB);
    }

    private void playTurn(Player actor, Player opponent, HumanController controller) {
        System.out.println("\n>>> Turno de " + actor.getName());

        actor.gainTurnAc();
        System.out.println(actor.getName() + " recebeu " + actor.getCharacterClass().getAcPerTurn() +
                " AC (AC atual: " + actor.getAc() + ").");

        for (String message : actor.triggerTurnStartEffects(opponent, arena)) {
            System.out.println(message);
        }

        for (String message : arena.triggerTileEffects(actor)) {
            System.out.println(message);
        }

        if (!actor.isAlive()) {
            System.out.println(actor.getName() + " não resistiu aos efeitos do início do turno.");
            return;
        }

        int actionsExecuted = 0;
        int maxActionsPerTurn = 12;

        while (actor.isAlive() && opponent.isAlive()) {
            List<Action> executableNonWaitActions = new ArrayList<>();
            for (Action action : actor.getCharacterClass().getActions()) {
                if (!(action instanceof WaitAction) && action.canExecute(actor, opponent, arena)) {
                    executableNonWaitActions.add(action);
                }
            }

            if (executableNonWaitActions.isEmpty()) {
                System.out.println(actor.getName() + " não possui ações adicionais disponíveis " +
                        "e passa o turno com " + actor.getAc() + " AC acumulado.");
                break;
            }

            Action action = controller.chooseAction(actor, opponent, arena);
            System.out.println("\nAção escolhida: " + action.getName());

            for (String message : action.execute(actor, opponent, arena)) {
                System.out.println(message);
            }

            printArenaAfterAction();

            actionsExecuted += 1;

            if (action.endsTurn()) {
                System.out.println(actor.getName() + " passou o turno com " + actor.getAc() + " AC acumulado.");
                break;
            }

            if (actor.getAc() <= 0) {
                System.out.println(actor.getName() + " ficou sem AC e encerrou o turno.");
                break;
            }

            if (actionsExecuted >= maxActionsPerTurn) {
                System.out.println("Limite de ações por turno atingido para evitar repetição excessiva.");
                break;
            }
        }

        for (String message : actor.triggerTurnEndEffects()) {
            System.out.println(message);
        }
    }

    private void printArenaAfterAction() {
        System.out.println("Arena atualizada: " + arena.render(playerA, playerB));
    }

    private void printPlayerSummary() {
        for (Player player : List.of(playerA, playerB)) {
            String cooldownText = "Nenhum";
            if (!player.getCooldowns().isEmpty()) {
                StringBuilder builder = new StringBuilder();
                int index = 0;
                for (Map.Entry<String, Integer> entry : player.getCooldowns().entrySet()) {
                    if (index > 0) {
                        builder.append(", ");
                    }
                    builder.append(entry.getKey()).append(":").append(entry.getValue()).append("AC");
                    index++;
                }
                cooldownText = builder.toString();
            }

            System.out.println(player.getSymbol() + " - " + player.getName() +
                    " [" + player.getCharacterClass().getName() + "] | " +
                    "HP " + player.getHp() + "/" + player.getMaxHp() +
                    " | AC " + player.getAc() +
                    " | Pos " + player.getPosition() +
                    " | Status: " + player.statusSummary() +
                    " | Cooldowns: " + cooldownText);
        }
    }

    private void showResult() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Fim da partida!");

        if (playerA.isAlive() && !playerB.isAlive()) {
            System.out.println("Vencedor: " + playerA.getName());
        } else if (playerB.isAlive() && !playerA.isAlive()) {
            System.out.println("Vencedor: " + playerB.getName());
        } else {
            System.out.println("Empate por limite de turnos.");
        }

        System.out.println("Arena final: " + arena.render(playerA, playerB));
    }
}