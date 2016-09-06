package questionsAndAnswers.mouse;

import java.util.PriorityQueue;
import java.util.Vector;

import event.Event;
import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.MouseType;
import mouse.action.Action;
import mouse.desire.MouseDesire;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;

public abstract class MouseQandA {
	
	protected PriorityQueue<MouseDesire> desires;
	protected MouseType color;
	protected IPosition initialPosition;
	protected Vector<IBoard> history;
	protected Vector<Event> eventHistory;
	
	public MouseQandA(MouseType color, IPosition position) {
		this.color = color;
		initialPosition = position;
		history = new Vector<IBoard>();
		eventHistory = new Vector<Event>();
	}
	
	public abstract Answer ask(QuestionType type, Object[] args);

	public void observe(IBoard board) {
		history.add(board);
	}
	
	public void observe(IBoard board, MouseType mouse, Action action, Boolean success, int go) {
		history.add(board);
		eventHistory.add(new Event(mouse, action, 100, success, go));
	}
	
	public MouseType getMouse() {
		return color;
	}
	
	public IPosition getInitialPosition() {
		return initialPosition;
	}

	
}
