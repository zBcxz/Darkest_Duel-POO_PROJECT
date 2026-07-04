package darkestduel.actions;

import java.util.ArrayList;
import java.util.List;

import darkestduel.game.Arena;
import darkestduel.model.Player;

/**
 * Representa a ação Bomba Incendiária.
 *
 * Cria zonas de fogo ao redor do alvo, causando dano
 * aos jogadores que estiverem nessas posições.
 */
public class FireBombAction extends Action {
    private final int maxRange;
    private final int minRange;
    private final int radius;
    private final int duration;
    private final int fireDamage;

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