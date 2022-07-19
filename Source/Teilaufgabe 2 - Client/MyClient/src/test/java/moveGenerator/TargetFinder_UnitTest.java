package moveGenerator;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.gameMap.GameMap;
import client.gameMap.GameMapNode;
import client.gameMap.Position;
import client.moveGenerator.TargetFinder;
import createData.CreateMaps;

public class TargetFinder_UnitTest {

	// see how this is set up by the @BeforeEach method
	private GameMap gameHalfMap = null;

	// execute the tests and check out the console to see the order of the
	// before/after executions

	// MUST BE STATIC!
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
		gameHalfMap = CreateMaps.getHalfMap0();
	}

	@AfterEach
	public void tearDown() {
		// executed after each test in this class
		// use to clean up stuff and dependencies
	}

	@Test
	public void getTargetNode_noHistory() {
		GameMapNode myNode = gameHalfMap.getNode(new Position(3, 2));
		Set<GameMapNode> uncoveredNodes = new HashSet<GameMapNode>();
		GameMap mapToSearch = new GameMap(gameHalfMap.getAllNodes());

		GameMapNode result = TargetFinder.getTargetNode(myNode, mapToSearch, uncoveredNodes, gameHalfMap);
		GameMapNode expected = gameHalfMap.getNode(new Position(3, 1));

		Assertions.assertEquals(expected, result,
				MessageFormat.format("Expected {0} to be best node", expected.getPosition()));
	}
}
