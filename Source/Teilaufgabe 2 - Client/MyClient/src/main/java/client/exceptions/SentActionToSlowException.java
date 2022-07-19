package client.exceptions;

public class SentActionToSlowException extends RuntimeException {

	public SentActionToSlowException(String errorMessage) {
		super(errorMessage);
	}

	public String getErrorMessage() {
		return this.getMessage();
	}
}
