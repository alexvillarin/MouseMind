package questionsAndAnswers;

public class AlwaysNoQandA extends MouseQandA {

	public Answer ask(QuestionType type, Object[] args) {
		return Answer.NO;
	}

}
