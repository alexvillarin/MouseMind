package mouse.movement;

import java.util.Iterator;
import java.util.PriorityQueue;

import event.Event;
import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.ITile;
import interfaces.MouseType;
import mouse.action.Action;
import mouse.desire.Desire;
import mouse.desire.MouseDesire;
import mouse.movement.desire.MouseCheeseFinder;
import mouse.movement.desire.MouseCheeseFinderWithoutBreak;
import mouse.movement.desire.MouseSprinter;
import mouse.movement.desire.MouseSprinterWithoutBreak;

public class MouseDesireMovement extends MouseMovement {

	private PriorityQueue<MouseDesire> desires;

	public MouseDesireMovement(PriorityQueue<MouseDesire> desires, IPosition position, Direction orientation,
			MouseType color) {
		super(position, orientation, color);
		this.desires = desires;
	}

	public Action nextAction() {
		MouseDesire desireToAchieve = desires.peek();
		if (desireToAchieve.getDesire().equals(Desire.REST))
			return Action.WAIT;
		else if (desireToAchieve.getDesire().equals(Desire.CHEESE)) {
			return desireCheese(desireToAchieve);
		} else if (desireToAchieve.getDesire().equals(Desire.WALK)) {
			return desireWalk(desireToAchieve);
		} else if (desireToAchieve.getDesire().equals(Desire.NOT_BREAK)) {
			return desireNotBreak();
		}
		return Action.WAIT;
	}

	protected static ITile east(IPosition position, IBoard board) {
		return board.getTile(position.getX(), position.getY() + 1);
	}

	protected static ITile north(IPosition position, IBoard board) {
		return board.getTile(position.getX() - 1, position.getY());
	}

	protected static ITile south(IPosition position, IBoard board) {
		return board.getTile(position.getX() + 1, position.getY());
	}

	protected static ITile west(IPosition position, IBoard board) {
		return board.getTile(position.getX(), position.getY() - 1);
	}

	private Action desireCheese(MouseDesire cheese) {
		Iterator<MouseDesire> it = desires.iterator();
		int notBreakWeight = 0;
		while (it.hasNext() && notBreakWeight == 0) {
			MouseDesire auxDesire = it.next();
			if (auxDesire.getDesire().equals(Desire.NOT_BREAK))
				notBreakWeight = auxDesire.getWeight();
		}
		if (cheese.getWeight() >= 75) {
			if (notBreakWeight > 50)
				return MouseCheeseFinder.nextAction(position, history.lastElement(), desires);
			else
				return MouseCheeseFinderWithoutBreak.nextAction(position, history.lastElement(), desires);
		} else if (cheese.getWeight() >= 50) {
			if (notBreakWeight > 25)
				return MouseCheeseFinder.nextAction(position, history.lastElement(), desires);
			else
				return MouseCheeseFinderWithoutBreak.nextAction(position, history.lastElement(), desires);
		} else {
			if (notBreakWeight > 0)
				return MouseCheeseFinder.nextAction(position, history.lastElement(), desires);
			else
				return MouseCheeseFinderWithoutBreak.nextAction(position, history.lastElement(), desires);
		}
	}

	private Action desireWalk(MouseDesire walk) {
		Iterator<MouseDesire> it = desires.iterator();
		int cheeseWeight = 0, notBreakWeight = 0;
		while (it.hasNext() && (cheeseWeight == 0 || notBreakWeight == 0)) {
			MouseDesire auxDesire = it.next();
			if (auxDesire.getDesire().name().equals("Cheese"))
				cheeseWeight = auxDesire.getWeight();
			else if (auxDesire.getDesire().name().equals("Not Break"))
				notBreakWeight = auxDesire.getWeight();
		}
		if (walk.getWeight() >= 75) {
			if (cheeseWeight > 50) {
				if (notBreakWeight > 50)
					return MouseCheeseFinder.nextAction(position, history.lastElement(), desires);
				else
					return MouseCheeseFinderWithoutBreak.nextAction(position, history.lastElement(), desires);
			} else {
				if (notBreakWeight > 50)
					return MouseSprinter.nextAction(orientation, position, history.lastElement());
				else
					return MouseSprinterWithoutBreak.nextAction(orientation, position, history.lastElement());
			}
		} else if (walk.getWeight() >= 50) {
			if (cheeseWeight > 25) {
				if (notBreakWeight > 25)
					return MouseCheeseFinder.nextAction(position, history.lastElement(), desires);
				else
					return MouseCheeseFinderWithoutBreak.nextAction(position, history.lastElement(), desires);
			} else {
				if (notBreakWeight > 25)
					return MouseSprinter.nextAction(orientation, position, history.lastElement());
				else
					return MouseSprinterWithoutBreak.nextAction(orientation, position, history.lastElement());
			}
		} else {
			if (cheeseWeight > 0) {
				if (notBreakWeight > 0)
					return MouseCheeseFinder.nextAction(position, history.lastElement(), desires);
				else
					return MouseCheeseFinderWithoutBreak.nextAction(position, history.lastElement(), desires);
			} else {
				if (notBreakWeight > 0)
					return MouseSprinter.nextAction(orientation, position, history.lastElement());
				else
					return MouseSprinterWithoutBreak.nextAction(orientation, position, history.lastElement());
			}
		}
	}

	private Action desireNotBreak() {
		MouseDesire notBreak = desires.poll();
		MouseDesire secondDesire = desires.peek();
		desires.add(notBreak);
		if (secondDesire == null || secondDesire.getDesire().equals(Desire.REST))
			return Action.WAIT;
		else if (secondDesire.getDesire().equals(Desire.CHEESE))
			return MouseCheeseFinder.nextAction(position, history.lastElement(), desires);
		else if (secondDesire.getDesire().equals(Desire.WALK)) {
			Iterator<MouseDesire> it = desires.iterator();
			int cheeseWeight = 0;
			while (it.hasNext() && cheeseWeight == 0) {
				MouseDesire auxDesire = it.next();
				if (auxDesire.getDesire().name().equals("Cheese"))
					cheeseWeight = auxDesire.getWeight();
			}
			if (cheeseWeight > 0)
				return MouseCheeseFinder.nextAction(position, history.lastElement(), desires);
			else
				return MouseSprinter.nextAction(orientation, position, history.lastElement());
		} else
			return Action.WAIT;
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
