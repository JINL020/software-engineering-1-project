package client.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import MessagesBase.EMove;
import MessagesBase.ERequestState;
import MessagesBase.HalfMap;
import MessagesBase.PlayerMove;
import MessagesBase.PlayerRegistration;
import MessagesBase.ResponseEnvelope;
import MessagesBase.UniquePlayerIdentifier;
import MessagesGameState.EPlayerGameState;
import MessagesGameState.GameState;
import MessagesGameState.PlayerState;
import client.enums.EGameMove;
import client.exceptions.NetworkCommunicationException;
import client.gameMap.GameMap;
import reactor.core.publisher.Mono;

public class Network {
	private final static Logger logger = LoggerFactory.getLogger(Network.class);

	private String gameId;
	private String playerId;
	private WebClient baseWebClient;

	NetworkConverter converter;

	public Network(String serverBaseUrl, String gameID) {
		this.gameId = gameID;
		this.baseWebClient = WebClient.builder().baseUrl(serverBaseUrl + "/games")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();
		// the network protocol uses XML
		this.converter = new NetworkConverter();
	}

	public UniquePlayerIdentifier registerClient(String firstname, String lastname, String studentId) {
		PlayerRegistration playerReg = new PlayerRegistration(firstname, lastname, studentId);
		// Mono is a empty container until someone put something inside
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/players")
				.body(BodyInserters.fromValue(playerReg))// specify the data which is sent to the server
				.retrieve().bodyToMono(ResponseEnvelope.class);// specify the object returned by the server

		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();
		// block till something is put in webAccess

		if (resultReg.getState() == ERequestState.Error) {
			throw new NetworkCommunicationException(resultReg.getExceptionMessage());
		}
		playerId = resultReg.getData().get().getUniquePlayerID();
		return resultReg.getData().get();
	}

	private GameState getGameState() {
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
				.uri("/" + gameId + "/states/" + playerId).retrieve().bodyToMono(ResponseEnvelope.class);
		// specify the object returned by the server
		ResponseEnvelope<GameState> requestResult = webAccess.block();

		if (requestResult.getState() == ERequestState.Error) {
			throw new NetworkCommunicationException(requestResult.getExceptionMessage());
		}
		return requestResult.getData().get();
	}

	public EPlayerGameState getEPlayerGameState() {// in eigens enum umwandeln??
		EPlayerGameState playerGameState = null;
		for (PlayerState player : getGameState().getPlayers()) {
			if (player.getUniquePlayerID().equals(playerId))
				playerGameState = player.getState();
		}
		return playerGameState;
	}

	public boolean isTurn() {
		if (getEPlayerGameState().equals(EPlayerGameState.MustAct))
			return true;
		return false;
	}

	public boolean hasWon() {
		if (this.getEPlayerGameState().equals(EPlayerGameState.Won))
			return true;
		return false;
	}

	public boolean hasLost() {
		if (getEPlayerGameState().equals(EPlayerGameState.Lost))
			return true;
		return false;
	}

	public void sendMap(GameMap gameHalfMap) {
		HalfMap halfMap = converter.convertToHalfMap(gameHalfMap, playerId);

		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/halfmaps/")
				.body(BodyInserters.fromValue(halfMap)).retrieve().bodyToMono(ResponseEnvelope.class);

		ResponseEnvelope<GameState> requestResult = webAccess.block();

		if (requestResult.getState() == ERequestState.Error) {
			throw new NetworkCommunicationException(requestResult.getExceptionMessage());
		}
	}

	public GameMap getGameFullMap() {
		return converter.convertToGameFullMap(getGameState().getMap());
	}

	public boolean hasCollectedTreasure() {
		for (PlayerState player : getGameState().getPlayers()) {
			if (player.getUniquePlayerID().equals(playerId) && player.hasCollectedTreasure()) {
				return true;
			}
		}
		return false;
	}

	public void sendMove(EGameMove move) {
		EMove MoveToSend = converter.convertToEMove(move);
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/moves")
				.body(BodyInserters.fromValue(PlayerMove.of(playerId, MoveToSend))).retrieve()
				.bodyToMono(ResponseEnvelope.class);

		ResponseEnvelope<GameState> requestResult = webAccess.block();

		if (requestResult.getState() == ERequestState.Error) {
			throw new NetworkCommunicationException(requestResult.getExceptionMessage());
		}
	}
}
