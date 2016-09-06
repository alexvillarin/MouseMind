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
import questionsAndAnswers.scientist.ScientistAI;

public class MainScientistAI {

	private static final int turnsLeft = 20;
	private static final int mice = 4;

	public static void main(String[] args) {
		PriorityQueue<MouseDesire> desiresBlue = initializeDesires1();
		PriorityQueue<MouseDesire> desiresGreen = initializeDesires2();
		PriorityQueue<MouseDesire> desiresRed = initializeDesires3();
		PriorityQueue<MouseDesire> desiresYellow = initializeDesires4();
		IBoard initialBoard = initializeBoard();
		IBoard board = initializeBoard();
		IPosition[] position = new IPosition[4];
		position[0] = new Position(0, 0);
		position[1] = new Position(0, 9);
		position[2] = new Position(9, 0);
		position[3] = new Position(9, 9);
		MouseAI[] ai = new MouseAI[4];
		ai[0] = new MouseAI(turnsLeft, MouseType.BLUE, position[0], Direction.NORTH, board, MovementType.DESIRE,
				desiresBlue, QandAType.LYING);
		ai[1] = new MouseAI(turnsLeft, MouseType.GREEN, position[1], Direction.NORTH, board, MovementType.DESIRE,
				desiresGreen, QandAType.LYING);
		ai[2] = new MouseAI(turnsLeft, MouseType.RED, position[2], Direction.NORTH, board, MovementType.DESIRE,
				desiresRed, QandAType.LYING);
		ai[3] = new MouseAI(turnsLeft, MouseType.YELLOW, position[3], Direction.NORTH, board, MovementType.DESIRE,
				desiresYellow, QandAType.LYING);

		for (int j = 0; j < mice; j++)
			ai[j].observe(board);

		for (int i = 0; i < turnsLeft; i++) {
			System.out.println("Turno: " + (i + 1));
			for (int j = 0; j < mice; j++) {
				Action nextAction = ai[j].nextAction();
				MouseType mouse = ai[j].getMouse();
				boolean success = successfulMove(nextAction, position, j, board);
				for (int k = 0; k < mice; k++) {
					ai[k].observe(board, mouse, nextAction, success, i * mice + j + 1);
				}
				// System.out.println(mouse + ": " + nextAction);
				System.out.println(mouse + ": " + position[j]);
			}
		}

		// New from MainMouseMovement
		ScientistAI scientist = new ScientistAI(initialBoard, board, ai);
		MouseType accused = scientist.ScientistInterrogation();

		System.out.println("Accused: " + accused);
	}

	private static PriorityQueue<MouseDesire> initializeDesires1() {
		MouseDesire eat = new MouseDesire(Desire.CHEESE, 100);
		MouseDesire notBreak = new MouseDesire(Desire.NOT_BREAK, 90);
		MouseDesire avoidPunishment = new MouseDesire(Desire.AVOID_PUNISHMENT, 10);
		PriorityQueue<MouseDesire> desires = new PriorityQueue<MouseDesire>();
		desires.add(eat);
		desires.add(notBreak);
		desires.add(avoidPunishment);
		return desires;
	}

	private static PriorityQueue<MouseDesire> initializeDesires2() {
		MouseDesire walk = new MouseDesire(Desire.WALK, 80);
		MouseDesire eat = new MouseDesire(Desire.SEE_BLUE, 70);
		MouseDesire notBreak = new MouseDesire(Desire.NOT_BREAK, 70);
		MouseDesire avoidPunishment = new MouseDesire(Desire.AVOID_PUNISHMENT, 10);
		PriorityQueue<MouseDesire> desires = new PriorityQueue<MouseDesire>();
		desires.add(walk);
		desires.add(eat);
		desires.add(notBreak);
		desires.add(avoidPunishment);
		return desires;
	}

	private static PriorityQueue<MouseDesire> initializeDesires3() {
		MouseDesire eat = new MouseDesire(Desire.CHEESE, 100);
		MouseDesire notBreak = new MouseDesire(Desire.NOT_BREAK, 70);
		MouseDesire avoidPunishment = new MouseDesire(Desire.AVOID_PUNISHMENT, 10);
		PriorityQueue<MouseDesire> desires = new PriorityQueue<MouseDesire>();
		desires.add(eat);
		desires.add(notBreak);
		desires.add(avoidPunishment);
		return desires;
	}

