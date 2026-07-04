package darkestduel.exceptions;

/**
 * Exceção lançada quando uma ação é criada com configuração inválida.
 */
public class InvalidActionException extends GameException {
    public InvalidActionException(String message) {
        super(message);
    }
}