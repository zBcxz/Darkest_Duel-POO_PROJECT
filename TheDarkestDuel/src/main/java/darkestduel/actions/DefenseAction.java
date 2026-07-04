package darkestduel.actions;

import darkestduel.effects.DefenseEffect;
import darkestduel.game.Arena;
import darkestduel.model.Player;

import java.util.ArrayList;
import java.util.List;
public class DefenseAction extends Action {
    public DefenseAction() {
        super(
                "Defender",
                "Reduz em 50% o próximo dano recebido. Expira em 3 turnos.",
                1
        );
    }

    @Override
    public List<String> execute(Player actor, Player target, Arena arena) {
        List<String> messages = new ArrayList<>();
        if (!canExecute(actor, target, arena)) {
            messages.add(unavailableReason(actor));
            return messages;
        }

        payAndApplyCooldown(actor);
        actor.addStatusEffect(new DefenseEffect(3, 0.50));
        messages.add(actor.getName() + " assumiu postura defensiva.");
        return messages;
    }
}