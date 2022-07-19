package GameMap;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import client.gameMap.GameMap;
import client.gameMap.GameMapNode;
import client.gameMap.Position;
import createData.CreateMaps;

public class GameMap_UnitTest {
	private GameMap halfmap = null;
	private GameMap fullmap0 = null;
	private GameMap fullmap1 = null;

	@BeforeAll
	public static void setUpBeforeClass() {
	}

	@AfterAll
	public static void tearDownAfterClass() {
	}

	@BeforeEach
	public void setUp() {
		halfmap = CreateMaps.getHalfMap1();
		fullmap0 = CreateMaps.getFullMap0();
		fullmap1 = CreateMaps.getFullMap1();
	}

	@AfterEach
	public void tearDown() {

	}

	@Test
	public void getAllGrasNodes_test() {
		int result = halfmap.getAllGrasNodes().size();
		int expected = 15;

		Assertions.assertEquals(expected, result, MessageFormat.format("Expected {0} grass nodes", expected));
	}

	@Test
	public void getEnemyMapNodes_longMap_test() {
		List<GameMapNode> enemyNodes = fullmap0.getEnemyMapNodes();

		List<Position> result = new ArrayList<Position>();
		for (GameMapNode node : enemyNodes) {
			result.add(node.getPosition());
		}

		List<Position> expected = new ArrayList<Position>();
		for (Position pos : fullmap0.getGameMap().keySet()) {
			if (pos.getX() > 7)
				expected.add(pos);
		}

		Assertions.assertEquals(expected, result, MessageFormat.format("Expected {0} ", expected));
	}

	@Test
	public void getEnemyMapNodes_squareMap_test() {
		List<GameMapNode> enemyNodes = fullmap1.getEnemyMapNodes();

		List<Position> result = new ArrayList<Position>();
		for (GameMapNode node : enemyNodes) {
			result.add(node.getPosition());
		}

		List<Position> expected = new ArrayList<Position>();
		for (Position pos : fullmap1.getGameMap().keySet()) {
			if (pos.getX() < 8 && pos.getY() >= 4)
				expected.add(pos);
		}

		Assertions.assertEquals(expected, result, MessageFormat.format("Expected {0} ", expected));
	}

	@ParameterizedTest
	@MethodSource("prepareData_getAllVisibleGrasNodes_test")
	public void getAllVisibleGrasNodes_test(Position pos, int expected) {
		int result = halfmap.getVisibleGrasNodes(pos).size();

		Assertions.assertEquals(expected, result,
				MessageFormat.format("Expected {0} grass nodes at {1}", expected, pos));
	}

	private static Stream<Arguments> prepareData_getAllVisibleGrasNodes_test() {
		return Stream.of(Arguments.of(new Position(0, 0), 0), Arguments.of(new Position(0, 1), 3),
				Arguments.of(new Position(1, 2), 4), Arguments.of(new Position(2, 1), 5),
				Arguments.of(new Position(3, 1), 6), Arguments.of(new Position(6, 1), 1),
				Arguments.of(new Position(7, 3), 0), Arguments.of(new Position(2, 2), 1));
	}

}
