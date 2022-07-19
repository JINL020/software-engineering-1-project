package server.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import server.map.GameFullMap;
import server.map.GameMap;
import server.map.PlayerMapInfo;
import server.player.EGamePlayerGameState;
import server.player.Player;
import server.player.PlayerId;

public class Game {
	private GameId gameId;
	private int actionCount = 0;

	private String gameStateId = UUID.randomUUID().toString();
	private List<Player> players = new ArrayList<Player>();
	private GameFullMap fullMap = new GameFullMap();

	public Game(GameId gameId) {
		this.gameId = gameId;
	}

	public void addPlayer(Player player) {
		players.add(player);
		updateGameStateId();
	}

	public void receiveHalfMap(PlayerMapInfo playerMapInfo, GameMap gameHalfMap) {
		fullMap.addHalfMap(playerMapInfo, gameHalfMap);
		updateGameStateId();
	}

	public void chooseFirstPlayer() {
		Random rand = new Random();
		players.get(rand.nextInt(2)).setState(EGamePlayerGameState.MustAct);
	}

	public void changeTurn() {
		for (Player player : players) {
			if (player.getState().equals(EGamePlayerGameState.MustAct)) {
				player.setState(EGamePlayerGameState.MustWait);
			} else if (player.getState().equals(EGamePlayerGameState.MustWait)) {
				player.setState(EGamePlayerGameState.MustAct);
			}
		}
	}

	public boolean isRegistered(PlayerId playerId) {
		for (Player player : players) {
			if (player.getPlayerId().equals(playerId)) {
				return true;
			}
		}
		return false;
	}

	public int getHalfMapCount() {
		return fullMap.getData().size();
	}

	public int getPlayerCount() {
		return players.size();
	}

	public GameId getGameId() {
		return gameId;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public GameFullMap getFullMap() {
		return fullMap;
	}

	public String getGameStateId() {
		return gameStateId;
	}

	public int getActionCount() {
		return actionCount;
	}

	public void increaseActionCount() {
		actionCount++;
	}

	private void updateGameStateId() {
		gameStateId = UUID.randomUUID().toString();
	}

}
