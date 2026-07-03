package darkestduel.game;

import darkestduel.model.Player;

public class TurnData {
        private Player actor;
        private Player opponent;
        private HumanController controller;

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