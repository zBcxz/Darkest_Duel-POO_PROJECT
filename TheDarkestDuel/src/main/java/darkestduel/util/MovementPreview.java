package darkestduel.util;


public class MovementPreview {
        private final boolean valid;
        private final int newPosition;
        private final String message;

        public MovementPreview(boolean valid, int newPosition, String message) {
            this.valid = valid;
            this.newPosition = newPosition;
            this.message = message;
        }

        public boolean isValid() {
            return valid;
        }

        public int getNewPosition() {
            return newPosition;
        }

        public String getMessage() {
            return message;
        }
    }