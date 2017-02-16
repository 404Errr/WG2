package player;

import java.awt.geom.Rectangle2D;

import data.Data;
import data.PlayerData;
import level.Tile;

public class PlayerHitbox implements Data, PlayerData {

	private Rectangle2D[] sides;
	private boolean[] touching;

	public PlayerHitbox() {
		sides = new Rectangle2D.Double[4];
		touching = new boolean[4];
		for (int i = 0;i<4;i++) {
			sides[i] = new Rectangle2D.Double();
		}
	}

	public void checkCollision(Tile tile) {
		for (int i = 0;i<4;i++) {
			if (tile.getBounds().intersects(sides[i])) {
				touching[i] = true;
			}
		}
	}

	public void move(double x, double y) {//FIXME
		for (int i = 0;i<4;i++) {
			touching[i] = false;
		}
		final double playerSize = PLAYER_SIZE, barWidth = 0.02d;//player size, width of hitbox rectangle
		sides[UP].setRect(x+barWidth, y, playerSize-barWidth, barWidth);
		sides[DOWN].setRect(x+barWidth, y+playerSize, playerSize-barWidth, barWidth);
		sides[RIGHT].setRect(x+playerSize, y+barWidth, barWidth, playerSize-barWidth);
		sides[LEFT].setRect(x, y+barWidth, barWidth, playerSize-barWidth);
	}

	public Rectangle2D getSide(int side) {
		return sides[side];
	}

	public boolean[] getSides() {
		return touching;
	}
}
