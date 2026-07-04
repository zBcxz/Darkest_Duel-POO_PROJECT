package darkestduel.actions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import darkestduel.classes.Warrior;
import darkestduel.game.Arena;
import darkestduel.model.Player;

class AttackActionTest {

    @Test
    void deveCausarDanoQuandoAlvoEstaNoAlcance() {
        Arena arena = new Arena(12);

        Player attacker = new Player("Atacante", "A", new Warrior(), 2);
        Player target = new Player("Alvo", "B", new Warrior(), 3);

        attacker.addAc(2);

        AttackAction action = new AttackAction(
                "Golpe de Espada",
                "Ataque corpo a corpo.",
                2,
                1,
                1
        );

        List<String> messages = action.execute(attacker, target, arena);

        assertTrue(target.getHp() < target.getMaxHp());
        assertEquals(0, attacker.getAc());
        assertFalse(messages.isEmpty());
    }

    @Test
    void naoDeveAtacarQuandoAlvoEstaForaDoAlcance() {
        Arena arena = new Arena(12);

        Player attacker = new Player("Atacante", "A", new Warrior(), 2);
        Player target = new Player("Alvo", "B", new Warrior(), 8);

        attacker.addAc(2);

        AttackAction action = new AttackAction(
                "Golpe de Espada",
                "Ataque corpo a corpo.",
                2,
                1,
                1
        );

        List<String> messages = action.execute(attacker, target, arena);

        assertEquals(target.getMaxHp(), target.getHp());
        assertTrue(messages.get(0).contains("fora do alcance"));
    }
}