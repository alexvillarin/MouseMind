package movement;

import java.util.HashSet;

import actions.Action;
import actions.Wait;
import ai.Desire;
import interfaces.Tile;

/*
 * Class that controls the movement of a mouse. the mouse waits always.
 */

public class MouseNone extends MouseMovement {

	public Action nextAction(HashSet<Desire> desires, HashSet<Desire> hates, Tile position, Direction orientation) {
		return new Wait();
	}

}
