package darkestduel.effects;

import java.util.List;
import java.util.ArrayList;

public abstract class StatusEffect {
        protected final String name;
                protected int duration;

        public StatusEffect(String name, int duration) {
            if (duration <= 0) {
                throw new IllegalArgumentException("A duração do efeito precisa ser positiva.");
            }
            this.name = name;
            this.duration = duration;
        }

        public String getName() {
            return name;
        }

        public int getDuration() {
            return duration;
        }

        public List<String> onTurnStart(Player owner, Player opponent, Arena arena) {
            return new ArrayList<>();
        }

        public DamageModification modifyIncomingDamage(Player owner, Player attacker, int damage) {
            return new DamageModification(damage, new ArrayList<>());
        }

        public List<String> onOwnerAttacked(Player owner, Player attacker, int finalDamage) {
            return new ArrayList<>();
        }

        public List<String> onTurnEnd(Player owner) {
            duration -= 1;
            return new ArrayList<>();
        }

        public boolean isExpired() {
            return duration <= 0;
        }
    }