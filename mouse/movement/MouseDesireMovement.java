package mouse.movement;

import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.ITile;
import mouse.action.Action;
import mouse.action.Wait;
import mouse.desire.Desire;
import mouse.movement.astar.MouseCheeseLover;

public class MouseDesireMovement extends MouseMovement {

	public Action nextAction() {
		Desire desireToAchieve = desires.peek().getDesire();
		if (desireToAchieve == Desire.Rest)
			return new Wait();
		else if (desireToAchieve == Desire.Walk)
			return MouseSprinter.nextAction(orientation, position, history.lastElement());
		else if (desireToAchieve == Desire.Cheese)
			return MouseCheeseLover.nextAction(position, history.lastElement());
		else
			return new Wait();
	}
	
	protected static ITile east(IPosition position, IBoard board) {
		return board.getTile(position.getX() - 1, position.getY());
	}
	
	protected static ITile north(IPosition position, IBoard board) {
		return board.getTile(position.getX() - 1, position.getY());
	}
	
	protected static ITile south(IPosition position, IBoard board) {
		return board.getTile(position.getX() - 1, position.getY());
	}
	
	protected static ITile west(IPosition position, IBoard board) {
		return board.getTile(position.getX() - 1, position.getY());
	}
}
