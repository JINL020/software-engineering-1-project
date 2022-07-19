package MVC;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import MessagesBase.UniquePlayerIdentifier;
import client.MVC.GameController;
import client.exceptions.InvalidPlayerRegistrationDataException;
import client.exceptions.SentActionToSlowException;
import client.network.Network;

public class GameController_UnitTest {

	// see how this is set up by the @BeforeEach method
	private Network network = null;
	private GameController controller = null;

	// execute the tests and check out the console to see the order of the
	// before/after executions

	@BeforeAll
	public static void setUpBeforeClass() {
		// executed once, before all tests in this class
		// use to prepare stuff and dependencies
	}

	// MUST BE STATIC!
	@AfterAll
	public static void tearDownAfterClass() {
		// executed once, after all tests in this class
		// use to clean up stuff and dependencies
	}

	@BeforeEach
	public void setUp() {
		// executed before each tests in this class
		// use to prepare stuff and dependencies
		network = Mockito.mock(Network.class);
		controller = new GameController(network);
	}

	@AfterEach
	public void tearDown() {
		// executed after each test in this class
		// use to clean up stuff and dependencies
	}

	@Test
	public void registerClient_ThrowException() {
		String firstName = "   ";
		String lastName = "Lee";
		String studentId = "743842949";
		Mockito.when(network.registerClient(firstName, lastName, studentId))
				.thenReturn(new UniquePlayerIdentifier("1234"));

		Executable testCode = () -> controller.registerClient(firstName, lastName, studentId);

		InvalidPlayerRegistrationDataException e = Assertions.assertThrows(InvalidPlayerRegistrationDataException.class,
				testCode,
				"We expected a exception because the the player registration data is not valid, but it was not thrown");
		System.out.println(e.getErrorMessage());
	}

	@Test
	public void registerClient_noException() {
		String firstName = "Jin-Jin";
		String lastName = "Lee";
		String studentId = "743842949";
		Mockito.when(network.registerClient(firstName, lastName, studentId))
				.thenReturn(new UniquePlayerIdentifier("1234"));
		try {
			controller.registerClient(firstName, lastName, studentId);
		} catch (InvalidPlayerRegistrationDataException e) {
		}
	}

	@Test
	public void sendGameHalfMap_tooSlow_throwException() {
		Mockito.when(network.hasLost()).thenReturn(true);

		Executable testCode1 = () -> controller.sendGameHalfMap();

		SentActionToSlowException e1 = Assertions.assertThrows(SentActionToSlowException.class, testCode1,
				"We expected a exception because the map was sent too slow, but it was not thrown");
		System.out.println(e1.getErrorMessage());

		Mockito.when(network.hasWon()).thenReturn(true);

		Executable testCode2 = () -> controller.sendGameHalfMap();

		SentActionToSlowException e2 = Assertions.assertThrows(SentActionToSlowException.class, testCode2,
				"We expected a exception because the map was sent too slow, but it was not thrown");
		System.out.println(e2.getErrorMessage());
	}

	@Test
	public void sendGameHalfMap_noException() {
		Mockito.when(network.isTurn()).thenReturn(true);
		controller.sendGameHalfMap();
	}

}
