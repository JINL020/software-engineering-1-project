package server.map;

public class GameMapNode {
	Position position;
	private EGameTerrain terrain;

	public GameMapNode(Position position, EGameTerrain terrain) {
		this.position = position;
		this.terrain = terrain;
	}

	public Position getPosition() {
		return position;
	}

	public EGameTerrain getTerrain() {
		return terrain;
	}

}