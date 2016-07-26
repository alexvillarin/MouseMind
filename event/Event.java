package event;

import interfaces.MouseType;
import mouse.action.Action;

public class Event {
	private MouseType mouse;
	private Action action;
	private boolean success;
	private int credibility;
	private int go;
	
	public Event(MouseType mouse, Action action, int credibility, boolean success, int go) {
		this.mouse = mouse;
		this.action = action;
		this.credibility = credibility;
		this.success = success;
		this.go = go;
	}
	
	public MouseType getMouse() {
		return mouse;
	}
	
	public Action getAction() {
		return action;
	}
	
	public int getArgs() {
		return credibility;
	}
	
	public boolean successfulEvent() {
		return success;
	}
	
	public int getTurn() {
		return go;
	}
}
