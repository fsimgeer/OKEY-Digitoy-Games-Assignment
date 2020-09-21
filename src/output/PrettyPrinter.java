package output;

import gameplay.Tile;
import gameplay.TileType;

public class PrettyPrinter {
		
	public PrettyPrinter() {
	}
	
	// The following two print functions are more for my own convenience, to check if my code is correct or not easily.
		// But you can see a more visual way of my code this way as well.
		public void printTileList(Tile[] tiles, Tile okey, int UNIQUE_TILE_C0UNT) {
			for(int i = 0; i < UNIQUE_TILE_C0UNT * 2; i++) {
				if(tiles[i].equals(okey)) {
					printGreenString(tiles[i].toString() + "\n");
				} else if(tiles[i].getType() == TileType.FalseJoker) {
					printYellowString(tiles[i].toString() + "n");
				} else {
					System.out.println(tiles[i].toString());
				}
			}
		}
		
		public void printTilesAsStacks(Tile[] tiles, int firstDie, int secondDie, Tile okey, int UNIQUE_TILE_C0UNT) {
			if(secondDie == 6) {
				printBlueString(tiles[0].toString() + "\n");
			} else {
				System.out.println(tiles[0].toString());
			}
			
			int len = 7;
			for(int i = 1; i < UNIQUE_TILE_C0UNT * 2; i+=5) {
				if(secondDie == 5 && firstDie == i / 5 + 1) {
					if(tiles[i].toString().length() > len)
						printBlueString(tiles[i].toString() + "\t");
					else
						printBlueString(tiles[i].toString() + "\t\t");
				} else {
					if(tiles[i].toString().length() > len)
						System.out.print(tiles[i].toString() + "\t");
					else
						System.out.print(tiles[i].toString() + "\t\t");
				}
			}
			System.out.println();
			for(int i = 1; i < UNIQUE_TILE_C0UNT * 2; i+=5) {
				if(secondDie == 4 && firstDie == i / 5 + 1) {
					if(tiles[i + 1].toString().length() > len)
						printBlueString(tiles[i + 1].toString() + "\t");
					else
						printBlueString(tiles[i + 1].toString() + "\t\t");
				} else {
					if(tiles[i + 1].toString().length() > len)
						System.out.print(tiles[i + 1].toString() + "\t");
					else
						System.out.print(tiles[i + 1].toString() + "\t\t");
				}
			}
			System.out.println();
			for(int i = 1; i < UNIQUE_TILE_C0UNT * 2; i+=5) {
				if(secondDie == 3 && firstDie == i / 5 + 1) {
					if(tiles[i + 2].toString().length() > len)
						printBlueString(tiles[i + 2].toString() + "\t");
					else
						printBlueString(tiles[i + 2].toString() + "\t\t" );
				} else {
					if(tiles[i + 2].toString().length() > len)
						System.out.print(tiles[i + 2].toString() + "\t");
					else
						System.out.print(tiles[i + 2].toString() + "\t\t");
				}
			}
			System.out.println();
			for(int i = 1; i < UNIQUE_TILE_C0UNT * 2; i+=5) {
				if(secondDie == 2 && firstDie == i / 5 + 1) {
					if(tiles[i + 3].toString().length() > len)
						printBlueString(tiles[i + 3].toString() + "\t");
					else
						printBlueString(tiles[i + 3].toString() + "\t\t");
				} else {
					if(tiles[i + 3].toString().length() > len)
						System.out.print(tiles[i + 3].toString() + "\t");
					else
						System.out.print(tiles[i + 3].toString() + "\t\t");
				}
			}
			System.out.println();
			for(int i = 1; i < UNIQUE_TILE_C0UNT * 2; i+=5) {
				if(secondDie == 1 && firstDie == i / 5 + 1) {
					if(tiles[i + 4].toString().length() > len)
						printBlueString(tiles[i + 4].toString() + "\t");
					else
						printBlueString(tiles[i + 4].toString() + "\t\t");
				} else {
					if(tiles[i + 4].toString().length() > len)
						System.out.print(tiles[i + 4].toString() + "\t");
					else
						System.out.print(tiles[i + 4].toString() + "\t\t");
				}
			}
			System.out.println("\n");
		}
				
		public void printGreenString(String s) {
			System.out.print("\033[32m" + s + "\033[0m");
		}
		
		public void printYellowString(String s) {
			System.out.print("\033[33m" + s + "\033[0m");
		}

		public void printBlueString(String s) {
			System.out.print("\033[34m" + s + "\033[0m");
		}
}
