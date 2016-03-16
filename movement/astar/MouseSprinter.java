package movement.astar;

import actions.Action;
import movement.MouseDesireMovement;

/*
 * Class that controls the movemente of a mouse. The mouse always walks trying to keep its direction 
 */

public class MouseSprinter extends MouseDesireMovement {

	public Action nextAction() {
		return sprint();
	}

}
