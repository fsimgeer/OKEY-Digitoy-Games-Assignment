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
	ArrayList<ArrayList<Tile>> sets;
	ArrayList<Tile> ones = new ArrayList<>();
	int numOfOkey = 0;
	boolean duplicateHand = false;
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public boolean isDuplicateHand() {
		return duplicateHand;
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	
	public int organizeHand(Tile okey) {
		List<Tile> list = new ArrayList<Tile>(tiles);
		
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

		sets = new ArrayList<>();
		
		addToSet(yellows, yellowsList, TileType.Yellow, 3);
		addToSet(blues, bluesList, TileType.Blue, 3);
		addToSet(blacks, blacksList, TileType.Black, 3);
		addToSet(reds, redsList, TileType.Red, 3);
		
		ArrayList<ArrayList<Tile>> sameNumbered = findGroupsOfSameNumberedTiles(yellows, blues, blacks, reds, 3);

		for(ArrayList<Tile> t : sameNumbered) {
			if(t.size() > 2)
				sets.add(t);
		}

		yellowsList = new ArrayList<>(findGroupsOfSameColoredTiles(yellows));
		bluesList = new ArrayList<>(findGroupsOfSameColoredTiles(blues));
		blacksList = new ArrayList<>(findGroupsOfSameColoredTiles(blacks));
		redsList = new ArrayList<>(findGroupsOfSameColoredTiles(reds));
		
		addToSet(yellows, yellowsList, TileType.Yellow, 2);
		addToSet(blues, bluesList, TileType.Blue, 2);
		addToSet(blacks, blacksList, TileType.Black, 2);
		addToSet(reds, redsList, TileType.Red, 2);
		
		sameNumbered = findGroupsOfSameNumberedTiles(yellows, blues, blacks, reds, 2);

		for(ArrayList<Tile> t : sameNumbered) {
			if(t.size() > 1)
				sets.add(t);
		}
		
		for(int i : yellows) {
			ones.add(new Tile(TileType.Yellow, i));
		}
		for(int i : blues) {
			ones.add(new Tile(TileType.Blue, i));
		}
		for(int i : blacks) {
			ones.add(new Tile(TileType.Black, i));
		}
		for(int i : reds) {
			ones.add(new Tile(TileType.Red, i));
		}
				
		int points = givePoints() + numOfOkey;
		
		for(ArrayList<Tile> li : sets) {
			for(int i = 0; i < li.size(); i++) {
				Tile t = li.get(i);
				if(t.equals(okey)) {
					li.remove(i);
					li.add(i, new Tile(TileType.FalseJoker, 0));
				}
			}
		}
		
		int duplicateHandPoint = (numberOfDuplicates() + numOfOkey) * 2;
		if(duplicateHandPoint > points) {
			points = duplicateHandPoint;
			duplicateHand = true;
			sets.clear();
			ones.clear();
			
			list = new ArrayList<Tile>(tiles);
			
			for(int i = list.size() - 1; i >= 0; i--) {
				Tile current = list.get(i);
				if(current.getType() == TileType.FalseJoker) {
					list.remove(current);
					list.add(new Tile(okey.getType(), okey.getNumber()));
				}
			}
			
			addDuplicatesToSet(list);
			
			for(Tile t : list)
				ones.add(t);
		}
		
		// System.out.println(points);
		
		return points;
	}
	
	// methods
	private int numberOfDuplicates() {
		Set<Tile> s = new HashSet<>(tiles);
		return (tiles.size() - s.size());
	}
	
	private void addDuplicatesToSet(List<Tile> list) {
		for(int i = 0; i < list.size(); i++) {
			for(int j = i + 1; j < list.size(); j++) {
				if(list.get(i).equals(list.get(j))) {
					ArrayList<Tile> newList = new ArrayList<>();
					newList.add(new Tile(list.get(i).getType(), list.get(i).getNumber()));
					newList.add(new Tile(list.get(i).getType(), list.get(i).getNumber()));
					sets.add(newList);
				}
			}
		}
		for(ArrayList<Tile> li : sets) {
			list.removeAll(li);
		}
		
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
	
	private void addToSet(ArrayList<Integer> list, ArrayList<ArrayList<Integer>> list2, TileType color, int tileCount) {
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
	
	private int givePoints() {
		ArrayList<Integer> setsTileCounts = new ArrayList<>();
		
		for(int i = 0; i < sets.size(); i++) {
			setsTileCounts.add(sets.get(i).size());
		}

		int numOf5= 0;
		int numOf4 = 0;
		int numOf3 = 0;
		int numOf2 = 0;
		int other = 0;
		int other2 = 0;
		
		for(int i = 0; i < sets.size(); i++) {
			if(setsTileCounts.get(i) == 5)
				numOf5++;
			else if(setsTileCounts.get(i) == 4)
				numOf4++;
			else if(setsTileCounts.get(i) == 3)
				numOf3++;
			else if(setsTileCounts.get(i) == 2)
				numOf2++;
			else if(other == 0)
				other = setsTileCounts.get(i);
			else
				other2 = setsTileCounts.get(i);
		}
			
		// System.out.println(setsTileCounts.toString());
		
		if(numOf4 == 3) {
			if(numOf3 == 1) {					// 4 4 3(1) 3
				return 14;
			} else {
				if(numOf2 > 0)					// 4 4 3(1) 2
					return 13;
				else							// 4 4 3(1)
					return 11;
			}
		} else if(numOf4 == 2) {
			if(numOf3 == 2) {					// 4 4 3 3
				return 14;
			} else if(numOf3 == 1) {
				if(numOf2 > 0)					// 4 4 3 2...
					return 13;
				else							// 4 4 3
					return 11;
			} else if(numOf3 == 0) {
				if(other > 0) {					// 4 4 (5<other<8)
					return 14;
				} else {
					if(numOf2 >= 2)				// 4 4 2 2
						return 12;
					else if(numOf2 == 1)		// 4 4 2
						return 10;
					else						// 4 4
						return 8;
				}
			}
		} else if(numOf4 == 1) {
			if(numOf3 == 2) {
				if(numOf5 == 1)					// 5 3(1) 3 3
					return 14;
				else if(numOf2 > 0)				// 4 3 3 2...
					return 12;
				else							// 4 3 3
					return 10;
			} else if(numOf3 == 1) {
				if(numOf5 == 1) {				// 5 3(1) 3
					return 11;
				} else if(other > 0) {			// 4 3 (5<other<9)
					if(other == 6)				// 4 3 (3 3)
						return 13;
					else						// 4 3 (4 3)
						return 14;
				} else {
					if(numOf2 >= 2)				// 4 3 2 2
						return 11;
					else if(numOf2 == 1)		// 4 3 2
						return 9;
					else						// 4 3
						return 7;
				}
						
			} else if(numOf3 == 0) {
				if(numOf5 == 2) {				// 5 3(2) 3(1)
					return 13;
				} else if(numOf5 == 1) {
					if(other > 0)				// 5 4 (3 3)
						return 14;
					else
						if(numOf2 >= 2)			// 4(1) 4 2 2
							return 12;
						else if(numOf2 == 1)	// 4(1) 4 2
							return 10;
						else					// 4(1) 4
							return 8;
				} else {
					if(other > 0) {				// 4 (5<other<12)
						if(other == 6)			// 4 (3 3)
							return 10;
						else if(other < 9)		// 4 (4 3)
							return 11;
						else if(other < 10)		// 4 (4 3 2)
							return 13;
						else					// 4 (4 3 3)
							return 14;
					} else {
						if(numOf2 >= 3)			// 4 2 2 2
							return 10;
						else if(numOf2 == 2)	// 4 2 2
							return 8;
						else if(numOf2 == 1)	// 4 2
							return 6;
						else					// 4
							return 4;
					}
				}
			}
		} else {
			if(numOf5 == 3) {					// 5 3(2) 3(2)
				return 13;
			} else if(numOf5 == 2) {
				if(numOf3 == 1)					// 5 3(2) 3
					return 13;
				else
					if(numOf2 >= 1)				// 5 3(2) 2
						return 12;
					else						// 5 3(2)
						return 10;
			} else if(numOf5 == 1) {
				if(other > 0)					// 5 (5<other<11)
					if(other <= 7)
						if(numOf3 == 1)			// 5 (3 3) 3
							return 14;
						else
							if(numOf2 >= 1)		// 5 (3 3) 2
								return 13;
							else				// 5 (3 3)
								return 11;
					else if(other == 8)			// 5 (3 3 (2))
						return 13;
					else						// 5 (3 3 3)
						return 14;
			} else {
				if(other2 > 0) {
					if(other2 == 6) {
						if(other == 6) {		// (3 3) (3 3)
							return 12;
						} else if(other == 7) {	// (3 3) (4 3)
							return 13;
						} else {	// (3 3) (4 4)
							return 14;
						}
					} else if(other2 == 7) {
						if(other == 6) {		// (4 3) (3 3)
							return 13;
						} else {				// (4 3) (4 3)
							return 14;
						}
					} else						// (4 4) (3 3)
						return 14;
				} else {
					if(other > 0) {
						if(other == 6) {
							if(numOf3 == 3) {				// (4 (2)) 3 3 3
								return 13;
							} else if(numOf3 == 2) {		// (4 (2)) 3 3
								return 12;
							} else if(numOf3 == 1) {
								if(numOf2 > 0) {			// (4 (2)) 3 2
									return 11;
								} else {					// (4 (2)) 3
									return 9;
								}
							} else {
								if(numOf2 >= 2) {			// (4 (2)) 2 2
									return 10;
								} else if(numOf2 == 1) {	// (4 (2)) 2
									return 8;
								} else {					// (4 (2))
									return 6;
								}
							}
						} else if(other == 7) {
							if(numOf3 == 3) {				// (5 (2)) 3 3 3
								return 14;
							} else if(numOf3 == 2) {		// (5 (2)) 3 3
								return 13;
							} else if(numOf3 == 1) {
								if(numOf2 > 0) {			// (5 (2)) 3 2
									return 12;
								} else {					// (5 (2)) 3
									return 10;
								}
							} else {
								if(numOf2 >= 2) {			// (5 (2)) 2 2
									return 11;
								} else if(numOf2 == 1) {	// (5 (2)) 2
									return 9;
								} else {					// (5 (2))
									return 7;
								}
							}
						} else if(other <= 9) {
							if(numOf3 == 2) {				// (5 3) 3 3
								return 14;
							} else if(numOf3 == 1) {
								if(numOf2 > 0) {			// (5 3) 3 2
									return 13;
								} else {					// (5 3) 3
									return 11;
								}
							} else {
								if(numOf2 >= 2) {			// (5 3) 2 2
									return 12;
								} else if(numOf2 == 1) {	// (5 3) 2
									return 10;
								} else {					// (5 3)
									return 8;
								}
							}
						} else if(other == 10) {
							if(numOf3 == 1) {				// (5 3 (2)) 3
								return 13;
							} else {
								if(numOf2 == 1) {			// (5 3 (2)) 2
									return 12;
								} else {					// (5 3 (2))
									return 10;
								}
							}
						} else if(other <= 12) {
							if(numOf3 == 1) {				// (5 3 3) 3
								return 14;
							} else {
								if(numOf2 == 1) {			// (5 3 3) 2
									return 13;
								} else {					// (5 3 3)
									return 11;
								}
							}
						} else if(other == 13) {			// (5 3 3 (2))
							return 13;
						} else {							// (5 3 3 3)
							return 14;
						}
					} else {
						if(numOf3 >= 4) {					// 3 3 3 3
							return 12;
						} else if(numOf3 == 3) {
							if(numOf2 > 0) {				// 3 3 3 2
								return 11;
							} else {						// 3 3 3
								return 9;
							}
						} else if(numOf3 == 2) {
							if(numOf2 >= 2) {				// 3 3 2 2
								return 10;
							} else if(numOf2 == 1) {		// 3 3 2					
								return 8;
							} else {						// 3 3
								return 6;
							}
						} else if(numOf3 == 1) {
							if(numOf2 >= 3) {				// 3 2 2 2
								return 9;
							} else if(numOf2 == 2) {		// 3 2 2				
								return 7;
							} else if(numOf2 == 1) {		// 3 2					
								return 6;
							} else {						// 3
								return 3;
							}
						} else {
							if(numOf2 >= 4) {				// 2 2 2 2
								return 8;
							} else if(numOf2 == 3) {		// 2 2 2				
								return 6;
							} else if(numOf2 == 2) {		// 2 2					
								return 4;
							} else if(numOf2 == 1) {		// 2				
								return 2;
							} else {						// -
								return 0;
							}
						}
					}
				}
			}
		}
		
		return -1;
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
	
	public void printOrganizedHand(Tile okey) {
		
		System.out.print("[\n");
		
		for(ArrayList<Tile> t : sets) {
			System.out.println("\t" + t.toString());
		}
		for(int i = 0; i < numOfOkey; i++) {
			System.out.print("\t");
			pp.printGreenString(okey.toString());
			System.out.println();
		}
		for(Tile t : ones) {
			System.out.println("\t" + t.toString());
		}

		System.out.print("]");
	}
}
