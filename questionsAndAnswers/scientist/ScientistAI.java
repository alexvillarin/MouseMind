package questionsAndAnswers.scientist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

import interfaces.EntityType;
import interfaces.IBoard;
import interfaces.IEntity;
import interfaces.IScientist;
import interfaces.ITile;
import interfaces.MouseType;
import interfaces.TileType;
import mouse.MouseAI;
import mouse.movement.MouseDesireMovement;
import mouse.movement.astar.AStarMovement;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;

/*
 * Class representing the scientist mind. It implements the algoritms to carry out 
 * the interrogation of the mice. It also save the conversation with the mice to show it
 */
public class ScientistAI implements IScientist {
	private IBoard finalBoard;
	private MouseRepresentation[] allMice;
	private ITile cheeseTile;
	private PriorityQueue<MouseRepresentation> defendantMice;
	private ArrayList<String> interrogation;

	public ScientistAI(IBoard initialBoard, IBoard finalBoard, MouseAI[] ai) {
		this.finalBoard = finalBoard;

		allMice = new MouseRepresentation[ai.length];
		for (int i = 0; i < ai.length; i++)
			allMice[i] = new MouseRepresentation(ai[i]);

		int width = initialBoard.getWidth();
		int height = initialBoard.getHeight();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				ITile tile = initialBoard.getTile(i, j);
				Iterator<IEntity> it = tile.getThings().iterator();
				while (it.hasNext()) {
					IEntity next = it.next();
					if (next.getType().equals(EntityType.CHEESE))
						cheeseTile = initialBoard.getTile(i, j);
				}

			}
		}

		defendantMice = new PriorityQueue<MouseRepresentation>();
		interrogation = new ArrayList<String>();
	}

	public MouseType ScientistInterrogation() {
		ArrayList<MouseRepresentation> suspiciousMice = new ArrayList<MouseRepresentation>();

		// If mouse turns itself in to the scientist then we add it to
		// suspicious
		for (MouseRepresentation mouse : allMice) {
			Object[] params = { mouse.getMouse() };
			Answer answer = mouse.ask(QuestionType.EAT_CHEESE, params);
			addAnswer(QuestionType.EAT_CHEESE, params, answer);
			if (answer == Answer.YES) {
				mouse.setSuspicious();
				suspiciousMice.add(mouse);
			}
		}
		if (suspiciousMice.size() > 1) {
			Iterator<MouseRepresentation> it = suspiciousMice.iterator();
			while (it.hasNext())
				it.next().reduceConfidence();
		}

		// We try to discover the positions of the mice during the execution
		int width = finalBoard.getWidth();
		int height = finalBoard.getHeight();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				ITile tile = finalBoard.getTile(i, j);
				if (tile.getType().equals(TileType.BROKEN_SHOJI))
					askForBrokenShoji(tile);
			}
		}

		for (MouseRepresentation mouse : allMice) {
			double charge = mouse.isSuspicious() ? mouse.getConfidence() : 0;
			charge += (1 - getTileDistance(mouse, cheeseTile) / (width + height)) * 65
					+ (100 - mouse.getConfidence()) * 0.45;
			mouse.setcharge(charge);
			defendantMice.add(mouse);
		}

		return defendantMice.peek().getMouse();
	}

	private void askForBrokenShoji(ITile tile) {
		boolean guiltyMouseKnown = false;
		// We ask every mouse about the tile
		for (MouseRepresentation mouse : allMice) {
			Object[] params = { mouse.getMouse(), tile };
			Answer answer = mouse.ask(QuestionType.MOUSE_IN_TILE, params);
			addAnswer(QuestionType.MOUSE_IN_TILE, params, answer);
			if (answer == Answer.YES) {
				guiltyMouseKnown = true;
				mouse.addTile(tile);
			}
		}

		// If no mouse admits that was in the tile, we look for the most
		// probable way to the cheese
		if (!guiltyMouseKnown) {
			for (MouseRepresentation mouse : allMice) {
				ITile initialTile = finalBoard.getTile(mouse.getInitialPosition());
				ArrayList<ITile> list = AStarMovement.AStarSearch(initialTile, cheeseTile, finalBoard);
				if (list.contains(tile)) {
					guiltyMouseKnown = true;
					mouse.reduceConfidence();
					mouse.addTile(tile);
				}
			}
		}

		// If no mouse has the tile in his most probable way to the cheese, we
		// charge the nearest mouse
		if (!guiltyMouseKnown) {
			MouseRepresentation closestMouse = allMice[0];
			ITile initialTile = finalBoard.getTile(closestMouse.getInitialPosition());
			int minDistance = AStarMovement.manhattanDistance(initialTile, tile);
			for (int i = 1; i < allMice.length; i++) {
				initialTile = finalBoard.getTile(allMice[i].getInitialPosition());
				int distance = AStarMovement.manhattanDistance(initialTile, tile);
				if (distance < minDistance) {
					closestMouse = allMice[i];
					minDistance = distance;
				}
			}
			closestMouse.addTile(tile);
		}
	}

	private void addAnswer(QuestionType type, Object[] params, Answer answer) {
		String str = type + ", ";
		for (int i = 0; i < params.length; i++)
			str += params[i] + ", ";
		str += answer;
		interrogation.add(str);
	}

	// Gets the distance between a mouse and the cheese
	private double getTileDistance(MouseRepresentation mouse, ITile cheese) {
		int minDistance = MouseDesireMovement.manhattanDistance(mouse.getInitialPosition(), cheese);
		Iterator<ITile> it = mouse.getTilesVisited().iterator();
		while (it.hasNext()) {
			ITile next = it.next();
			int distance = AStarMovement.manhattanDistance(next, cheese);
			if (distance < minDistance)
				minDistance = distance;
		}
		return minDistance;
	}

	// Return information about the interrogation
	public String getInterrogation() {
		Iterator<String> it = interrogation.iterator();
		String str = it.next() + "\n";
		while (it.hasNext())
			str += it.next() + "\n";
		return str;
	}

	// Return information about the probabilities of each mice to be guilty
	public String getDefendatMice() {
		return defendantMice.toString();
	}
}
