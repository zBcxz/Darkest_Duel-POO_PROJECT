package darkestduel.util;

import java.util.List;

public class DamageReport {
        private int damage;
        private List<String> messages;

        public DamageReport(int damage, List<String> messages) {
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