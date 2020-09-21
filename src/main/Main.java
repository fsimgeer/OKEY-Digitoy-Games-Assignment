package main;

import gameplay.Okey;
import output.PrettyPrinter;
import static gameplay.Okey.UNIQUE_TILE_C0UNT;

public class Main {

	public static void main(String[] args) {
		printMessage();
		
		int playerCount = 4;
		
		PrettyPrinter pp = new PrettyPrinter();
		Okey okey = new Okey(playerCount);
		
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
		System.out.println();
		
		if(okey.getWinner().size() == 1) {
			System.out.println("Aşağıdaki elle kazanmaya en yakın kişi " + okey.getWinner().get(0).getName() + "!");
			okey.getWinner().get(0).printOrganizedHand(okey.getOkey());
		} else {
			for(int i = 0; i < okey.getWinner().size(); i++) {
				if(i == okey.getWinner().size() - 1) {
					System.out.print(" ve " + okey.getWinner().get(i).getName() + " ");
				} else if(i == okey.getWinner().size() - 2){
					System.out.print(okey.getWinner().get(i).getName() + " ");
				} else {
					System.out.print(okey.getWinner().get(i).getName() + ", ");
				}
			}
			System.out.println("aşağıdaki ellerle kazanmaya en yakın olan kişiler:");
			for(int i = 0; i < okey.getWinner().size(); i++) {
				System.out.println(okey.getWinner().get(i).getName() + ":");
				okey.getWinner().get(i).printOrganizedHand(okey.getOkey());
				System.out.println();
			}
		}
		
	}
	
	public static void printMessage() {
		System.out.println("Merhaba.");
		System.out.println("Aşağıda anlamsız harfler gördüğünüz yerler varsa ([34m gibi), konsolunuz ANSI kodlarını algılayamıyor demektir.");
		System.out.println("Görsel anlamda işinizin biraz daha kolay olmasını, bazı yazıları renkli görebilmeyi istiyorsanız, ANSI plugin'inizi açabilir");
		System.out.println("ya da bu plugin'i indirebilirsiniz. Eclipse kullanıyorsanız bu plugin'e aşağıdaki linkten ulaşabilirsiniz:\n");
		System.out.println("https://marketplace.eclipse.org/content/ansi-escape-console\n");
		System.out.println("****************************************************************************************************************************");
	}
	

}
