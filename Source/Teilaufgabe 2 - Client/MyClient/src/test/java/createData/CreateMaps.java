package createData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.enums.EGameFortState;
import client.enums.EGameTerrain;
import client.gameMap.GameMap;
import client.gameMap.GameMapGenerator;
import client.gameMap.GameMapNode;
import client.gameMap.Position;

public class CreateMaps {
	public static GameMap getHalfMap0() {
		GameMapGenerator gameGen = new GameMapGenerator();

		GameMap map0 = gameGen.createEmptyGrassHalfMap();

		map0.getNode(new Position(3, 2)).setFortState(EGameFortState.MyFortPresent);

		map0.getNode(new Position(0, 1)).setTerrain(EGameTerrain.MOUNTAIN);
		map0.getNode(new Position(0, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map0.getNode(new Position(1, 0)).setTerrain(EGameTerrain.MOUNTAIN);
		map0.getNode(new Position(1, 1)).setTerrain(EGameTerrain.MOUNTAIN);
		map0.getNode(new Position(1, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map0.getNode(new Position(3, 1)).setTerrain(EGameTerrain.MOUNTAIN);
		map0.getNode(new Position(4, 0)).setTerrain(EGameTerrain.MOUNTAIN);
		map0.getNode(new Position(4, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map0.getNode(new Position(5, 0)).setTerrain(EGameTerrain.MOUNTAIN);
		map0.getNode(new Position(5, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map0.getNode(new Position(6, 1)).setTerrain(EGameTerrain.MOUNTAIN);
		map0.getNode(new Position(6, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map0.getNode(new Position(7, 3)).setTerrain(EGameTerrain.MOUNTAIN);

		map0.getNode(new Position(2, 3)).setTerrain(EGameTerrain.WATER);
		map0.getNode(new Position(3, 3)).setTerrain(EGameTerrain.WATER);
		map0.getNode(new Position(4, 3)).setTerrain(EGameTerrain.WATER);
		map0.getNode(new Position(0, 0)).setTerrain(EGameTerrain.WATER);

		return map0;
	}

	public static GameMap getHalfMap1() {
		GameMapGenerator gameGen = new GameMapGenerator();

		GameMap map1 = gameGen.createEmptyGrassHalfMap();

		map1.getNode(new Position(4, 2)).setFortState(EGameFortState.MyFortPresent);

		map1.getNode(new Position(0, 1)).setTerrain(EGameTerrain.MOUNTAIN);
		map1.getNode(new Position(1, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map1.getNode(new Position(1, 3)).setTerrain(EGameTerrain.MOUNTAIN);
		map1.getNode(new Position(2, 1)).setTerrain(EGameTerrain.MOUNTAIN);
		map1.getNode(new Position(2, 3)).setTerrain(EGameTerrain.MOUNTAIN);
		map1.getNode(new Position(3, 1)).setTerrain(EGameTerrain.MOUNTAIN);
		map1.getNode(new Position(3, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map1.getNode(new Position(5, 1)).setTerrain(EGameTerrain.MOUNTAIN);
		map1.getNode(new Position(6, 0)).setTerrain(EGameTerrain.MOUNTAIN);
		map1.getNode(new Position(6, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map1.getNode(new Position(6, 3)).setTerrain(EGameTerrain.MOUNTAIN);
		map1.getNode(new Position(7, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map1.getNode(new Position(7, 3)).setTerrain(EGameTerrain.MOUNTAIN);

		map1.getNode(new Position(0, 0)).setTerrain(EGameTerrain.WATER);
		map1.getNode(new Position(3, 3)).setTerrain(EGameTerrain.WATER);
		map1.getNode(new Position(4, 3)).setTerrain(EGameTerrain.WATER);
		map1.getNode(new Position(5, 3)).setTerrain(EGameTerrain.WATER);

		return map1;
	}

	public static GameMap getHalfMap3() {
		Map<Position, GameMapNode> emptyGrassMap = new HashMap<Position, GameMapNode>();
		for (int i = 0; i < 4; i++) {
			for (int j = 8; j < 16; j++) {
				Position pos = new Position(j, i);
				GameMapNode grasNode = new GameMapNode(pos, EGameTerrain.GRASS);
				emptyGrassMap.put(pos, grasNode);
			}
		}

		GameMap map3 = new GameMap(emptyGrassMap);

		map3.getNode(new Position(11, 2)).setFortState(EGameFortState.EnemyFortPresent);

		map3.getNode(new Position(8, 3)).setTerrain(EGameTerrain.MOUNTAIN);
		map3.getNode(new Position(9, 1)).setTerrain(EGameTerrain.MOUNTAIN);
		map3.getNode(new Position(9, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map3.getNode(new Position(10, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map3.getNode(new Position(11, 0)).setTerrain(EGameTerrain.MOUNTAIN);
		map3.getNode(new Position(12, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map3.getNode(new Position(13, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map3.getNode(new Position(14, 0)).setTerrain(EGameTerrain.MOUNTAIN);
		map3.getNode(new Position(14, 1)).setTerrain(EGameTerrain.MOUNTAIN);
		map3.getNode(new Position(14, 2)).setTerrain(EGameTerrain.MOUNTAIN);
		map3.getNode(new Position(15, 0)).setTerrain(EGameTerrain.MOUNTAIN);
		map3.getNode(new Position(15, 1)).setTerrain(EGameTerrain.MOUNTAIN);
		map3.getNode(new Position(15, 3)).setTerrain(EGameTerrain.MOUNTAIN);

		map3.getNode(new Position(8, 0)).setTerrain(EGameTerrain.WATER);
		map3.getNode(new Position(10, 3)).setTerrain(EGameTerrain.WATER);
		map3.getNode(new Position(11, 3)).setTerrain(EGameTerrain.WATER);
		map3.getNode(new Position(12, 3)).setTerrain(EGameTerrain.WATER);

		return map3;
	}

	public static GameMap getHalfMap4() {
		Map<Position, GameMapNode> emptyGrassMap = new HashMap<Position, GameMapNode>();
		for (int i = 4; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Position pos = new Position(j, i);
				GameMapNode grasNode = new GameMapNode(pos, EGameTerrain.GRASS);
				emptyGrassMap.put(pos, grasNode);
			}
		}

		GameMap map4 = new GameMap(emptyGrassMap);

		map4.getNode(new Position(4, 6)).setFortState(EGameFortState.EnemyFortPresent);

		map4.getNode(new Position(0, 5)).setTerrain(EGameTerrain.MOUNTAIN);
		map4.getNode(new Position(1, 6)).setTerrain(EGameTerrain.MOUNTAIN);
		map4.getNode(new Position(1, 7)).setTerrain(EGameTerrain.MOUNTAIN);
		map4.getNode(new Position(2, 5)).setTerrain(EGameTerrain.MOUNTAIN);
		map4.getNode(new Position(2, 7)).setTerrain(EGameTerrain.MOUNTAIN);
		map4.getNode(new Position(3, 5)).setTerrain(EGameTerrain.MOUNTAIN);
		map4.getNode(new Position(3, 6)).setTerrain(EGameTerrain.MOUNTAIN);
		map4.getNode(new Position(5, 5)).setTerrain(EGameTerrain.MOUNTAIN);
		map4.getNode(new Position(6, 4)).setTerrain(EGameTerrain.MOUNTAIN);
		map4.getNode(new Position(6, 6)).setTerrain(EGameTerrain.MOUNTAIN);
		map4.getNode(new Position(6, 7)).setTerrain(EGameTerrain.MOUNTAIN);
		map4.getNode(new Position(7, 6)).setTerrain(EGameTerrain.MOUNTAIN);
		map4.getNode(new Position(7, 7)).setTerrain(EGameTerrain.MOUNTAIN);

		map4.getNode(new Position(0, 4)).setTerrain(EGameTerrain.WATER);
		map4.getNode(new Position(3, 7)).setTerrain(EGameTerrain.WATER);
		map4.getNode(new Position(4, 7)).setTerrain(EGameTerrain.WATER);
		map4.getNode(new Position(5, 7)).setTerrain(EGameTerrain.WATER);

		return map4;
	}

	public static GameMap getFullMap0() {
		List<GameMapNode> nodes = new ArrayList<GameMapNode>();
		nodes.addAll(getHalfMap0().getAllNodes());
		nodes.addAll(getHalfMap3().getAllNodes());
		GameMap fullMap0 = new GameMap(nodes);

		return fullMap0;
	}

	public static GameMap getFullMap1() {
		List<GameMapNode> nodes = new ArrayList<GameMapNode>();
		nodes.addAll(getHalfMap0().getAllNodes());
		nodes.addAll(getHalfMap4().getAllNodes());
		GameMap fullMap1 = new GameMap(nodes);

		return fullMap1;
	}
}
