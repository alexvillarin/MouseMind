package questionsAndAnswers;

import java.util.Vector;

import interfaces.Board;

public abstract class MouseQandA {
	
	public abstract boolean ask(QuestionType type, Object[] args, Vector<Board> history, boolean cheese);
	
}
