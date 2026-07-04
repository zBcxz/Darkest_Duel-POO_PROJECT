package darkestduel.util;

import java.util.List;


public class DamageModification {
        private final int damage;
        private final List<String> messages;

        public DamageModification(int damage, List<String> messages) {
            this.damage = damage;
            this.messages = messages;
        }

        public int getDamage() {
            return damage;
        }

        public List<String> getMessages() {
            return messages;
        }
    }