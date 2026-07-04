package darkestduel.classes;

import java.util.List;

import darkestduel.actions.Action;
import darkestduel.actions.AttackAction;
import darkestduel.actions.DefenseAction;
import darkestduel.actions.WaitAction;

/**
 * Representa a classe Spear Master.
 *
 * Classe equilibrada que utiliza ataques de lança
 * com alcance curto e médio, além de ações defensivas.
 */
public class SpearMaster extends CharacterClass {
        public SpearMaster() {
            super("Spear Master", 34, 2, 1, 8, 3, 1, 0.10, 2);
        }

        /**
         * Retorna as ações específicas disponíveis para esta classe.
         *
         * @return lista de ações da classe
         */
        @Override
        public List<Action> getActions() {
            List<Action> actions = getMovementActions();
            actions.add(new AttackAction("Estocada", "Ataque com lança.", 2, 1, 2));
            actions.add(new AttackAction("Varredura", "Ataque forte de alcance médio.", 3, 1, 2, 2, 4));
            actions.add(new DefenseAction());
            actions.add(new WaitAction());
            return actions;
        }
    }