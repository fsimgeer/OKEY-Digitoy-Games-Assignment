package main;

import java.awt.Color;
import java.util.Random;

import gameplay.Player;
import gameplay.Tile;
import gameplay.TileColor;

public class Main {
	final static int EACH_COLORED_TILE_C0UNT = 13;
	final static int UNIQUE_TILE_C0UNT = EACH_COLORED_TILE_C0UNT * 4 + 1;	//= 53

	public static void main(String[] args) {
		
		Tile[] tiles;
		Player[] players;
		int playerCount = 4;
		
		// Create tiles
		// Create false joker as (TileColor = FalseJoker, number = 0)
		tiles = new Tile[UNIQUE_TILE_C0UNT * 2];

		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < EACH_COLORED_TILE_C0UNT; j++) {
				tiles[(i * UNIQUE_TILE_C0UNT) + j] = new Tile(TileColor.Sarı, j + 1);
			}
			for(int j = 0; j < EACH_COLORED_TILE_C0UNT; j++) {
				tiles[(i * UNIQUE_TILE_C0UNT) + j + EACH_COLORED_TILE_C0UNT] = new Tile(TileColor.Mavi, j + 1);
			}
			for(int j = 0; j < EACH_COLORED_TILE_C0UNT; j++) {
				tiles[(i * UNIQUE_TILE_C0UNT) + j + EACH_COLORED_TILE_C0UNT * 2] = new Tile(TileColor.Siyah, j + 1);
			}
			for(int j = 0; j < EACH_COLORED_TILE_C0UNT; j++) {
				tiles[(i * UNIQUE_TILE_C0UNT) + j + EACH_COLORED_TILE_C0UNT * 3] = new Tile(TileColor.Kırmızı, j + 1);
			}
			tiles[(i * UNIQUE_TILE_C0UNT) + EACH_COLORED_TILE_C0UNT * 4] = new Tile(TileColor.FalseJoker, 0);
		}
		
		shuffleTileArray(tiles);	// Shuffle tile array
		
		// The first 6 elements of the array is the first stack, the rest are stacks of 5
		Random random = new Random();
		int firstDie = random.nextInt(6) + 1;	// 1 to 6
		int secondDie = random.nextInt(6) + 1;
		
		System.out.println("İlk zar: " + firstDie);
		System.out.println("İkinci zar: " + secondDie + "\n");
		
		Tile faceUpTile;
		if(secondDie == 6) {	// Find the face up tile
			faceUpTile = tiles[0];
		} else {
			faceUpTile = tiles[1 + (firstDie - 1) * 5 + (5 - secondDie)];
		}
		
		System.out.println("\033[34m" + "Gösterge: " + faceUpTile.toString() + "\033[0m" );
		
		Tile okey;
		
		if(faceUpTile.getNumber() - 1 % 13 == 12) {	// Find joker (okey)
			okey = new Tile(faceUpTile.getColor(), faceUpTile.getNumber() - 12);
		} else {
			okey = new Tile(faceUpTile.getColor(), faceUpTile.getNumber() + 1);			
		}
		
		System.out.println("\033[32m" + "Okey: " + okey.toString() + "\033[0m" + "\n");

		//printTileList(tiles, okey);
		printTilesAsStacks(tiles, firstDie, secondDie, okey);
		
		// Create players
		players = new Player[playerCount];
		for(int i = 0; i < playerCount; i++) {
			players[i] = new Player("Oyuncu " + (i + 1));
		}
		
		shufflePlayerArray(players);
		
		// Add 1 stack of tiles to each player, starting from the next stack after the face up tile's stack, repeat it 2 times
		int temp = 1 + (firstDie) * 5;
		for(int k = 0; k < 2; k++) {
			for(int i = 0; i < playerCount; i++) {
				for(int j = 0; j < 5; j++) {
					players[i].addTile(tiles[temp]);
					temp++;
				}
			}
		}
		
		// Add the next stack to the first player
		for(int i = 0; i < 5; i++) {
			players[0].addTile(tiles[temp]);
			temp++;
		}
		
		// Add 4 tiles to the rest of the players
		for(int i = 0; i < playerCount - 1; i++) {
			for(int j = 0; j < 4; j++) {
				players[i + 1].addTile(tiles[temp]);
				temp++;
			}
		}
		
		for(int i = 0; i < playerCount; i++) {
			players[i].print(okey);
		}
		System.out.println();
		
		players[0].matchTiles(okey);
		//players[1].matchTiles(okey);
		//players[2].matchTiles(okey);
		//players[3].matchTiles(okey);

	}
	
	// The following two print functions are more for my own convenience, to check if my code is correct or not easily.
	// But you can see a more visual way of my code this way as well.
	public static void printTileList(Tile[] tiles, Tile okey) {
		for(int i = 0; i < UNIQUE_TILE_C0UNT * 2; i++) {
			if(tiles[i].equals(okey)) {
				System.out.println("\033[32m" + tiles[i].toString() + "\033[0m");
			} else if(tiles[i].getColor() == TileColor.FalseJoker) {
				System.out.println("\033[33m" + tiles[i].toString() + "\033[0m");
			} else {
				System.out.println(tiles[i].toString());
			}
		}
	}
	
	public static void printTilesAsStacks(Tile[] tiles, int firstDie, int secondDie, Tile okey) {
		if(secondDie == 6) {
			System.out.println("\033[34m" + tiles[0].toString() + "\033[0m");
		} else {
			System.out.println(tiles[0].toString());
		}
		
		int len = 7;
		for(int i = 1; i < UNIQUE_TILE_C0UNT * 2; i+=5) {
			if(secondDie == 5 && firstDie == i / 5 + 1) {
				if(tiles[i].toString().length() > len)
					System.out.print("\033[34m" + tiles[i].toString() + "\t" + "\033[0m");
				else
					System.out.print("\033[34m" + tiles[i].toString() + "\t\t" + "\033[0m");
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
					System.out.print("\033[34m" + tiles[i + 1].toString() + "\t" + "\033[0m");
				else
					System.out.print("\033[34m" + tiles[i + 1].toString() + "\t\t" + "\033[0m");
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
					System.out.print("\033[34m" + tiles[i + 2].toString() + "\t" + "\033[0m");
				else
					System.out.print("\033[34m" + tiles[i + 2].toString() + "\t\t" + "\033[0m");
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
					System.out.print("\033[34m" + tiles[i + 3].toString() + "\t" + "\033[0m");
				else
					System.out.print("\033[34m" + tiles[i + 3].toString() + "\t\t" + "\033[0m");
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
					System.out.print("\033[34m" + tiles[i + 4].toString() + "\t" + "\033[0m");
				else
					System.out.print("\033[34m" + tiles[i + 4].toString() + "\t\t" + "\033[0m");
			} else {
				if(tiles[i + 4].toString().length() > len)
					System.out.print(tiles[i + 4].toString() + "\t");
				else
					System.out.print(tiles[i + 4].toString() + "\t\t");
			}
		}
		System.out.println("\n");
	}
	
	// Fisher - Yates Shuffle Algorithm
	public static void shuffleTileArray(Tile[] tiles) {
		int index;
		Tile temp;
	    Random random = new Random();
	    
	    for(int i = tiles.length - 1; i > 0; i--) {
	        index = random.nextInt(i + 1);
	        temp = tiles[index];
	        tiles[index] = tiles[i];
	        tiles[i] = temp;
	    }
	}
	
	public static void shufflePlayerArray(Player[] players) {
		int index;
		Player tempPlayer;
	    Random random = new Random();
	    
	    for(int i = players.length - 1; i > 0; i--) {
	        index = random.nextInt(i + 1);
	        tempPlayer = players[index];
	        players[index] = players[i];
	        players[i] = tempPlayer;
	    }
	}
}
