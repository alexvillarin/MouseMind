package mouse.movement;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import event.Event;
import interfaces.EntityType;
import interfaces.IBoard;
import interfaces.IEntity;
import interfaces.IPosition;
import interfaces.ITile;
import interfaces.MouseType;
import mouse.action.Action;
import mouse.desire.Desire;
import mouse.desire.MouseDesire;
import mouse.movement.astar.AStarMovement;
import mouse.movement.astar.AStarMovementWithoutBreak;
import mouse.movement.desire.MouseSprinter;
import mouse.movement.desire.MouseSprinterWithoutBreak;

/*
 * This class must manage the actions of a mouse based on its desires. It implements
 * the abstract class MouseMovement
 */
public class MouseDesireMovement extends MouseMovement {

	private PriorityQueue<MouseDesire> desires;
	private int turnsLeft;
	private IPosition home;

	public MouseDesireMovement(PriorityQueue<MouseDesire> desires, IPosition position, Direction orientation,
			MouseType color, int turnsLeft) {
		super(position, orientation, color);
		this.desires = desires;
		this.turnsLeft = turnsLeft;
		this.home = position;
	}

	// Returns the next action that the mouse must execute in order to satisfy
	// its desires
	public Action nextAction() {
		turnsLeft--;
		MouseDesire desireToAchieve = desires.peek();
		if (desireToAchieve == null || desireToAchieve.getDesire().equals(Desire.REST))
			return Action.WAIT;
		else if (desireToAchieve.getDesire().equals(Desire.CHEESE)
				|| desireToAchieve.getDesire().equals(Desire.SEE_BLUE)
				|| desireToAchieve.getDesire().equals(Desire.SEE_GREEN)
				|| desireToAchieve.getDesire().equals(Desire.SEE_RED)
				|| desireToAchieve.getDesire().equals(Desire.SEE_YELLOW)) {
			return desireSet(desireToAchieve);
		} else if (desireToAchieve.getDesire().equals(Desire.WALK)) {
			return desireWalk(desireToAchieve);
		} else if (desireToAchieve.getDesire().equals(Desire.GO_BACK_HOME)) {
			return desireGoBackHome();
		} else if (desireToAchieve.getDesire().equals(Desire.NOT_BREAK)) {
			return desireNotBreak();
		}
		return Action.WAIT;
	}

	// The desire with the highest weight is one of the following: CHEESE,
	// SEE_BLUE, SEE_GREEN, SEE_RED, SEE_YELLOW. The mouse have to find where is
	// the entity that satisfies its desire and execute the action related.
	private Action desireSet(MouseDesire desire) {
		Iterator<MouseDesire> it = desires.iterator();
		ArrayList<MouseDesire> list = new ArrayList<MouseDesire>();
		boolean notBreak = false;
		while (it.hasNext()) {
			MouseDesire next = it.next();
			Desire nextDesire = next.getDesire();
			if ((nextDesire.equals(Desire.CHEESE) || nextDesire.equals(Desire.SEE_BLUE)
					|| nextDesire.equals(Desire.SEE_GREEN) || nextDesire.equals(Desire.SEE_RED)
					|| nextDesire.equals(Desire.SEE_YELLOW)) && Math.abs(desire.getWeight() - next.getWeight()) < 25)
				list.add(next);
			else if (next.equals(new MouseDesire(Desire.NOT_BREAK, 0))
					&& Math.abs(desire.getWeight() - next.getWeight()) < 25)
				notBreak = true;
		}
		AbstractMap.SimpleEntry<Desire, ITile> result = getBestDesire(list);
		if (result.getValue() == null) {
			desires.remove(new MouseDesire(result.getKey(), 0));
			return nextAction();
		} else
			return getActionForDesire(result.getKey(), result.getValue(), notBreak);

	}

