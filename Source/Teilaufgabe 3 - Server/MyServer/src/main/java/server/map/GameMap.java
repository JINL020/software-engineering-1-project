package server.map;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
	private List<GameMapNode> gameMap;

	public GameMap(List<GameMapNode> gameMap) {
		this.gameMap = gameMap;
	}

	public GameMapNode getNode(Position pos) {
		GameMapNode ret = null;
		for (GameMapNode node : gameMap) {
			if (node.getPosition().equals(pos)) {
				ret = node;
			}
		}
		return ret;
	}

	public List<GameMapNode> getAllNodes() {
		return gameMap;
	}

	public List<GameMapNode> getAllGrassNodes() {
		List<GameMapNode> grassNodes = new ArrayList<GameMapNode>();
		for (GameMapNode node : gameMap) {
			if (node.getTerrain().equals(EGameTerrain.GRASS)) {
				grassNodes.add(node);
			}
		}
		return grassNodes;
	}

}
