package movement;

import java.util.HashSet;

import actions.Action;
import actions.ActionType;
import ai.Desire;
import interfaces.Tile;

public class MouseRandom extends MouseMovement {

	public Action nextAction(HashSet<Desire> desires, HashSet<Desire> hates, Tile position, Direction orientation) {
		return new Action(ActionType.random());
	}

}
