package darkestduel.actions;

import darkestduel.game.Arena;
import darkestduel.model.Player;

import java.util.ArrayList;
import java.util.List;

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