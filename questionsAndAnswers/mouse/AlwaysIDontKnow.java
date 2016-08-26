package questionsAndAnswers.mouse;

import interfaces.IPosition;
import interfaces.MouseType;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;

public class AlwaysIDontKnow extends MouseQandA {

	public AlwaysIDontKnow(MouseType color, IPosition position) {
		super(color, position);
	}

	public Answer ask(QuestionType type, Object[] args) {
		return Answer.I_DONT_KNOW;
	}

}
