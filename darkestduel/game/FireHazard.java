package darkestduel.game;

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

        public void tick() {
            duration -= 1;
        }

        public boolean isExpired() {
            return duration <= 0;
        }
    }