package darkestduel.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import darkestduel.exceptions.InvalidDamageMultiplierException;
import darkestduel.exceptions.InvalidMovementException;
import darkestduel.util.RollResult;

class CharacterClassTest {

    @Test
    void deveCalcularCustoDeMovimento() {
        Warrior warrior = new Warrior();

        assertEquals(1, warrior.movementCost(1));
        assertEquals(1, warrior.movementCost(2));
        assertEquals(2, warrior.movementCost(3));
    }

    @Test
    void deveLancarErroParaMovimentoInvalido() {
        Warrior warrior = new Warrior();

        assertThrows(InvalidMovementException.class, () -> {
            warrior.movementCost(0);
        });
    }

    @Test
    void deveLancarErroParaMultiplicadorDeDanoInvalido() {
        Warrior warrior = new Warrior();

        assertThrows(InvalidDamageMultiplierException.class, () -> {
            warrior.rollDamage(0);
        });
    }

    @Test
    void deveRolarDanoComValorPositivo() {
        Warrior warrior = new Warrior();

        RollResult result = warrior.rollDamage(1);

        assertTrue(result.getTotalDamage() > 0);
    }
}