package questionsAndAnswers;

import interfaces.MouseType;

public class AlwaysYesQandA extends MouseQandA {

	public AlwaysYesQandA(MouseType color) {
		super(color);
	}

	public Answer ask(QuestionType type, Object[] args) {
		return Answer.YES;
	}

}
