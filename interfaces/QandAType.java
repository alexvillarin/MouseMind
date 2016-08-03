package interfaces;

import questionsAndAnswers.AlwaysIDontKnow;
import questionsAndAnswers.AlwaysNoQandA;
import questionsAndAnswers.AlwaysYesQandA;
import questionsAndAnswers.MouseQandA;

public enum QandAType {
	LYING, TRUE, ALWAYS_NO, ALWAYS_YES;

	public MouseQandA getMouseQandA(MouseType color) {
		switch (this) {
		case ALWAYS_YES:
			return new AlwaysYesQandA(color);
		case ALWAYS_NO:
			return new AlwaysNoQandA(color);
		default:
			return new AlwaysIDontKnow(color);
		}
	}
}
