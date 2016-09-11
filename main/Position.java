package main;

import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.ITile;

// A example implementation for testing purposes of the interface IPosition
public class Position implements IPosition {
	private int X;
	private int Y;

	public Position(int X, int Y) {
		this.X = X;
		this.Y = Y;
	}

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}
	
	public IPosition east(IBoard board) {
		return getPosition(board.getTile(X, Y + 1));
	}

	public IPosition north(IBoard board) {
		return getPosition(board.getTile(X - 1, Y));
	}

	public IPosition south(IBoard board) {
		return getPosition(board.getTile(X + 1, Y));
	}

	public IPosition west(IBoard board) {
		return getPosition(board.getTile(X, Y - 1));
	}

	public boolean equals(Object other) {
		return (other instanceof Position && X == ((Position) other).X && Y == ((Position) other).Y);
	}

	public String toString() {
		return "X: " + X + ", Y: " + Y;
	}
	
	private IPosition getPosition(ITile tile) {
		if (tile != null)
			return tile.getPosition();
		else
			return null;
	}
}
