package client.gameMap;

import client.enums.EGameFortState;
import client.enums.EGamePlayerPositionState;
import client.enums.EGameTerrain;
import client.enums.EGameTreasureState;

public class GameMapNode implements Comparable<GameMapNode> {
	Position position;
	private EGameTerrain terrain;
	private EGamePlayerPositionState playerPosState;
	private EGameTreasureState treasureState;
	private EGameFortState fortState;

	private int cost;
	private GameMapNode parent;

	public GameMapNode(Position position, EGameTerrain terrain) {
		this.position = position;
		this.terrain = terrain;
		this.playerPosState = EGamePlayerPositionState.NoPlayerPresent;
		this.treasureState = EGameTreasureState.NoOrUnknownTreasureState;
		this.fortState = EGameFortState.NoOrUnknownFortState;
		this.cost = Integer.MAX_VALUE;
		this.parent = null;
	}

	public GameMapNode(Position position, EGameTerrain terrain, EGamePlayerPositionState playerPosState,
			EGameTreasureState treasureState, EGameFortState fortState) {
		this.position = position;
		this.terrain = terrain;
		this.playerPosState = playerPosState;
		this.treasureState = treasureState;
		this.fortState = fortState;
		this.cost = Integer.MAX_VALUE;
		this.parent = null;
	}

	public Position getPosition() {
		return this.position;
	}

	public EGameTerrain getTerrain() {
		return this.terrain;
	}

	public void setTerrain(EGameTerrain terrain) {
		this.terrain = terrain;
	}

	public EGameFortState getFortState() {
		return fortState;
	}

	public void setFortState(EGameFortState fortState) {
		this.fortState = fortState;
	}

	public EGamePlayerPositionState getPlayerPosState() {
		return this.playerPosState;
	}

	public EGameTreasureState getTreasureState() {
		return this.treasureState;
	}

	public int getCost() {
		return this.cost;
	}

	public GameMapNode getParent() {
		return this.parent;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setParent(GameMapNode parent) {
		this.parent = parent;
	}

	@Override
	public int compareTo(GameMapNode o) {
		return this.cost - o.cost;
	}

	@Override
	public String toString() {
		return "GameMapNode [position=" + position + ", terrain=" + terrain + ", playerPosState=" + playerPosState
				+ ", treasureState=" + treasureState + ", fortState=" + fortState + ", cost=" + cost + ", parent="
				+ parent + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cost;
		result = prime * result + ((fortState == null) ? 0 : fortState.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((playerPosState == null) ? 0 : playerPosState.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((terrain == null) ? 0 : terrain.hashCode());
		result = prime * result + ((treasureState == null) ? 0 : treasureState.hashCode());
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
		GameMapNode other = (GameMapNode) obj;
		if (cost != other.cost)
			return false;
		if (fortState != other.fortState)
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (playerPosState != other.playerPosState)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (terrain != other.terrain)
			return false;
		if (treasureState != other.treasureState)
			return false;
		return true;
	}

}
