package darkestduel.actions;

import darkestduel.effects.RegenerationEffect;
import darkestduel.game.Arena;
import darkestduel.model.Player;

import java.util.ArrayList;
import java.util.List;

public class HealAction extends Action {
    private int duration;
    private int healPerTurn;

    public HealAction(String name, String description, int acCost, int duration, int healPerTurn, int cooldown) {
        super(name, description, acCost, cooldown, false);
        this.duration = duration;
        this.healPerTurn = healPerTurn;
    }

    @Override
    public List<String> execute(Player actor, Player target, Arena arena) {
        List<String> messages = new ArrayList<>();
        if (!canExecute(actor, target, arena)) {
            messages.add(unavailableReason(actor));
            return messages;
        }

        payAndApplyCooldown(actor);
        actor.addStatusEffect(new RegenerationEffect(duration, healPerTurn));
        messages.add(actor.getName() + " iniciou Regeneração.");
        return messages;
    }
}