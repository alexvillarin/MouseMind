package mouse.action;

public class Action {
	private ActionType type;
	
	public Action(ActionType type) {
		this.type = type;
	}
	
	public ActionType getType() {
		return type;
	}
	
}
