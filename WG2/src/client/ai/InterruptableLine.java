package client.ai;

import client.level.Level;
import data.AIData;
import data.Data;
import data.PlayerData;
import data.TileData;

public abstract class InterruptableLine implements Data, AIData, TileData, PlayerData {

	protected float x1, y1, x2, y2;
	protected boolean uninterrupted;

	public abstract void setLocation();

	public boolean setUninterrupted() {
		int x1 = (int)this.x1, y1 = (int)this.y1, x2 = (int)this.x2, y2 = (int)this.y2;
		int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1), sx = x1<x2?1:-1,  sy = y1<y2?1:-1, err = dx-dy, e2;
		while (true) {
			if (x1>=0&&y1>=0&&x1<Level.getWidth()&&y1<Level.getHeight()&&Level.getTile(x1, y1).isSolid(SOLID_WALLS)&&Level.getTile(x1, y1).isSolid(SOLID_PROJECTILES)) return false;
			if (x1==x2&&y1==y2) return true;
			e2 = 2*err;
			if (e2>-dy) {
				err = err-dy;
				x1 = x1+sx;
			}
			if (e2<dx) {
				err = err+dx;
				y1 = y1+sy;
			}
		}
	}

	public void update() {
		uninterrupted = setUninterrupted();
	}

	public boolean isUninterrupted() {
		return uninterrupted;
	}

	public float getX1() {
		return x1;
	}

	public float getY1() {
		return y1;
	}

	public float getX2() {
		return x2;
	}

	public float getY2() {
		return y2;
	}
}
