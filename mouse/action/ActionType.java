package mouse.action;

public enum ActionType {
	wait, move, eat;
	
	private static double elements = 3;
	
	public static ActionType random() {
		double value = Math.random() * elements;
		if (value < 1)
			return wait;
		if (value < 2)
			return move;
		return eat;
		
	}
}
