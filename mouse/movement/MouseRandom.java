package mouse.movement;

import interfaces.IPosition;
import interfaces.MouseType;
import mouse.action.Action;

/*
 * Class that extends the abstract class MouseMovement, which controls the actions of a mouse.
 * The mouse performs a random action.
 */
public class MouseRandom extends MouseMovement {

	public MouseRandom(IPosition position, Direction orientation,
			MouseType color) {
		super(position, orientation, color);
	}

	public Action nextAction() {
		return Action.random();
	}

}
