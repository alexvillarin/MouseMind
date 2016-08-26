package main;

import java.util.ArrayList;
import java.util.List;

import interfaces.IEntity;
import interfaces.IPosition;
import interfaces.ITile;
import interfaces.TileType;

public class Tile implements ITile {

	private TileType type;
	private List<IEntity> things = new ArrayList<IEntity>();
	private IPosition position;

	public Tile(int X, int Y, TileType type) {
		position = new Position(X, Y);
		this.type = type;
	}

	public TileType getType() {
		return type;
	}

	public void breakShoji() {
		if (type.equals(TileType.SHOJI))
			type = TileType.BROKEN_SHOJI;
	}

	public List<IEntity> getThings() {
		return things;
	}

	public IPosition getPosition() {
		return position;
	}

	public void add(IEntity f) {
		things.add(f);
	}

	public void remove(IEntity f) {
		things.remove(f);
	}

	public boolean equals(Object other) {
		return (other instanceof Tile && position.equals(((Tile) other).position));
	}

	public String toString() {
		return position.toString();
	}
}
