package main;

import gameplay.Okey;
import output.PrettyPrinter;
import static gameplay.Okey.UNIQUE_TILE_C0UNT;

public class Main {

	public static void main(String[] args) {
		
		int playerCount = 4;
		
		PrettyPrinter pp = new PrettyPrinter();
		Okey okey = new Okey(playerCount);
		/*
		System.out.println("İlk zar: " + okey.getFirstDie());
		System.out.println("İkinci zar: " + okey.getSecondDie() + "\n");

		pp.printBlueString("Gösterge: " + okey.getFaceUpTile().toString());
		System.out.println();

		pp.printGreenString("Okey: " + okey.getOkey().toString());
		System.out.println("\n");

		//printTileList(tiles, okey);
		pp.printTilesAsStacks(okey.getTiles(), okey.getFirstDie(), okey.getSecondDie(), okey.getOkey(), UNIQUE_TILE_C0UNT);
		
		for(int i = 0; i < playerCount; i++) {
			okey.getPlayers()[i].print(okey.getOkey());
		}
		System.out.println();*/
	}
	
	
	

}
