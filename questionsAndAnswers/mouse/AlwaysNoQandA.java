package questionsAndAnswers.mouse;

import interfaces.IPosition;
import interfaces.MouseType;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;

public class AlwaysNoQandA extends MouseQandA {

	public AlwaysNoQandA(MouseType color, IPosition position) {
		super(color, position);
	}

	public Answer ask(QuestionType type, Object[] args) {
		return Answer.NO;
	}

}
