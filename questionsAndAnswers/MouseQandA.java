package questionsAndAnswers;

import java.util.Vector;

import interfaces.Board;

public abstract class MouseQandA {
	
	protected Vector<Board> history;
	
	public abstract boolean ask(QuestionType type, Object[] args);

	public void observe(Board board) {
		history.add(board);
	}
	
}
