package mouse.movement;

import java.util.PriorityQueue;
import java.util.Vector;

import event.Event;
import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.Mouse;
import mouse.action.Action;
import mouse.action.Eat;
import mouse.desire.Desire;
import mouse.desire.MouseDesire;

/*
 * Class that controls the movement of a mouse.
 */

public abstract class MouseMovement {
	protected PriorityQueue<MouseDesire> desires;
	protected IPosition position;
	protected Direction orientation;
	private Mouse color;
	protected Vector<IBoard> history;
	protected Vector<Event> eventHistory;

	public abstract Action nextAction();

	public void observe(IBoard board) {
		history.add(board);
	}

	public void observe(IBoard board, Mouse mouse, Action action, Boolean success) {
		if (success) {
			history.add(board);
			eventHistory.add(new Event(mouse, action, null));
			
			if (mouse.equals(color) && action.equals(new Eat()))
				desires.remove(new MouseDesire(Desire.Cheese, 0));
		}
	}

}