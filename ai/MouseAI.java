package ai;

import java.util.HashSet;
import java.util.Vector;

import actions.Action;
import interfaces.Board;
import interfaces.Color;
import interfaces.ControllerType;
import interfaces.Mouse;
import interfaces.Tile;
import movement.Direction;
import movement.MouseMovement;
import questionsAndAnswers.MouseQandA;
import questionsAndAnswers.QuestionType;

public class MouseAI extends Mouse {
	private boolean cheese;
	private HashSet<Desire> desires;
	private HashSet<Desire> hates;
	private Vector<Board> history;
	private MouseMovement movement;
	private MouseQandA qanda;
	

	public MouseAI(int turnsLeft, Color color, Tile position, Direction orientation, Board initialBoard,
			HashSet<Desire> desires, MouseMovement movement, MouseQandA qanda) {
		super(turnsLeft, color, ControllerType.computer, position, orientation);
		this.cheese = false;
		this.desires = desires;
		this.history = new Vector<Board>();
		this.history.add(initialBoard);
		this.movement = movement;
		this.qanda = qanda;
	}

	public void observe(Board board) {
		history.add(board);
	}

	public Action nextAction() {
		return movement.nextAction(desires, hates, position, orientation, history);
	}

	public boolean ask(QuestionType type, Object[] args) {
		return qanda.ask(type, args, history, cheese);
	}

}
