package movement;

import actions.Action;
import actions.ActionType;
import actions.Move;

public class MouseRandom extends MouseMovement {

	public Action nextAction() {
		ActionType action = ActionType.random();
		if (action == ActionType.move)
			return new Move(Direction.random());
		return new Action(action);
	}

}
