package movement;

import actions.Action;
import actions.Move;
import actions.Wait;
import ai.Desire;
import interfaces.TileType;
import movement.astar.MouseCheeseLover;

public class MouseDesireMovement extends MouseMovement {

	public Action nextAction() {
		Desire desireToAchieve = desires.peek().getDesire();
		if (desireToAchieve == Desire.Nothing)
			return new Wait();
		else if (desireToAchieve == Desire.Walk)
			return sprint();
		else if (desireToAchieve == Desire.Cheese)
			return MouseCheeseLover.nextAction(position, history);
		else
			return new Wait();
	}

	protected Action sprint() {
		if ((orientation == Direction.EAST && position.East().typeOfTile() != TileType.obstaculo)
				|| (orientation == Direction.NORTH && position.North().typeOfTile() != TileType.obstaculo)
				|| (orientation == Direction.SOUTH && position.South().typeOfTile() != TileType.obstaculo)
				|| (orientation == Direction.WEST && position.West().typeOfTile() != TileType.obstaculo))
			return new Move(orientation);
		else
			return new Move(Direction.random());
	}
}
