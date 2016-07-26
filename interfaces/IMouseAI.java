package interfaces;

import mouse.action.Action;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;

public interface IMouseAI {
	public void observe(IBoard board);

	public void observe(IBoard board, MouseType Mouse, Action action, Boolean success, int go);

	public Action nextAction();

	public Answer ask(QuestionType type, Object[] args);
}
