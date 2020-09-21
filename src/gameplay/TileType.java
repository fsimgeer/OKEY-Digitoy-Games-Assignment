package gameplay;

public enum TileType {
	Yellow("Sarı"), Blue("Mavi"), Black("Siyah"), Red("Kırmızı"), FalseJoker("Sahte Okey");

	private String turkishName;
	
	TileType(String turkishName) {
		this.turkishName = turkishName;
	}
	
	@Override
	public String toString() {
		return turkishName;
	}

}
