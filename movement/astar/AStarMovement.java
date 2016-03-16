package movement.astar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.PriorityQueue;
import interfaces.Tile;
import movement.SortedMapSimpleEntry;

public abstract class AStarMovement {

	protected static ArrayList<Tile> AStarSearch(Tile position, Tile target) {
		// The set of nodes already evaluated.
		HashSet<Tile> closedSet = new HashSet<Tile>();

		// The set of currently discovered nodes still to be evaluated.
		// Initially, only the start node is known.
		HashSet<Tile> openSet = new HashSet<Tile>();
		openSet.add(position);

		// For each node, which node it can most efficiently be reached from.
		// If a node can be reached from many nodes, cameFrom will eventually
		// contain the
		// most efficient previous step.
		Hashtable<Tile, Tile> cameFrom = new Hashtable<Tile, Tile>();

		// For each node, the cost of getting from the start node to that node.
		Hashtable<Tile, Integer> gScore = new Hashtable<Tile, Integer>();
		// The cost of going from start to start is zero.
		gScore.put(position, 0);
		// For each node, the total cost of getting from the start node to the
		// goal
		// by passing by that node. That value is partly known, partly
		// heuristic.
		PriorityQueue<SortedMapSimpleEntry<Tile, Integer>> fScore = new PriorityQueue<SortedMapSimpleEntry<Tile, Integer>>();
		// For the first node, that value is completely heuristic.
		fScore.add(new SortedMapSimpleEntry<Tile, Integer>(position,
				gScore.get(position) + manhattanDistance(position, target)));

		while (!openSet.isEmpty()) {
			Tile current = fScore.poll().getKey();
			if (current == target)
				return getPath(cameFrom, target);

			openSet.remove(current);
			closedSet.add(current);
			for (Tile neighbour : current.neighbours()) {
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
					fScore.add(new SortedMapSimpleEntry<Tile, Integer>(neighbour,
							gScore.get(neighbour) + manhattanDistance(neighbour, target)));
				}
			}
		}
		return null;
	}
	
	private static Integer moveToNeighbour(Tile neighbour) {
		return 1;
	}

	private static Integer manhattanDistance(Tile position, Tile target) {
		return Math.abs(position.getX() - target.getX()) + Math.abs(position.getY() - target.getY());
	}

	private static ArrayList<Tile> getPath(Hashtable<Tile, Tile> cameFrom, Tile current) {
		ArrayList<Tile> total_path = new ArrayList<Tile>();
		total_path.add(current);
		while (cameFrom.containsKey(current)) {
			current = cameFrom.get(current);
			total_path.add(current);
		}
		return total_path;
	}
}
