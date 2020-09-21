package gameplay;

import java.util.concurrent.ThreadLocalRandom;

import output.PrettyPrinter;

public class Okey {
	
	// variables
	final static ThreadLocalRandom random = ThreadLocalRandom.current();
	
	public final static int EACH_COLORED_TILE_C0UNT = 13;
	public final static int UNIQUE_TILE_C0UNT = EACH_COLORED_TILE_C0UNT * 4 + 1;	//= 53
	
	Tile[] tiles;
	Player[] players;
	int playerCount = 2;
	Tile okey;
	Tile faceUpTile = new Tile(TileType.FalseJoker, 0);
	int firstDie = 0;
	int secondDie = 0;
	
	PrettyPrinter pp = new PrettyPrinter();
	
	// constructor
	public Okey(int playerCount) {
		this.playerCount = playerCount;
		
		// Create tiles
		// Create false joker as (TileColor = FalseJoker, number = 0(or any))
		tiles = new Tile[UNIQUE_TILE_C0UNT * 2];

		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < EACH_COLORED_TILE_C0UNT; j++) {
				tiles[(i * UNIQUE_TILE_C0UNT) + j] = new Tile(TileType.Yellow, j + 1);
			}
			for(int j = 0; j < EACH_COLORED_TILE_C0UNT; j++) {
				tiles[(i * UNIQUE_TILE_C0UNT) + j + EACH_COLORED_TILE_C0UNT] = new Tile(TileType.Blue, j + 1);
			}
			for(int j = 0; j < EACH_COLORED_TILE_C0UNT; j++) {
				tiles[(i * UNIQUE_TILE_C0UNT) + j + EACH_COLORED_TILE_C0UNT * 2] = new Tile(TileType.Black, j + 1);
			}
			for(int j = 0; j < EACH_COLORED_TILE_C0UNT; j++) {
				tiles[(i * UNIQUE_TILE_C0UNT) + j + EACH_COLORED_TILE_C0UNT * 3] = new Tile(TileType.Red, j + 1);
			}
			tiles[(i * UNIQUE_TILE_C0UNT) + EACH_COLORED_TILE_C0UNT * 4] = new Tile(TileType.FalseJoker, 0);
		}
		
		shuffleTileArray(tiles);	// Shuffle tile array
		
		while(faceUpTile.getType() == TileType.FalseJoker) {
			// The first 6 elements of the array is the first stack, the rest are stacks of 5
			firstDie = random.nextInt(6) + 1;	// 1 to 6
			secondDie = random.nextInt(6) + 1;
			
			if(secondDie == 6) {	// Find the face up tile
				faceUpTile = tiles[0];
			} else {
				faceUpTile = tiles[1 + (firstDie - 1) * 5 + (5 - secondDie)];
			}
		}

		if(faceUpTile.getNumber() - 1 % 13 == 12) {	// Find joker (okey)
			okey = new Tile(faceUpTile.getType(), faceUpTile.getNumber() - 12);
		} else {
			okey = new Tile(faceUpTile.getType(), faceUpTile.getNumber() + 1);			
		}
		
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
				
		players[0].matchTiles(okey);
		//players[1].matchTiles(okey);
		//players[2].matchTiles(okey);
		//players[3].matchTiles(okey);
	}
	
	// methods	
	public Tile[] getTiles() {
		return tiles;
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public Tile getOkey() {
		return okey;
	}
	
	public Tile getFaceUpTile() {
		return faceUpTile;
	}
	
	public int getFirstDie() {
		return firstDie;
	}
	
	public int getSecondDie() {
		return secondDie;
	}
	
	
	// Fisher - Yates Shuffle Algorithm
	public void shuffleTileArray(Tile[] tiles) {
		int index;
		Tile temp;
	    
	    for(int i = tiles.length - 1; i > 0; i--) {
	        index = random.nextInt(i + 1);
	        temp = tiles[index];
	        tiles[index] = tiles[i];
	        tiles[i] = temp;
	    }
	}
	
	public void shufflePlayerArray(Player[] players) {
		int index;
		Player tempPlayer;
	    
	    for(int i = players.length - 1; i > 0; i--) {
	        index = random.nextInt(i + 1);
	        tempPlayer = players[index];
	        players[index] = players[i];
			players[i] = tempPlayer;
		}
	}
}
