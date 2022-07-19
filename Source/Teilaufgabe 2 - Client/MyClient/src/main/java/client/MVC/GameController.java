package client.MVC;

import java.util.ArrayDeque;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.enums.EGameMove;
import client.exceptions.InvalidPlayerRegistrationDataException;
import client.exceptions.SentActionToSlowException;
import client.gameMap.GameMap;
import client.gameMap.GameMapGenerator;
import client.gameMap.GameMapNode;
import client.moveGenerator.MoveGenerator;
import client.network.Network;

public class GameController {
	private final static Logger logger = LoggerFactory.getLogger(GameController.class);
	private Network network;

	public GameController(Network network) {
		this.network = network;
	}

	public void registerClient(String firstName, String lastName, String studentId)
			throws InvalidPlayerRegistrationDataException {
		if (firstName.isBlank() || lastName.isBlank() || studentId.isBlank()) {
			throw new InvalidPlayerRegistrationDataException(firstName, lastName, studentId);
		}
		String myPlayerId = network.registerClient(firstName, lastName, studentId).getUniquePlayerID();
		logger.info("Successfully registered client: your playerId is " + myPlayerId);
		return;
	}

	public void sendGameHalfMap() {
		GameMapGenerator gameMapGen = new GameMapGenerator();
		GameMap myGameHalfMap = gameMapGen.generateGameHalfMap();
		logger.info("created GameHalfMap:\n" + myGameHalfMap);

		while (!network.isTurn()) {
			if (network.hasLost()) {
				throw new SentActionToSlowException("Your Client sent actions too slow");
			}
			if (network.hasWon()) {
				throw new SentActionToSlowException("Enemy Client sent actions too slow");
			}
		}
		network.sendMap(myGameHalfMap);
		logger.info("sent GameHalfMap");
		return;
	}

	public GameMap getGameFullMap() {// do some checks???
		while (!network.isTurn()) {
			if (network.hasLost()) {
				throw new SentActionToSlowException("Your Client sent actions too slow");
			}
			if (network.hasWon()) {
				throw new SentActionToSlowException("Enemy Client sent actions too slow");
			}
		}
		GameMap gameFullMap = network.getGameFullMap();
		logger.info("received GameFullMap:\n" + gameFullMap);
		return gameFullMap;
	}

	public void startGame(MatchState matchState) {
		logger.info("game has started");
		updateMatchState(matchState);

		Queue<EGameMove> movesToSend = new ArrayDeque<EGameMove>();

		logger.info("searching for treasure");
		searchTreasure(matchState);
		logger.info("found treasure");

		if (!matchState.hasCollectedTreasure()) {
			goToDestination(matchState, matchState.getTreasureNode());
		}
		logger.info("collected treasure");

		logger.info("searching for enemy fort");
		searchEnemyFort(matchState);
		logger.info("found enemy fort");

		if (!matchState.hasArrivedAtEnemyFort()) {
			goToDestination(matchState, matchState.getEnemyFortNode());
		}
		logger.info("arrived at enemy fort");

		updateMatchState(matchState);
	}

	private void searchTreasure(MatchState matchState) {
		Queue<EGameMove> movesToSend = new ArrayDeque<EGameMove>();
		while (!matchState.hasFoundTreasure()) {
			if (movesToSend.isEmpty()) {
				logger.info("calc moves");
				movesToSend.addAll(MoveGenerator.getNextMoves(matchState.getMyNode(), matchState.getUncoveredNodes(),
						matchState.getGameFullMap(), matchState.hasFoundTreasure()));
			}
			sendMove(movesToSend.poll());
			updateMatchState(matchState);
		}
	}

	private void searchEnemyFort(MatchState matchState) {
		Queue<EGameMove> movesToSend = new ArrayDeque<EGameMove>();
		while (!matchState.hasFoundEnemyFort()) {
			if (movesToSend.isEmpty()) {
				logger.debug("calc moves");
				movesToSend.addAll(MoveGenerator.getNextMoves(matchState.getMyNode(), matchState.getUncoveredNodes(),
						matchState.getGameFullMap(), matchState.hasFoundTreasure()));
			}
			sendMove(movesToSend.poll());
			updateMatchState(matchState);
		}
	}

	private void goToDestination(MatchState matchState, GameMapNode destination) {
		Queue<EGameMove> movesToSend = new ArrayDeque<EGameMove>();
		movesToSend = MoveGenerator.getMovesToDestination(matchState.getMyNode(), destination,
				matchState.getGameFullMap());
		while (!movesToSend.isEmpty()) {
			sendMove(movesToSend.poll());
			updateMatchState(matchState);
		}
	}

	private void sendMove(EGameMove move) {
		while (!network.isTurn()) {
			if (network.hasLost()) {
				throw new SentActionToSlowException("Your Client sent actions too slow");
			}
			if (network.hasWon()) {
				throw new SentActionToSlowException("Enemy Client sent actions too slow");
			}
		}
		network.sendMove(move);
	}

	public void updateMatchState(MatchState matchState) {
		GameMap currentMap = network.getGameFullMap();
		matchState.setMatchState(currentMap, network.hasCollectedTreasure());
	}
}