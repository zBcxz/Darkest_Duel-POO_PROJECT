package darkestduel.classes;

import darkestduel.actions.Action;
import darkestduel.actions.MovementAction;
import darkestduel.exceptions.InvalidDamageMultiplierException;
import darkestduel.exceptions.InvalidMovementException;
import darkestduel.util.RollResult;
import java.util.*;

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

    public int movementCost(int distance) {
        if (distance < 1) {
            throw new InvalidMovementException("A distância de movimento precisa ser positiva.");
        }
        return (distance + 1) / 2;
    }

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

    public abstract List<Action> getActions();
}

