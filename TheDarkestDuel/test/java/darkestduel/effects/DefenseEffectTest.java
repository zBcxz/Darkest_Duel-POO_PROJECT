package darkestduel.effects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import darkestduel.classes.Warrior;
import darkestduel.model.Player;
import darkestduel.util.DamageModification;

class DefenseEffectTest {

    @Test
    void deveReduzirDanoRecebidoPelaMetade() {
        Player defender = new Player("Defensor", "A", new Warrior(), 2);
        Player attacker = new Player("Atacante", "B", new Warrior(), 3);

        DefenseEffect effect = new DefenseEffect(3, 0.50);

        DamageModification modification = effect.modifyIncomingDamage(defender, attacker, 10);

        assertEquals(5, modification.getDamage());
        assertFalse(modification.getMessages().isEmpty());
    }

    @Test
    void deveExpirarDepoisDeSerUsado() {
        Player defender = new Player("Defensor", "A", new Warrior(), 2);
        Player attacker = new Player("Atacante", "B", new Warrior(), 3);

        DefenseEffect effect = new DefenseEffect(3, 0.50);

        effect.modifyIncomingDamage(defender, attacker, 10);

        assertTrue(effect.isExpired());
    }
}