package darkestduel.classes;

import darkestduel.actions.Action;
import darkestduel.actions.AttackAction;
import darkestduel.actions.CounterAttackAction;
import darkestduel.actions.EvasionAction;
import darkestduel.actions.WaitAction;
import java.util.List;

public class Assassin extends CharacterClass {
        public Assassin() {
            super("Assassin", 16, 2, 1, 6, 4, 0, 0.10, 2);
        }

        @Override
        public List<Action> getActions() {
            List<Action> actions = getMovementActions();
            actions.add(new AttackAction("Adaga", "Ataque rápido corpo a corpo.", 1, 1, 1));
            actions.add(new AttackAction("Ataque Sombrio", "Ataque poderoso com cooldown.", 3, 1, 1, 2, 4));
            actions.add(new EvasionAction(0.60));
            actions.add(new CounterAttackAction(1));
            actions.add(new WaitAction());
            return actions;
        }
    }