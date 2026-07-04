package darkestduel.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import darkestduel.actions.Action;
import darkestduel.actions.MovementAction;
import darkestduel.exceptions.InvalidDamageMultiplierException;
import darkestduel.exceptions.InvalidMovementException;
import darkestduel.util.RollResult;

/**
 * Classe abstrata que representa uma classe de personagem jogável.
 *
 * Define atributos comuns como HP máximo, AC por turno, dados de dano,
 * limite de movimento, chance de crítico e multiplicador crítico.
 */
public abstract class CharacterClass {
    private static final Random RANDOM = new Random();
    protected final String name;
    protected final int maxHp;
    protected final int acPerTurn;
    protected final int diceAmount;
    protected final int diceSides;
    protected final int maxMovement;
    protected final int separationPenalty;
    protected final double criticalChance;
    protected final int criticalMultiplier;

    /**
     * Cria uma classe de personagem com seus atributos principais.
     *
     * @param name nome da classe
     * @param maxHp HP máximo
     * @param acPerTurn AC recebido por turno
     * @param diceAmount quantidade de dados usados no dano
     * @param diceSides quantidade de lados de cada dado
     * @param maxMovement limite máximo de movimento
     * @param separationPenalty bônus de AC dado ao oponente ao se separar em combate
     * @param criticalChance chance de causar dano crítico
     * @param criticalMultiplier multiplicador aplicado ao dano crítico
     */
    public CharacterClass(
            String name,
            int maxHp,
            int acPerTurn,
            int diceAmount,
            int diceSides,
            int maxMovement,
            int separationPenalty,
            double criticalChance,
            int criticalMultiplier
    ) {
        this.name = name;
        this.maxHp = maxHp;
        this.acPerTurn = acPerTurn;
        this.diceAmount = diceAmount;
        this.diceSides = diceSides;
        this.maxMovement = maxMovement;
        this.separationPenalty = separationPenalty;
        this.criticalChance = criticalChance;
        this.criticalMultiplier = criticalMultiplier;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAcPerTurn() {
        return acPerTurn;
    }

    public int getDiceAmount() {
        return diceAmount;
    }

    public int getDiceSides() {
        return diceSides;
    }

    public int getMaxMovement() {
        return maxMovement;
    }

    public int getSeparationPenalty() {
        return separationPenalty;
    }

    /**
 * Realiza a rolagem de dano da classe.
 *
 * @param multiplier multiplicador de dano da ação
 * @return resultado completo da rolagem de dano
 * @throws darkestduel.exceptions.InvalidDamageMultiplierException se o multiplicador for menor que 1
 */
    public RollResult rollDamage(int multiplier) {
        if (multiplier < 1) {
            throw new InvalidDamageMultiplierException("O multiplicador de dano precisa ser >= 1.");
        }

        List<Integer> rolls = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < diceAmount; i++) {
            int roll = RANDOM.nextInt(diceSides) + 1;
            rolls.add(roll);
            sum += roll;
        }

        int baseDamage = sum * multiplier;
        boolean isCritical = RANDOM.nextDouble() < criticalChance;
        int totalDamage = isCritical ? baseDamage * criticalMultiplier : baseDamage;

        return new RollResult(
                diceAmount,
                diceSides,
                rolls,
                baseDamage,
                isCritical,
                criticalMultiplier,
                totalDamage
        );
    }

    /**
 * Calcula o custo em AC para uma determinada distância de movimento.
 *
 * @param distance distância a ser percorrida
 * @return custo em AC
 * @throws darkestduel.exceptions.InvalidMovementException se a distância for menor que 1
 */
    public int movementCost(int distance) {
        if (distance < 1) {
            throw new InvalidMovementException("A distância de movimento precisa ser positiva.");
        }
        return (distance + 1) / 2;
    }

    /**
 * Gera automaticamente as ações de aproximação e afastamento da classe.
 *
 * @return lista de ações de movimento disponíveis
 */
    public List<Action> getMovementActions() {
        List<Action> actions = new ArrayList<>();

        for (int distance = 1; distance <= maxMovement; distance++) {
            int cost = movementCost(distance);

            actions.add(new MovementAction(
                    "Aproximar " + distance,
                    "Move " + distance + " casa(s) em direção ao adversário.",
                    cost,
                    distance
            ));

            actions.add(new MovementAction(
                    "Afastar " + distance,
                    "Move " + distance + " casa(s) para longe do adversário.",
                    cost,
                    -distance
            ));
        }

        return actions;
    }

    /**
 * Retorna todas as ações disponíveis para a classe.
 *
 * @return lista de ações da classe
 */
    public abstract List<Action> getActions();
}

