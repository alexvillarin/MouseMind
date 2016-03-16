package interfaces;

import actions.Action;
import questionsAndAnswers.QuestionType;

public interface Mouse {	
	public void observe(Board board);
	public Action nextAction();
	public boolean ask(QuestionType type, Object[] args);
}
