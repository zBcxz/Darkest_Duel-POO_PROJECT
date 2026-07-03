package darkestduel.effects;

import java.util.List;
import java.util.ArrayList;

public class EvasionEffect extends StatusEffect {
        private final double chance;

        public EvasionEffect(int duration, double chance) {
            super("Evasão", duration);
            this.chance = chance;
        }

        @Override
        public DamageModification modifyIncomingDamage(Player owner, Player attacker, int damage) {
            duration = 0;

            List<String> messages = new ArrayList<>();
            if (RANDOM.nextDouble() < chance) {
                messages.add(owner.getName() + " evadiu completamente o ataque!");
                return new DamageModification(0, messages);
            }

            messages.add(owner.getName() + " tentou evadir, mas falhou.");
            return new DamageModification(damage, messages);
        }
    }