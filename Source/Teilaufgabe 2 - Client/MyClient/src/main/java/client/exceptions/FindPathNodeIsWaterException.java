package client.exceptions;

import client.gameMap.GameMap;
import client.gameMap.GameMapNode;

public class FindPathNodeIsWaterException extends RuntimeException {
	private GameMapNode start;
	private GameMapNode destination;
	private GameMap gameMap;

	public FindPathNodeIsWaterException(GameMapNode start, GameMapNode destination, GameMap gameMap) {
		this.start = start;
		this.destination = destination;
		this.gameMap = gameMap;
	}

	public String getErrorMessage() {
		String s = "";
		s += "The path you are trying to calculate is invalid. One of the nodes is water\n";
		s += "start: " + start.getPosition() + "is " + start.getTerrain() + "\n";
		s += "destination: " + destination.getPosition() + "is " + destination.getTerrain() + "\n";
		s += gameMap + "\n";
		return s;
	}
}
