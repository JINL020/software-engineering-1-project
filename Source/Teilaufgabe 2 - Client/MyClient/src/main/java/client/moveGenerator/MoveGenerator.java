package client.moveGenerator;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.enums.EGameMove;
import client.gameMap.GameMap;
import client.gameMap.GameMapNode;
import client.gameMap.Position;

public class MoveGenerator {
	private final static Logger logger = LoggerFactory.getLogger(MoveGenerator.class);

	public static Queue<EGameMove> getNextMoves(GameMapNode myNode, Set<GameMapNode> uncoveredNodes,
			GameMap gameFullMap, boolean hasFoundTreausure) {

		GameMap toSearchHalfMap = new GameMap(gameFullMap.getMyMapNodes());
		if (hasFoundTreausure) {
			toSearchHalfMap = new GameMap(gameFullMap.getEnemyMapNodes());
		}
		GameMapNode destination = TargetFinder.getTargetNode(myNode, toSearchHalfMap, uncoveredNodes, gameFullMap);
		logger.info("destination is " + destination.getPosition());

		List<Position> route = PathFinder.findPath(myNode, destination, gameFullMap);
		logger.info("route is " + route);

		Queue<EGameMove> movesToSend = translateToMoveQueue(route, gameFullMap);
		logger.info("movesToSend is " + movesToSend);

		return movesToSend;
	}

	public static Queue<EGameMove> getMovesToDestination(GameMapNode myPos, GameMapNode destination,
			GameMap gameFullMap) {
		logger.info("destination is " + destination.getPosition());
		List<Position> route = PathFinder.findPath(myPos, destination, gameFullMap);
		logger.info("route is " + route);

		Queue<EGameMove> movesToSend = translateToMoveQueue(route, gameFullMap);
		logger.info("movesToSend is " + movesToSend);

		return movesToSend;
	}

	private static Queue<EGameMove> translateToMoveQueue(List<Position> pathPositions, GameMap gameMap) {
		Queue<EGameMove> moveList = new ArrayDeque<EGameMove>();
		for (int i = 0; i < pathPositions.size() - 1; i++) {
			Position node1 = pathPositions.get(i);
			Position node2 = pathPositions.get(i + 1);
			EGameMove direction = getMoveDirection(node1, node2);
			for (int j = 0; j < gameMap.getNode(node1).getTerrain().getCost()
					+ gameMap.getNode(node2).getTerrain().getCost(); j++) {
				moveList.add(direction);
			}
		}
		return moveList;
	}

	private static EGameMove getMoveDirection(Position posA, Position posB) {
		int xDistance = posA.getX() - posB.getX();
		int yDistance = posA.getY() - posB.getY();

		if (xDistance == 0 && yDistance == 1)
			return EGameMove.UP;
		else if (xDistance == 0 && yDistance == -1)
			return EGameMove.DOWN;
		else if (xDistance == 1 && yDistance == 0)
			return EGameMove.LEFT;
		else {
			return EGameMove.RIGHT;
		}
	}
}