	// The desire with the highest weight is WALK. The mouse have to check if it
	// can satisfy another desire at the same time. If it can satisfy more than
	// one desire it will execute the corresponding action
	private Action desireWalk(MouseDesire desire) {
		Iterator<MouseDesire> it = desires.iterator();
		PriorityQueue<MouseDesire> list = new PriorityQueue<MouseDesire>();
		boolean notBreak = false;
		while (it.hasNext()) {
			MouseDesire next = it.next();
			Desire nextDesire = next.getDesire();
			if ((nextDesire.equals(Desire.CHEESE) || nextDesire.equals(Desire.SEE_BLUE)
					|| nextDesire.equals(Desire.SEE_GREEN) || nextDesire.equals(Desire.SEE_RED)
					|| nextDesire.equals(Desire.SEE_YELLOW) || nextDesire.equals(Desire.GO_BACK_HOME))
					&& Math.abs(desire.getWeight() - next.getWeight()) < 50)
				list.add(next);
			else if (next.equals(new MouseDesire(Desire.NOT_BREAK, 0))
					&& Math.abs(desire.getWeight() - next.getWeight()) < 50)
				notBreak = true;
		}
		MouseDesire queueHead = list.peek();
		if (queueHead != null && queueHead.getDesire().equals(Desire.GO_BACK_HOME)) {
			if (manhattanDistance(position, home) >= turnsLeft + 1)
				return goHome(notBreak);
			else {
				list.remove();
				if (!list.isEmpty()) {
					ArrayList<MouseDesire> listArray = new ArrayList<MouseDesire>(list);
					AbstractMap.SimpleEntry<Desire, ITile> result = getBestDesire(listArray);
					return getActionForDesire(result.getKey(), result.getValue(), notBreak);
				} else
					return MouseSprinter.nextAction(orientation, position, history.lastElement());
			}
		} else if (!list.isEmpty()) {
			ArrayList<MouseDesire> listArray = new ArrayList<MouseDesire>(list);
			AbstractMap.SimpleEntry<Desire, ITile> result = getBestDesire(listArray);
			if (result.getValue() != null)
				return getActionForDesire(result.getKey(), result.getValue(), notBreak);
			else {
				while (!list.isEmpty())
					desires.remove(list.poll());
				return goHome(notBreak);
			}
		} else
			return MouseSprinter.nextAction(orientation, position, history.lastElement());
	}

	// The desire with the highest weight is GO_BACK_HOME. The mouse can satisfy
	// other desires while the number of turns reamining are enough to arrive to
	// the initial tile.
	private Action desireGoBackHome() {
		MouseDesire goBackHome = desires.poll();
		MouseDesire secondDesire = desires.peek();
		if (secondDesire == null || secondDesire.equals(Desire.REST))
			return Action.WAIT;
		else {
			Iterator<MouseDesire> it = desires.iterator();
			ArrayList<MouseDesire> list = new ArrayList<MouseDesire>();
			boolean notBreak = false;
			boolean walk = false;
			while (it.hasNext()) {
				MouseDesire next = it.next();
				Desire nextDesire = next.getDesire();
				if ((nextDesire.equals(Desire.CHEESE) || nextDesire.equals(Desire.SEE_BLUE)
						|| nextDesire.equals(Desire.SEE_GREEN) || nextDesire.equals(Desire.SEE_RED)
						|| nextDesire.equals(Desire.SEE_YELLOW))
						&& Math.abs(goBackHome.getWeight() - next.getWeight()) < 50)
					list.add(next);
				else if (next.equals(new MouseDesire(Desire.NOT_BREAK, 0))
						&& Math.abs(goBackHome.getWeight() - next.getWeight()) < 50)
					notBreak = true;
			}
			if (manhattanDistance(position, home) >= turnsLeft + 1)
				return goHome(notBreak);
			else if (!list.isEmpty()) {
				AbstractMap.SimpleEntry<Desire, ITile> result = getBestDesire(list);
				return getActionForDesire(result.getKey(), result.getValue(), notBreak);
			} else if (walk)
				if (notBreak)
					return MouseSprinterWithoutBreak.nextAction(orientation, position, history.lastElement());
				else
					return MouseSprinter.nextAction(orientation, position, history.lastElement());
			else
				return Action.WAIT;
		}
	}

