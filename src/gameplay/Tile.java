package gameplay;

public class Tile {
	private TileColor color;
	private int number;
	
	// constructor
	public Tile(TileColor color, int number) {
		this.color = color;
		this.number = number;
	}
	
	// methods
	public TileColor getColor() {
		return color;
	}
	
	public int getNumber() {
		return number;
	}
	
	public boolean equals(Tile tile) {
		if(color == tile.color && number == tile.number)
			return true;
		return false;
	}
	
	public String toString() {
		if(color == TileColor.FalseJoker) {
			return ("Sahte Okey");
		}
		return (color + " " + number);
	}
}
