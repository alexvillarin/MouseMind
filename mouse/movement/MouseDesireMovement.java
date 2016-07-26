package mouse.movement;

import java.util.Iterator;
import java.util.PriorityQueue;

import interfaces.IBoard;
import interfaces.IPosition;
import interfaces.ITile;
import mouse.action.Action;
import mouse.action.Wait;
import mouse.desire.Desire;
import mouse.desire.MouseDesire;

public class MouseDesireMovement extends MouseMovement {

	public Action nextAction() {
		MouseDesire desireToAchieve = desires.peek();
		if (desireToAchieve.getDesire().equals(Desire.Rest))
			return new Wait();
		else if (desireToAchieve.getDesire().equals(Desire.Cheese)) {
			return desireCheese(desireToAchieve);
		} else if (desireToAchieve.getDesire().equals(Desire.Walk)) {
			return desireWalk(desireToAchieve);
		} else if (desireToAchieve.getDesire().equals(Desire.NotBreak)) {
			return desireNotBreak();
		} return new Wait();
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
	
	private Action desireCheese(MouseDesire cheese) {
		Iterator<MouseDesire> it = desires.iterator();
		int notBreakWeight = 0;
		while (it.hasNext() && notBreakWeight == 0) {
			MouseDesire auxDesire = it.next();
			if (auxDesire.getDesire().name().equals("Not Break"))
				notBreakWeight = auxDesire.getWeight();
		}
		if (cheese.getWeight() >= 75) {
			if (notBreakWeight > 50)
				return MouseCheeseFinder.nextAction(position, history.lastElement(), desires);
			else {
				PriorityQueue<MouseDesire> desiresModified = deleteNotBreak(desires);
				return MouseCheeseFinder.nextAction(position, history.lastElement(), desiresModified);
			}
		} else if (cheese.getWeight() >= 50) {
			if (notBreakWeight > 25)
				return MouseCheeseFinder.nextAction(position, history.lastElement(), desires);
			else {
				PriorityQueue<MouseDesire> desiresModified = deleteNotBreak(desires);
				return MouseCheeseFinder.nextAction(position, history.lastElement(), desiresModified);
			}
		} else {
			if (notBreakWeight > 0)
				return MouseCheeseFinder.nextAction(position, history.lastElement(), desires);
			else {
				PriorityQueue<MouseDesire> desiresModified = deleteNotBreak(desires);
				return MouseCheeseFinder.nextAction(position, history.lastElement(), desiresModified);
			}
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
				else {
					PriorityQueue<MouseDesire> desiresModified = deleteNotBreak(desires);
					return MouseCheeseFinder.nextAction(position, history.lastElement(), desiresModified);
				}
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
				else {
					PriorityQueue<MouseDesire> desiresModified = deleteNotBreak(desires);
					return MouseCheeseFinder.nextAction(position, history.lastElement(), desiresModified);
				}
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
				else {
					PriorityQueue<MouseDesire> desiresModified = deleteNotBreak(desires);
					return MouseCheeseFinder.nextAction(position, history.lastElement(), desiresModified);
				}
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
		if (secondDesire.getDesire().equals(Desire.Rest))
			return new Wait();
		else if (secondDesire.getDesire().equals(Desire.Cheese))
			return MouseCheeseFinder.nextAction(position, history.lastElement(), desires);
		else if (secondDesire.getDesire().equals(Desire.Walk)) {
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
			return new Wait();
	}
	
	private PriorityQueue<MouseDesire> deleteNotBreak(PriorityQueue<MouseDesire> desires) {
		desires.remove(new MouseDesire(Desire.NotBreak, 0));
		return desires;
	}
}
