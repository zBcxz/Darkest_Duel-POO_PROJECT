package darkestduel.actions;

import java.util.ArrayList;
import java.util.List;

import darkestduel.effects.EvasionEffect;
import darkestduel.game.Arena;
import darkestduel.model.Player;

/**
 * Representa a ação Evadir.
 *
 * Aplica um efeito de evasão que pode anular completamente
 * o próximo ataque recebido.
 */
public class EvasionAction extends Action {
    private double chance;

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