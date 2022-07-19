package client.network;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import MessagesBase.EMove;
import MessagesBase.ETerrain;
import MessagesBase.HalfMap;
import MessagesBase.HalfMapNode;
import MessagesGameState.EFortState;
import MessagesGameState.EPlayerPositionState;
import MessagesGameState.ETreasureState;
import MessagesGameState.FullMap;
import MessagesGameState.FullMapNode;
import client.enums.EGameFortState;
import client.enums.EGameMove;
import client.enums.EGamePlayerPositionState;
import client.enums.EGameTerrain;
import client.enums.EGameTreasureState;
import client.gameMap.GameMap;
import client.gameMap.GameMapNode;
import client.gameMap.Position;

public class NetworkConverter {

	// my data structure
	private EGameTerrain convertToEGameTerrain(ETerrain terrain) {
		switch (terrain) {
		case Mountain:
			return EGameTerrain.MOUNTAIN;
		case Water:
			return EGameTerrain.WATER;
		default:
			return EGameTerrain.GRASS;
		}
	}

	// network data structure
	private ETerrain convertToETerrain(EGameTerrain terrain) {
		switch (terrain) {
		case MOUNTAIN:
			return ETerrain.Mountain;
		case WATER:
			return ETerrain.Water;
		default:
			return ETerrain.Grass;
		}
	}

	// convert to my data structures
	private EGameFortState convertToEGameFortState(EFortState fortState) {
		switch (fortState) {
		case EnemyFortPresent:
			return EGameFortState.EnemyFortPresent;
		case MyFortPresent:
			return EGameFortState.MyFortPresent;
		default:
			return EGameFortState.NoOrUnknownFortState;
		}
	}

	private EGameTreasureState convertToEGameTreasureState(ETreasureState treasureState) {
		switch (treasureState) {
		case MyTreasureIsPresent:
			return EGameTreasureState.MyTreasurePresent;
		default:
			return EGameTreasureState.NoOrUnknownTreasureState;
		}
	}

	private EGamePlayerPositionState convertToEGamePlayerPositionState(EPlayerPositionState treasureState) {
		switch (treasureState) {
		case BothPlayerPosition:
			return EGamePlayerPositionState.BothPlayerPresent;
		case EnemyPlayerPosition:
			return EGamePlayerPositionState.EnemyPlayerPresent;
		case MyPlayerPosition:
			return EGamePlayerPositionState.MyPlayerPresent;
		default:
			return EGamePlayerPositionState.NoPlayerPresent;
		}
	}

	public EMove convertToEMove(EGameMove move) {
		switch (move) {
		case UP:
			return EMove.Up;
		case DOWN:
			return EMove.Down;
		case LEFT:
			return EMove.Left;
		default:
			return EMove.Right;
		}
	}

	public HalfMap convertToHalfMap(GameMap gameHalfMap, String playerId) {
		Set<HalfMapNode> halfMapNodes = new HashSet<HalfMapNode>();
		for (GameMapNode gameMapNode : gameHalfMap.getGameMap().values()) {
			int x = gameMapNode.getPosition().getX();
			int y = gameMapNode.getPosition().getY();
			boolean fortressIsPresent = gameMapNode.getFortState().equals(EGameFortState.MyFortPresent);
			ETerrain terrain = this.convertToETerrain(gameMapNode.getTerrain());
			HalfMapNode halfMapNode = new HalfMapNode(x, y, fortressIsPresent, terrain);
			halfMapNodes.add(halfMapNode);
		}
		HalfMap halfMap = new HalfMap(playerId, halfMapNodes);
		return halfMap;
	}

	public GameMap convertToGameFullMap(Optional<FullMap> fullMap) {
		Collection<FullMapNode> fullMapNodes = fullMap.get().getMapNodes();
		Map<Position, GameMapNode> gameMap = new HashMap<Position, GameMapNode>();
		for (FullMapNode node : fullMapNodes) {
			Position pos = new Position(node.getX(), node.getY());
			GameMapNode gameMapNode = this.convertToGameMapNode(node);
			gameMap.put(pos, gameMapNode);
		}
		GameMap gameFullMap = new GameMap(gameMap);
		return gameFullMap;
	}

	private GameMapNode convertToGameMapNode(FullMapNode node) {
		Position position = new Position(node.getX(), node.getY());
		EGameTerrain terrain = convertToEGameTerrain(node.getTerrain());
		EGamePlayerPositionState playerPosState = convertToEGamePlayerPositionState(node.getPlayerPositionState());
		EGameTreasureState treasureState = convertToEGameTreasureState(node.getTreasureState());
		EGameFortState fortState = convertToEGameFortState(node.getFortState());

		GameMapNode gameFullMapNode = new GameMapNode(position, terrain, playerPosState, treasureState, fortState);
		return gameFullMapNode;
	}

}
