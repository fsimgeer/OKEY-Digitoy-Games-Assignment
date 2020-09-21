package gameplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import output.PrettyPrinter;

public class Player {
	private String name;
	private List<Tile> tiles = new ArrayList<>();
	public PrettyPrinter pp = new PrettyPrinter();
	
	public Player(String name) {
		this.name = name;
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
	public void matchTiles(Tile okey) {
		/*
		okey = new Tile(TileType.Yellow, 3);
		
		tiles.clear();
		tiles.add(new Tile(TileType.Yellow, 1));
		tiles.add(new Tile(TileType.Yellow, 2));
		tiles.add(new Tile(TileType.Yellow, 3));
		tiles.add(new Tile(TileType.Yellow, 4));
		tiles.add(new Tile(TileType.Yellow, 5));
		tiles.add(new Tile(TileType.Yellow, 6));
		tiles.add(new Tile(TileType.Yellow, 7));
		tiles.add(new Tile(TileType.Yellow, 8));
		tiles.add(new Tile(TileType.Yellow, 9));
		tiles.add(new Tile(TileType.FalseJoker, 0));
		tiles.add(new Tile(TileType.Yellow, 1));
		tiles.add(new Tile(TileType.Yellow, 2));
		tiles.add(new Tile(TileType.Yellow, 3));
		tiles.add(new Tile(TileType.Yellow, 4));
		tiles.add(new Tile(TileType.Yellow, 5));
		tiles.add(new Tile(TileType.Yellow, 6));
		tiles.add(new Tile(TileType.Yellow, 7));
		tiles.add(new Tile(TileType.Yellow, 8));
		tiles.add(new Tile(TileType.Yellow, 9));
		tiles.add(new Tile(TileType.FalseJoker, 0));
		*/
		List<Tile> list = tiles;
		int numOfOkey = 0;
		
		for(int i = list.size() - 1; i >= 0; i--) {
			Tile current = list.get(i);
			if(current.equals(okey)) {
				numOfOkey++;
				list.remove(current);
			} else if(current.getType() == TileType.FalseJoker) {
				list.remove(current);
				list.add(new Tile(okey.getType(), okey.getNumber()));
			}
		}
		
		ArrayList<Integer> yellows = new ArrayList<>();
		ArrayList<Integer> blues = new ArrayList<>();
		ArrayList<Integer> blacks = new ArrayList<>();
		ArrayList<Integer> reds = new ArrayList<>();
		
		for(Tile tile : list) {
			if(tile.getType() == TileType.Yellow) {
				yellows.add(tile.getNumber());
			} else if(tile.getType() == TileType.Blue) {
				blues.add(tile.getNumber());
			} else if(tile.getType() == TileType.Black) {
				blacks.add(tile.getNumber());
			} else if(tile.getType() == TileType.Red){
				reds.add(tile.getNumber());
			}
		}
		
		Collections.sort(yellows);
		Collections.sort(blues);
		Collections.sort(blacks);
		Collections.sort(reds);
		
		ArrayList<ArrayList<Integer>> yellowsList = new ArrayList<>(findGroupsOfSameColoredTiles(yellows));
		ArrayList<ArrayList<Integer>> bluesList = new ArrayList<>(findGroupsOfSameColoredTiles(blues));
		ArrayList<ArrayList<Integer>> blacksList = new ArrayList<>(findGroupsOfSameColoredTiles(blacks));
		ArrayList<ArrayList<Integer>> redsList = new ArrayList<>(findGroupsOfSameColoredTiles(reds));
		
		System.out.println("Yellows:" + yellows.toString());
		System.out.println("Blues:\t" + blues.toString());
		System.out.println("Blacks:\t" + blacks.toString());
		System.out.println("Reds:\t" + reds.toString());
		System.out.println();

		ArrayList<ArrayList<Tile>> sets = new ArrayList<>();
		
		addToSet(yellows, yellowsList, TileType.Yellow, sets, 3);
		addToSet(blues, bluesList, TileType.Blue, sets, 3);
		addToSet(blacks, blacksList, TileType.Black, sets, 3);
		addToSet(reds, redsList, TileType.Red, sets, 3);
		
		ArrayList<ArrayList<Tile>> sameNumbered = findGroupsOfSameNumberedTiles(yellows, blues, blacks, reds, 3);

		for(ArrayList<Tile> t : sameNumbered) {
			if(t.size() > 2)
				sets.add(t);
		}

		yellowsList = new ArrayList<>(findGroupsOfSameColoredTiles(yellows));
		bluesList = new ArrayList<>(findGroupsOfSameColoredTiles(blues));
		blacksList = new ArrayList<>(findGroupsOfSameColoredTiles(blacks));
		redsList = new ArrayList<>(findGroupsOfSameColoredTiles(reds));
		
		addToSet(yellows, yellowsList, TileType.Yellow, sets, 2);
		addToSet(blues, bluesList, TileType.Blue, sets, 2);
		addToSet(blacks, blacksList, TileType.Black, sets, 2);
		addToSet(reds, redsList, TileType.Red, sets, 2);
		
		sameNumbered = findGroupsOfSameNumberedTiles(yellows, blues, blacks, reds, 2);

		for(ArrayList<Tile> t : sameNumbered) {
			if(t.size() > 1)
				sets.add(t);
		}
		
		for(ArrayList<Tile> t : sets) {
			System.out.println(t.toString());
		}
		
		System.out.println(organizeSets(sets));
	}
	
	// methods
	private int numberOfDuplicates() {
		Set<Tile> s = new HashSet<>(tiles);
		return (tiles.size() - s.size());
	}
	
	private ArrayList<ArrayList<Integer>> findGroupsOfSameColoredTiles(ArrayList<Integer> list){
		ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
		if(list.isEmpty()) {
			return temp;
		}
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
				ArrayList<Integer> l = new ArrayList<>();
				l.add(13);
				l.add(1);
				temp.add(l);
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
					ArrayList<Integer> l = new ArrayList<>();
					l.add(13);
					l.add(1);
					temp.add(l);
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
		
		temp.removeIf(l -> l.isEmpty());

		return temp;
	}
	
	private ArrayList<ArrayList<Tile>> findGroupsOfSameNumberedTiles(ArrayList<Integer> yellows, ArrayList<Integer> blues,ArrayList<Integer> blacks,ArrayList<Integer> reds, int tileCount){
		ArrayList<ArrayList<Tile>> temp = new ArrayList<ArrayList<Tile>>();
		
		for(int i = 1; i <= 13; i++) {
			ArrayList<Tile> temp2 = new ArrayList<Tile>();
			if(yellows.contains(i)) {
				temp2.add(new Tile(TileType.Yellow, i));
			}
			if(blues.contains(i)) {
				temp2.add(new Tile(TileType.Blue, i));
			}
			if(blacks.contains(i)) {
				temp2.add(new Tile(TileType.Black, i));
			}
			if(reds.contains(i)) {
				temp2.add(new Tile(TileType.Red, i));
			}
			
			if(temp2.size() >= tileCount) {
				if(yellows.contains(i)) {
					yellows.remove(yellows.indexOf(i));
				}
				if(blues.contains(i)) {
					blues.remove(blues.indexOf(i));
				}
				if(blacks.contains(i)) {
					blacks.remove(blacks.indexOf(i));
				}
				if(reds.contains(i)) {
					reds.remove(reds.indexOf(i));
				}
				temp.add(temp2);
			}
		}
		
		return temp;
	}
	
	private void addToSet(ArrayList<Integer> list, ArrayList<ArrayList<Integer>> list2, TileType color, ArrayList<ArrayList<Tile>> sets, int tileCount) {
		ArrayList<Tile> temp;
		for(int i = 0; i < list2.size(); i++) {
			if(list2.get(i).size() >= tileCount) {
				temp = new ArrayList<>();
				for(Integer in : list2.get(i)) {
					temp.add(new Tile(color, in));
					list.remove(list.indexOf(in));
				}
				sets.add(temp);
				list2.remove(list2.indexOf(list2.get(i)));
			}
		}
	}
	
	private int organizeSets(ArrayList<ArrayList<Tile>> sets) {
		ArrayList<Integer> setsTileCounts = new ArrayList<>();
		
		for(int i = 0; i < sets.size(); i++) {
			setsTileCounts.add(sets.get(i).size());
		}

		int numOf5= 0;
		int numOf4 = 0;
		int numOf3 = 0;
		int numOf2 = 0;
		int numOfRemaining = 0;
		
		for(int i = 0; i < sets.size(); i++) {
			if(setsTileCounts.get(i) == 5)
				numOf5++;
			else if(setsTileCounts.get(i) == 4)
				numOf4++;
			else if(setsTileCounts.get(i) == 3)
				numOf3++;
			else if(setsTileCounts.get(i) == 2)
				numOf2++;
		}
		numOfRemaining = setsTileCounts.size() - (numOf5 + numOf4 + numOf3 + numOf2);
		System.out.println(numOfRemaining);
				
		System.out.println(setsTileCounts.toString());
		System.out.println("num of 4: " + numOf4);
		System.out.println("num of 3: " + numOf3);
		
		if(numOf4 == 2) {
			if(numOf3 == 2) {
				return 14;
			} else if(numOf3 == 1) {
				if(numOf2 > 0)
					return 13;
				else
					return 11;
			}
		} else if(numOf4 == 1) {
			if(numOf3 == 2) {
				
			} else if(numOf3 == 1) {
				
			}
		}
		
		return 0;
	}
	
	public void print(Tile okey) {
		System.out.print(name + ": [");
		for(int i = 0; i < tiles.size(); i++) {
			if(i == tiles.size() - 1) {
				if(tiles.get(i).equals(okey)) {
					pp.printGreenString(tiles.get(i).toString());
				} else if(tiles.get(i).getType() == TileType.FalseJoker) {
					pp.printYellowString(tiles.get(i).toString());
				} else {
					System.out.print(tiles.get(i).toString());
				}
			} else {
				if(tiles.get(i).equals(okey)) {
					pp.printGreenString(tiles.get(i).toString());
					System.out.print(", ");
				} else if(tiles.get(i).getType() == TileType.FalseJoker) {
					pp.printYellowString(tiles.get(i).toString());
					System.out.print(", ");
				} else {
					System.out.print(tiles.get(i).toString() + ", ");
				}
			}
		}
		System.out.println("]");
	}
}