	private static PriorityQueue<MouseDesire> initializeDesires4() {
		MouseDesire eat = new MouseDesire(Desire.REST, 100);
		MouseDesire notBreak = new MouseDesire(Desire.NOT_BREAK, 70);
		MouseDesire avoidPunishment = new MouseDesire(Desire.AVOID_PUNISHMENT, 10);
		PriorityQueue<MouseDesire> desires = new PriorityQueue<MouseDesire>();
		desires.add(eat);
		desires.add(notBreak);
		desires.add(avoidPunishment);
		return desires;
	}

	private static Board initializeBoard() {
		Tile[][] tiles = new Tile[10][10];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				tiles[i][j] = new Tile(i, j, TileType.EMPTY);

		tiles[0][3] = new Tile(0, 3, TileType.SHOJI);
		tiles[1][1] = new Tile(1, 1, TileType.OBSTACLE);
		tiles[1][2] = new Tile(1, 2, TileType.OBSTACLE);
		tiles[1][3] = new Tile(1, 3, TileType.OBSTACLE);
		tiles[1][6] = new Tile(1, 6, TileType.SHOJI);
		tiles[1][7] = new Tile(1, 7, TileType.OBSTACLE);
		tiles[1][8] = new Tile(1, 8, TileType.OBSTACLE);
		tiles[2][1] = new Tile(2, 1, TileType.OBSTACLE);
		tiles[2][8] = new Tile(2, 8, TileType.OBSTACLE);
		//tiles[3][0] = new Tile(3, 0, TileType.SHOJI);
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

		tiles[0][0].add(new Entity(0, 0, EntityType.MOUSE_BLUE));
		tiles[0][9].add(new Entity(0, 9, EntityType.MOUSE_GREEN));
		tiles[9][0].add(new Entity(9, 0, EntityType.MOUSE_RED));
		tiles[9][9].add(new Entity(9, 9, EntityType.MOUSE_YELLOW));
		tiles[4][5].add(new Entity(4, 5, EntityType.CHEESE));
		return new Board(tiles);
	}

	private static boolean successfulMove(Action nextAction, IPosition[] position, int j, IBoard board) {
		if (nextAction.equals(Action.MOVE_EAST))
			if (position[j].getY() == 9)
				return false;
			else {
				position[j] = new Position(position[j].getX(), position[j].getY() + 1);
				if (board.getTile(position[j]).getType().equals(TileType.SHOJI))
					board.getTile(position[j]).breakShoji();
				return true;
			}
		else if (nextAction.equals(Action.MOVE_NORTH))
			if (position[j].getX() == 0)
				return false;
			else {
				position[j] = new Position(position[j].getX() - 1, position[j].getY());
				if (board.getTile(position[j]).getType().equals(TileType.SHOJI))
					board.getTile(position[j]).breakShoji();
				return true;
			}
		else if (nextAction.equals(Action.MOVE_SOUTH))
			if (position[j].getX() == 9)
				return false;
			else {
				position[j] = new Position(position[j].getX() + 1, position[j].getY());
				if (board.getTile(position[j]).getType().equals(TileType.SHOJI))
					board.getTile(position[j]).breakShoji();
				return true;
			}
		else if (nextAction.equals(Action.MOVE_WEST))
			if (position[j].getY() == 0)
				return false;
			else {
				position[j] = new Position(position[j].getX(), position[j].getY() - 1);
				if (board.getTile(position[j]).getType().equals(TileType.SHOJI))
					board.getTile(position[j]).breakShoji();
				return true;
			}
		else if (nextAction.equals(Action.EAT))
			if (position[j].getX() != 4 || position[j].getY() != 5)
				return false;
			else {
				board.getTile(4, 5).remove(new Entity(4, 5, EntityType.CHEESE));
				return true;
			}
		else if (nextAction.equals(Action.TALK))
			return true;
		else
			return false;
	}

}
