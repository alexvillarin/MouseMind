package mouse.movement;

import mouse.action.Action;
import mouse.action.Wait;

/*
 * Class that controls the movement of a mouse. the mouse waits always.
 */

public class MouseNone extends MouseMovement {

	public Action nextAction() {
		return new Wait();
	}

}
