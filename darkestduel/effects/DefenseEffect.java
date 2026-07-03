package darkestduel.effects;

import java.util.List;
import java.util.ArrayList;

public class DefenseEffect extends StatusEffect {
        private final double reduction;

        public DefenseEffect(int duration, double reduction) {
            super("Defesa", duration);
            this.reduction = reduction;
        }

        @Override
        public DamageModification modifyIncomingDamage(Player owner, Player attacker, int damage) {
            int reducedDamage = (int) (damage * (1 - reduction));
            duration = 0;

            List<String> messages = new ArrayList<>();
            messages.add(owner.getName() + " bloqueou " + (int) (reduction * 100) + "% do dano com Defesa.");
            return new DamageModification(reducedDamage, messages);
        }
    }