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
		
		
		System.out.println("Yellows:" + yellows.toString());
		System.out.println(findSameColoredTileGroups(yellows).toString());

		System.out.println("Blues:\t" + blues.toString());
		System.out.println(findSameColoredTileGroups(blues).toString());

		System.out.println("Blacks:\t" + blacks.toString());
		System.out.println(findSameColoredTileGroups(blacks).toString());

		System.out.println("Reds:\t" + reds.toString());
		System.out.println(findSameColoredTileGroups(reds).toString());
		
		ArrayList<ArrayList<Tile>> sameNumbered = findSameNumberedTileGroups(yellows, blues, blacks, reds);
		for(int i = 0; i < sameNumbered.size(); i++) {
			for(Tile t : sameNumbered.get(i)) {
				System.out.print(t.toString() + ", ");
			}
			System.out.println();
		}
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
	
	public ArrayList<ArrayList<Integer>> findSameColoredTileGroups(ArrayList<Integer> list){
		if(list.isEmpty()) {
			return null;
		}
		ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
		ArrayList<Integer> temp2 = new ArrayList<>();
		int i = 0;
		int j = 0;
		
		while(i < list.size()) {
			//flag = false;
			temp2.add(list.get(i));
			while(list.contains(temp2.get(j) + 1)) {
				temp2.add(temp2.get(j) + 1);
				j++;
				i++;
			}
			if(temp2.size() > 1) {
				temp.add(new ArrayList<Integer>(temp2));
			}
			i++;
			temp2.clear();
			j = 0;
		}
		
		boolean done = false;
		if(list.contains(1) && list.contains(13) && !list.contains(2)) {
			for(ArrayList<Integer> el : temp) {
				if(el.contains(13)) {
					el.add(1);
					done = true;
				}
			}
			if(!done) {
				temp.add(new ArrayList<Integer>() {{add(13); add(1);}});
			}
		} else if (list.contains(1) && list.contains(13)) {
			int numOfOnes = 0;
			for(int integ : list) {
				if(integ == 1)
					numOfOnes++;
			}
			if(numOfOnes > 1) {
				for(ArrayList<Integer> el : temp) {
					if(el.contains(13)) {
						el.add(1);
						done = true;
					}
				}
				if(!done) {
					temp.add(new ArrayList<Integer>() {
						{
							add(13);
							add(1);
						}
					});
				}
			} else {
				int size2 = 0;
				int size13 = 0;
				for(ArrayList<Integer> el : temp) {
					if(el.contains(2)) {
						size2 = el.size();
					} else if(el.contains(13)) {
						size13 = el.size();
					}
				}
				System.out.println(size2 + " " + size13);
				if(size13 >= size2) {
					for(ArrayList<Integer> el : temp) {
						if(el.contains(13)) {
							el.add(1);
						} else if(el.contains(2)) {
							if(el.size() == 2) {
								el.clear();
							} else {
								el.remove(el.indexOf(1));
							}
						}
					}
				}
			}
		}
		
		for(ArrayList<Integer> l : temp) {
			if(l.isEmpty()) {
				temp.remove(l);
			}
		}

		return temp;
	}
	
	public ArrayList<ArrayList<Tile>> findSameNumberedTileGroups(ArrayList<Integer> yellows, ArrayList<Integer> blues,ArrayList<Integer> blacks,ArrayList<Integer> reds){
		ArrayList<ArrayList<Tile>> temp = new ArrayList<ArrayList<Tile>>();
		
		for(int i = 1; i <= 13; i++) {
			ArrayList<Tile> temp2 = new ArrayList<Tile>();
			if(yellows.contains(i)) {
				temp2.add(new Tile(TileColor.Sarı, i));
			}
			if(blues.contains(i)) {
				temp2.add(new Tile(TileColor.Mavi, i));
			}
			if(blacks.contains(i)) {
				temp2.add(new Tile(TileColor.Siyah, i));
			}
			if(reds.contains(i)) {
				temp2.add(new Tile(TileColor.Kırmızı, i));
			}
			temp.add(temp2);
		}
		
		return temp;
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
