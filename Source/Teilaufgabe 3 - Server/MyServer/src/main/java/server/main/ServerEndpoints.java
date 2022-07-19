package server.main;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import MessagesBase.HalfMap;
import MessagesBase.PlayerMove;
import MessagesBase.PlayerRegistration;
import MessagesBase.ResponseEnvelope;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import MessagesGameState.GameState;
import server.exceptions.GenericExampleException;
import server.exceptions.InvalidMapException;
import server.game.EGameMove;
import server.game.GameId;
import server.game.GameIdGenerator;
import server.map.GameMap;
import server.map.PlayerMapInfo;
import server.mapValidator.HalfMapValidator;
import server.player.Player;
import server.player.PlayerId;

@RestController
@RequestMapping(value = "/games")
public class ServerEndpoints {
	Server server = new Server();

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody UniqueGameIdentifier newGame(
			@RequestParam(required = false, defaultValue = "false", value = "enableDebugMode") boolean enableDebugMode,
			@RequestParam(required = false, defaultValue = "false", value = "enableDummyCompetition") boolean enableDummyCompetition) {

		GameId gameId = new GameId(GameIdGenerator.createGameId());

		server.newGame(gameId);

		return Converter.convertToUniqueGameIdentifier(gameId);
	}

	@RequestMapping(value = "/{gameID}/players", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<UniquePlayerIdentifier> registerPlayer(
			@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody PlayerRegistration playerReg) {

		GameId gameId = Converter.convertToGameId(gameID);

		server.checkDoesGameExist(gameId);
		server.checkMaxClientCountReached(gameId);

		Player player = Converter.covertToPlayer(new PlayerId(), playerReg);

		server.registerPlayer(player, gameId);

		ResponseEnvelope<UniquePlayerIdentifier> playerIdMessage = new ResponseEnvelope<>(
				Converter.covertToUniquePlayerIdentifier(player.getPlayerId()));

		return playerIdMessage;
	}

	@RequestMapping(value = "/{gameID}/halfmaps", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<Object> receiveHalfMap(@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody HalfMap halfMap) {

		GameId gameId = Converter.convertToGameId(gameID);
		PlayerId playerId = new PlayerId(halfMap.getUniquePlayerID());

		server.checkDoesGameExist(gameId);
		server.checkPlayerIsRegistered(playerId, gameId);
		server.checkClientCountBeforeSendingMap(gameId);
		server.checkPlayerAlreadySentMap(playerId, gameId);

		Player player = server.getPlayer(playerId, gameId);

		if (!HalfMapValidator.check(halfMap)) {
			server.setLoser(player, gameId);
			throw new InvalidMapException("InvalidMapException", "your map does not fullfill the requirements");
		}

		PlayerMapInfo playerMapInfo = Converter.convertToPlayerMapInfo(halfMap);
		GameMap gameHalfMap = Converter.convertToGameMap(halfMap);

		server.receiveHalfMap(playerMapInfo, gameHalfMap, gameId);

		return new ResponseEnvelope<Object>();
	}

	@RequestMapping(value = "/{gameID}/states/{playerID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<GameState> getGameState(@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @PathVariable UniquePlayerIdentifier playerID) {

		GameId gameId = Converter.convertToGameId(gameID);
		PlayerId playerId = new PlayerId(playerID.getUniquePlayerID());

		server.checkDoesGameExist(gameId);
		server.checkPlayerIsRegistered(playerId, gameId);

		ResponseEnvelope<GameState> gameStateMessage = new ResponseEnvelope<GameState>(
				server.getGameState(playerId, gameId));// ??????????????

		return gameStateMessage;
	}

	@RequestMapping(value = "/{gameID}/moves", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<Object> receiveMove(@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody PlayerMove playerMove) {

		GameId gameId = Converter.convertToGameId(gameID);
		PlayerId playerId = new PlayerId(playerMove.getUniquePlayerID());
		EGameMove move = Converter.convertToEGameMove(playerMove.getMove());

		server.checkDoesGameExist(gameId);
		server.checkPlayerIsRegistered(playerId, gameId);
		// server.checkIsTurn();

		server.receiveMove(move, playerId, gameId);

		return new ResponseEnvelope<Object>();
	}

	@ExceptionHandler({ GenericExampleException.class })
	public @ResponseBody ResponseEnvelope<?> handleException(GenericExampleException ex, HttpServletResponse response) {
		ResponseEnvelope<?> result = new ResponseEnvelope<>(ex.getErrorName(), ex.getMessage());

		response.setStatus(HttpServletResponse.SC_OK);
		return result;
	}
}
