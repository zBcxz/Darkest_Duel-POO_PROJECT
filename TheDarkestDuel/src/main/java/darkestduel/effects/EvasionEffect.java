package darkestduel.effects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import darkestduel.model.Player;
import darkestduel.util.DamageModification;

/**
 * Efeito de evasão que possui chance de anular completamente
 * o próximo dano recebido.
 */
public class EvasionEffect extends StatusEffect {
        private static final Random RANDOM = new Random();
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