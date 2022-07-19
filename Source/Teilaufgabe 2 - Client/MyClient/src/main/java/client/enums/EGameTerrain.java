package client.enums;

public enum EGameTerrain {
	GRASS(1) {
		@Override
		public String toString() {
			return "G";
		}
	},
	WATER(0) {
		@Override
		public String toString() {
			return "W";
		}
	},
	MOUNTAIN(2) {
		@Override
		public String toString() {
			return "M";
		}
	};

	private final int cost;

	private EGameTerrain(final int cost) {
		this.cost = cost;
	}

	public int getCost() {
		return cost;
	}

}
