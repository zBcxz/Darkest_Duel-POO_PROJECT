package darkestduel.exceptions;

/**
 * Exceção lançada quando o multiplicador de dano é inválido.
 */
public class InvalidDamageMultiplierException extends GameException {
    public InvalidDamageMultiplierException(String message) {
        super(message);
    }
}