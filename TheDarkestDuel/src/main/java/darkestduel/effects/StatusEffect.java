package darkestduel.effects;

import java.util.ArrayList;
import java.util.List;

import darkestduel.exceptions.InvalidStatusEffectException;
import darkestduel.game.Arena;
import darkestduel.model.Player;
import darkestduel.util.DamageModification;

/**
 * Classe abstrata que representa um efeito de status aplicado a um jogador.
 *
 * Efeitos de status podem agir no início do turno, modificar dano recebido,
 * reagir a ataques e expirar após determinada duração.
 */
public abstract class StatusEffect {
        protected final String name;
                protected int duration;

                /**
 * Cria um efeito de status.
 *
 * @param name nome do efeito
 * @param duration duração do efeito em turnos
 * @throws darkestduel.exceptions.InvalidStatusEffectException se a duração for menor ou igual a zero
 */
        public StatusEffect(String name, int duration) {
            if (duration <= 0) {
                throw new InvalidStatusEffectException("A duração do efeito precisa ser positiva.");
            }
            this.name = name;
            this.duration = duration;
        }

        public String getName() {
            return name;
        }

        public int getDuration() {
            return duration;
        }

        /**
 * Executa a lógica do efeito no início do turno do jogador.
 *
 * @param owner jogador que possui o efeito
 * @param opponent jogador adversário
 * @param arena arena da partida
 * @return mensagens geradas pelo efeito
 */
        public List<String> onTurnStart(Player owner, Player opponent, Arena arena) {
            return new ArrayList<>();
        }

        /**
 * Permite que o efeito modifique o dano recebido pelo jogador.
 *
 * @param owner jogador que possui o efeito
 * @param attacker jogador atacante
 * @param damage dano antes da modificação
 * @return resultado da modificação do dano
 */
        public DamageModification modifyIncomingDamage(Player owner, Player attacker, int damage) {
            return new DamageModification(damage, new ArrayList<>());
        }

        public List<String> onOwnerAttacked(Player owner, Player attacker, int finalDamage) {
            return new ArrayList<>();
        }

        /**
 * Executa a lógica do efeito no fim do turno do jogador.
 *
 * @param owner jogador que possui o efeito
 * @return mensagens geradas pelo efeito
 */
        public List<String> onTurnEnd(Player owner) {
            duration -= 1;
            return new ArrayList<>();
        }

        public boolean isExpired() {
            return duration <= 0;
        }
    }