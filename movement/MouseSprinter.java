package movement;

import java.util.HashSet;
import actions.Action;
import actions.Move;
import ai.Desire;
import interfaces.Tile;
import interfaces.TileType;

/*
 * Class that controls the movemente of a mouse. The mouse always walks trying to keep its direction 
 */

public class MouseSprinter extends MouseMovement {

	public Action nextAction(HashSet<Desire> desires, HashSet<Desire> hates, Tile position, Direction orientation) {
		if ((orientation == Direction.EAST && position.East().typeOfTile() != TileType.obstaculo) ||
			(orientation == Direction.NORTH && position.North().typeOfTile() != TileType.obstaculo) ||
			(orientation == Direction.SOUTH && position.South().typeOfTile() != TileType.obstaculo) ||
			(orientation == Direction.WEST && position.West().typeOfTile() != TileType.obstaculo))
			return new Move(orientation);
		else
			return new Move(Direction.random());
	}

}
