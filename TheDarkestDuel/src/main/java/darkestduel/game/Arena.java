package darkestduel.game;

import darkestduel.exceptions.InvalidArenaException;
import darkestduel.model.Player;
import darkestduel.util.DamageReport;
import darkestduel.util.MovementPreview;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
 public class Arena {
        private int size;
        private Map<Integer, FireHazard> fireHazards = new HashMap<>();

        public Arena(int size) {
            if (size < 4) {
                throw new InvalidArenaException("A arena precisa ter pelo menos 4 casas.");
            }
            this.size = size;
        }

        public int distanceBetween(Player playerA, Player playerB) {
            return Math.abs(playerA.getPosition() - playerB.getPosition());
        }

        public int directionTowards(Player actor, Player target) {
            return target.getPosition() > actor.getPosition() ? 1 : -1;
        }

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