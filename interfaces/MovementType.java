package interfaces;

import mouse.movement.MouseDesireMovement;
import mouse.movement.MouseMovement;
import mouse.movement.MouseNone;
import mouse.movement.MouseRandom;
import mouse.movement.MouseSprinter;

public enum MovementType {
	NONE, RANDOM, SPRINTER, DESIRE;

	public MouseMovement getMouseMovement() {
		switch(this) {
		case NONE:
			return new MouseNone();
		case RANDOM:
			return new MouseRandom();
		case SPRINTER:
			return new MouseSprinter();
		case DESIRE:
			return new MouseDesireMovement();
		default:
			return new MouseNone();
		}
	}
}
