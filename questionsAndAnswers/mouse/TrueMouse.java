package questionsAndAnswers.mouse;

import java.util.Iterator;

import event.Event;
import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.ITile;
import interfaces.MouseType;
import mouse.action.Action;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;

public class TrueMouse extends MouseQandA {

	public TrueMouse(MouseType color, IPosition position) {
		super(color, position);
	}

	public Answer ask(QuestionType type, Object[] args) {
		if (type.equals(QuestionType.EAT_CHEESE)) {
			MouseType mouse = (MouseType) args[0];
			Iterator<Event> it = eventHistory.iterator();
			while (it.hasNext()) {
				Event next = it.next();
				if (next.getAction().equals(Action.EAT))
					if (next.getMouse().equals(mouse))
						return Answer.YES;
					else
						return Answer.NO;
			}
			return Answer.I_DONT_KNOW;
		} else if (type.equals(QuestionType.MOUSE_IN_TILE)) {
			MouseType mouse = (MouseType) args[0];
			ITile tile = (ITile) args[1];
			Iterator<IBoard> it = history.iterator();
			while (it.hasNext()) {
				IBoard next = it.next();
				if (next.getTile(tile.getPosition()).getThings().contains(mouse))
					return Answer.YES;
			}
			return Answer.I_DONT_KNOW;
		} else
			return Answer.SILENCE;
	}

}
