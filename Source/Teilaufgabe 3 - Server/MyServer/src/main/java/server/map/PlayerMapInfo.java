package server.map;

import java.util.Random;

import server.player.PlayerId;

public class PlayerMapInfo {
	private PlayerId playerId;
	private Position myPlayerPos;
	private Position myFortPos;
	private Position myTreasurePos;

	public PlayerMapInfo(String playerId, Position myPlayerPos, Position myFortPos) {
		this.playerId = new PlayerId(playerId);
		this.myPlayerPos = myPlayerPos;
		this.myFortPos = myFortPos;
		this.myTreasurePos = null;
	}

	public PlayerId getPlayerId() {
		return playerId;
	}

	public Position getMyPlayerPos() {
		return myPlayerPos;
	}

	public Position getMyFortPos() {
		return myFortPos;
	}

	public Position getMyTreasurePos() {
		return myTreasurePos;
	}

	public void setMyPlayerPos(Position myPlayerPos) {
		this.myPlayerPos = myPlayerPos;
	}

	public void setMyFortPos(Position myFortPos) {
		this.myFortPos = myFortPos;
	}

	public void setMyTreasurePos(Position myTreasurePos) {
		this.myTreasurePos = myTreasurePos;
	}

	public PlayerMapInfo getDummy() {
		Random rand = new Random();
		Position fakePos = new Position(rand.nextInt(8), rand.nextInt(4));
		PlayerMapInfo dummy = new PlayerMapInfo(playerId.getId(), fakePos, myFortPos);
		return dummy;
	}

}
