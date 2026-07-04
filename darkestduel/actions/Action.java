package darkestduel.actions;

import darkestduel.exceptions.InvalidActionException;
import darkestduel.game.Arena;
import darkestduel.model.Player;
import java.util.List;

public abstract class Action {
    protected final String name;
    protected final String description;
    protected final int acCost;
    protected final int cooldown;
    protected final boolean endsTurn;

    public Action(String name, String description, int acCost) {
        this(name, description, acCost, 0, false);
    }

    public Action(String name, String description, int acCost, int cooldown, boolean endsTurn) {
        if (acCost < 0) {
            throw new InvalidActionException("O custo de AC não pode ser negativo.");
        }
        this.name = name;
        this.description = description;
        this.acCost = acCost;
        this.cooldown = cooldown;
        this.endsTurn = endsTurn;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAcCost() {
        return acCost;
    }

    public boolean endsTurn() {
        return endsTurn;
    }

    public String getKey() {
        return name;
    }

    public boolean canExecute(Player actor, Player target, Arena arena) {
        return actor.getAc() >= acCost && actor.cooldownRemaining(getKey()) == 0;
    }

    public String unavailableReason(Player actor) {
        if (actor.getAc() < acCost) {
            return "AC insuficiente: precisa de " + acCost + ", possui " + actor.getAc() + ".";
        }

        int remainingCooldown = actor.cooldownRemaining(getKey());
        if (remainingCooldown > 0) {
            return "Cooldown restante: consumir mais " + remainingCooldown + " AC.";
        }

        return "Ação disponível.";
    }

    protected void payAndApplyCooldown(Player actor) {
        actor.spendAc(acCost);
        actor.setCooldown(getKey(), cooldown);
    }

    public abstract List<String> execute(Player actor, Player target, Arena arena);
}