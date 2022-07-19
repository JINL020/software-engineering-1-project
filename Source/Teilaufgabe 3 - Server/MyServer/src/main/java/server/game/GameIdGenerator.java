package server.game;

import java.util.Random;

public class GameIdGenerator {
	private static String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
	private static int length = 5;

	public static String createGameId() {
		Random rand = new Random();

		String gameId = "";
		for (int i = 0; i < length; i++) {
			gameId += characters.charAt(rand.nextInt(characters.length()));
		}

		return gameId;
	}

}
