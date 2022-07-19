package client.moveGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import client.enums.EGameTerrain;
import client.exceptions.FindPathNodeIsWaterException;
import client.gameMap.GameMap;
import client.gameMap.GameMapNode;
import client.gameMap.Position;

public class PathFinder {

	// this function utilizes uniform cost search to find the shortest path from
	// startNode to endNode
	public static List<Position> findPath(GameMapNode startNode, GameMapNode targetNode, GameMap gameMap) {
		if (startNode.getTerrain().equals(EGameTerrain.WATER) || targetNode.getTerrain().equals(EGameTerrain.WATER)) {
			throw new FindPathNodeIsWaterException(startNode, targetNode, gameMap);
		}
		for (GameMapNode node : gameMap.getAllNodes()) {
			node.setCost(Integer.MAX_VALUE);
			node.setParent(null);
		}
		startNode.setCost(0);

		PriorityQueue<GameMapNode> openSet = new PriorityQueue<GameMapNode>();
		Set<GameMapNode> closedSet = new HashSet<GameMapNode>();

		List<Position> path = new ArrayList<Position>();

		openSet.add(startNode);
		while (openSet.size() > 0) {
			GameMapNode currentNode = openSet.poll();
			if (currentNode.getPosition().equals(targetNode.getPosition())) {
				path = retracePath(startNode, targetNode);
				return path;
			}
			for (GameMapNode neighbor : getNeighbors(currentNode, gameMap)) {
				if (!neighbor.getTerrain().equals(EGameTerrain.WATER) && !closedSet.contains(neighbor)) {
					int newCost = currentNode.getCost() + calcCost(currentNode, neighbor);
					if (newCost < neighbor.getCost()) {
						neighbor.setCost(newCost);
						neighbor.setParent(currentNode);
						openSet.add(neighbor);
					}
				}
			}
			closedSet.add(currentNode);
		}
		return path;
	}

	// this function returns all neighbor nodes (right, left, up, down)
	// and it also considers the fact that edge nodes do not have all 4 neighbors.}
	private static List<GameMapNode> getNeighbors(GameMapNode currentNode, GameMap gameMap) {
		List<GameMapNode> neighbors = new ArrayList<GameMapNode>();
		int currentX = currentNode.getPosition().getX();
		int currentY = currentNode.getPosition().getY();

		if (gameMap.getGameMap().containsKey(new Position(currentX - 1, currentY)))
			neighbors.add(gameMap.getNode(new Position(currentX - 1, currentY)));
		if (gameMap.getGameMap().containsKey(new Position(currentX + 1, currentY)))
			neighbors.add(gameMap.getNode(new Position(currentX + 1, currentY)));
		if (gameMap.getGameMap().containsKey(new Position(currentX, currentY - 1)))
			neighbors.add(gameMap.getNode(new Position(currentX, currentY - 1)));
		if (gameMap.getGameMap().containsKey(new Position(currentX, currentY + 1)))
			neighbors.add(gameMap.getNode(new Position(currentX, currentY + 1)));

		return neighbors;
	}

	// this function calculates the cost from nodeA to NodeB
	private static int calcCost(GameMapNode nodeA, GameMapNode nodeB) {
		int cost = 0;
		cost = nodeA.getTerrain().getCost() + nodeB.getTerrain().getCost();
		return cost;
	}

	// this function gives us the path from startNode to endNode
	private static List<Position> retracePath(GameMapNode startNode, GameMapNode endNode) {
		List<Position> path = new ArrayList<Position>();
		GameMapNode currentNode = endNode;
		while (currentNode.getPosition() != startNode.getPosition()) {
			path.add(currentNode.getPosition());
			currentNode = currentNode.getParent();
		}
		path.add(startNode.getPosition());
		Collections.reverse(path);
		return path;
	}

}
