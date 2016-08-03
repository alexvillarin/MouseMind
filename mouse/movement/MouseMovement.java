package mouse.movement;

import java.util.Vector;

import event.Event;
import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.MouseType;
import mouse.action.Action;

/*
 * Class that controls the movement of a mouse.
 */

public abstract class MouseMovement {
	protected IPosition position;
	protected Direction orientation;
	protected MouseType color;
	protected Vector<IBoard> history;
	protected Vector<Event> eventHistory;

	public MouseMovement(IPosition position, Direction orientation,
			MouseType color) {
		this.position = position;
		this.orientation = orientation;
		this.color = color;
		history = new Vector<IBoard>();
		eventHistory = new Vector<Event>();
	}

	public abstract Action nextAction();

	public void observe(IBoard board) {
		history.add(board);
	}

	public void observe(IBoard board, MouseType mouse, Action action,
			Boolean success, int go) {
		if (success && mouse.equals(color)) {
			if (action.equals(Action.MOVE_EAST)) {
				position = position.east(history.lastElement());
				orientation = Direction.EAST;
			} else if (action.equals(Action.MOVE_NORTH)) {
				position = position.north(history.lastElement());
				orientation = Direction.NORTH;
			} else if (action.equals(Action.MOVE_SOUTH)) {
				position = position.south(history.lastElement());
				orientation = Direction.SOUTH;
			} else if (action.equals(Action.MOVE_WEST)) {
				position = position.west(history.lastElement());
				orientation = Direction.WEST;
			}
		}

		history.add(board);
		eventHistory.add(new Event(mouse, action, 100, success, go));
	}

}
