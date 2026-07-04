package darkestduel.classes;

import java.util.List;

import darkestduel.actions.Action;
import darkestduel.actions.AttackAction;
import darkestduel.actions.EvasionAction;
import darkestduel.actions.FireBombAction;
import darkestduel.actions.WaitAction;

/**
 * Representa a classe Archer.
 *
 * Classe de longo alcance, com alta mobilidade,
 * ataques à distância, evasão e bomba incendiária.
 */
public class Archer extends CharacterClass {
        public Archer() {
            super("Archer", 44, 2, 1, 10, 6, 2, 0.20, 2);
        }
        /**
        * Retorna as ações específicas disponíveis para esta classe.
        *
        * @return lista de ações da classe
        */
        @Override
        public List<Action> getActions() {
            List<Action> actions = getMovementActions();
            actions.add(new AttackAction("Tiro Curto", "Disparo de curta/média distância.", 1, 2, 4));
            actions.add(new AttackAction("Tiro Preciso", "Disparo forte de longa distância.", 2, 3, 8, 2, 3));
            actions.add(new FireBombAction());
            actions.add(new EvasionAction());
            actions.add(new WaitAction());
            return actions;
        }
    }
