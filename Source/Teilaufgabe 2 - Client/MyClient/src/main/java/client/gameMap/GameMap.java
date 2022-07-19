package client.gameMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.enums.EGameFortState;
import client.enums.EGamePlayerPositionState;
import client.enums.EGameTerrain;
import client.enums.EGameTreasureState;

public class GameMap {
	private int width;
	private int height;

	private Map<Position, GameMapNode> gameMap;

	public GameMap(Map<Position, GameMapNode> gameMap) {
		this.gameMap = gameMap;
		this.setWidthAndHeight();
	}

	public GameMap(List<GameMapNode> gameMapNodes) {
		Map<Position, GameMapNode> map = new HashMap<Position, GameMapNode>();
		for (GameMapNode node : gameMapNodes) {
			map.put(node.getPosition(), node);
		}
		this.gameMap = map;
		this.setWidthAndHeight();
	}

	private void setWidthAndHeight() {
		List<Integer> helperX = new ArrayList<Integer>();
		List<Integer> helperY = new ArrayList<Integer>();
		for (GameMapNode node : this.gameMap.values()) {
			helperX.add(node.getPosition().getX());
			helperY.add(node.getPosition().getY());
		}
		this.width = Collections.max(helperX) + 1;
		this.height = Collections.max(helperY) + 1;
	}

	public Map<Position, GameMapNode> getGameMap() {
		return this.gameMap;
	}

	public Position getFortPosition() {
		Position fortPos = null;
		for (GameMapNode node : this.gameMap.values()) {
			if (node.getFortState().equals(EGameFortState.MyFortPresent))
				fortPos = node.getPosition();
		}
		return fortPos;
	}

	public GameMapNode getNode(Position pos) {
		return gameMap.get(pos);
	}

	public List<GameMapNode> getAllNodes() {
		List<GameMapNode> nodes = new ArrayList<GameMapNode>();
		nodes.addAll(gameMap.values());
		return nodes;
	}

	public List<GameMapNode> getAllGrasNodes() {
		List<GameMapNode> grasNodes = getAllNodes();
		grasNodes.removeIf(node -> (!node.getTerrain().equals(EGameTerrain.GRASS)));
		return grasNodes;
	}

	public List<GameMapNode> getVisibleGrasNodes(Position currentPos) {
		List<GameMapNode> visibleGrasNodes = getVisibleNodes(currentPos);
		visibleGrasNodes.removeIf(n -> (!n.getTerrain().equals(EGameTerrain.GRASS)));
		return visibleGrasNodes;
	}

	public List<GameMapNode> getVisibleNodes(Position currentPos) {
		List<GameMapNode> visibleNodes = new ArrayList<GameMapNode>();
		GameMapNode node = getNode(currentPos);
		if (node.getTerrain().equals(EGameTerrain.WATER))
			return visibleNodes;
		if (node.getTerrain().equals(EGameTerrain.GRASS)) {
			visibleNodes.add(node);
		}
		if (node.getTerrain().equals(EGameTerrain.MOUNTAIN)) {// is MOUTAIN
			int currentX = node.getPosition().getX();
			int currentY = node.getPosition().getY();
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					Position pos = new Position(currentX + x, currentY + y);
					if (gameMap.containsKey(pos)) {
						visibleNodes.add(getNode(pos));
					}
				}
			}
		}
		return visibleNodes;
	}

	public List<GameMapNode> getMyMapNodes() {
		List<GameMapNode> myNodes = new ArrayList<GameMapNode>();
		Position myFort = getFortPosition();

		// if long map and fort left
		if (width == 16 && myFort.getX() < 8) {
			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 8; x++) {
					myNodes.add(getNode(new Position(x, y)));
				}
			}
		}
		// if long map and fort right
		if (width == 16 && myFort.getX() >= 8) {
			for (int y = 0; y < 4; y++) {
				for (int x = 8; x < 16; x++) {
					myNodes.add(getNode(new Position(x, y)));
				}
			}
		}
		// if square map and fort above
		if (width == 8 && myFort.getY() < 4) {
			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 8; x++) {
					myNodes.add(getNode(new Position(x, y)));
				}
			}
		}
		// if square map and below
		if (width == 8 && myFort.getY() >= 4) {
			for (int y = 4; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					myNodes.add(getNode(new Position(x, y)));
				}
			}
		}
		return myNodes;
	}

	public List<GameMapNode> getEnemyMapNodes() {
		List<GameMapNode> enemyNodes = getAllNodes();
		enemyNodes.removeAll(getMyMapNodes());
		return enemyNodes;
	}

	@Override
	public String toString() {
		boolean squareMap = this.height == 4 ? false : true;
		String s = "";
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				Position pos = new Position(j, i);
				GameMapNode node = this.getNode(pos);
				if (node.getFortState().equals(EGameFortState.MyFortPresent)
						|| node.getFortState().equals(EGameFortState.EnemyFortPresent))
					if (node.getPlayerPosState().equals(EGamePlayerPositionState.BothPlayerPresent))
						s = s + " |><" + node.getTerrain().toString() + "<>|  ";
					else if (node.getPlayerPosState().equals(EGamePlayerPositionState.EnemyPlayerPresent))
						s = s + "  |>" + node.getTerrain().toString() + "<|  ";
					else if (node.getPlayerPosState().equals(EGamePlayerPositionState.MyPlayerPresent))
						s = s + "  |<" + node.getTerrain().toString() + ">|  ";
					else {
						s = s + "   |" + node.getTerrain().toString() + "|   ";
					}
				else if (node.getTreasureState().equals(EGameTreasureState.MyTreasurePresent)) {
					s = s + "  **" + node.getTerrain().toString() + "**  ";
				} else if (node.getPlayerPosState().equals(EGamePlayerPositionState.EnemyPlayerPresent))
					s = s + "   >" + node.getTerrain().toString() + "<   ";
				else if (node.getPlayerPosState().equals(EGamePlayerPositionState.MyPlayerPresent))
					s = s + "   <" + node.getTerrain().toString() + ">   ";
				else if (node.getPlayerPosState().equals(EGamePlayerPositionState.BothPlayerPresent))
					s = s + "  <>" + node.getTerrain().toString() + "><  ";
				else {
					s = s + "    " + node.getTerrain().toString() + "    ";
				}
				if (squareMap == false && j == 7)
					s = s + "|";
			}
			s = s + "\n\n";
			if (squareMap && i == 3)
				s = s + "   ------------------------------------------------------------------\n";
		}
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gameMap == null) ? 0 : gameMap.hashCode());
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameMap other = (GameMap) obj;
		if (gameMap == null) {
			if (other.gameMap != null)
				return false;
		} else if (!gameMap.equals(other.gameMap))
			return false;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}
}
