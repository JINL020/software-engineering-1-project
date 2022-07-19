package moveGenerator;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.enums.EGameMove;
import client.gameMap.GameMap;
import client.gameMap.GameMapNode;
import client.gameMap.Position;
import client.moveGenerator.MoveGenerator;
import createData.CreateMaps;

public class MoveGenerator_UnitTest {

	// see how this is set up by the @BeforeEach method
	private GameMap gameFullMap = null;

	// execute the tests and check out the console to see the order of the
	// before/after executionss

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
		gameFullMap = CreateMaps.getFullMap0();
	}

	@AfterEach
	public void tearDown() {
		// executed after each test in this class
		// use to clean up stuff and dependencies
	}

	@Test
	public void getNextMoves_withTreausureNotFound() {
		// fort pos
		GameMapNode myNode = gameFullMap.getNode(new Position(3, 2));
		Set<GameMapNode> uncoveredNodes = new HashSet<GameMapNode>();

		Queue<EGameMove> result = new ArrayDeque<EGameMove>();
		result.addAll(MoveGenerator.getNextMoves(myNode, uncoveredNodes, gameFullMap, false));

		Queue<EGameMove> expected = new ArrayDeque<EGameMove>();
		expected.add(EGameMove.UP);
		expected.add(EGameMove.UP);
		expected.add(EGameMove.UP);

		while (!result.isEmpty()) {
			Assertions.assertEquals(expected.poll(), result.poll(), MessageFormat.format("Expected: {0}", expected));
		}
	}

	@Test
	public void getMovesToDestination_test() {
		GameMapNode myNode = gameFullMap.getNode(new Position(3, 2));
		GameMapNode destination = gameFullMap.getNode(new Position(0, 3));

		Queue<EGameMove> result = new ArrayDeque<EGameMove>();
		result.addAll(MoveGenerator.getMovesToDestination(myNode, destination, gameFullMap));

		Queue<EGameMove> expected = new ArrayDeque<EGameMove>();
		expected.add(EGameMove.LEFT);
		expected.add(EGameMove.LEFT);
		expected.add(EGameMove.LEFT);
		expected.add(EGameMove.LEFT);
		expected.add(EGameMove.LEFT);

		expected.add(EGameMove.DOWN);
		expected.add(EGameMove.DOWN);
		expected.add(EGameMove.DOWN);

		expected.add(EGameMove.LEFT);
		expected.add(EGameMove.LEFT);

		while (!result.isEmpty()) {
			Assertions.assertEquals(expected.poll(), result.poll(), MessageFormat.format("Expected: {0}", expected));
		}
	}

}
