package interfaces;

import movement.Direction;

public interface Board {
	
	public Tile[][] getBoard();
	public boolean hasCheesse();
	public Mouse[] getMouses();
	public void moveMouse(Mouse mouse, Direction direction);
}
