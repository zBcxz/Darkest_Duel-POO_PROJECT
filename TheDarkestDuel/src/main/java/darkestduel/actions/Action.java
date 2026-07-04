package darkestduel.actions;

import java.util.List;

import darkestduel.exceptions.InvalidActionException;
import darkestduel.game.Arena;
import darkestduel.model.Player;

/**
 * Classe abstrata que representa uma ação executável por um jogador.
 *
 * Toda ação possui nome, descrição, custo em AC, cooldown
 * e pode ou não encerrar o turno.
 */
public abstract class Action {
    protected final String name;
    protected final String description;
    protected final int acCost;
    protected final int cooldown;
    protected final boolean endsTurn;

    /**
 * Cria uma ação sem cooldown e que não encerra o turno.
 *
 * @param name nome da ação
 * @param description descrição da ação
 * @param acCost custo da ação em AC
 */
    public Action(String name, String description, int acCost) {
        this(name, description, acCost, 0, false);
    }

    /**
 * Cria uma ação com custo, cooldown e comportamento de encerramento de turno.
 *
 * @param name nome da ação
 * @param description descrição da ação
 * @param acCost custo da ação em AC
 * @param cooldown cooldown da ação medido em AC consumido
 * @param endsTurn indica se a ação encerra o turno
 * @throws darkestduel.exceptions.InvalidActionException se o custo de AC for negativo
 */
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

/**
 * Verifica se a ação pode ser executada pelo jogador.
 *
 * @param actor jogador que executará a ação
 * @param target alvo da ação
 * @param arena arena onde a ação será executada
 * @return true se a ação puder ser executada; false caso contrário
 */
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

    /**
 * Executa a ação.
 *
 * @param actor jogador que executa a ação
 * @param target alvo da ação
 * @param arena arena onde a ação ocorre
 * @return lista de mensagens descrevendo o resultado da ação
 */
    public abstract List<String> execute(Player actor, Player target, Arena arena);
}