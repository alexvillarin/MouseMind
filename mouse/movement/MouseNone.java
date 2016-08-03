package mouse.movement;

import interfaces.IPosition;
import interfaces.MouseType;

import mouse.action.Action;

/*
 * Class that controls the movement of a mouse. the mouse waits always.
 */

public class MouseNone extends MouseMovement {

	public MouseNone(IPosition position, Direction orientation, MouseType color) {
		super(position, orientation, color);
	}

	public Action nextAction() {
		return Action.WAIT;
	}

}
