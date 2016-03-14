package movement;

import java.util.HashSet;
import java.util.Vector;

import actions.Action;
import actions.ActionType;
import actions.Move;
import ai.Desire;
import interfaces.Board;
import interfaces.Tile;

public class MouseRandom extends MouseMovement {

	public Action nextAction(HashSet<Desire> desires, HashSet<Desire> hates, Tile position, Direction orientation,
			Vector<Board> history) {
		ActionType action = ActionType.random();
		if (action == ActionType.move)
			return new Move(Direction.random());
		return new Action(action);
	}

}
