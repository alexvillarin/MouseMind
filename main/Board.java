package main;

import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.ITile;

// A example implementation for testing purposes of the interface IBoard
public class Board implements IBoard {
	private ITile[][] board;
	private boolean cheese = true;

	public Board(ITile[][] tiles) {
		board = new ITile[tiles.length][];
		for (int i = 0; i < tiles.length; i++) {
			board[i] = new ITile[tiles[i].length];
			for (int j = 0; j < tiles[i].length; j++)
				board[i][j] = tiles[i][j];
		}
	}

	public ITile getTile(int x, int y) {
		if (0 <= x && x < 10 && 0 <= y && y < 10)
			return board[x][y];
		return null;
	}

	public ITile getTile(IPosition position) {
		if (0 <= position.getX() && position.getX() < 10 && 0 <= position.getY() && position.getY() < 10)
			return board[position.getX()][position.getY()];
		return null;
	}

	public int getWidth() {
		return 10;
	}

	public int getHeight() {
		return 10;
	}

	public boolean hasCheesse() {
		return cheese;
	}

	public void updateTile(int x, int y, ITile tile) {
		board[x][y] = tile;
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++)
				str += board[i][j] + " ";
			str += "\n";
		}
		return str;
	}
}
