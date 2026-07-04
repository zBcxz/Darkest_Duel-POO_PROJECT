package darkestduel.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import darkestduel.exceptions.InvalidArenaException;
import darkestduel.model.Player;
import darkestduel.util.DamageReport;
import darkestduel.util.MovementPreview;

/**
 * Representa a arena da partida.
 *
 * A arena é linear, possui tamanho fixo, controla a posição dos jogadores
 * e gerencia perigos de ambiente, como casas em chamas.
 */
public class Arena {
        private int size;
        private Map<Integer, FireHazard> fireHazards = new HashMap<>();

        /**
 * Cria uma nova arena.
 *
 * @param size quantidade de casas da arena
 * @throws darkestduel.exceptions.InvalidArenaException se o tamanho for menor que 4
 */
        public Arena(int size) {
            if (size < 4) {
                throw new InvalidArenaException("A arena precisa ter pelo menos 4 casas.");
            }
            this.size = size;
        }

        /**
 * Calcula a distância entre dois jogadores.
 *
 * @param playerA primeiro jogador
 * @param playerB segundo jogador
 * @return distância absoluta entre as posições dos jogadores
 */
        public int distanceBetween(Player playerA, Player playerB) {
            return Math.abs(playerA.getPosition() - playerB.getPosition());
        }

        public int directionTowards(Player actor, Player target) {
            return target.getPosition() > actor.getPosition() ? 1 : -1;
        }

        /**
 * Simula um movimento antes de executá-lo.
 *
 * @param actor jogador que deseja se mover
 * @param opponent jogador adversário
 * @param relativeSteps quantidade relativa de passos
 * @return prévia indicando se o movimento é válido e a nova posição
 */
        public MovementPreview previewMovement(Player actor, Player opponent, int relativeSteps) {
            int direction = directionTowards(actor, opponent);
            int newPosition = actor.getPosition() + relativeSteps * direction;

            if (newPosition < 0 || newPosition >= size) {
                return new MovementPreview(false, actor.getPosition(), "Movimento inválido: fora da arena.");
            }

            if (newPosition == opponent.getPosition()) {
                return new MovementPreview(false, actor.getPosition(), "Movimento inválido: casa ocupada pelo adversário.");
            }

            return new MovementPreview(true, newPosition, "Movimento válido.");
        }

        /**
 * Move um jogador na arena, caso o movimento seja válido.
 *
 * @param actor jogador que será movido
 * @param opponent jogador adversário
 * @param relativeSteps quantidade relativa de passos
 * @return mensagens descrevendo o resultado do movimento
 */
        public List<String> movePlayer(Player actor, Player opponent, int relativeSteps) {
            MovementPreview preview = previewMovement(actor, opponent, relativeSteps);
            List<String> messages = new ArrayList<>();

            if (!preview.isValid()) {
                messages.add(preview.getMessage());
                return messages;
            }

            int oldDistance = distanceBetween(actor, opponent);
            actor.setPosition(preview.getNewPosition());
            int newDistance = distanceBetween(actor, opponent);

            messages.add(actor.getName() + " moveu-se para a posição " + actor.getPosition() + ".");

            if (oldDistance == 1 && newDistance > 1) {
                int bonus = actor.getCharacterClass().getSeparationPenalty();
                if (bonus > 0) {
                    opponent.addAc(bonus);
                    messages.add(actor.getName() + " separou-se em combate; " + opponent.getName() + " ganhou " + bonus + " AC.");
                }
            }

            return messages;
        }

        /**
 * Adiciona zonas de fogo na arena.
 *
 * @param center posição central da zona de fogo
 * @param radius raio da zona de fogo
 * @param duration duração do fogo em turnos
 * @param damage dano causado pelo fogo
 * @return mensagens descrevendo as posições afetadas
 */
        public List<String> addFireZone(int center, int radius, int duration, int damage) {
            List<Integer> affectedPositions = new ArrayList<>();
            for (int position = center - radius; position <= center + radius; position++) {
                if (position >= 0 && position < size) {
                    fireHazards.put(position, new FireHazard(duration, damage));
                    affectedPositions.add(position);
                }
            }

            List<String> messages = new ArrayList<>();
            messages.add("As casas " + affectedPositions + " estão em chamas por " + duration + " turnos.");
            return messages;
        }

        public List<String> triggerTileEffects(Player player) {
            FireHazard hazard = fireHazards.get(player.getPosition());
            if (hazard == null) {
                return new ArrayList<>();
            }

            DamageReport report = player.receiveDamage(hazard.getDamage(), null);
            List<String> messages = new ArrayList<>();
            messages.add(player.getName() + " sofreu " + report.getDamage() + " dano por estar em uma casa em chamas.");
            messages.addAll(report.getMessages());
            return messages;
        }

        public void tickHazards() {
            List<Integer> positions = new ArrayList<>(fireHazards.keySet());
            for (Integer position : positions) {
                FireHazard hazard = fireHazards.get(position);
                hazard.tick();
                if (hazard.isExpired()) {
                    fireHazards.remove(position);
                }
            }
        }

        /**
 * Renderiza a arena em formato textual.
 *
 * @param playerA primeiro jogador
 * @param playerB segundo jogador
 * @return representação textual da arena
 */
        public String render(Player playerA, Player playerB) {
            String[] cells = new String[size];
            for (int i = 0; i < size; i++) {
                cells[i] = "_";
            }

            for (Integer position : fireHazards.keySet()) {
                cells[position] = "*";
            }

            cells[playerA.getPosition()] = playerA.getSymbol();
            cells[playerB.getPosition()] = playerB.getSymbol();

            return String.join(" ", cells);
        }
    }