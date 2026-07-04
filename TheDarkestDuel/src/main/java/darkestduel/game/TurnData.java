package darkestduel.game;

import darkestduel.model.Player;

/**
 * Agrupa os dados do turno atual.
 *
 * Armazena o jogador ativo, o oponente e o controlador
 * responsável por escolher a ação do turno.
 */
public class TurnData {
        private Player actor;
        private Player opponent;
        private HumanController controller;
/**
 * Cria os dados de um turno.
 *
 * @param actor jogador ativo
 * @param opponent jogador adversário
 * @param controller controlador do jogador ativo
 */
        public TurnData(Player actor, Player opponent, HumanController controller) {
            this.actor = actor;
            this.opponent = opponent;
            this.controller = controller;
        }

        public Player getActor() {
            return actor;
        }

        public Player getOpponent() {
            return opponent;
        }

        public HumanController getController() {
            return controller;
        }
    }