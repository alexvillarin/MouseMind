package questionsAndAnswers;

import java.util.ArrayList;

import interfaces.Board;

public abstract class MouseQandA {
	
	public abstract boolean ask(QuestionType type, Object[] args, ArrayList<Board> history, boolean cheese);
	
}
