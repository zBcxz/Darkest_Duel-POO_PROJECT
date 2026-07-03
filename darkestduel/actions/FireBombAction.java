package darkestduel.actions;

import darkestduel.game.Arena;
import darkestduel.model.Player;

import java.util.ArrayList;
import java.util.List;

public class FireBombAction extends Action {
    private int minRange;
    private int maxRange;
    private int radius;
    private int duration;
    private int fireDamage;

    public FireBombAction() {
        super(
                "Bomba Incendiária",
                "Incendeia 3 casas adjacentes ao redor do adversário.",
                3,
                5,
                false
        );
        this.minRange = 2;
        this.maxRange = 6;
        this.radius = 1;
        this.duration = 3;
        this.fireDamage = 2;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public boolean isTargetInRange(Player actor, Player target, Arena arena) {
        int distance = arena.distanceBetween(actor, target);
        return distance >= minRange && distance <= maxRange;
    }

    @Override
    public boolean canExecute(Player actor, Player target, Arena arena) {
        return super.canExecute(actor, target, arena) && isTargetInRange(actor, target, arena);
    }

    @Override
    public List<String> execute(Player actor, Player target, Arena arena) {
        List<String> messages = new ArrayList<>();

        if (!isTargetInRange(actor, target, arena)) {
            messages.add(target.getName() + " está fora do alcance da Bomba Incendiária.");
            return messages;
        }

        if (!super.canExecute(actor, target, arena)) {
            messages.add(unavailableReason(actor));
            return messages;
        }

        payAndApplyCooldown(actor);

        messages.add(actor.getName() + " arremessou uma Bomba Incendiária.");
        messages.addAll(arena.addFireZone(
                target.getPosition(),
                radius,
                duration,
                fireDamage
        ));
        return messages;
    }
}