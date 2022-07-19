package client.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.MVC.GameController;
import client.MVC.GameMapVisualisation;
import client.MVC.MatchState;
import client.exceptions.FindPathNodeIsWaterException;
import client.exceptions.InvalidPlayerRegistrationDataException;
import client.exceptions.NetworkCommunicationException;
import client.exceptions.SentActionToSlowException;
import client.network.Network;

public class MainClient {
	private final static Logger logger = LoggerFactory.getLogger(MainClient.class);

	public static void main(String[] args) {
		String serverBaseUrl = args[1];
		String gameId = args[2];

		Network myNetwork = new Network(serverBaseUrl, gameId);

		GameController controller = new GameController(myNetwork);

		try {
			controller.registerClient("Jin-Jin", "Lee", "11913405");

			controller.sendGameHalfMap();

			MatchState matchState = new MatchState(controller.getGameFullMap());

			GameMapVisualisation view = new GameMapVisualisation(matchState);

			controller.startGame(matchState);

		} catch (InvalidPlayerRegistrationDataException e) {
			logger.error(e.getErrorMessage());
		} catch (SentActionToSlowException e) {
			logger.error(e.getErrorMessage());
		} catch (NetworkCommunicationException e) {
			logger.error(e.getErrorMessage());
		} catch (FindPathNodeIsWaterException e) {
			logger.error(e.getErrorMessage());
		} finally {
			if (myNetwork.hasLost()) {
				System.out.println("GAME OVER - You have lost");
			}
			if (myNetwork.hasWon()) {
				System.out.println("WOOOHOO - You have won!!!");
			}
			System.exit(0);
		}
	}

}
