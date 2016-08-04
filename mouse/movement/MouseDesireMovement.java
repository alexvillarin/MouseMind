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

	public Action nextAction() {
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
		} else if (desireToAchieve.getDesire().equals(Desire.AVOID_PUNISHMENT)) {
			return desireAvoidPunishment();
		} else if (desireToAchieve.getDesire().equals(Desire.NOT_BREAK)) {
			return desireNotBreak();
		}
		return Action.WAIT;
	}

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
		return getActionForDesire(result.getKey(), result.getValue(), notBreak);

	}

	private Action desireWalk(MouseDesire desire) {
		Iterator<MouseDesire> it = desires.iterator();
		PriorityQueue<MouseDesire> list = new PriorityQueue<MouseDesire>();
		boolean notBreak = false;
		while (it.hasNext()) {
			MouseDesire next = it.next();
			Desire nextDesire = next.getDesire();
			if ((nextDesire.equals(Desire.CHEESE) || nextDesire.equals(Desire.SEE_BLUE)
					|| nextDesire.equals(Desire.SEE_GREEN) || nextDesire.equals(Desire.SEE_RED)
					|| nextDesire.equals(Desire.SEE_YELLOW) || nextDesire.equals(Desire.AVOID_PUNISHMENT))
					&& Math.abs(desire.getWeight() - next.getWeight()) < 50)
				list.add(next);
			else if (next.equals(new MouseDesire(Desire.NOT_BREAK, 0))
					&& Math.abs(desire.getWeight() - next.getWeight()) < 50)
				notBreak = true;
		}
		MouseDesire queueHead = list.peek();
		if (queueHead != null && queueHead.getDesire().equals(Desire.AVOID_PUNISHMENT)) {
			if (manhattanDistance(position, home) >= turnsLeft)
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
			return getActionForDesire(result.getKey(), result.getValue(), notBreak);
		} else
			return MouseSprinter.nextAction(orientation, position, history.lastElement());
	}

	private Action desireAvoidPunishment() {
		MouseDesire avoidPunishment = desires.poll();
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
						&& Math.abs(avoidPunishment.getWeight() - next.getWeight()) < 50)
					list.add(next);
				else if (next.equals(new MouseDesire(Desire.NOT_BREAK, 0))
						&& Math.abs(avoidPunishment.getWeight() - next.getWeight()) < 50)
					notBreak = true;
			}
			if (manhattanDistance(position, home) >= turnsLeft)
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

	private Action desireNotBreak() {
		Iterator<MouseDesire> it = desires.iterator();
		PriorityQueue<MouseDesire> list = new PriorityQueue<MouseDesire>();
		boolean walk = false;
		while (it.hasNext()) {
			MouseDesire next = it.next();
			Desire nextDesire = next.getDesire();
			if (nextDesire.equals(Desire.CHEESE) || nextDesire.equals(Desire.SEE_BLUE)
					|| nextDesire.equals(Desire.SEE_GREEN) || nextDesire.equals(Desire.SEE_RED)
					|| nextDesire.equals(Desire.SEE_YELLOW) || nextDesire.equals(Desire.AVOID_PUNISHMENT))
				list.add(next);
			else if (next.equals(new MouseDesire(Desire.WALK, 0)))
				walk = true;
		}
		MouseDesire queueHead = list.peek();
		if (queueHead != null && queueHead.getDesire().equals(Desire.AVOID_PUNISHMENT)) {
			if (manhattanDistance(position, home) >= turnsLeft)
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
					typeToSearch = EntityType.MIRROR;
				else
					typeToSearch = EntityType.MOUSE_BLUE;

			else if (next.getDesire().equals(Desire.SEE_GREEN))
				if (color.equals(MouseType.GREEN))
					typeToSearch = EntityType.MIRROR;
				else
					typeToSearch = EntityType.MOUSE_GREEN;

			else if (next.getDesire().equals(Desire.SEE_RED))
				if (color.equals(MouseType.RED))
					typeToSearch = EntityType.MIRROR;
				else
					typeToSearch = EntityType.MOUSE_RED;

			else if (next.getDesire().equals(Desire.SEE_YELLOW))
				if (color.equals(MouseType.YELLOW))
					typeToSearch = EntityType.MIRROR;
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

	private double getNextCost(int weight, int md) {
		IBoard board = history.lastElement();
		int maxMD = board.getWidth() + board.getHeight();
		return (100 - weight) / 100 * 0.6 + md / maxMD * 0.4;
	}

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
		ITile finalTile = it.next();
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

	private static Integer manhattanDistance(IPosition position, ITile target) {
		return Math.abs(position.getX() - target.getPosition().getX())
				+ Math.abs(position.getY() - target.getPosition().getY());
	}

	private int manhattanDistance(IPosition pos1, IPosition pos2) {
		return Math.abs(pos1.getX() - pos2.getX()) + Math.abs(pos1.getY() - pos2.getY());
	}

	public void observe(IBoard board, MouseType mouse, Action action, Boolean success, int go) {
		history.add(board);
		eventHistory.add(new Event(mouse, action, 100, success, go));
		if (success && mouse.equals(color)) {
			if (action.equals(Action.EAT))
				desires.remove(new MouseDesire(Desire.CHEESE, 0));
			else if (action.equals(Action.MOVE_EAST)) {
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
