package server.mapValidator;

import MessagesBase.ETerrain;
import MessagesBase.HalfMap;
import MessagesBase.HalfMapNode;

// true == water
// false == grass or mountain (is the default)
public class HalfMapValidator {
	public static boolean check(HalfMap halfMap) {

		boolean mapSizeRequirement = checkMapSizeRequirement(halfMap);
		boolean terrainRequirement = checkTerrainRequirement(halfMap);
		boolean noIslandRequirement = checkNoIslandRequirement(halfMap);
		boolean waterEdgeRequirement = checkWaterEdgeRequirement(halfMap);
		boolean fortOnGrassRequirement = checkFortOnGrassRequirement(halfMap);

		if (mapSizeRequirement && terrainRequirement && noIslandRequirement && waterEdgeRequirement
				&& fortOnGrassRequirement) {
			return true;
		}

		return false;
	}

	private static boolean checkMapSizeRequirement(HalfMap halfMap) {
		if (halfMap.getMapNodes().size() == 32) {
			return true;
		}
		return false;
	}

	private static boolean checkTerrainRequirement(HalfMap halfMap) {
		int grasCounter = 0;
		int mountainCounter = 0;
		int waterCounter = 0;

		for (HalfMapNode node : halfMap.getMapNodes()) {
			ETerrain terrain = node.getTerrain();
			if (terrain.equals(ETerrain.Grass)) {
				grasCounter++;
			}
			if (terrain.equals(ETerrain.Mountain)) {
				mountainCounter++;
			}
			if (terrain.equals(ETerrain.Water)) {
				waterCounter++;
			}
		}

		if (grasCounter >= 15 && mountainCounter >= 3 && waterCounter >= 4) {
			return true;
		}
		return false;
	}

	private static boolean checkNoIslandRequirement(HalfMap halfMap) {
		boolean[][] waterMap = getWaterMap(halfMap);

		HalfMapNode grasNode = getGrasNode(halfMap);

		int accesibleFieldsCount = FloodFill.getAccesibleNodesCount(waterMap, grasNode.getX(), grasNode.getY());
		if (accesibleFieldsCount == 32) {
			return true;
		}

		return false;
	}

	private static boolean checkWaterEdgeRequirement(HalfMap halfMap) {
		boolean[][] waterMap = getWaterMap(halfMap);

		int waterCounterUpperEdge = 0;
		for (boolean x : waterMap[0]) {
			if (x == true)
				waterCounterUpperEdge++;
		}

		int waterCounterLowerEdge = 0;
		for (boolean x : waterMap[3]) {
			if (x == true)
				waterCounterLowerEdge++;
		}

		int waterCounterLeftEdge = 0;
		int waterCounterRightEdge = 0;
		for (boolean[] y : waterMap) {
			if (y[0] == true)
				waterCounterLeftEdge++;
			if (y[7] == true)
				waterCounterRightEdge++;
		}

		if (waterCounterLeftEdge >= 1)
			return false;
		if (waterCounterRightEdge >= 1)
			return false;
		if (waterCounterUpperEdge >= 3)
			return false;
		if (waterCounterLowerEdge >= 3)
			return false;
		return true;
	}

	private static boolean checkFortOnGrassRequirement(HalfMap halfMap) {
		for (HalfMapNode node : halfMap.getMapNodes()) {
			if (node.isFortPresent() && node.getTerrain().equals(ETerrain.Grass)) {
				return true;
			}
		}
		return false;
	}

	private static boolean[][] getWaterMap(HalfMap halfMap) {
		boolean[][] waterMap = new boolean[4][8];

		for (HalfMapNode node : halfMap.getMapNodes()) {
			if (node.getTerrain().equals(ETerrain.Water)) {
				waterMap[node.getY()][node.getX()] = true;
			}
		}

		return waterMap;
	}

	private static HalfMapNode getGrasNode(HalfMap halfMap) {

		for (HalfMapNode node : halfMap.getMapNodes()) {
			if (node.getTerrain().equals(ETerrain.Grass)) {
				return node;
			}
		}
		return null;
	}

}
