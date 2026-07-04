package darkestduel.exceptions;

/**
 * Exceção lançada quando uma operação com AC recebe valor inválido.
 */
public class InvalidAcException extends GameException {
    public InvalidAcException(String message) {
        super(message);
    }
}