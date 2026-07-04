package darkestduel.exceptions;

/**
 * Exceção lançada quando a arena é criada com configuração inválida.
 */
public class InvalidArenaException extends GameException {
    public InvalidArenaException(String message) {
        super(message);
    }
}