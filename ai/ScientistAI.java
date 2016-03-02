package ai;

import interfaces.Mouse;
import interfaces.Scientist;
import questionsAndAnswers.QuestionType;

public class ScientistAI extends Scientist {
	
	public Mouse ScientistInterrogation() {
		Mouse[] allMouses = board.getMouses();
		for (Mouse mouse : allMouses)
			if (mouse.ask(QuestionType.Cheese, null))
				return mouse;
		return allMouses[(int) Math.round(Math.random() * allMouses.length)];
	}

}
