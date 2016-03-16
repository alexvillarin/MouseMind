package ai;

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

public class MouseAI implements Mouse {
	private MouseMovement movement;
	private MouseQandA qanda;
	
	private int turnsLeft;
	private Color color;
	private ControllerType controller;
	private Tile position;
	private Direction orientation;
	

	public MouseAI(int turnsLeft, Color color, Tile position, Direction orientation, Board initialBoard,
			MouseMovement movement, MouseQandA qanda) {
		this.turnsLeft = turnsLeft;
		this.color = color;
		this.controller = ControllerType.computer;
		this.position = position;
		this.orientation = orientation;
		
		this.movement = movement;
		this.qanda = qanda;
	}

	public void observe(Board board) {
		movement.observe(board);
		qanda.observe(board);
	}

	public Action nextAction() {
		return movement.nextAction();
	}

	public boolean ask(QuestionType type, Object[] args) {
		return qanda.ask(type, args);
	}

}
