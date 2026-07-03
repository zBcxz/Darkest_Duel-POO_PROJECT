package darkestduel.effects;

import java.util.List;
import java.util.ArrayList;

public class CounterAttackEffect extends StatusEffect {
        private  int acBonus;

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