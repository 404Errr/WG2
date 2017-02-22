package client.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import client.entity.Entity;
import client.player.AIPlayer;
import client.player.ControlledPlayer;
import data.ColorData;

public class Game implements ColorData {
	private static ControlledPlayer player;//the user's player
	private static List<Entity> entities;

	public static void init() {
		player = new ControlledPlayer(COLOR_PLAYER, 1f, 1f);//the player
		entities = new ArrayList<>();
		entities.add(player);
		entities.add(new AIPlayer(Color.RED, 10f, 10f));
	}

	public static ControlledPlayer getPlayer() {
		return player;
	}

	public static Entity getEntity(int entity) {
		return entities.get(entity);
	}

	public static void addEntity(Entity entity) {
		entities.add(entity);
	}

	public static List<Entity> getEntities() {
		return entities;
	}
}
