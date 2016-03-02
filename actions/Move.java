package actions;

import movement.Direction;

public class Move extends Action {
	private Direction dir;

	public Move(Direction dir) {
		super(ActionType.move);
		this.dir = dir;
	}

}
