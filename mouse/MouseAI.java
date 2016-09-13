package mouse;

import java.util.PriorityQueue;

import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.MouseType;
import interfaces.MovementType;
import interfaces.QandAType;
import interfaces.IMouseAI;
import mouse.action.Action;
import mouse.desire.MouseDesire;
import mouse.movement.Direction;
import mouse.movement.MouseMovement;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;
import questionsAndAnswers.mouse.MouseQandA;

/*
 * Implementation of the IMouseAI interface. It has two main parts. The first called MouseMovement
 * related to mouse actions during the night, and the second one related to answer the scientist
 * questions on the morning.
 */
public class MouseAI implements IMouseAI {
	private MouseMovement movement;
	private MouseQandA qanda;
	private MouseType color;

	public MouseAI(int turnsLeft, MouseType color, IPosition position,
			Direction orientation, IBoard initialBoard, MovementType movement,
			PriorityQueue<MouseDesire> desires, QandAType qanda) {
		this.movement = movement.getMouseMovement(desires, position,
				orientation, color, turnsLeft);
		this.qanda = qanda.getMouseQandA(color, position);
		this.color = color;
	}

	/*
	 * Method to observe the board when not action was noticed. It is used to get the initial
	 * state of the board and when the mouse can't see the action performed.
	 */
	public void observe(IBoard board) {
		movement.observe(board);
		qanda.observe(board);
	}

	// Method to observe the state of the board after an action was performed
	public void observe(IBoard board, MouseType mouse, Action action,
			Boolean success, int go) {
		movement.observe(board, mouse, action, success, go);
		qanda.observe(board, mouse, action, success, go);
	}

	// Method to get the next action to execute
	public Action nextAction() {
		return movement.nextAction();
	}

	// Method to answer the questions of the scientist
	public Answer ask(QuestionType type, Object[] args) {
		return qanda.ask(type, args);
	}
	
	public MouseType getMouse() {
		return color;
	}

	public IPosition getInitialPosition() {
		return qanda.getInitialPosition();
	}
}
