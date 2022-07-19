package server.mapValidator;

public class FloodFill {
	public static int getAccesibleNodesCount(boolean[][] map, int startX, int startY) {
		floodFill(map, startX, startY);

		int waterCounter = 0;
		for (boolean[] y : map) {
			for (boolean x : y)
				if (x == true)
					waterCounter++;
		}
		return waterCounter;
	}

	private static void floodFill(boolean[][] map, int x, int y) {// false == gras and true == water
		if (x < 0 || y < 0 || x >= map[0].length || y >= map.length || map[y][x] == true)
			return;
		fill(map, x, y);
		return;
	}

	private static void fill(boolean[][] map, int x, int y) {
		if (x < 0 || y < 0 || x >= map[0].length || y >= map.length || map[y][x] == true)
			return;
		map[y][x] = true;
		fill(map, x - 1, y);
		fill(map, x + 1, y);
		fill(map, x, y - 1);
		fill(map, x, y + 1);
	}

}
