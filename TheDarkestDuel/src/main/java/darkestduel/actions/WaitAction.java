package darkestduel.actions;

import java.util.ArrayList;
import java.util.List;

import darkestduel.game.Arena;
import darkestduel.model.Player;

/**
 * Representa a ação Esperar.
 *
 * Encerra o turno sem gastar AC, permitindo que o jogador
 * preserve os pontos de ação acumulados.
 */
public class WaitAction extends Action {
    public WaitAction() {
        super(
                "Esperar",
                "Não faz nada neste turno. Os ACs acumulados permanecem.",
                0,
                0,
                true
        );
    }

    @Override
    public List<String> execute(Player actor, Player target, Arena arena) {
        List<String> messages = new ArrayList<>();
        messages.add(actor.getName() + " esperou e preservou seus ACs.");
        return messages;
    }
}