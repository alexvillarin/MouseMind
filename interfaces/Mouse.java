package interfaces;

import actions.Action;
import movement.Direction;
import questionsAndAnswers.QuestionType;

public abstract class Mouse {
	protected int turnsLeft;
	protected Color color;
	protected ControllerType controller;
	protected Tile position;
	protected Direction orientation;

	public Mouse(int turnsLeft, Color color, ControllerType controller, Tile position, Direction orientation) {
		this.turnsLeft = turnsLeft;
		this.color = color;
		this.controller = controller;
		this.position = position;
		this.orientation = orientation;
	}
	
	public abstract void observe(Board board);
	public abstract Action nextAction();
	public abstract boolean ask(QuestionType type, Object[] args);
}
