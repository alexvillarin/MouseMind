package mouse.movement.astar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;

import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.ITile;
import interfaces.TileType;
import mouse.movement.SortedMapSimpleEntry;

public class AStarMovement {

	public static ArrayList<ITile> AStarSearch(ITile position, ITile target, IBoard board) {
		int boardSize = board.getHeight() * board.getWidth();
		// The set of nodes already evaluated.
		HashSet<ITile> closedSet = new HashSet<ITile>();

		// The set of currently discovered nodes still to be evaluated.
		// Initially, only the start node is known.
		HashSet<ITile> openSet = new HashSet<ITile>();
		openSet.add(position);

		// For each node, which node it can most efficiently be reached from.
		// If a node can be reached from many nodes, cameFrom will eventually
		// contain the most efficient previous step.
		Hashtable<ITile, ITile> cameFrom = new Hashtable<ITile, ITile>();

		// For each node, the cost of getting from the start node to that node.
		Hashtable<ITile, Integer> gScore = new Hashtable<ITile, Integer>();
		// The cost of going from start to start is zero.
		gScore.put(position, 0);
		// For each node, the total cost of getting from the start node to the
		// goal by passing by that node. That value is partly known, partly
		// heuristic.
		PriorityQueue<SortedMapSimpleEntry<ITile, Integer>> fScore = new PriorityQueue<SortedMapSimpleEntry<ITile, Integer>>();
		// For the first node, that value is completely heuristic.
		Integer gScorePos = gScore.get(position);
		gScorePos = (gScorePos != null) ? gScorePos : boardSize;
		fScore.add(new SortedMapSimpleEntry<ITile, Integer>(position, gScorePos + manhattanDistance(position, target)));

		while (!openSet.isEmpty()) {
			ITile current = fScore.poll().getKey();
			if (current.equals(target))
				return getPath(cameFrom, target);

			openSet.remove(current);
			closedSet.add(current);
			for (ITile neighbour : neighbours(current.getPosition(), board)) {
				if (neighbour != null && !closedSet.contains(neighbour)) {
					// The distance from start to goal passing through current
					// and the neighbour.
					int tentative_gScore = gScore.get(current) + moveToNeighbour(neighbour, boardSize);
					if (!openSet.contains(neighbour)) // Discover a new node
						openSet.add(neighbour);
					else if (tentative_gScore >= gScore.get(neighbour))
						continue;

					cameFrom.put(neighbour, current);
					gScore.put(neighbour, tentative_gScore);
					fScore.add(new SortedMapSimpleEntry<ITile, Integer>(neighbour,
							gScore.get(neighbour) + manhattanDistance(neighbour, target)));
				}
			}
		}
		return null;
	}

	private static int moveToNeighbour(ITile neighbour, int boardSize) {
		if (neighbour.getType().equals(TileType.OBSTACLE))
			return 2 * boardSize;
		else
			return 1;
	}

	public static int manhattanDistance(ITile position, ITile target) {
		if (position == null || target == null)
			return Integer.MAX_VALUE;
		else
			return Math.abs(position.getPosition().getX() - target.getPosition().getX())
					+ Math.abs(position.getPosition().getY() - target.getPosition().getY());
	}

	private static ArrayList<ITile> getPath(Hashtable<ITile, ITile> cameFrom, ITile current) {
		ArrayList<ITile> total_path = new ArrayList<ITile>();
		total_path.add(current);
		while (cameFrom.containsKey(current)) {
			current = cameFrom.get(current);
			total_path.add(current);
		}
		return total_path;
	}

	private static ITile[] neighbours(IPosition position, IBoard board) {
		ITile[] neighbours = { east(position, board), north(position, board), south(position, board),
				west(position, board) };
		return neighbours;
	}

	private static ITile east(IPosition position, IBoard board) {
		return board.getTile(position.getX(), position.getY() + 1);
	}

	private static ITile north(IPosition position, IBoard board) {
		return board.getTile(position.getX() - 1, position.getY());
	}

	private static ITile south(IPosition position, IBoard board) {
		return board.getTile(position.getX() + 1, position.getY());
	}

	private static ITile west(IPosition position, IBoard board) {
		return board.getTile(position.getX(), position.getY() - 1);
	}

	// Function for testing purposes. It isn't used
	public static void printPath(PriorityQueue<SortedMapSimpleEntry<ITile, Integer>> fScore) {
		Iterator<SortedMapSimpleEntry<ITile, Integer>> it = fScore.iterator();
		while (it.hasNext()) {
			SortedMapSimpleEntry<ITile, Integer> next = it.next();
			System.out.println(next);
		}
	}

	// Function for testing purposes. It isn't used
	public static void printPathSorted(PriorityQueue<SortedMapSimpleEntry<ITile, Integer>> fScore) {
		PriorityQueue<SortedMapSimpleEntry<ITile, Integer>> copy = new PriorityQueue<SortedMapSimpleEntry<ITile, Integer>>();
		while (!fScore.isEmpty()) {
			SortedMapSimpleEntry<ITile, Integer> next = fScore.remove();
			copy.add(next);
			System.out.println(next);
		}
		System.out.println();
		while (!copy.isEmpty())
			fScore.add(copy.remove());
	}
}
