package mouse.movement;

import interfaces.IPosition;
import interfaces.MouseType;

import mouse.action.Action;

/*
 * Class that extends the abstract class MouseMovement, which controls the actions of a mouse.
 * The mouse performs always the action WAIT.
 */

public class MouseNone extends MouseMovement {

	public MouseNone(IPosition position, Direction orientation, MouseType color) {
		super(position, orientation, color);
	}

	public Action nextAction() {
		return Action.WAIT;
	}

}
