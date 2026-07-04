package darkestduel.effects;

import darkestduel.model.Player;
import java.util.ArrayList;
import java.util.List;

public class CounterAttackEffect extends StatusEffect {
        private final int acBonus;

        public CounterAttackEffect(int duration, int acBonus) {
            super("Contra-ataque", duration);
            this.acBonus = acBonus;
        }

        @Override
        public List<String> onOwnerAttacked(Player owner, Player attacker, int finalDamage) {
            List<String> messages = new ArrayList<>();
            if (attacker == null) {
                return messages;
            }

            owner.addAc(acBonus);
            duration = 0;
            messages.add(owner.getName() + " ativou Contra-ataque e ganhou " + acBonus + " AC.");
            return messages;
        }
    }