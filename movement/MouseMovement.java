package movement;

import java.util.HashSet;
import actions.Action;
import ai.Desire;
import interfaces.Tile;

/*
 * Class that controls the movement of a mouse.
 */

public abstract class MouseMovement {

	public abstract Action nextAction(HashSet<Desire> desires, HashSet<Desire> hates, Tile position,
			Direction orientation);

}
