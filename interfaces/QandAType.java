package interfaces;

import questionsAndAnswers.AlwaysIDontKnow;
import questionsAndAnswers.AlwaysNoQandA;
import questionsAndAnswers.AlwaysYesQandA;
import questionsAndAnswers.MouseQandA;

public enum QandAType {
	LYING, TRUE, ALWAYS_NO, ALWAYS_YES;

	public MouseQandA getMouseQandA() {
		switch (this) {
		case ALWAYS_YES:
			return new AlwaysYesQandA();
		case ALWAYS_NO:
			return new AlwaysNoQandA();
		default:
			return new AlwaysIDontKnow();
		}
	}
}
