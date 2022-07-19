package client.MVC;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedHashSet;
import java.util.Set;

import client.enums.EGameFortState;
import client.enums.EGamePlayerPositionState;
import client.enums.EGameTreasureState;
import client.gameMap.GameMap;
import client.gameMap.GameMapNode;
import client.gameMap.Position;

public class MatchState {
	private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private GameMap gameFullMap;

	private boolean foundTreasure;
	private boolean collectedTreasure;
	private boolean foundEnemyFort;
	private boolean arrivedAtEnemyFort;

	private GameMapNode treasureNode;
	private GameMapNode enemyFortNode;

	private Set<Position> history = new LinkedHashSet<Position>();

	public MatchState(GameMap gameFullMap) {
		this.gameFullMap = gameFullMap;

		this.foundTreasure = false;
		this.collectedTreasure = false;
		this.foundEnemyFort = false;
		this.arrivedAtEnemyFort = false;

		this.treasureNode = null;
		this.enemyFortNode = null;

		updateHistory();
	}

	public void addListener(PropertyChangeListener view) {
		changes.addPropertyChangeListener(view);
	}

	public void setMatchState(GameMap newGameMap, boolean collectedTreasure) {
		GameMap oldGameMap = this.gameFullMap;
		gameFullMap = newGameMap;

		updateTreasureNode();
		updateEnemyFortNode();

		updateFoundAndCollectedTreasure(collectedTreasure);

		updateArrivedAtEnemyFort();

		updateHistory();

		// inform all interested parties about changes
		changes.firePropertyChange("updated MatchState", oldGameMap, newGameMap);
	}

	private void updateFoundAndCollectedTreasure(boolean collected) {
		if (collected == true) {
			foundTreasure = true;
			collectedTreasure = true;
		}
	}

	private void updateTreasureNode() {
		for (GameMapNode node : gameFullMap.getAllGrasNodes()) {
			if (node.getTreasureState().equals(EGameTreasureState.MyTreasurePresent)) {
				foundTreasure = true;
				treasureNode = node;
			}
		}
		return;
	}

	private void updateEnemyFortNode() {
		for (GameMapNode node : gameFullMap.getAllGrasNodes()) {
			if (node.getFortState().equals(EGameFortState.EnemyFortPresent)) {
				foundEnemyFort = true;
				enemyFortNode = node;
			}
		}
		return;
	}

	private void updateArrivedAtEnemyFort() {
		if (foundEnemyFort && getMyNode().equals(getEnemyFortNode())) {
			arrivedAtEnemyFort = true;
		}
	}

	private void updateHistory() {
		history.add(getMyNode().getPosition());
	}

	public GameMap getGameFullMap() {
		return gameFullMap;
	}

	public boolean hasFoundTreasure() {
		return foundTreasure;
	}

	public boolean hasCollectedTreasure() {
		return collectedTreasure;
	}

	public boolean hasFoundEnemyFort() {
		return foundEnemyFort;
	}

	public boolean hasArrivedAtEnemyFort() {
		return arrivedAtEnemyFort;
	}

	public GameMapNode getMyNode() {
		GameMapNode myNode = null;
		for (GameMapNode node : gameFullMap.getAllNodes()) {
			if (node.getPlayerPosState().equals(EGamePlayerPositionState.MyPlayerPresent)
					|| node.getPlayerPosState().equals(EGamePlayerPositionState.BothPlayerPresent))
				myNode = node;
		}
		return myNode;
	}

	public GameMapNode getEnemyNode() {
		GameMapNode enemyNode = null;
		for (GameMapNode node : gameFullMap.getAllNodes()) {
			if (node.getPlayerPosState().equals(EGamePlayerPositionState.EnemyPlayerPresent)
					|| node.getPlayerPosState().equals(EGamePlayerPositionState.BothPlayerPresent))
				enemyNode = node;
		}
		return enemyNode;
	}

	public GameMapNode getTreasureNode() {
		return treasureNode;
	}

	public GameMapNode getEnemyFortNode() {
		return enemyFortNode;
	}

	public Set<Position> getHistory() {
		return history;
	}

	public Set<GameMapNode> getUncoveredNodes() {
		Set<GameMapNode> uncoveredNodes = new LinkedHashSet<GameMapNode>();
		for (Position pos : history) {
			uncoveredNodes.addAll(gameFullMap.getVisibleNodes(pos));
		}
		return uncoveredNodes;
	}
}