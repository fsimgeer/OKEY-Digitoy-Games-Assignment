package gameplay;

public class Tile {
	private TileType type;
	private int number;
	
	// constructor
	public Tile(TileType type, int number) {
		this.type = type;
		this.number = number;
	}
	
	// methods
	public TileType getType() {
		return type;
	}
	
	public int getNumber() {
		return number;
	}
	
	@Override
	public boolean equals(Object other) {
		if(this == other)
			return true;
		if(!(other instanceof Tile))
			return false;
		Tile tile = (Tile) other;
		return (type == tile.type && number == tile.number);
	}
	
	@Override
	public int hashCode() {
		int i = 23;
		i = i * 13 + type.hashCode();
		i = i * 13 + number;
		return i;
	}
	
	@Override
	public String toString() {
		if(type == TileType.FalseJoker)
			return type.toString();
		return (type + " " + number);
	}
}
