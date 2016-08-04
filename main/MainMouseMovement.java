package main;

import java.util.PriorityQueue;

import interfaces.EntityType;
import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.MouseType;
import interfaces.MovementType;
import interfaces.QandAType;
import interfaces.TileType;
import mouse.MouseAI;
import mouse.action.Action;
import mouse.desire.Desire;
import mouse.desire.MouseDesire;
import mouse.movement.Direction;

public class MainMouseMovement {

	private static final int turnsLeft = 20;
	private static final int mice = 4;

	public static void main(String[] args) {
		PriorityQueue<MouseDesire> desiresRed = initializeDesires1();
		PriorityQueue<MouseDesire> desiresBlue = initializeDesires2();
		PriorityQueue<MouseDesire> desiresGreen = initializeDesires3();
		PriorityQueue<MouseDesire> desiresYellow = initializeDesires4();
		IBoard board = initializeBoard();
		IPosition positionRed = new Position(0, 0);
		IPosition positionBlue = new Position(0, 9);
		IPosition positionGreen = new Position(9, 0);
		IPosition positionYellow = new Position(9, 9);
		MouseAI[] ai = new MouseAI[4];
		ai[0] = new MouseAI(turnsLeft, MouseType.RED, positionRed, Direction.NORTH, board, MovementType.DESIRE,
				desiresRed, QandAType.TRUE);
		ai[1] = new MouseAI(turnsLeft, MouseType.BLUE, positionBlue, Direction.NORTH, board, MovementType.DESIRE,
				desiresBlue, QandAType.TRUE);
		ai[2] = new MouseAI(turnsLeft, MouseType.GREEN, positionGreen, Direction.NORTH, board, MovementType.DESIRE,
				desiresGreen, QandAType.TRUE);
		ai[3] = new MouseAI(turnsLeft, MouseType.YELLOW, positionYellow, Direction.NORTH, board, MovementType.DESIRE,
				desiresYellow, QandAType.TRUE);

		for (int j = 0; j < mice; j++)
			ai[j].observe(board);

		for (int i = 0; i < turnsLeft; i++) {
			System.out.println("Turno: " + (i + 1));
			for (int j = 0; j < mice; j++) {
				Action nextAction = ai[j].nextAction();
				MouseType mouse = ai[j].getMouse();
				for (int k = 0; k < mice; k++)
					ai[k].observe(board, mouse, nextAction, true, i * mice + j + 1);
				System.out.println(mouse + ": " + nextAction);
			}
		}
	}

	private static PriorityQueue<MouseDesire> initializeDesires1() {
		MouseDesire eat = new MouseDesire(Desire.CHEESE, 100);
		MouseDesire notBreak = new MouseDesire(Desire.NOT_BREAK, 70);
		PriorityQueue<MouseDesire> desires = new PriorityQueue<MouseDesire>();
		desires.add(eat);
		desires.add(notBreak);
		return desires;
	}

	private static PriorityQueue<MouseDesire> initializeDesires2() {
		MouseDesire walk = new MouseDesire(Desire.WALK, 80);
		MouseDesire eat = new MouseDesire(Desire.CHEESE, 70);
		MouseDesire notBreak = new MouseDesire(Desire.NOT_BREAK, 70);
		PriorityQueue<MouseDesire> desires = new PriorityQueue<MouseDesire>();
		desires.add(walk);
		desires.add(eat);
		desires.add(notBreak);
		return desires;
	}

	private static PriorityQueue<MouseDesire> initializeDesires3() {
		MouseDesire eat = new MouseDesire(Desire.CHEESE, 100);
		MouseDesire notBreak = new MouseDesire(Desire.NOT_BREAK, 70);
		PriorityQueue<MouseDesire> desires = new PriorityQueue<MouseDesire>();
		desires.add(eat);
		desires.add(notBreak);
		return desires;
	}

	private static PriorityQueue<MouseDesire> initializeDesires4() {
		MouseDesire eat = new MouseDesire(Desire.REST, 100);
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

		tiles[1][1] = new Tile(1, 1, TileType.OBSTACLE);
		tiles[1][2] = new Tile(1, 2, TileType.OBSTACLE);
		tiles[1][3] = new Tile(1, 3, TileType.OBSTACLE);
		tiles[1][6] = new Tile(1, 6, TileType.SHOJI);
		tiles[1][7] = new Tile(1, 7, TileType.OBSTACLE);
		tiles[1][8] = new Tile(1, 8, TileType.OBSTACLE);
		tiles[2][1] = new Tile(2, 1, TileType.OBSTACLE);
		tiles[2][8] = new Tile(2, 8, TileType.OBSTACLE);
		tiles[3][1] = new Tile(3, 1, TileType.OBSTACLE);
		tiles[3][8] = new Tile(3, 8, TileType.OBSTACLE);
		tiles[6][8] = new Tile(6, 8, TileType.OBSTACLE);
		tiles[7][0] = new Tile(7, 0, TileType.OBSTACLE);
		tiles[7][1] = new Tile(7, 1, TileType.OBSTACLE);
		tiles[7][8] = new Tile(7, 8, TileType.OBSTACLE);
		tiles[8][1] = new Tile(8, 1, TileType.OBSTACLE);
		tiles[8][2] = new Tile(8, 2, TileType.OBSTACLE);
		tiles[8][3] = new Tile(8, 3, TileType.OBSTACLE);
		tiles[8][6] = new Tile(8, 6, TileType.SHOJI);
		tiles[8][7] = new Tile(8, 7, TileType.SHOJI);
		tiles[8][8] = new Tile(8, 8, TileType.OBSTACLE);

		tiles[0][0].add(new Entity(0, 0, EntityType.MOUSE_RED));
		tiles[0][9].add(new Entity(0, 9, EntityType.MOUSE_BLUE));
		tiles[9][0].add(new Entity(9, 0, EntityType.MOUSE_GREEN));
		tiles[9][9].add(new Entity(9, 9, EntityType.MOUSE_YELLOW));
		tiles[4][5].add(new Entity(4, 5, EntityType.CHEESE));
		return new Board(tiles);
	}

}
