package server.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FullMapBuilder {
	private static Random rand = new Random();

	public static void combineHalfMaps(GameFullMap fullMap) {
		Map<PlayerMapInfo, GameMap> halfMaps = fullMap.getData();

		List<PlayerMapInfo> playerMapInfos = new ArrayList<PlayerMapInfo>();
		List<GameMap> gameHalfMaps = new ArrayList<GameMap>();

		for (PlayerMapInfo playerMapInfo : halfMaps.keySet()) {
			playerMapInfos.add(playerMapInfo);
			gameHalfMaps.add(halfMaps.get(playerMapInfo));
		}

		placeTreasure(playerMapInfos, gameHalfMaps);

		int mapType = rand.nextInt(2);
		int index = rand.nextInt(2);

		if (mapType == 0) {// long map
			moveHalfMapRight(playerMapInfos.get(index), gameHalfMaps.get(index));
		}
		if (mapType == 1) {// square map
			moveHalfMapDown(playerMapInfos.get(index), gameHalfMaps.get(index));
		}
	}

	private static void placeTreasure(List<PlayerMapInfo> playerMapInfos, List<GameMap> gameHalfMaps) {
		playerMapInfos.get(0).setMyTreasurePos(getRandomGrassNode(gameHalfMaps.get(0)).getPosition());
		playerMapInfos.get(1).setMyTreasurePos(getRandomGrassNode(gameHalfMaps.get(1)).getPosition());
	}

	private static GameMapNode getRandomGrassNode(GameMap halfMap) {
		List<GameMapNode> grassNodes = halfMap.getAllGrassNodes();
		return grassNodes.get(rand.nextInt(grassNodes.size()));
	}

	private static void moveHalfMapRight(PlayerMapInfo playerMapInfo, GameMap gameHalfMap) {
		Position myFortAndPlayerPos = playerMapInfo.getMyFortPos();
		Position newFortAndPlayerPos = new Position(myFortAndPlayerPos.getX() + 8, myFortAndPlayerPos.getY());
		playerMapInfo.setMyFortPos(newFortAndPlayerPos);
		playerMapInfo.setMyPlayerPos(newFortAndPlayerPos);

		Position myTreasurePos = playerMapInfo.getMyTreasurePos();
		Position newTreasurePos = new Position(myTreasurePos.getX() + 8, myTreasurePos.getY());
		playerMapInfo.setMyTreasurePos(newTreasurePos);

		for (GameMapNode node : gameHalfMap.getAllNodes()) {
			int nodeXPos = node.getPosition().getX();
			node.getPosition().setX(nodeXPos + 8);
		}
	}

	private static void moveHalfMapDown(PlayerMapInfo playerMapInfo, GameMap gameHalfMap) {
		Position myFortAndPlayerPos = playerMapInfo.getMyFortPos();
		Position newFortAndPlayerPos = new Position(myFortAndPlayerPos.getX(), myFortAndPlayerPos.getY() + 4);
		playerMapInfo.setMyFortPos(newFortAndPlayerPos);
		playerMapInfo.setMyPlayerPos(newFortAndPlayerPos);

		Position myTreasurePos = playerMapInfo.getMyTreasurePos();
		Position newTreasurePos = new Position(myTreasurePos.getX(), myTreasurePos.getY() + 4);
		playerMapInfo.setMyTreasurePos(newTreasurePos);

		for (GameMapNode node : gameHalfMap.getAllNodes()) {
			int nodeYPos = node.getPosition().getY();
			node.getPosition().setY(nodeYPos + 4);
		}
	}

}
