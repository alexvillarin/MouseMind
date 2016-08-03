package mouse.movement.desire;

import java.util.PriorityQueue;

import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.MouseType;
import interfaces.TileType;
import mouse.action.Action;
import mouse.desire.MouseDesire;
import mouse.movement.Direction;
import mouse.movement.MouseDesireMovement;

/*
 * Class that controls the movement of a mouse. The mouse always walks trying to keep its direction 
 */

public class MouseSprinter extends MouseDesireMovement {
	
	public MouseSprinter(PriorityQueue<MouseDesire> desires,
			IPosition position, Direction orientation, MouseType color) {
		super(desires, position, orientation, color);
	}

	public static Action nextAction(Direction orientation, IPosition position,
			IBoard board) {
		return sprint(orientation, position, board);
	}

	private static Action sprint(Direction orientation, IPosition position,
			IBoard board) {
		if ((orientation == Direction.EAST && east(position,
				board).getType() != TileType.OBSTACLE)
				|| (orientation == Direction.NORTH && north(
						position, board).getType() != TileType.OBSTACLE)
				|| (orientation == Direction.SOUTH && south(
						position, board).getType() != TileType.OBSTACLE)
				|| (orientation == Direction.WEST && west(
						position, board).getType() != TileType.OBSTACLE))
			return Action.move(orientation);
		else
			return Action.move(Direction.random());
	}
}
