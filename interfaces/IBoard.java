package interfaces;

public interface IBoard {

	public ITile getTile(int x, int y);

	public ITile getTile(IPosition position);

	public int getWidth();

	public int getHeight();

	public boolean hasCheesse();
}
