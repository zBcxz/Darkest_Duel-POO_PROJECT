package darkestduel.effects;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import darkestduel.classes.Warrior;
import darkestduel.model.Player;

class CounterAttackEffectTest {

    @Test
    void deveAdicionarAcQuandoJogadorForAtacado() {
        Player defender = new Player("Defensor", "A", new Warrior(), 2);
        Player attacker = new Player("Atacante", "B", new Warrior(), 3);

        CounterAttackEffect effect = new CounterAttackEffect(3, 2);

        List<String> messages = effect.onOwnerAttacked(defender, attacker, 5);

        assertEquals(2, defender.getAc());
        assertFalse(messages.isEmpty());
    }

    @Test
    void deveExpirarDepoisDeAtivarContraAtaque() {
        Player defender = new Player("Defensor", "A", new Warrior(), 2);
        Player attacker = new Player("Atacante", "B", new Warrior(), 3);

        CounterAttackEffect effect = new CounterAttackEffect(3, 2);

        effect.onOwnerAttacked(defender, attacker, 5);

        assertTrue(effect.isExpired());
    }
}