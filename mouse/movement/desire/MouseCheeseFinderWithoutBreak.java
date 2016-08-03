package mouse.movement.desire;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import interfaces.EntityType;
import interfaces.IBoard;
import interfaces.IEntity;
import interfaces.IPosition;
import interfaces.ITile;
import interfaces.MouseType;
import interfaces.TileType;
import mouse.action.Action;
import mouse.desire.Desire;
import mouse.desire.MouseDesire;
import mouse.movement.Direction;
import mouse.movement.MouseDesireMovement;
import mouse.movement.astar.AStarMovement;

public class MouseCheeseFinderWithoutBreak extends MouseDesireMovement {

	public MouseCheeseFinderWithoutBreak(PriorityQueue<MouseDesire> desires, IPosition position, Direction orientation,
			MouseType color) {
		super(deleteNotBreak(desires), position, orientation, color);
	}

	public static Action nextAction(IPosition position, IBoard current, PriorityQueue<MouseDesire> desires) {
		ITile tile = current.getTile(position.getX(), position.getY());
		for (IEntity element : tile.getThings())
			if (element.getType() == EntityType.CHEESE)
				return Action.EAT;
		ArrayList<ITile> list = AStarMovement.AStarSearch(current.getTile(position.getX(), position.getY()),
				SearchCheese(current), current, deleteNotBreak(desires));
		ITile nextPosition = list.get(list.size() - 2);
		if (nextPosition.equals(east(position, current)))
			return Action.MOVE_EAST;
		else if (nextPosition.equals(north(position, current)))
			return Action.MOVE_NORTH;
		else if (nextPosition.equals(south(position, current)))
			return Action.MOVE_SOUTH;
		else if (nextPosition.equals(west(position, current)))
			return Action.MOVE_WEST;
		else
			return Action.WAIT;
	}

	private static ITile SearchCheese(IBoard board) {
		ArrayList<ITile> possibleDestinations = new ArrayList<ITile>();
		ArrayList<ITile> unknownDestinations = new ArrayList<ITile>();
		int height = board.getHeight();
		int width = board.getWidth();
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				ITile tile = board.getTile(i, j);
				List<IEntity> tileElements = tile.getThings();
				Iterator<IEntity> it = tileElements.iterator();
				while (it.hasNext())
					if (it.next().getType() == EntityType.CHEESE)
						return tile;
				if (tile.getType() != TileType.OBSTACLE)
					if (tile.getType() != TileType.UNKNOWN)
						possibleDestinations.add(tile);
					else
						unknownDestinations.add(tile);
			}

		// If the mouse can't see the cheese it will want to go to a random Tile
		if (possibleDestinations.size() > 0)
			return possibleDestinations.get((int) (Math.random() * (possibleDestinations.size() - 1)));
		return unknownDestinations.get((int) (Math.random() * (unknownDestinations.size() - 1)));
	}

	private static PriorityQueue<MouseDesire> deleteNotBreak(PriorityQueue<MouseDesire> desires) {
		desires.remove(new MouseDesire(Desire.NOT_BREAK, 0));
		return desires;
	}
}
