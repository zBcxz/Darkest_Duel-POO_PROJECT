package darkestduel.util;

import java.util.List;

/**
 * Representa uma modificação aplicada ao dano recebido.
 *
 * Contém o novo valor de dano e as mensagens geradas
 * durante a modificação.
 */
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