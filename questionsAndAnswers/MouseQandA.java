package questionsAndAnswers;

import java.util.PriorityQueue;
import java.util.Vector;

import event.Event;
import interfaces.IBoard;
import interfaces.Mouse;
import mouse.action.Action;
import mouse.action.Eat;
import mouse.desire.Desire;
import mouse.desire.MouseDesire;

public abstract class MouseQandA {
	
	protected PriorityQueue<MouseDesire> desires;
	private Mouse color;
	protected Vector<IBoard> history;
	private Vector<Event> eventHistory;
	
	public abstract Answer ask(QuestionType type, Object[] args);

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
	
	public Mouse getMouse() {
		return color;
	}

	
}
