package darkestduel.exceptions;

/**
 * Exceção lançada quando o jogador tenta gastar mais AC do que possui.
 */
public class InsufficientAcException extends GameException {
    public InsufficientAcException(String message) {
        super(message);
    }
}