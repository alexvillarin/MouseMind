package interfaces;

// Interface of the board where the game take place
public interface IBoard {

	public ITile getTile(int x, int y);

	public ITile getTile(IPosition position);

	public int getWidth();

	public int getHeight();

	public boolean hasCheesse();
}
