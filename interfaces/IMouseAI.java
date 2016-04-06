package interfaces;

import mouse.action.Action;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;

public interface IMouseAI {
	public void observe(IBoard board);

	public void observe(IBoard board, Mouse Mouse, Action action, Boolean success);

	public Action nextAction();

	public Answer ask(QuestionType type, Object[] args);
}
