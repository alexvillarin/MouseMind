package movement;

public enum Direction {
	NORTH, SOUTH, EAST, WEST;
	
	// Return a random direction
	public static Direction random() {
		int value = (int) (Math.random() * 4);
		if (value < 1)
			return Direction.NORTH;
		if (value < 2)
			return Direction.SOUTH;
		if (value < 3)
			return Direction.EAST;
		else
			return Direction.WEST;
	}
	
	// Return a rendom direction from the array of possible values
	public static Direction random(Direction[] possibleValues) {
		int value = (int) (Math.random() * possibleValues.length);
		for (int i = 0; i < possibleValues.length; i++)
			if (value <= i + 1)
				return possibleValues[i];
		return random();
	}
}
