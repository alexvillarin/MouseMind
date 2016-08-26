package interfaces;

import questionsAndAnswers.mouse.AlwaysIDontKnow;
import questionsAndAnswers.mouse.AlwaysNoQandA;
import questionsAndAnswers.mouse.AlwaysYesQandA;
import questionsAndAnswers.mouse.IntelligentMouse;
import questionsAndAnswers.mouse.MouseQandA;
import questionsAndAnswers.mouse.TrueMouse;

public enum QandAType {
	LYING, TRUE, ALWAYS_NO, ALWAYS_YES, ALWAYS_I_DONT_KNOW;

	public MouseQandA getMouseQandA(MouseType color, IPosition position) {
		switch (this) {
		case ALWAYS_YES:
			return new AlwaysYesQandA(color, position);
		case ALWAYS_NO:
			return new AlwaysNoQandA(color, position);
		case ALWAYS_I_DONT_KNOW:
			return new AlwaysIDontKnow(color, position);
		case TRUE:
			return new TrueMouse(color, position);
		case LYING:
			return new IntelligentMouse(color, position);
		default:
			return new AlwaysIDontKnow(color, position);
		}
	}
}
