package darkestduel.actions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import darkestduel.classes.Warrior;
import darkestduel.game.Arena;
import darkestduel.model.Player;

class MovementActionTest {

    @Test
    void deveMoverJogadorQuandoMovimentoForValido() {
        Arena arena = new Arena(12);

        Player playerA = new Player("Jogador A", "A", new Warrior(), 2);
        Player playerB = new Player("Jogador B", "B", new Warrior(), 8);

        playerA.addAc(2);

        MovementAction action = new MovementAction(
                "Aproximar 2",
                "Move 2 casas em direção ao adversário.",
                1,
                2
        );

        List<String> messages = action.execute(playerA, playerB, arena);

        assertEquals(4, playerA.getPosition());
        assertEquals(1, playerA.getAc());
        assertFalse(messages.isEmpty());
    }

    @Test
    void naoDeveMoverJogadorSemAcSuficiente() {
        Arena arena = new Arena(12);

        Player playerA = new Player("Jogador A", "A", new Warrior(), 2);
        Player playerB = new Player("Jogador B", "B", new Warrior(), 8);

        MovementAction action = new MovementAction(
                "Aproximar 2",
                "Move 2 casas em direção ao adversário.",
                1,
                2
        );

        List<String> messages = action.execute(playerA, playerB, arena);

        assertEquals(2, playerA.getPosition());
        assertTrue(messages.get(0).contains("AC insuficiente"));
    }
}