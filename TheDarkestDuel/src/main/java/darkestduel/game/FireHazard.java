package darkestduel.game;
/**
 * Representa uma zona de fogo na arena.
 *
 * O fogo possui duração limitada e causa dano ao jogador
 * que estiver na posição afetada.
 */
public class FireHazard {
        private int duration;
        private int damage;

        public FireHazard(int duration, int damage) {
            this.duration = duration;
            this.damage = damage;
        }

        public int getDamage() {
            return damage;
        }

        /**
 * Reduz a duração restante do fogo em um turno.
 */
        public void tick() {
            duration -= 1;
        }

        /**
 * Verifica se o fogo expirou.
 *
 * @return true se a duração for menor ou igual a zero; false caso contrário
 */
        public boolean isExpired() {
            return duration <= 0;
        }
    }