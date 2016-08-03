package mouse.movement.desire;

import java.util.HashSet;
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

public class MouseSprinterWithoutBreak extends MouseDesireMovement {

	public MouseSprinterWithoutBreak(PriorityQueue<MouseDesire> desires, IPosition position, Direction orientation,
			MouseType color) {
		super(desires, position, orientation, color);
	}

	public static Action nextAction(Direction orientation, IPosition position, IBoard board) {
		HashSet<Direction> possibleDirections = new HashSet<Direction>();
		for (Direction dir : Direction.values())
			possibleDirections.add(dir);
		return sprint(orientation, position, board, possibleDirections);
	}

	private static Action sprint(Direction orientation, IPosition position, IBoard board,
			HashSet<Direction> possibleDirections) {
		possibleDirections.remove(orientation);
		ITile eastTile = east(position, board), northTile = north(position, board), southTile = south(position, board),
				westTile = west(position, board);
		TileType east = (eastTile != null) ? eastTile.getType() : TileType.OBSTACLE,
				north = (northTile != null) ? northTile.getType() : TileType.OBSTACLE,
				south = (southTile != null) ? southTile.getType() : TileType.OBSTACLE,
				west = (westTile != null) ? westTile.getType() : TileType.OBSTACLE;
		if ((orientation == Direction.EAST && east != TileType.OBSTACLE && east != TileType.SHOJI)
				|| (orientation == Direction.NORTH && north != TileType.OBSTACLE && north != TileType.SHOJI)
				|| (orientation == Direction.SOUTH && south != TileType.OBSTACLE && south != TileType.SHOJI)
				|| (orientation == Direction.WEST && west != TileType.OBSTACLE && west != TileType.SHOJI))
			return Action.move(orientation);
		else {
			if (possibleDirections.size() > 0) {
				Direction newOrientation = Direction.random(possibleDirections.toArray(Direction.values()));
				return sprint(newOrientation, position, board, possibleDirections);
			} else
				return Action.WAIT;
		}
	}

}
