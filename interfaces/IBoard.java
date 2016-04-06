package interfaces;

public interface IBoard {

	public ITile getTile(int x, int y);
	
	public int getWidth();
	
	public int getHeight();

	public boolean hasCheesse();

}
