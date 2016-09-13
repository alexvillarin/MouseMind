package questionsAndAnswers.mouse;

import interfaces.IPosition;
import interfaces.MouseType;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;

/*
 * This class exnteds MouseQandA abstract class. This always reply with I_DONT_KNOW
 */
public class AlwaysIDontKnow extends MouseQandA {

	public AlwaysIDontKnow(MouseType color, IPosition position) {
		super(color, position);
	}

	public Answer ask(QuestionType type, Object[] args) {
		return Answer.I_DONT_KNOW;
	}

}
