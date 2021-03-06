package mouse.action;

import mouse.movement.Direction;

/*
 * Actions that the mice can perform. MOVE_EAST, MOVE_NORTH, MOVE_SOUTH and MOVE_WEST are the result of
 * applying the action MOVE to one specific direction
 */
public enum Action {
	EAT, MOVE_EAST, MOVE_NORTH, MOVE_SOUTH, MOVE_WEST, TALK, WAIT;

	private static double elements = 4;

	public static Action move(Direction orientation) {
		if (orientation.equals(Direction.EAST))
			return MOVE_EAST;
		if (orientation.equals(Direction.NORTH))
			return MOVE_NORTH;
		if (orientation.equals(Direction.SOUTH))
			return MOVE_SOUTH;
		if (orientation.equals(Direction.WEST))
			return MOVE_WEST;
		return WAIT;
	}

	public static Action random() {
		double value = Math.random() * elements;
		if (value < 1)
			return EAT;
		if (value < 1.25)
			return MOVE_EAST;
		if (value < 1.5)
			return MOVE_NORTH;
		if (value < 1.75)
			return MOVE_SOUTH;
		if (value < 2)
			return MOVE_WEST;
		if (value < 3)
			return TALK;
		return WAIT;

	}
}
