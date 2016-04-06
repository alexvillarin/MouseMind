package mouse.desire;

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
	
	public int compareTo(MouseDesire other) {
		return new Integer(other.weight).compareTo(this.weight);
	}
	
	private Desire desire;
	private int weight;
}
