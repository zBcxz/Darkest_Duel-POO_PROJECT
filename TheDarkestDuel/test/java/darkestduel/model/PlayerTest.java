package darkestduel.model;

import darkestduel.classes.Warrior;
import darkestduel.exceptions.InsufficientAcException;
import darkestduel.exceptions.InvalidAcException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void deveCriarJogadorComHpMaximoEZeroAc() {
        Player player = new Player("Jogador A", "A", new Warrior(), 3);

        assertEquals(36, player.getHp());
        assertEquals(0, player.getAc());
        assertTrue(player.isAlive());
    }

    @Test
    void deveAdicionarEGastarAc() {
        Player player = new Player("Jogador A", "A", new Warrior(), 3);

        player.addAc(4);
        player.spendAc(2);

        assertEquals(2, player.getAc());
    }

    @Test
    void deveLancarErroAoAdicionarAcNegativo() {
        Player player = new Player("Jogador A", "A", new Warrior(), 3);

        assertThrows(InvalidAcException.class, () -> {
            player.addAc(-1);
        });
    }

    @Test
    void deveLancarErroAoGastarAcInsuficiente() {
        Player player = new Player("Jogador A", "A", new Warrior(), 3);

        assertThrows(InsufficientAcException.class, () -> {
            player.spendAc(1);
        });
    }
}