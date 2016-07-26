package questionsAndAnswers;

import interfaces.IBoard;
import interfaces.IScientist;
import interfaces.MouseType;

public class ScientistAI implements IScientist {
	private IBoard board;
	private MouseQandA[] allMice;
	
	public ScientistAI(IBoard board, MouseQandA[] mice) {
		this.board = board;
		this.allMice = mice;
	}
	
	public MouseType ScientistInterrogation() {
		for (MouseQandA mouse : allMice)
			if (mouse.ask(QuestionType.Cheese, null) == Answer.YES)
				return mouse.getMouse();
		return allMice[(int) Math.round(Math.random() * allMice.length)].getMouse();
	}

}
