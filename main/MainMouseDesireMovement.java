package main;

import interfaces.EntityType;
import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.MouseType;
import interfaces.MovementType;
import interfaces.QandAType;
import interfaces.TileType;

import java.util.PriorityQueue;

import mouse.MouseAI;
import mouse.desire.Desire;
import mouse.desire.MouseDesire;
import mouse.movement.Direction;

public class MainMouseDesireMovement {

	// Deprecated. Better use MainMouseMovement instead
	public static void main(String[] args) {
		PriorityQueue<MouseDesire> desires = initializeDesires();
		IBoard board = initializeBoard();
		IPosition position = new Position(0, 0);
		Direction orientation = Direction.NORTH;
		MouseType color = MouseType.BLUE;
		MouseAI ai = new MouseAI(10, color, position, orientation, board, MovementType.DESIRE, desires, QandAType.TRUE);
		ai.observe(board);
		System.out.println(ai.nextAction());
	}

	private static PriorityQueue<MouseDesire> initializeDesires() {
		MouseDesire eat = new MouseDesire(Desire.CHEESE, 100);
		MouseDesire notBreak = new MouseDesire(Desire.NOT_BREAK, 70);
		PriorityQueue<MouseDesire> desires = new PriorityQueue<MouseDesire>();
		desires.add(eat);
		desires.add(notBreak);
		return desires;
	}

	private static Board initializeBoard() {
		Tile[][] tiles = new Tile[10][10];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				tiles[i][j] = new Tile(i, j, TileType.EMPTY);
		tiles[0][0].add(new Entity(0, 0, EntityType.MOUSE_BLUE));
		tiles[2][3].add(new Entity(2, 3, EntityType.CHEESE));
		return new Board(tiles);
	}
}
