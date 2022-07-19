package client.exceptions;

public class InvalidPlayerRegistrationDataException extends Exception {
	private String firstName;
	private String lastName;
	private String studentId;

	public InvalidPlayerRegistrationDataException(String firstName, String lastName, String studentId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentId = studentId;
	}

	public String getErrorMessage() {
		String s = "";
		s += "Your player registration information is invalid\n";
		s += "first name: " + firstName + "\n";
		s += "last name: " + lastName + "\n";
		s += "studentId: " + studentId + "\n";
		return s;
	}
}
