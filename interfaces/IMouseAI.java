package interfaces;

import mouse.action.Action;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;

/* 
 * Interface that the AI of the mouse must fulfill. This interface contains the methods required
 * to get the environments changes, execute actions and answer questions 
 */
public interface IMouseAI {
	public void observe(IBoard board);

	public void observe(IBoard board, MouseType Mouse, Action action, Boolean success, int go);

	public Action nextAction();

	public Answer ask(QuestionType type, Object[] args);
}
