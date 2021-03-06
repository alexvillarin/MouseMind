package mouse.desire;

/*
 * This class implements the specific desire of a mouse. 
 * The desire has a weight that represents the its importance to the mouse 
 */
public class MouseDesire implements Comparable<MouseDesire> {

	public MouseDesire(Desire desire, int weight) {
		this.desire = desire;
		this.weight = weight;
	}

	public Desire getDesire() {
		return desire;
	}

	public int getWeight() {
		return weight;
	}

	public boolean equals(Object other) {
		return (other instanceof MouseDesire && desire.equals(((MouseDesire) other).desire));
	}

	public int compareTo(MouseDesire other) {
		return other.weight.compareTo(this.weight);
	}

	public String toString() {
		return desire.toString();
	}

	private Desire desire;
	private Integer weight;
}
