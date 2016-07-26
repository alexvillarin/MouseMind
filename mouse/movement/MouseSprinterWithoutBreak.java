package mouse.movement;

import java.util.HashSet;

import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.TileType;
import mouse.action.Action;
import mouse.action.Move;
import mouse.action.Wait;

public class MouseSprinterWithoutBreak extends MouseDesireMovement {

	public static Action nextAction(Direction orientation, IPosition position, IBoard board) {
		HashSet<Direction> possibleDirections = new HashSet<Direction>();
		for (Direction dir : Direction.values())
			possibleDirections.add(dir);
		return sprint(orientation, position, board, possibleDirections);
	}
	
	private static Action sprint(Direction orientation, IPosition position, IBoard board, HashSet<Direction> possibleDirections) {
		possibleDirections.remove(orientation);
		TileType east = east(position, board).getType(),
				north = north(position, board).getType(),
				south = south(position, board).getType(),
				west = west(position, board).getType();
		if ((orientation == Direction.EAST && east != TileType.OBSTACLE && east != TileType.SHOJI)
				|| (orientation == Direction.NORTH && north != TileType.OBSTACLE && north != TileType.SHOJI)
				|| (orientation == Direction.SOUTH && south != TileType.OBSTACLE && south != TileType.SHOJI)
				|| (orientation == Direction.WEST && west != TileType.OBSTACLE && west != TileType.SHOJI))
			return new Move(orientation);
		else {
			if (possibleDirections.size() > 0) {
				Direction newOrientation = Direction.random(possibleDirections.toArray(Direction.values())); 
				return sprint(newOrientation, position, board, possibleDirections);
			} else
				return new Wait();
		}
	}

}
