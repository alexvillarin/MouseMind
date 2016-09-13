package questionsAndAnswers.mouse;

import interfaces.IPosition;
import interfaces.MouseType;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;

/*
 * This class exnteds MouseQandA abstract class. This always reply with YES
 */
public class AlwaysYesQandA extends MouseQandA {

	public AlwaysYesQandA(MouseType color, IPosition position) {
		super(color, position);
	}

	public Answer ask(QuestionType type, Object[] args) {
		return Answer.YES;
	}

}
