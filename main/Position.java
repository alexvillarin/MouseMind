package main;

import interfaces.IBoard;
import interfaces.IPosition;

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
		return board.getTile(X, Y + 1).getPosition();
	}

	public IPosition north(IBoard board) {
		return board.getTile(X - 1, Y).getPosition();
	}

	public IPosition south(IBoard board) {
		return board.getTile(X + 1, Y).getPosition();
	}

	public IPosition west(IBoard board) {
		return board.getTile(X, Y - 1).getPosition();
	}

	public boolean equals(Object other) {
		return (other instanceof Position && X == ((Position) other).X && Y == ((Position) other).Y);
	}

	public String toString() {
		return "X: " + X + ", Y: " + Y;
	}
}
