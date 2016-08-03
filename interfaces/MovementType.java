package interfaces;

import java.util.PriorityQueue;

import mouse.desire.MouseDesire;
import mouse.movement.Direction;
import mouse.movement.MouseDesireMovement;
import mouse.movement.MouseMovement;
import mouse.movement.MouseNone;
import mouse.movement.MouseRandom;
import mouse.movement.desire.MouseSprinter;

public enum MovementType {
	NONE, RANDOM, SPRINTER, DESIRE;

	public MouseMovement getMouseMovement(PriorityQueue<MouseDesire> desires,
			IPosition position, Direction orientation, MouseType mouse) {
		switch (this) {
		case NONE:
			return new MouseNone(position, orientation, mouse);
		case RANDOM:
			return new MouseRandom(position, orientation, mouse);
		case SPRINTER:
			return new MouseSprinter(desires, position, orientation, mouse);
		case DESIRE:
			return new MouseDesireMovement(desires, position, orientation,
					mouse);
		default:
			return new MouseNone(position, orientation, mouse);
		}
	}
}
