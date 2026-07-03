package darkestduel.util;

import java.util.List;

public class RollResult {
        private final int diceAmount;
        private final int diceSides;
        private final List<Integer> rolls;
        private final int baseDamage;
        private final boolean critical;
        private final int criticalMultiplier;
        private final int totalDamage;

    public RollResult(
                int diceAmount,
                int diceSides,
                List<Integer> rolls,
                int baseDamage,
                boolean critical,
                int criticalMultiplier,
                int totalDamage
        ) {
            this.diceAmount = diceAmount;
            this.diceSides = diceSides;
            this.rolls = rolls;
            this.baseDamage = baseDamage;
            this.critical = critical;
            this.criticalMultiplier = criticalMultiplier;
            this.totalDamage = totalDamage;
        }

        public int getTotalDamage() {
            return totalDamage;
        }

        public String describe() {
            StringBuilder rollsText = new StringBuilder();
            for (int i = 0; i < rolls.size(); i++) {
                if (i > 0) {
                    rollsText.append("+");
                }
                rollsText.append(rolls.get(i));
            }

            String dice = diceAmount + "d" + diceSides;
            String criticalText = critical ? " CRÍTICO!" : "";
            return dice + " [" + rollsText + "] = " + baseDamage + criticalText;
        }
    }