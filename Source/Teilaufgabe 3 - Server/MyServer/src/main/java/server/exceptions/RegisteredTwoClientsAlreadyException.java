package server.exceptions;

public class RegisteredTwoClientsAlreadyException extends GenericExampleException {

	public RegisteredTwoClientsAlreadyException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
