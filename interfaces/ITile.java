package interfaces;

import java.util.List;

public interface ITile {
	
	// Returns the type of this tile
	public TileType getType();
	
	// Return the things on the tile
	public List<IEntity> getThings();
	
	// Return the position of the tile
	public IPosition getPosition();

}
