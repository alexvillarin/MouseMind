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
import questionsAndAnswers.MouseQandA;
import questionsAndAnswers.QuestionType;

public class MouseAI implements IMouseAI {
	private MouseMovement movement;
	private MouseQandA qanda;
	private MouseType color;

	public MouseAI(int turnsLeft, MouseType color, IPosition position,
			Direction orientation, IBoard initialBoard, MovementType movement,
			PriorityQueue<MouseDesire> desires, QandAType qanda) {
		this.movement = movement.getMouseMovement(desires, position,
				orientation, color);
		this.qanda = qanda.getMouseQandA(color);
		this.color = color;
	}

	public void observe(IBoard board) {
		movement.observe(board);
		qanda.observe(board);
	}

	public void observe(IBoard board, MouseType mouse, Action action,
			Boolean success, int go) {
		movement.observe(board, mouse, action, success, go);
		qanda.observe(board, mouse, action, success, go);
	}

	public Action nextAction() {
		return movement.nextAction();
	}

	public Answer ask(QuestionType type, Object[] args) {
		return qanda.ask(type, args);
	}
	
	public MouseType getMouse() {
		return color;
	}
}
