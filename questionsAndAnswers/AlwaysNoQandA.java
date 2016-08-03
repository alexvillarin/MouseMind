package questionsAndAnswers;

import interfaces.MouseType;

public class AlwaysNoQandA extends MouseQandA {

	public AlwaysNoQandA(MouseType color) {
		super(color);
	}

	public Answer ask(QuestionType type, Object[] args) {
		return Answer.NO;
	}

}
