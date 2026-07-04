package darkestduel.exceptions;

/**
 * Exceção lançada quando uma regra de movimento recebe valor inválido.
 */
public class InvalidMovementException extends GameException {
    public InvalidMovementException(String message) {
        super(message);
    }
}