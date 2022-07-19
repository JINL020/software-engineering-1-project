package moveGenerator;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import client.exceptions.FindPathNodeIsWaterException;
import client.gameMap.GameMap;
import client.gameMap.GameMapNode;
import client.gameMap.Position;
import client.moveGenerator.PathFinder;
import createData.CreateMaps;

public class PathFinder_UnitTest {
	GameMap gameHalfMap = null;

	@BeforeAll
	public static void setUpBeforeClass() {

	}

	@AfterAll
	public static void tearDownAfterClass() {

	}

	@BeforeEach
	public void setUp() {
		gameHalfMap = CreateMaps.getHalfMap0();
	}

	@AfterEach
	public void tearDown() {
	}

	@Test
	public void findPath_onWaterNodes_shouldThrowException() {
		GameMapNode myPosNode = gameHalfMap.getNode(new Position(3, 2));
		GameMapNode waterNode = gameHalfMap.getNode(new Position(0, 0));

		Executable testCode = () -> PathFinder.findPath(myPosNode, waterNode, gameHalfMap);

		FindPathNodeIsWaterException e = Assertions.assertThrows(FindPathNodeIsWaterException.class, testCode,
				"We expected a exception because the target node is water, but it was not thrown");

		System.out.println(e.getErrorMessage());
	}

	@ParameterizedTest
	@MethodSource("prepareData_gotShortestPath_searchPath_gotShortestPath")
	public void gotShortestPath_searchPath_gotShortestPath(Position targetPosition, int expected) {
		GameMapNode start = gameHalfMap.getNode(new Position(3, 2));
		GameMapNode target = gameHalfMap.getNode(targetPosition);

		PathFinder.findPath(start, target, gameHalfMap);
		int result = target.getCost();

		Assertions.assertEquals(expected, result,
				MessageFormat.format("Expected {0} MoveActions needed for shortest Path between {1} and {2}", expected,
						start.getPosition(), targetPosition));

	}

	private static Stream<Arguments> prepareData_gotShortestPath_searchPath_gotShortestPath() {

		return Stream.of(Arguments.of(new Position(3, 2), 0), Arguments.of(new Position(0, 1), 11),
				Arguments.of(new Position(0, 3), 10), Arguments.of(new Position(7, 0), 16),
				Arguments.of(new Position(7, 2), 14), Arguments.of(new Position(7, 3), 15),
				Arguments.of(new Position(7, 0), 16));
	}

}
