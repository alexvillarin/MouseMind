package questionsAndAnswers;

import interfaces.IBoard;
import interfaces.IScientist;
import interfaces.Mouse;

public class ScientistAI implements IScientist {
	private IBoard board;
	private MouseQandA[] allMouses;
	
	public ScientistAI(IBoard board, MouseQandA[] mouses) {
		this.board = board;
		this.allMouses = mouses;
	}
	
	public Mouse ScientistInterrogation() {
		for (MouseQandA mouse : allMouses)
			if (mouse.ask(QuestionType.Cheese, null) == Answer.YES)
				return mouse.getMouse();
		return allMouses[(int) Math.round(Math.random() * allMouses.length)].getMouse();
	}

}
