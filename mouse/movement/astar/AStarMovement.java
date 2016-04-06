package mouse.movement.astar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.PriorityQueue;

import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.ITile;
import mouse.movement.SortedMapSimpleEntry;

public abstract class AStarMovement {

	protected static ArrayList<ITile> AStarSearch(ITile position, ITile target, IBoard board) {
		// The set of nodes already evaluated.
		HashSet<ITile> closedSet = new HashSet<ITile>();

		// The set of currently discovered nodes still to be evaluated.
		// Initially, only the start node is known.
		HashSet<ITile> openSet = new HashSet<ITile>();
		openSet.add(position);

		// For each node, which node it can most efficiently be reached from.
		// If a node can be reached from many nodes, cameFrom will eventually
		// contain the
		// most efficient previous step.
		Hashtable<ITile, ITile> cameFrom = new Hashtable<ITile, ITile>();

		// For each node, the cost of getting from the start node to that node.
		Hashtable<ITile, Integer> gScore = new Hashtable<ITile, Integer>();
		// The cost of going from start to start is zero.
		gScore.put(position, 0);
		// For each node, the total cost of getting from the start node to the
		// goal
		// by passing by that node. That value is partly known, partly
		// heuristic.
		PriorityQueue<SortedMapSimpleEntry<ITile, Integer>> fScore = new PriorityQueue<SortedMapSimpleEntry<ITile, Integer>>();
		// For the first node, that value is completely heuristic.
		fScore.add(new SortedMapSimpleEntry<ITile, Integer>(position,
				gScore.get(position) + manhattanDistance(position, target)));

		while (!openSet.isEmpty()) {
			ITile current = fScore.poll().getKey();
			if (current == target)
				return getPath(cameFrom, target);

			openSet.remove(current);
			closedSet.add(current);
			for (ITile neighbour : neighbours(current.getPosition(), board)) {
				if (!closedSet.contains(neighbour)) {
					// The distance from start to goal passing through current
					// and the neighbour.
					int tentative_gScore = gScore.get(current) + moveToNeighbour(neighbour);
					if (!openSet.contains(neighbour)) // Discover a new node
						openSet.add(neighbour);
					else if (tentative_gScore >= gScore.get(neighbour))
						continue; // This is not a better path.

					// This path is the best until now. Record it!
					cameFrom.put(neighbour, current);
					gScore.put(neighbour, tentative_gScore);
					fScore.add(new SortedMapSimpleEntry<ITile, Integer>(neighbour,
							gScore.get(neighbour) + manhattanDistance(neighbour, target)));
				}
			}
		}
		return null;
	}

	private static Integer moveToNeighbour(ITile neighbour) {
		return 1;
	}

	private static Integer manhattanDistance(ITile position, ITile target) {
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

	protected static ITile[] neighbours(IPosition position, IBoard board) {
		ITile[] neighbours = { east(position, board), north(position, board), south(position, board),
				west(position, board) };
		return neighbours;
	}

	protected static ITile east(IPosition position, IBoard board) {
		return board.getTile(position.getX() - 1, position.getY());
	}

	protected static ITile north(IPosition position, IBoard board) {
		return board.getTile(position.getX() - 1, position.getY());
	}

	protected static ITile south(IPosition position, IBoard board) {
		return board.getTile(position.getX() - 1, position.getY());
	}

	protected static ITile west(IPosition position, IBoard board) {
		return board.getTile(position.getX() - 1, position.getY());
	}
}
