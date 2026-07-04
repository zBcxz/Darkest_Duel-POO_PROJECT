package darkestduel.effects;

import darkestduel.game.Arena;
import darkestduel.model.Player;
import java.util.ArrayList;
import java.util.List;

public class RegenerationEffect extends StatusEffect {
        private final int healPerTurn;

        public RegenerationEffect(int duration, int healPerTurn) {
            super("Regeneração", duration);
            this.healPerTurn = healPerTurn;
        }

        @Override
        public List<String> onTurnStart(Player owner, Player opponent, Arena arena) {
            int healed = owner.heal(healPerTurn);
            List<String> messages = new ArrayList<>();
            if (healed > 0) {
                messages.add(owner.getName() + " regenerou " + healed + " HP.");
            }
            return messages;
        }
    }