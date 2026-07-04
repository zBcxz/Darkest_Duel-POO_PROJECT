package darkestduel.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import darkestduel.classes.Warrior;
import darkestduel.exceptions.InvalidArenaException;
import darkestduel.model.Player;
import darkestduel.util.MovementPreview;

class ArenaTest {

    @Test
    void deveLancarErroQuandoArenaForPequena() {
        assertThrows(InvalidArenaException.class, () -> {
            new Arena(3);
        });
    }

    @Test
    void deveCalcularDistanciaEntreJogadores() {
        Arena arena = new Arena(12);

        Player playerA = new Player("Jogador A", "A", new Warrior(), 2);
        Player playerB = new Player("Jogador B", "B", new Warrior(), 8);

        assertEquals(6, arena.distanceBetween(playerA, playerB));
    }

    @Test
    void naoDevePermitirMovimentoParaForaDaArena() {
        Arena arena = new Arena(12);

        Player playerA = new Player("Jogador A", "A", new Warrior(), 0);
        Player playerB = new Player("Jogador B", "B", new Warrior(), 5);

        MovementPreview preview = arena.previewMovement(playerA, playerB, -1);

        assertFalse(preview.isValid());
    }
}