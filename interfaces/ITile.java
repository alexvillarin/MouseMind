package interfaces;

import java.util.List;

public interface ITile {
	
	// Returns the type of this tile
	public TileType getType();
	
	// 
	public void breakShoji();
	
	// Return the things on the tile
	public List<IEntity> getThings();
	
	// Return the position of the tile
	public IPosition getPosition();
	
	// Adds an entity to the tile
	public void add(IEntity f);

	// Removes an entity from the tile
	public void remove(IEntity f);

}
