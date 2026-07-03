package darkestduel.actions;

import darkestduel.model.Player;


import java.util.List;
import java.util.ArrayList;

public class EvasionAction extends Action {
    private final double chance;

    public EvasionAction() {
        this(0.50);
    }

    public EvasionAction(double chance) {
        super(
                "Evadir",
                "Chance de negar completamente o próximo ataque. Expira em 3 turnos.",
                1
        );
        this.chance = chance;
    }

    @Override
    public List<String> execute(Player actor, Player target, Arena arena) {
        List<String> messages = new ArrayList<>();
        if (!canExecute(actor, target, arena)) {
            messages.add(unavailableReason(actor));
            return messages;
        }

        payAndApplyCooldown(actor);
        actor.addStatusEffect(new EvasionEffect(3, chance));
        messages.add(actor.getName() + " preparou uma evasão.");
        return messages;
    }
}