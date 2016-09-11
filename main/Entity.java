package main;

import interfaces.EntityType;
import interfaces.IEntity;
import interfaces.IPosition;

// A example implementation for testing purposes of the interface IEntity
public class Entity implements IEntity {
	private IPosition position;
	private EntityType type;

	public Entity(int X, int Y, EntityType type) {
		position = new Position(X, Y);
		this.type = type;
	}

	public IPosition getPosition() {
		return position;
	}

	public EntityType getType() {
		return type;
	}

	public void setPosition(int X, int Y) {
		position = new Position(X, Y);
	}

	public boolean equals(Object other) {
		return (other instanceof Entity && type.equals(((Entity) other).type) && position
				.equals(((Entity) other).position));
	}
}
