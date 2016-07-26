package mouse.movement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import interfaces.EntityType;
import interfaces.IBoard;
import interfaces.IEntity;
import interfaces.IPosition;
import interfaces.ITile;
import interfaces.TileType;
import mouse.action.Action;
import mouse.action.Eat;
import mouse.action.Move;
import mouse.action.Wait;
import mouse.desire.MouseDesire;
import mouse.movement.astar.AStarMovement;

public class MouseCheeseFinder extends MouseDesireMovement {

	public static Action nextAction(IPosition position, IBoard current, PriorityQueue<MouseDesire> desires) {
		ITile tile = current.getTile(position.getX(), position.getY());
		for (IEntity element : tile.getThings())
			if (element.getType() == EntityType.CHEESE)
				return new Eat();
		ArrayList<ITile> list = AStarMovement.AStarSearch(current.getTile(position.getX(), position.getY()),
				SearchCheese(current), current, desires);
		ITile nextPosition = list.get(list.size() - 2);
		if (nextPosition.equals(east(position, current)))
			return new Move(Direction.EAST);
		else if (nextPosition.equals(north(position, current)))
			return new Move(Direction.NORTH);
		else if (nextPosition.equals(south(position, current)))
			return new Move(Direction.SOUTH);
		else if (nextPosition.equals(west(position, current)))
			return new Move(Direction.WEST);
		else
			return new Wait();
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
}
