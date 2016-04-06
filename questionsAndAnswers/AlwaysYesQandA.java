package questionsAndAnswers;

public class AlwaysYesQandA extends MouseQandA {

	public Answer ask(QuestionType type, Object[] args) {
		return Answer.YES;
	}

}
