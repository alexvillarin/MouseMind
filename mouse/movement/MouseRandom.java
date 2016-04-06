package mouse.movement;

import mouse.action.Action;
import mouse.action.ActionType;
import mouse.action.Move;

public class MouseRandom extends MouseMovement {

	public Action nextAction() {
		ActionType action = ActionType.random();
		if (action == ActionType.move)
			return new Move(Direction.random());
		return new Action(action);
	}

}
