package mouse;

import interfaces.IBoard;
import interfaces.Mouse;
import interfaces.MovementType;
import interfaces.QandAType;
import interfaces.IMouseAI;
import interfaces.ITile;
import mouse.action.Action;
import mouse.movement.Direction;
import mouse.movement.MouseMovement;
import questionsAndAnswers.Answer;
import questionsAndAnswers.MouseQandA;
import questionsAndAnswers.QuestionType;

public class MouseAI implements IMouseAI {
	private MouseMovement movement;
	private MouseQandA qanda;
	

	

	public MouseAI(int turnsLeft, Mouse color, ITile position, Direction orientation, IBoard initialBoard,
			MovementType movement, QandAType qanda) {
		this.movement = movement.getMouseMovement();
		this.qanda = qanda.getMouseQandA();
	}

	public void observe(IBoard board) {
		movement.observe(board);
		qanda.observe(board);
	}
	
	public void observe(IBoard board, Mouse mouse, Action action, Boolean success) {
		movement.observe(board, mouse, action, success);
		qanda.observe(board, mouse, action, success);
	}

	public Action nextAction() {
		return movement.nextAction();
	}

	public Answer ask(QuestionType type, Object[] args) {
		return qanda.ask(type, args);
	}
}
