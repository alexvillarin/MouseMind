package mouse.action;

import mouse.movement.Direction;

public class Move extends Action {
	private Direction dir;

	public Move(Direction dir) {
		super(ActionType.move);
		this.dir = dir;
	}
	
	public Direction getDirection() {
		return dir;
	}

}
