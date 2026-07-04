package darkestduel.effects;

import darkestduel.exceptions.InvalidStatusEffectException;
import darkestduel.game.Arena;
import darkestduel.model.Player;
import darkestduel.util.DamageModification;
import java.util.ArrayList;
import java.util.List;

public abstract class StatusEffect {
        protected final String name;
                protected int duration;

        public StatusEffect(String name, int duration) {
            if (duration <= 0) {
                throw new InvalidStatusEffectException("A duração do efeito precisa ser positiva.");
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