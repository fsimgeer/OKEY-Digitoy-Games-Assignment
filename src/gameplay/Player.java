package gameplay;

import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Tile> tiles = new ArrayList<>();
	
	public Player(String name) {
		this.name = name;
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
	public void matchTiles(Tile okey) {
		ArrayList<Tile> list = tiles;
		boolean containsOkey = false;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(okey)) {
				containsOkey = true;
				list.remove(list.get(i));
				i--;
			}
		}
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getColor() == TileColor.FalseJoker) {
				list.remove(list.get(i));
				list.add(new Tile(okey.getColor(), okey.getNumber()));
				i--;
			}
		}
		
		ArrayList<Integer> yellows = new ArrayList<>();
		ArrayList<Integer> blues = new ArrayList<>();
		ArrayList<Integer> blacks = new ArrayList<>();
		ArrayList<Integer> reds = new ArrayList<>();
		
		for(Tile tile : list) {
			if(tile.getColor() == TileColor.Sarı) {
				yellows.add(tile.getNumber());
			} else if(tile.getColor() == TileColor.Mavi) {
				blues.add(tile.getNumber());
			} else if(tile.getColor() == TileColor.Siyah) {
				blacks.add(tile.getNumber());
			} else if(tile.getColor() == TileColor.Kırmızı){
				reds.add(tile.getNumber());
			}
		}
		
		yellows.sort(null);
		blues.sort(null);
		blacks.sort(null);
		reds.sort(null);
		
		
		System.out.println("containsOkey: " + containsOkey);
		System.out.println(yellows.toString());
	}
	
	public int numberOfDuplicates() {
		int temp = 0;
		for(Tile t1 : tiles) {
			for (Tile t2 : tiles) {
				if(t1.equals(t2))
					temp++;
			}
		}
		return ((temp - tiles.size()) / 2);
	}
	
	public void print(Tile okey) {
		System.out.print(name + ": [");
		for(int i = 0; i < tiles.size(); i++) {
			if(i == tiles.size() - 1) {
				if(tiles.get(i).equals(okey)) {
					System.out.print("\033[32m" + tiles.get(i).toString() + "\033[0m");
				} else if(tiles.get(i).getColor() == TileColor.FalseJoker) {
					System.out.print("\033[33m" + tiles.get(i).toString() + "\033[0m");
				} else {
					System.out.print(tiles.get(i).toString());
				}
			}else {
				if(tiles.get(i).equals(okey)) {
					System.out.print("\033[32m" + tiles.get(i).toString() + ", " + "\033[0m");
				} else if(tiles.get(i).getColor() == TileColor.FalseJoker) {
					System.out.print("\033[33m" + tiles.get(i).toString() + ", " + "\033[0m");
				} else {
					System.out.print(tiles.get(i).toString() + ", ");
				}
			}
		}
		System.out.println("]");
	}
}
