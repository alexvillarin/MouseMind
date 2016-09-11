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
		System.out.print(type + ", ");
		for (int i = 0; i < args.length; i++)
			System.out.print(args[i] + ", ");
		Answer ret = qanda.ask(type, args);
		System.out.println(ret);
		return ret;
	}
	
	public MouseType getMouse() {
		return color;
	}

	public IPosition getInitialPosition() {
		return qanda.getInitialPosition();
	}
}
