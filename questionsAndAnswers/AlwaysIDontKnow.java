package questionsAndAnswers;

import interfaces.MouseType;

public class AlwaysIDontKnow extends MouseQandA {

	public AlwaysIDontKnow(MouseType color) {
		super(color);
	}

	public Answer ask(QuestionType type, Object[] args) {
		return Answer.I_DONT_KNOW;
	}

}
