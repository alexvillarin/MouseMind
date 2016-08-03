package interfaces;

public interface IPosition {

	public int getX();

	public int getY();

	public IPosition east(IBoard board);

	public IPosition north(IBoard board);

	public IPosition south(IBoard board);

	public IPosition west(IBoard board);

}
