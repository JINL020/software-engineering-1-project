package client.moveGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.gameMap.GameMap;
import client.gameMap.GameMapNode;
import client.gameMap.Position;

public class TargetFinder {
	private final static Logger logger = LoggerFactory.getLogger(TargetFinder.class);

	// this function gives us the closest node that uncovers the most fields
	public static GameMapNode getTargetNode(GameMapNode myNode, GameMap toSearchHalfMap,
			Set<GameMapNode> uncoveredNodes, GameMap gameFullMap) {
		List<GameMapNode> higestValueNodes = getHighestValueNodes(toSearchHalfMap, uncoveredNodes);
		GameMapNode bestNode = closestNode(myNode, higestValueNodes, gameFullMap);
		return bestNode;
	}

	// this function gives us all nodes with the highest value
	private static List<GameMapNode> getHighestValueNodes(GameMap toSearchHalfMap, Set<GameMapNode> uncoveredNodes) {
		int maxValue = 0;

		for (GameMapNode node : toSearchHalfMap.getAllNodes()) {
			int value = getNodeValue(node.getPosition(), toSearchHalfMap, uncoveredNodes);
			maxValue = Math.max(maxValue, value);
		}

		List<GameMapNode> highestValueNodes = new ArrayList<GameMapNode>();

		if (maxValue == 1 || maxValue == 2) {
			highestValueNodes.addAll(toSearchHalfMap.getAllGrasNodes());
			highestValueNodes.removeAll(uncoveredNodes);
		} else {
			for (GameMapNode node : toSearchHalfMap.getAllNodes()) {
				int value = getNodeValue(node.getPosition(), toSearchHalfMap, uncoveredNodes);
				if (value == maxValue) {
					highestValueNodes.add(node);
				}
			}
		}
		return highestValueNodes;
	}

	private static int getNodeValue(Position pos, GameMap toSearchHalfMap, Set<GameMapNode> uncoveredNodes) {
		List<GameMapNode> visibleGrasNodes = toSearchHalfMap.getVisibleGrasNodes(pos);

		visibleGrasNodes.removeIf(node -> !toSearchHalfMap.getAllNodes().contains(node));
		visibleGrasNodes.removeAll(uncoveredNodes);

		return visibleGrasNodes.size();
	}

	// this function gives us the closest node using the Manhattan distance
	private static GameMapNode closestNode(GameMapNode start, List<GameMapNode> higestValueNodes, GameMap gameFullMap) {
		GameMapNode clostestNode = null;
		int minDistance = Integer.MAX_VALUE;

		for (GameMapNode node : higestValueNodes) {
			PathFinder.findPath(start, node, gameFullMap);
			int distance = node.getCost();
			minDistance = Math.min(minDistance, distance);
		}
		for (GameMapNode node : higestValueNodes) {
			if (node.getCost() == minDistance)
				clostestNode = node;
		}
		return clostestNode;
	}

}
