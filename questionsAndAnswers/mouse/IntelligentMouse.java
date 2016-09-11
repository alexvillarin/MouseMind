package questionsAndAnswers.mouse;

import java.util.ArrayList;
import java.util.Iterator;

import event.Event;
import interfaces.EntityType;
import interfaces.IBoard;
import interfaces.IEntity;
import interfaces.IPosition;
import interfaces.ITile;
import interfaces.MouseType;
import mouse.action.Action;
import mouse.movement.astar.AStarMovement;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;

public class IntelligentMouse extends MouseQandA {

	private boolean thought;
	private boolean guilty;
	private boolean cheeseEaten;
	private MouseType mostProbableGuilty;
	private ITile cheeseTile;

	public IntelligentMouse(MouseType color, IPosition position) {
		super(color, position);
		thought = false;
	}

	public void getConclusions() {
		getCheeseTile();
		getWhoAteCheese();
		if (!cheeseEaten)
			getMicePositions();
		thought = true;
	}

	private void getCheeseTile() {
		IBoard initial = history.firstElement();
		int height = initial.getHeight();
		int width = initial.getWidth();
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				Iterator<IEntity> it = initial.getTile(i, j).getThings().iterator();
				while (it.hasNext()) {
					if (it.next().getType().equals(EntityType.CHEESE))
						cheeseTile = initial.getTile(i, j);
				}
			}
	}

	// Gets the nearest mouse to the cheese
	private void getMicePositions() {
		int distance = Integer.MAX_VALUE;
		Iterator<IBoard> it = history.iterator();
		while (it.hasNext()) {
			IBoard next = it.next();
			int height = next.getHeight();
			int width = next.getWidth();
			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++) {
					ITile tile = next.getTile(i, j);
					int newDistance = AStarMovement.manhattanDistance(tile, cheeseTile);
					if (newDistance < distance) {
						Iterator<IEntity> things = tile.getThings().iterator();
						while (things.hasNext()) {
							IEntity nextEntity = things.next();
							if (nextEntity.getType().equals(EntityType.MOUSE_BLUE) && !color.equals(MouseType.BLUE))
								mostProbableGuilty = MouseType.BLUE;
							else if (nextEntity.getType().equals(EntityType.MOUSE_GREEN)
									&& !color.equals(MouseType.GREEN))
								mostProbableGuilty = MouseType.GREEN;
							else if (nextEntity.getType().equals(EntityType.MOUSE_RED) && !color.equals(MouseType.RED))
								mostProbableGuilty = MouseType.RED;
							else if (nextEntity.getType().equals(EntityType.MOUSE_YELLOW)
									&& !color.equals(MouseType.YELLOW))
								mostProbableGuilty = MouseType.YELLOW;
						}
					}
				}
		}
	}

	private void getWhoAteCheese() {
		Iterator<Event> it = eventHistory.iterator();
		guilty = false;
		cheeseEaten = false;
		while (it.hasNext()) {
			Event next = it.next();
			if (next.getAction().equals(Action.EAT) && next.successfulEvent()) {
				cheeseEaten = true;
				if (next.getMouse().equals(color))
					guilty = true;
				else
					mostProbableGuilty = next.getMouse();
			}
		}
	}

	public Answer ask(QuestionType type, Object[] args) {
		if (!thought)
			getConclusions();
		if (type.equals(QuestionType.EAT_CHEESE)) {
			MouseType mouse = (MouseType) args[0];
			return askForCheese(mouse);
		} else if (type.equals(QuestionType.MOUSE_IN_TILE)) {
			MouseType mouse = (MouseType) args[0];
			ITile tile = (ITile) args[1];
			return askForTile(mouse, tile);
		} else
			return Answer.SILENCE;
	}

	private Answer askForCheese(MouseType mouse) {
		if (mouse.equals(color))
			return Answer.NO;
		else if (guilty && mouse.equals(mostProbableGuilty))
			return Answer.YES;
		else if (!guilty && mouse.equals(mostProbableGuilty))
			return Answer.MAYBE;
		else if (!guilty) // !mouse.equals(mostProbableGuilty)
			return Answer.NO;
		else
			return Answer.I_DONT_KNOW;
	}

	private Answer askForTile(MouseType mouse, ITile tile) {
		if (mouse.equals(color)) {
			ITile initialTile = history.lastElement().getTile(initialPosition);
			int cheeseDistance = AStarMovement.manhattanDistance(initialTile, cheeseTile);
			int cheeseTileDistance = AStarMovement.manhattanDistance(tile, cheeseTile);
			// The scientist is asking for a tile near to the cheese
			if (cheeseTileDistance < cheeseDistance / 2)
				return Answer.NO;
			// The scientist is asking for a tile in the path to the cheese
			ArrayList<ITile> list = AStarMovement.AStarSearch(initialTile, cheeseTile, history.lastElement());
			if (list.contains(tile))
				return Answer.NO;
			// The scientist is asking for other tile
			Iterator<IBoard> it = history.iterator();
			while (it.hasNext()) {
				IBoard next = it.next();
				if (next.getTile(tile.getPosition()).getThings().contains(mouse)) {
					// The sicentist is asking for a tile very far
					int tileDistance = AStarMovement.manhattanDistance(initialTile, cheeseTile);
					if (tileDistance > cheeseDistance / 2)
						return Answer.SILENCE;
					else // The scientist is asking for a close tile
						return Answer.YES;
				}

			}
			return Answer.NO;
		} else {
			Iterator<IBoard> it = history.iterator();
			while (it.hasNext()) {
				IBoard next = it.next();
				if (next.getTile(tile.getPosition()).getThings().contains(mouse))
					return Answer.YES;
			}
			return Answer.I_DONT_KNOW;
		}
	}

}
