package darkestduel.exceptions;

/**
 * Exceção base para erros específicos do jogo.
 *
 * Todas as exceções personalizadas do projeto herdam desta classe.
 */
public class GameException extends RuntimeException {
    public GameException(String message) {
        super(message);
    }
}