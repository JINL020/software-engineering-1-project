package client.MVC;

import java.beans.PropertyChangeListener;

import client.gameMap.Position;

public class GameMapVisualisation {

	public GameMapVisualisation(MatchState matchState) {
		matchState.addListener(modelChangedListener);
	}

	final PropertyChangeListener modelChangedListener = event -> {
		Object model = event.getSource();

		if (model instanceof MatchState) {
			MatchState castedModel = (MatchState) model;
			displayMatchState(castedModel);
		}
	};

	private void displayMatchState(MatchState castedModel) {
		String s = "\n";
		s += "My Position: " + castedModel.getMyNode().getPosition() + "\n";
		s += "Enemy Position: " + castedModel.getEnemyNode().getPosition() + "\n";
		s += "Treasure Position: ";
		if (castedModel.hasCollectedTreasure()) {
			s += "collected\n";
		} else {
			s += (castedModel.hasFoundTreasure() ? castedModel.getTreasureNode().getPosition() : "not found yet")
					+ "\n";
		}
		s += "EnemyFort Position: ";
		if (castedModel.hasArrivedAtEnemyFort()) {
			s += "arrived at enemy fort\n";
		} else {
			s += (castedModel.hasFoundEnemyFort() ? castedModel.getEnemyFortNode().getPosition() : "not found yet")
					+ "\n";
		}
		s += "\n" + castedModel.getGameFullMap();
		s += "Visited nodes: ";
		for (Position pos : castedModel.getHistory()) {
			s += pos.toString();
		}
		s += "\n\n\n";
		System.out.println(s);
	}
}