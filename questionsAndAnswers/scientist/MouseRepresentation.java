package questionsAndAnswers.scientist;

import java.util.ArrayList;
import interfaces.IPosition;
import interfaces.ITile;
import interfaces.MouseType;
import mouse.MouseAI;
import questionsAndAnswers.Answer;
import questionsAndAnswers.QuestionType;

public class MouseRepresentation implements Comparable<MouseRepresentation> {
	private MouseAI mouseAI;
	private double confidence;
	private static final double increaseFactor = 1.1;
	private static final double reduceFactor = 0.9;
	private ArrayList<ITile> tilesVisited;
	private boolean suspicious;
	private Double charge;

	public MouseRepresentation(MouseAI ai) {
		mouseAI = ai;
		confidence = 100;
		tilesVisited = new ArrayList<ITile>();
	}

	public Answer ask(QuestionType cheese, Object[] object) {
		return mouseAI.ask(cheese, object);
	}

	public double getConfidence() {
		return confidence;
	}

	public double increaseConfidence() {
		confidence *= increaseFactor;
		if (confidence > 100)
			confidence = 100;
		return confidence;
	}

	public double reduceConfidence() {
		confidence *= reduceFactor;
		if (confidence < 1)
			confidence = 1;
		return confidence;
	}

	public MouseType getMouse() {
		return mouseAI.getMouse();
	}

	public void addTile(ITile tile) {
		tilesVisited.add(tile);
	}

	public ArrayList<ITile> getTilesVisited() {
		return tilesVisited;
	}

	public IPosition getInitialPosition() {
		return mouseAI.getInitialPosition();
	}

	public void setSuspicious() {
		suspicious = true;
	}

	public boolean isSuspicious() {
		return suspicious;
	}

	public void setcharge(double charge) {
		this.charge = charge;
	}

	public int compareTo(MouseRepresentation arg0) {
		return arg0.charge.compareTo(charge);
	}
	
	public String toString() {
		return mouseAI.getMouse() + " - charge: " + charge + ", confidence: " + confidence;
	}
}
