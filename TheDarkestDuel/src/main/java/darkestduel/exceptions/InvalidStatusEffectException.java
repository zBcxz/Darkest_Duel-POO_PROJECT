package darkestduel.exceptions;

/**
 * Exceção lançada quando um efeito de status é criado com valores inválidos.
 */
public class InvalidStatusEffectException extends GameException {
    public InvalidStatusEffectException(String message) {
        super(message);
    }
}