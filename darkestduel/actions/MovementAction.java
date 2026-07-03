package darkestduel.actions;

import darkestduel.game.Arena;
import darkestduel.model.Player;
import darkestduel.util.MovementPreview;

import java.util.ArrayList;
import java.util.List;

public class MovementAction extends Action {
    private final int steps;

    public MovementAction(String name, String description, int acCost, int steps) {
        super(name, description, acCost);
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }

    @Override
    public boolean canExecute(Player actor, Player target, Arena arena) {
        MovementPreview preview = arena.previewMovement(actor, target, steps);
        return super.canExecute(actor, target, arena) && preview.isValid();
    }

    @Override
    public List<String> execute(Player actor, Player target, Arena arena) {
        MovementPreview preview = arena.previewMovement(actor, target, steps);

        if (!preview.isValid()) {
            List<String> messages = new ArrayList<>();
            messages.add(preview.getMessage());
            return messages;
        }

        if (!super.canExecute(actor, target, arena)) {
            List<String> messages = new ArrayList<>();
            messages.add(unavailableReason(actor));
            return messages;
        }

        payAndApplyCooldown(actor);
        return arena.movePlayer(actor, target, steps);
    }
}