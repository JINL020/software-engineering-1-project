package server.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import MessagesBase.EMove;
import MessagesBase.ETerrain;
import MessagesBase.HalfMap;
import MessagesBase.HalfMapNode;
import MessagesBase.PlayerRegistration;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import MessagesGameState.EFortState;
import MessagesGameState.EPlayerGameState;
import MessagesGameState.EPlayerPositionState;
import MessagesGameState.ETreasureState;
import MessagesGameState.FullMap;
import MessagesGameState.FullMapNode;
import MessagesGameState.PlayerState;
import server.game.EGameMove;
import server.game.GameId;
import server.map.EGameTerrain;
import server.map.GameFullMap;
import server.map.GameMap;
import server.map.GameMapNode;
import server.map.PlayerMapInfo;
import server.map.Position;
import server.player.EGamePlayerGameState;
import server.player.Player;
import server.player.PlayerId;

public class Converter {

	public static UniqueGameIdentifier convertToUniqueGameIdentifier(GameId gameId) {
		UniqueGameIdentifier gameIdentifier = new UniqueGameIdentifier(gameId.getId());
		return gameIdentifier;
	}

	public static GameId convertToGameId(UniqueGameIdentifier uniqueGameIdentifier) {
		GameId gameId = new GameId(uniqueGameIdentifier.getUniqueGameID());
		return gameId;
	}

	public static Player covertToPlayer(PlayerId playerId, PlayerRegistration playerReg) {
		Player player = new Player(playerId, playerReg.getStudentFirstName(), playerReg.getStudentLastName(),
				playerReg.getStudentID());
		return player;
	}

	public static UniquePlayerIdentifier covertToUniquePlayerIdentifier(PlayerId playerId) {
		UniquePlayerIdentifier uniquePlayerIdentifier = new UniquePlayerIdentifier(playerId.getId());
		return uniquePlayerIdentifier;
	}

	/*
	 * public static PlayerId covertToPlayerId(UniquePlayerIdentifier
	 * uniquePlayerIdentifier) { PlayerId playerId = new
	 * PlayerId(uniquePlayerIdentifier.getUniquePlayerID()); return playerId; }
	 */

	public static EPlayerGameState convertToEPlayerGameState(EGamePlayerGameState state) {
		switch (state) {
		case MustAct:
			return EPlayerGameState.MustAct;
		case Won:
			return EPlayerGameState.Won;
		case Lost:
			return EPlayerGameState.Lost;
		default:
			return EPlayerGameState.MustWait;
		}
	}

	public static PlayerState convertToPlayerState(Player player) {
		String firstName = player.getFirstName();
		String lastName = player.getLastName();
		String studentId = player.getStudentId();
		EPlayerGameState state = convertToEPlayerGameState(player.getState());
		boolean hasCollectedTreasure = player.hasCollectedTreasure();

		UniquePlayerIdentifier playerId = covertToUniquePlayerIdentifier(player.getPlayerId());

		PlayerState playerState = new PlayerState(firstName, lastName, studentId, state, playerId,
				hasCollectedTreasure);

		return playerState;
	}

	public static PlayerMapInfo convertToPlayerMapInfo(HalfMap halfMap) {
		String playerId = halfMap.getUniquePlayerID();
		Position myPlayerAndFortPos = null;

		for (HalfMapNode node : halfMap.getMapNodes()) {
			if (node.isFortPresent()) {
				myPlayerAndFortPos = new Position(node.getX(), node.getY());
			}
		}

		return new PlayerMapInfo(playerId, myPlayerAndFortPos, myPlayerAndFortPos);
	}

	public static GameMap convertToGameMap(HalfMap halfMap) {
		List<GameMapNode> mapNodes = new ArrayList<GameMapNode>();

		for (HalfMapNode node : halfMap.getMapNodes()) {
			Position pos = new Position(node.getX(), node.getY());
			EGameTerrain terrain = convertToEGameTerrain(node.getTerrain());
			GameMapNode gameMapNode = new GameMapNode(pos, terrain);
			mapNodes.add(gameMapNode);
		}
		return new GameMap(mapNodes);
	}

	public static EGameTerrain convertToEGameTerrain(ETerrain terrain) {
		switch (terrain) {
		case Mountain:
			return EGameTerrain.MOUNTAIN;
		case Water:
			return EGameTerrain.WATER;
		default:
			return EGameTerrain.GRASS;
		}
	}

	private static ETerrain convertToETerrain(EGameTerrain terrain) {
		switch (terrain) {
		case MOUNTAIN:
			return ETerrain.Mountain;
		case WATER:
			return ETerrain.Water;
		default:
			return ETerrain.Grass;
		}
	}

	public static Optional<FullMap> convertToFullMap(GameFullMap gameFullMap, PlayerId playerId) {
		List<FullMapNode> fullMapNodes = new ArrayList<FullMapNode>();

		for (GameMap map : gameFullMap.getData().values()) {
			for (GameMapNode node : map.getAllNodes()) {
				fullMapNodes.add(convertToFullMapNode(node, gameFullMap.getPlayerMapInfos(), playerId));
			}
		}
		return Optional.of(new FullMap(fullMapNodes));
	}

	private static FullMapNode convertToFullMapNode(GameMapNode node, Set<PlayerMapInfo> playerMapInfos,
			PlayerId playerId) {

		PlayerMapInfo myPlayerMapInfo = null;
		PlayerMapInfo enemyPlayerMapInfo = null;

		for (PlayerMapInfo playerMapInfo : playerMapInfos) {
			if (playerMapInfo.getPlayerId().equals(playerId)) {
				myPlayerMapInfo = playerMapInfo;
			} else {
				enemyPlayerMapInfo = playerMapInfo.getDummy();
			}
		}

		Position nodePos = node.getPosition();

		EFortState fortState = EFortState.NoOrUnknownFortState;
		EPlayerPositionState playerPositionState = EPlayerPositionState.NoPlayerPresent;
		ETerrain terrain = convertToETerrain(node.getTerrain());
		ETreasureState treasureState = ETreasureState.NoOrUnknownTreasureState;// show when found

		if (myPlayerMapInfo.getMyFortPos().equals(nodePos)) {
			fortState = EFortState.MyFortPresent;
		}
		/*
		 * if (enemyPlayerMapInfo.getMyFortPos().equals(nodePos)) { fortState =
		 * EFortState.EnemyFortPresent; }
		 */

		if (myPlayerMapInfo.getMyPlayerPos().equals(nodePos)) {
			playerPositionState = EPlayerPositionState.MyPlayerPosition;
		}
		if (enemyPlayerMapInfo.getMyPlayerPos().equals(nodePos)) {
			playerPositionState = EPlayerPositionState.EnemyPlayerPosition;
		}
		if (myPlayerMapInfo.getMyPlayerPos().equals(nodePos) && enemyPlayerMapInfo.getMyPlayerPos().equals(nodePos)) {
			playerPositionState = EPlayerPositionState.BothPlayerPosition;
		}

		return new FullMapNode(terrain, playerPositionState, treasureState, fortState, nodePos.getX(), nodePos.getY());
	}

	public static EGameMove convertToEGameMove(EMove move) {
		switch (move) {
		case Up:
			return EGameMove.UP;
		case Down:
			return EGameMove.DOWN;
		case Left:
			return EGameMove.LEFT;
		default:
			return EGameMove.RIGHT;
		}
	}
}
