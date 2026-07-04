package darkestduel.util;

import java.util.List;

/**
 * Representa o relatório final de dano recebido por um jogador.
 *
 * Contém o dano efetivamente aplicado e as mensagens geradas
 * durante o processamento do dano.
 */
public class DamageReport {
        private final int damage;
        private final List<String> messages;

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