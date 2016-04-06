package event;

import interfaces.Mouse;
import mouse.action.Action;

public class Event {
	private Mouse mouse;
	private Action action;
	private Object[] args;
	
	public Event(Mouse mouse, Action action, Object[] args) {
		this.mouse = mouse;
		this.action = action;
		this.args = args;
	}
	
	public Mouse getMouse() {
		return mouse;
	}
	
	public Action getAction() {
		return action;
	}
	
	public Object[] getArgs() {
		return args;
	}
}
