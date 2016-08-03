package mouse.movement;

import interfaces.IPosition;
import interfaces.MouseType;
import mouse.action.Action;

public class MouseRandom extends MouseMovement {

	public MouseRandom(IPosition position, Direction orientation,
			MouseType color) {
		super(position, orientation, color);
	}

	public Action nextAction() {
		return Action.random();
	}

}
