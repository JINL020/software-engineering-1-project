package server.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameFullMap {
	private Map<PlayerMapInfo, GameMap> data = new HashMap<PlayerMapInfo, GameMap>();

	public void addHalfMap(PlayerMapInfo playerMapInfo, GameMap gameHalfMap) {
		data.put(playerMapInfo, gameHalfMap);
	}

	public Map<PlayerMapInfo, GameMap> getData() {
		return data;
	}

	public Set<PlayerMapInfo> getPlayerMapInfos() {
		return data.keySet();
	}

}
