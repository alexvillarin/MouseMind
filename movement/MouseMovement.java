package movement;

import java.util.PriorityQueue;
import java.util.Vector;

import actions.Action;
import ai.MouseDesire;
import interfaces.Board;
import interfaces.Tile;

/*
 * Class that controls the movement of a mouse.
 */

public abstract class MouseMovement {
	protected PriorityQueue<MouseDesire> desires;
	protected Tile position;
	protected Direction orientation;
	protected Vector<Board> history;

	public abstract Action nextAction();

	public void observe(Board board) {
		history.add(board);
	}

}