	// The desire with the highest weight is NOT_BREAK. The mouse can move over
	// the board without breaking shojis. It will try to satisfy other desires
	// avoiding not broken shojis.
	private Action desireNotBreak() {
		Iterator<MouseDesire> it = desires.iterator();
		PriorityQueue<MouseDesire> list = new PriorityQueue<MouseDesire>();
		boolean walk = false;
		while (it.hasNext()) {
			MouseDesire next = it.next();
			Desire nextDesire = next.getDesire();
			if (nextDesire.equals(Desire.CHEESE) || nextDesire.equals(Desire.SEE_BLUE)
					|| nextDesire.equals(Desire.SEE_GREEN) || nextDesire.equals(Desire.SEE_RED)
					|| nextDesire.equals(Desire.SEE_YELLOW) || nextDesire.equals(Desire.GO_BACK_HOME))
				list.add(next);
			else if (next.equals(new MouseDesire(Desire.WALK, 0)))
				walk = true;
		}
		MouseDesire queueHead = list.peek();
		if (queueHead != null && queueHead.getDesire().equals(Desire.GO_BACK_HOME)) {
			if (manhattanDistance(position, home) >= turnsLeft + 1)
				return goHome(true);
			else {
				list.remove();
				if (!list.isEmpty()) {
					ArrayList<MouseDesire> listArray = new ArrayList<MouseDesire>(list);
					AbstractMap.SimpleEntry<Desire, ITile> result = getBestDesire(listArray);
					return getActionForDesire(result.getKey(), result.getValue(), true);
				} else
					return Action.WAIT;
			}
		} else if (!list.isEmpty()) {
			ArrayList<MouseDesire> listArray = new ArrayList<MouseDesire>(list);
			AbstractMap.SimpleEntry<Desire, ITile> result = getBestDesire(listArray);
			return getActionForDesire(result.getKey(), result.getValue(), true);
		} else if (walk)
			return MouseSprinterWithoutBreak.nextAction(orientation, position, history.lastElement());
		else
			return Action.WAIT;
	}

	// Gets the best desire to achieve and the Tile where the action related
	// must be performed. The possible desires are : CHEESE, SEE_BLUE,
	// SEE_GREEN, SEE_RED, SEE_YELLOW.
	private AbstractMap.SimpleEntry<Desire, ITile> getBestDesire(ArrayList<MouseDesire> list) {
		// List could not be empty because we added the initial desire
		Iterator<MouseDesire> it = list.iterator();
		AbstractMap.SimpleEntry<Desire, ITile> result = null;
		double cost = Integer.MAX_VALUE;
		while (it.hasNext()) {
			MouseDesire next = it.next();
			ITile nextTile;
			double nextCost = Integer.MAX_VALUE;
			EntityType typeToSearch = null;

			if (next.getDesire().equals(Desire.CHEESE))
				typeToSearch = EntityType.CHEESE;

			else if (next.getDesire().equals(Desire.SEE_BLUE))
				if (color.equals(MouseType.BLUE))
					typeToSearch = EntityType.TOY;
				else
					typeToSearch = EntityType.MOUSE_BLUE;

			else if (next.getDesire().equals(Desire.SEE_GREEN))
				if (color.equals(MouseType.GREEN))
					typeToSearch = EntityType.TOY;
				else
					typeToSearch = EntityType.MOUSE_GREEN;

			else if (next.getDesire().equals(Desire.SEE_RED))
				if (color.equals(MouseType.RED))
					typeToSearch = EntityType.TOY;
				else
					typeToSearch = EntityType.MOUSE_RED;

			else if (next.getDesire().equals(Desire.SEE_YELLOW))
				if (color.equals(MouseType.YELLOW))
					typeToSearch = EntityType.TOY;
				else
					typeToSearch = EntityType.MOUSE_YELLOW;

			nextTile = searchEntity(history.lastElement(), typeToSearch);
			nextCost = getNextCost(next.getWeight(), manhattanDistance(position, nextTile));

			if (nextCost < cost) {
				cost = nextCost;
				result = new AbstractMap.SimpleEntry<Desire, ITile>(next.getDesire(), nextTile);
			}
		}
		return result;
	}

	// Get the weighted cost of a desire. This cost depends on the weight of the
	// desire and the cost of perform the action. The cost is calculated by the
	// distance to the tile where the mouse must perform the action.
	private double getNextCost(int weight, int md) {
		IBoard board = history.firstElement();
		int maxMD = board.getWidth() + board.getHeight();
		return (100 - weight) / 100 * 0.6 + md / maxMD * 0.4;
	}

	// Returns the nearest tile where is located the entity searched.
	private ITile searchEntity(IBoard board, EntityType entity) {
		ArrayList<ITile> possibleDestinations = new ArrayList<ITile>();
		int height = board.getHeight();
		int width = board.getWidth();
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				ITile tile = board.getTile(i, j);
				List<IEntity> tileElements = tile.getThings();
				Iterator<IEntity> it = tileElements.iterator();
				while (it.hasNext())
					if (it.next().getType() == entity)
						possibleDestinations.add(tile);
			}

