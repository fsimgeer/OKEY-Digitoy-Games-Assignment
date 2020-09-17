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
		
		Tile faceUpTile;
		if(secondDie == 6) {	// Find the face up tile
			faceUpTile = tiles[0];
		} else {
			faceUpTile = tiles[1 + (firstDie - 1) * 5 + (5 - secondDie)];
		}
		
		System.out.println("Gösterge: " + faceUpTile.toString());
		
		Tile okey;
		
		if(faceUpTile.getNumber() - 1 % 13 == 12) {	// Find joker (okey)
			okey = new Tile(faceUpTile.getColor(), faceUpTile.getNumber() - 12);
		} else {
			okey = new Tile(faceUpTile.getColor(), faceUpTile.getNumber() + 1);			
		}
		
		System.out.println("\033[32m" + "Okey: " + okey.toString() + "\033[0m" + "\n");

		//printTileList(tiles, okey);
		
		// Create players
		players = new Player[playerCount];
		for(int i = 0; i < playerCount; i++) {
			players[i] = new Player("Oyuncu " + (i + 1));
		}
		
		shufflePlayerArray(players);
		
		// Add the next 15 tiles to first player
		int temp = 1 + (firstDie) * 5;
		for(int i = 0; i < 15; i++) {
			players[0].addTile(tiles[temp]);
			temp++;
		}
		
		// Add 14 tiles each to the rest, in order
		for(int i = 0; i < playerCount - 1; i++) {
			for(int j = 0; j < 14; j++) {
				players[i + 1].addTile(tiles[temp]);
				temp++;
			}
		}
		
		for(int i = 0; i < playerCount; i++) {
			players[i].print(okey);
		}
		System.out.println();
		
		players[0].matchTiles(okey);
		players[1].matchTiles(okey);
		players[2].matchTiles(okey);
		players[3].matchTiles(okey);

	}
	
	
	
	
	
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
