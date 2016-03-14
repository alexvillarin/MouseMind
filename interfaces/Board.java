package interfaces;

import movement.Direction;

public abstract class Board {
	public Tile[][] board;
	
	public abstract boolean hasCheesse();
	public abstract Mouse[] getMouses();
	public abstract void moveMouse(Mouse mouse, Direction direction);
}
