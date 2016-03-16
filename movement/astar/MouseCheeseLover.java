package movement.astar;

import java.util.ArrayList;
import java.util.Vector;

import actions.Action;
import actions.Eat;
import actions.Move;
import actions.Wait;
import interfaces.Board;
import interfaces.Tile;
import movement.Direction;

public class MouseCheeseLover extends AStarMovement {

	public static Action nextAction(Tile position, Vector<Board> history) {
		if (position.hasCheese())
			return new Eat();
		Board current = history.lastElement();
		ArrayList<Tile> list = AStarSearch(position, SearchCheese(current.getBoard()));
		Tile nextPosition = list.get(list.size() - 2);
		if (nextPosition.equals(position.East()))
			return new Move(Direction.EAST);
		else if (nextPosition.equals(position.North()))
			return new Move(Direction.NORTH);
		else if (nextPosition.equals(position.South()))
			return new Move(Direction.SOUTH);
		else if (nextPosition.equals(position.West()))
			return new Move(Direction.WEST);
		else
			return new Wait();
	}
	
	private static Tile SearchCheese(Tile[][] board) {
		ArrayList<Tile> possibleDestinations = new ArrayList<Tile>();
		for (Tile[] boardRow : board)
			for (Tile tile : boardRow)
				if (tile.hasCheese())
					return tile;
				else
					possibleDestinations.add(tile);
		// If the mouse can't see the cheese it will want to go to a random Tile
		return possibleDestinations.get((int) (Math.random() * (possibleDestinations.size() - 1)));
	}
}
