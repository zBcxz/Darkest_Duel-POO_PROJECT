package darkestduel.actions;

import java.util.ArrayList;
import java.util.List;

import darkestduel.game.Arena;
import darkestduel.model.Player;
import darkestduel.util.DamageReport;
import darkestduel.util.RollResult;

/**
 * Representa uma ação de ataque.
 *
 * O ataque possui alcance mínimo, alcance máximo e multiplicador de dano.
 */
public class AttackAction extends Action {
    private final int minRange;
    private final int maxRange;
    private final int damageMultiplier;

    public AttackAction(String name, String description, int acCost, int minRange, int maxRange) {
        this(name, description, acCost, minRange, maxRange, 1, 0);
    }

    public AttackAction(
            String name,
            String description,
            int acCost,
            int minRange,
            int maxRange,
            int damageMultiplier,
            int cooldown
    ) {
        super(name, description, acCost, cooldown, false);
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.damageMultiplier = damageMultiplier;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    /**
 * Verifica se o alvo está dentro do alcance da ação.
 *
 * @param actor jogador atacante
 * @param target jogador alvo
 * @param arena arena da partida
 * @return true se o alvo estiver no alcance; false caso contrário
 */
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
            messages.add(target.getName() + " está fora do alcance de " + name + ".");
            return messages;
        }

        if (!super.canExecute(actor, target, arena)) {
            messages.add(unavailableReason(actor));
            return messages;
        }

        payAndApplyCooldown(actor);

        RollResult rollResult = actor.getCharacterClass().rollDamage(damageMultiplier);
        DamageReport report = target.receiveDamage(rollResult.getTotalDamage(), actor);

        messages.add(actor.getName() + " usou " + name + ".");
        messages.add("Rolagem: " + rollResult.describe() + ".");
        messages.add(target.getName() + " sofreu " + report.getDamage() + " dano.");
        messages.addAll(report.getMessages());

        return messages;
    }
}