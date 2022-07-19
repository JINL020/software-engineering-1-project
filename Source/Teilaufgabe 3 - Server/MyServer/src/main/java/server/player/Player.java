package server.player;

public class Player {
	private PlayerId playerId;
	private String firstName;
	private String lastName;
	private String studentId;
	private EGamePlayerGameState state;
	private boolean collectedTreasure;

	public Player(PlayerId playerId, String firstName, String lastName, String studentId) {
		this.playerId = playerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentId = studentId;
		this.state = EGamePlayerGameState.MustWait;
		this.collectedTreasure = false;

	}

	public PlayerId getPlayerId() {
		return playerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getStudentId() {
		return studentId;
	}

	public EGamePlayerGameState getState() {
		return state;
	}

	public boolean hasCollectedTreasure() {
		return collectedTreasure;
	}

	public void setState(EGamePlayerGameState state) {
		this.state = state;
	}

	public Player getDummyPlayer() {
		return new Player(new PlayerId(), firstName, lastName, studentId);
	}

}
