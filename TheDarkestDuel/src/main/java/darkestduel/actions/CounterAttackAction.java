package darkestduel.actions;

import java.util.ArrayList;
import java.util.List;

import darkestduel.effects.CounterAttackEffect;
import darkestduel.game.Arena;
import darkestduel.model.Player;

/**
 * Representa a ação Contra-ataque.
 *
 * Aplica um efeito que concede AC ao jogador caso ele seja atacado.
 */
public class CounterAttackAction extends Action {
    private  int acBonus;

    public CounterAttackAction(int acBonus) {
        super(
                "Contra-ataque",
                "Ganha AC caso seja atacado nos próximos 3 turnos.",
                1
        );
        this.acBonus = acBonus;
    }

    @Override
    public List<String> execute(Player actor, Player target, Arena arena) {
        List<String> messages = new ArrayList<>();
        if (!canExecute(actor, target, arena)) {
            messages.add(unavailableReason(actor));
            return messages;
        }

        payAndApplyCooldown(actor);
        actor.addStatusEffect(new CounterAttackEffect(3, acBonus));
        messages.add(actor.getName() + " preparou um contra-ataque.");
        return messages;
    }
}