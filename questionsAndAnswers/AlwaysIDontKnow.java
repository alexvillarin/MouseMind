package questionsAndAnswers;

public class AlwaysIDontKnow extends MouseQandA {

	public Answer ask(QuestionType type, Object[] args) {
		return Answer.I_DONT_KNOW;
	}

}
