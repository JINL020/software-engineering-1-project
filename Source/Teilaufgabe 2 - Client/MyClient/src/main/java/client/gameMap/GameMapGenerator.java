package client.gameMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import client.enums.EGameFortState;
import client.enums.EGameTerrain;

public class GameMapGenerator {
	private Random random = new Random();

	public GameMap generateGameHalfMap() {
		GameMap gameHalfMap = createEmptyGrassHalfMap();

		placeFortress(gameHalfMap);
		placeWaterFields(gameHalfMap);
		placeMountainFields(gameHalfMap, 5);
		return gameHalfMap;
	}

	public GameMap createEmptyGrassHalfMap() {
		Map<Position, GameMapNode> emptyGrassMap = new HashMap<Position, GameMapNode>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				Position pos = new Position(j, i);
				GameMapNode grasNode = new GameMapNode(pos, EGameTerrain.GRASS);
				emptyGrassMap.put(pos, grasNode);
			}
		}
		return new GameMap(emptyGrassMap);
	}

	private void placeFortress(GameMap gameHalfMap) {
		gameHalfMap.getNode(new Position(random.nextInt(1) + 3, random.nextInt(1) + 1))
				.setFortState(EGameFortState.MyFortPresent);
	}

	private void placeWaterFields(GameMap gameHalfMap) {
		switch (random.nextInt(2)) {
		case 0:
			gameHalfMap.getNode(new Position(1, 0)).setTerrain(EGameTerrain.WATER);
			gameHalfMap.getNode(new Position(1, 3)).setTerrain(EGameTerrain.WATER);
			break;
		case 1:
			gameHalfMap.getNode(new Position(1, 0)).setTerrain(EGameTerrain.WATER);
			gameHalfMap.getNode(new Position(1, 1)).setTerrain(EGameTerrain.WATER);
			break;
		case 2:
			gameHalfMap.getNode(new Position(1, 2)).setTerrain(EGameTerrain.WATER);
			gameHalfMap.getNode(new Position(1, 3)).setTerrain(EGameTerrain.WATER);
			break;
		}

		switch (random.nextInt(2)) {
		case 0:
			gameHalfMap.getNode(new Position(6, 0)).setTerrain(EGameTerrain.WATER);
			gameHalfMap.getNode(new Position(6, 3)).setTerrain(EGameTerrain.WATER);
			break;
		case 1:
			gameHalfMap.getNode(new Position(6, 0)).setTerrain(EGameTerrain.WATER);
			gameHalfMap.getNode(new Position(6, 1)).setTerrain(EGameTerrain.WATER);
			break;
		case 2:
			gameHalfMap.getNode(new Position(6, 2)).setTerrain(EGameTerrain.WATER);
			gameHalfMap.getNode(new Position(6, 3)).setTerrain(EGameTerrain.WATER);
			break;
		}

	}

	// this function set random grass fields to mountains fields
	private void placeMountainFields(GameMap gameHalfMap, int randomMountains) {
		for (GameMapNode node : gameHalfMap.getAllNodes()) {
			if (node.getPosition().getX() == 0 || node.getPosition().getX() == 7) {
				node.setTerrain(EGameTerrain.MOUNTAIN);
			}
		}
		for (int i = 0; i < randomMountains; i++) {
			List<GameMapNode> grassNodes = gameHalfMap.getAllGrasNodes();
			grassNodes.remove(gameHalfMap.getNode(gameHalfMap.getFortPosition()));
			grassNodes.get(random.nextInt(grassNodes.size())).setTerrain(EGameTerrain.MOUNTAIN);
		}
	}
}
