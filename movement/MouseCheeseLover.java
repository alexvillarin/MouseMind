package movement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

import actions.Action;
import actions.Eat;
import actions.Move;
import ai.Desire;
import interfaces.Board;
import interfaces.Tile;

public class MouseCheeseLover extends MouseAStar {

	public Action nextAction(HashSet<Desire> desires, HashSet<Desire> hates, Tile position, Direction orientation,
			Vector<Board> history) {
		if (position.hasCheese())
			return new Eat();
		Board current = history.lastElement();
		ArrayList<Tile> list = AStarSearch(position, SearchTarget(current.board));
		Tile nextPosition = list.get(list.size() - 2);
		if (nextPosition.equals(position.East()))
			return new Move(Direction.EAST);
		else if (nextPosition.equals(position.North()))
			return new Move(Direction.NORTH);
		else if (nextPosition.equals(position.South()))
			return new Move(Direction.SOUTH);
		else if (nextPosition.equals(position.West()))
			return new Move(Direction.WEST);
		else return null;
	}
	
	private Tile SearchTarget(Tile[][] board) {
		return null;
	}

}
