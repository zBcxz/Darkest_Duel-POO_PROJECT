package darkestduel.classes;

import java.util.List;

import darkestduel.actions.Action;
import darkestduel.actions.AttackAction;
import darkestduel.actions.CounterAttackAction;
import darkestduel.actions.DefenseAction;
import darkestduel.actions.HealAction;
import darkestduel.actions.WaitAction;

/**
 * Representa a classe Warrior.
 *
 * Classe resistente, focada em combate corpo a corpo,
 * defesa, contra-ataque e regeneração.
 */
public class Warrior extends CharacterClass {
        public Warrior() {
            super("Warrior", 36, 2, 1, 8, 2, 1, 0.10, 2);
        }
        /**
        * Retorna as ações específicas disponíveis para esta classe.
        *
        * @return lista de ações da classe
        */
        @Override
        public List<Action> getActions() {
            List<Action> actions = getMovementActions();
            actions.add(new AttackAction("Golpe de Espada", "Ataque corpo a corpo.", 2, 1, 1));
            actions.add(new AttackAction("Golpe Pesado", "Ataque forte com cooldown.", 3, 1, 1, 2, 4));
            actions.add(new DefenseAction());
            actions.add(new CounterAttackAction(2));
            actions.add(new HealAction("Segundo Fôlego", "Recupera HP por alguns turnos.", 2, 3, 2, 5));
            actions.add(new WaitAction());
            return actions;
        }
    }