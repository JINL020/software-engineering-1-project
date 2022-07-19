package client.exceptions;

public class NetworkCommunicationException extends RuntimeException {
	public NetworkCommunicationException(String errorMessage) {
		super(errorMessage);
	}

	public String getErrorMessage() {
		String s = "Something went wrong with server communication:\n";
		s += this.getMessage();
		return s;
	}
}
