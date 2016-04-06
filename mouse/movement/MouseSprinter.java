package mouse.movement;

import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.TileType;
import mouse.action.Action;
import mouse.action.Move;

/*
 * Class that controls the movement of a mouse. The mouse always walks trying to keep its direction 
 */

public class MouseSprinter extends MouseDesireMovement {

	public static Action nextAction(Direction orientation, IPosition position, IBoard board) {
		return sprint(orientation, position, board);
	}
	
	private static Action sprint(Direction orientation, IPosition position, IBoard board) {
		if ((orientation == Direction.EAST && east(position, board).getType() != TileType.OBSTACLE)
				|| (orientation == Direction.NORTH && north(position, board).getType() != TileType.OBSTACLE)
				|| (orientation == Direction.SOUTH && south(position, board).getType() != TileType.OBSTACLE)
				|| (orientation == Direction.WEST && west(position, board).getType() != TileType.OBSTACLE))
			return new Move(orientation);
		else
			return new Move(Direction.random());
	}

}
