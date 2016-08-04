package interfaces;

import java.util.PriorityQueue;

import mouse.desire.MouseDesire;
import mouse.movement.Direction;
import mouse.movement.MouseDesireMovement;
import mouse.movement.MouseMovement;
import mouse.movement.MouseNone;
import mouse.movement.MouseRandom;
import mouse.movement.desire.MouseSprinter;
import mouse.movement.desire.MouseSprinterWithoutBreak;

public enum MovementType {
	NONE, RANDOM, SPRINTER, SPRINTER_WITHOUT_BREAK, DESIRE;

	public MouseMovement getMouseMovement(PriorityQueue<MouseDesire> desires,
			IPosition position, Direction orientation, MouseType mouse, int turnsLeft) {
		switch (this) {
		case NONE:
			return new MouseNone(position, orientation, mouse);
		case RANDOM:
			return new MouseRandom(position, orientation, mouse);
		case SPRINTER:
			return new MouseSprinter(desires, position, orientation, mouse, turnsLeft);
		case SPRINTER_WITHOUT_BREAK:
			return new MouseSprinterWithoutBreak(desires, position, orientation, mouse, turnsLeft);
		case DESIRE:
			return new MouseDesireMovement(desires, position, orientation,
					mouse, turnsLeft);
		default:
			return new MouseNone(position, orientation, mouse);
		}
	}
}
