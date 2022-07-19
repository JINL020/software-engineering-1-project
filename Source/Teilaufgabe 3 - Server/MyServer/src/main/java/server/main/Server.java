package server.main;

import java.util.ArrayList;
import java.util.List;

import MessagesGameState.GameState;
import MessagesGameState.PlayerState;
import server.exceptions.GameIdNotFoundException;
import server.exceptions.MapCanOnlyBeSentOnceException;
import server.exceptions.PlayerNotRegisteredException;
import server.exceptions.RegisteredTwoClientsAlreadyException;
import server.exceptions.WaitForTwoPlayersBeforeMapSendingException;
import server.game.EGameMove;
import server.game.Game;
import server.game.GameId;
import server.map.FullMapBuilder;
import server.map.GameMap;
import server.map.PlayerMapInfo;
import server.player.EGamePlayerGameState;
import server.player.Player;
import server.player.PlayerId;

public class Server {
	private List<Game> activeGames = new ArrayList<Game>();

	public void newGame(GameId gameId) {
		if (activeGames.size() == 999) {
			activeGames.remove(0);
		}
		Game game = new Game(gameId);
		activeGames.add(game);
	}

	public void registerPlayer(Player player, GameId gameId) {
		Game currentGame = getGame(gameId);
		currentGame.addPlayer(player);
		if (currentGame.getPlayerCount() == 2) {
			currentGame.chooseFirstPlayer();
		}
	}

	public void receiveHalfMap(PlayerMapInfo playerMapInfo, GameMap gameHalfMap, GameId gameId) {
		Game currentGame = getGame(gameId);

		currentGame.receiveHalfMap(playerMapInfo, gameHalfMap);

		currentGame.changeTurn();

		if (currentGame.getHalfMapCount() == 2) {
			FullMapBuilder.combineHalfMaps(currentGame.getFullMap());
		}
	}

	public GameState getGameState(PlayerId playerId, GameId gameId) {
		Game currentGame = getGame(gameId);

		List<PlayerState> playerStates = new ArrayList<PlayerState>();

		for (Player player : currentGame.getPlayers()) {
			if (player.getPlayerId().equals(playerId)) {
				playerStates.add(Converter.convertToPlayerState(player));
			} else {
				playerStates.add(Converter.convertToPlayerState(player.getDummyPlayer()));
			}
		}

		if (currentGame.getHalfMapCount() == 2) {
			return new GameState(Converter.convertToFullMap(currentGame.getFullMap(), playerId), playerStates,
					currentGame.getGameStateId());
		}
		return new GameState(playerStates, currentGame.getGameStateId());
	}

	public void receiveMove(EGameMove move, PlayerId playerId, GameId gameId) {
		Game currentGame = getGame(gameId);
		currentGame.increaseActionCount();

		if (currentGame.getActionCount() == 200) {
			for (Player player : getGame(gameId).getPlayers()) {
				player.setState(EGamePlayerGameState.Lost);
			}
		}
	}

	private int getRegisteredPlayerCount(GameId gameId) {
		return getGame(gameId).getPlayers().size();
	}

	public Player getPlayer(PlayerId playerId, GameId gameId) {
		for (Player player : getGame(gameId).getPlayers()) {
			if (playerId.equals(player.getPlayerId()))
				return player;
		}
		return null;
	}

	public void setLoser(Player Loser, GameId gameId) {
		for (Player player : getGame(gameId).getPlayers()) {
			if (Loser.getPlayerId().equals(player.getPlayerId())) {
				player.setState(EGamePlayerGameState.Lost);
			} else {
				player.setState(EGamePlayerGameState.Won);
			}
		}
	}

	public void checkDoesGameExist(GameId gameId) {
		for (Game game : activeGames) {
			if (game.getGameId().equals(gameId))
				return;
		}
		throw new GameIdNotFoundException("GameIdNotFoundException", "The GameId " + gameId + " does not exist.");
	}

	public void checkPlayerIsRegistered(PlayerId playerId, GameId gameId) {
		if (getGame(gameId).isRegistered(playerId)) {
			return;
		}
		throw new PlayerNotRegisteredException("PlayerNotRegisteredException",
				"The player " + playerId + " is not registered for game " + gameId);
	}

	public void checkMaxClientCountReached(GameId gameId) {
		if (getRegisteredPlayerCount(gameId) < 2) {
			return;
		}
		throw new RegisteredTwoClientsAlreadyException("RegisteredTwoClientsAlreadyException",
				"There are already 2 clients registered with this gameId.");
	}

	public void checkClientCountBeforeSendingMap(GameId gameId) {
		if (getRegisteredPlayerCount(gameId) < 2) {
			throw new WaitForTwoPlayersBeforeMapSendingException("WaitForTwoPlayersBeforeMapSendingException",
					"You have to wait till 2 players are registered before sending a HalfMap");
		}
	}

	public void checkPlayerAlreadySentMap(PlayerId playerId, GameId gameId) {
		for (PlayerMapInfo playerMapInfo : getGame(gameId).getFullMap().getPlayerMapInfos()) {
			if (playerMapInfo.getPlayerId().equals(playerId))
				throw new MapCanOnlyBeSentOnceException("MapCanOnlyBeSentOnceException", "you already sent a map");
		}
	}

	private Game getGame(GameId gameId) {
		for (Game game : activeGames) {
			if (game.getGameId().equals(gameId))
				return game;
		}
		return null;
	}

}
