package mouse.movement.desire;

import java.util.PriorityQueue;

import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.ITile;
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

	public MouseSprinter(PriorityQueue<MouseDesire> desires, IPosition position, Direction orientation,
			MouseType color, int turnsLeft) {
		super(desires, position, orientation, color, turnsLeft);
	}

	public static Action nextAction(Direction orientation, IPosition position, IBoard board) {
		return sprint(orientation, position, board);
	}

	private static Action sprint(Direction orientation, IPosition position, IBoard board) {
		ITile eastTile = east(position, board), northTile = north(position, board), southTile = south(position, board),
				westTile = west(position, board);
		TileType east = (eastTile != null) ? eastTile.getType() : TileType.OBSTACLE,
				north = (northTile != null) ? northTile.getType() : TileType.OBSTACLE,
				south = (southTile != null) ? southTile.getType() : TileType.OBSTACLE,
				west = (westTile != null) ? westTile.getType() : TileType.OBSTACLE;
		if ((orientation == Direction.EAST && east != TileType.OBSTACLE)
				|| (orientation == Direction.NORTH && north != TileType.OBSTACLE)
				|| (orientation == Direction.SOUTH && south != TileType.OBSTACLE)
				|| (orientation == Direction.WEST && west != TileType.OBSTACLE))
			return Action.move(orientation);
		else
			return Action.move(Direction.random());
	}
}
