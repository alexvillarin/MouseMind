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

/*
 * This class exnteds MouseQandA abstract class. The mouse reply sincerely
 */
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
				// If someone ate the cheese
				if (next.getAction().equals(Action.EAT))
					if (next.getMouse().equals(mouse)) // If the mouse ate the cheese
						return Answer.YES;
					else // If another mouse ate the cheese
						return Answer.NO;
			}
			// If the mouse didn't see who ate the cheese
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
			// If the mouse didn't see if that mouse was in the given tile
			return Answer.I_DONT_KNOW;
		} else // The scientist is asking for something that the mouse doesn't recognise
			return Answer.SILENCE;
	}

}