		Iterator<ITile> it = possibleDestinations.iterator();
		int distance = Integer.MAX_VALUE;
		ITile finalTile = null;
		while (it.hasNext()) {
			ITile next = it.next();
			int nextDistance = manhattanDistance(position, next);
			if (nextDistance < distance) {
				distance = nextDistance;
				finalTile = next;
			}
		}
		return finalTile;
	}

	// Returns the action related to the desire that we want to achieve
	private Action getActionForDesire(Desire desire, ITile tile, boolean notBreak) {
		if (manhattanDistance(position, tile) > 0) {
			IBoard current = history.lastElement();
			ArrayList<ITile> list;
			if (notBreak)
				list = AStarMovementWithoutBreak.AStarSearch(current.getTile(position), tile, current);
			else
				list = AStarMovement.AStarSearch(current.getTile(position), tile, current);
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
		} else if (desire.equals(Desire.CHEESE))
			return Action.EAT;
		else // We are with the mouse we want to talk
			return Action.TALK;
	}

	// When the game is about to end the mouse might want to go back to the
	// initial tile. This method get the best action to go to the initial tile.
	private Action goHome(boolean notBreak) {
		ITile homeTile = history.lastElement().getTile(home);
		if (manhattanDistance(position, home) > 0) {
			IBoard current = history.lastElement();
			ArrayList<ITile> list;
			if (notBreak)
				list = AStarMovementWithoutBreak.AStarSearch(current.getTile(position), homeTile, current);
			else
				list = AStarMovement.AStarSearch(current.getTile(position), homeTile, current);
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
		} else
			return Action.WAIT;
	}

	public static int manhattanDistance(IPosition position, ITile target) {
		if (position == null || target == null)
			return Integer.MAX_VALUE;
		else
			return Math.abs(position.getX() - target.getPosition().getX())
					+ Math.abs(position.getY() - target.getPosition().getY());
	}

	public static int manhattanDistance(IPosition pos1, IPosition pos2) {
		if (pos1 == null || pos2 == null)
			return Integer.MAX_VALUE;
		return Math.abs(pos1.getX() - pos2.getX()) + Math.abs(pos1.getY() - pos2.getY());
	}

	// The mouse observes an action performed. If a objective was achieved the
	// desire must be remove from the list of desires.
	public void observe(IBoard board, MouseType mouse, Action action, Boolean success, int go) {
		history.add(board);
		eventHistory.add(new Event(mouse, action, 100, success, go));
		if (success && mouse.equals(color)) {
			if (action.equals(Action.EAT))
				desires.remove(new MouseDesire(Desire.CHEESE, 0));
			else if (action.equals(Action.TALK)) {
				Iterator<IEntity> it = board.getTile(this.position).getThings().iterator();
				while (it.hasNext()) {
					IEntity next = it.next();
					if (next.getType().equals(EntityType.MOUSE_BLUE) && !color.equals(MouseType.BLUE))
						desires.remove(new MouseDesire(Desire.SEE_BLUE, 0));
					else if (next.getType().equals(EntityType.MOUSE_GREEN) && !color.equals(MouseType.GREEN))
						desires.remove(new MouseDesire(Desire.SEE_GREEN, 0));
					else if (next.getType().equals(EntityType.MOUSE_RED) && !color.equals(MouseType.RED))
						desires.remove(new MouseDesire(Desire.SEE_RED, 0));
					else if (next.getType().equals(EntityType.MOUSE_YELLOW) && !color.equals(MouseType.YELLOW))
						desires.remove(new MouseDesire(Desire.SEE_YELLOW, 0));
				}
				desires.remove(new MouseDesire(Desire.CHEESE, 0));
			} else if (action.equals(Action.MOVE_EAST)) {
				position = position.east(history.lastElement());
				orientation = Direction.EAST;
			} else if (action.equals(Action.MOVE_NORTH)) {
				position = position.north(history.lastElement());
				orientation = Direction.NORTH;
			} else if (action.equals(Action.MOVE_SOUTH)) {
				position = position.south(history.lastElement());
				orientation = Direction.SOUTH;
			} else if (action.equals(Action.MOVE_WEST)) {
				position = position.west(history.lastElement());
				orientation = Direction.WEST;
			}
		}
	}
}
