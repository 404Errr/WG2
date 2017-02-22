package client.player;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import client.entity.Entity;
import client.game.Game;
import client.level.Level;
import client.weapon.Gun;
import client.weapon.GunType;
import data.Data;
import data.PlayerData;
import data.TileData;
import data.WeaponData;
import util.Util;

public abstract class Player extends Entity implements WeaponData, PlayerData, Data, TileData {
	protected float facing;//facing is in radians
	protected boolean[] canMove;//r,d,l,u
	protected Color color;
	protected List<Gun> guns;
	protected Gun activeGun;

	public Player(Color color, float x, float y) {
		super(color, x, y);
		this.color = color;
		canMove = new boolean[4];
		guns = new ArrayList<>();
		if (ALL_GUNS_AT_START) {
			for (GunType type:GunType.getTypes()) {
				addGun(type);
			}
			selectGun(STARTING_GUN);
		}
	}

	public void move(float x, float y, float facing) {
		super.move(x, y);
		setFacing(facing);
	}

	protected void notAcceleratingCheck() {//affects ddX and ddY
		if ((!canMove(UP)&&ddY<0)||(!canMove(DOWN)&&ddY>0)) {//if opposite movement keys have the same value or if cant move in the direction acceleration is set to
			ddY = 0;//stop accelerating
		}
		if ((!canMove(LEFT)&&ddX<0)||(!canMove(RIGHT)&&ddX>0)) {
			ddX = 0;
		}
	}

	protected void dPosition(float dX, float dY) {
		setAllCanMove(true);//set all values in canMove to true
		float inc = 0.025f, remaining, sign;//inc - the increment between collision checks
		remaining = Math.abs(dX);//the magnitude of dX
		sign = Math.signum(dX);//the sign of dX
		while (remaining>0) {
			if (remaining>=inc) x+=inc*sign;//if remaining isnt smaller than increment, change x by increment
			else x+=remaining*sign;//if it is, change x by remaining
			if (checkWallCollision()) {//if hit something
				if (sign>0) setCanMove(RIGHT, false);//if was trying to move to the right, setcanmove right to false
				if (sign<0) setCanMove(LEFT, false);
				x = Math.round(x);//reallign to grid
				break;//stop checking x
			}
			remaining-=inc;
		}
		remaining = Math.abs(dY);
		sign = Math.signum(dY);
		while (remaining>0) {
			if (remaining>=inc) y+=inc*sign;
			else y+=remaining*sign;
			if (checkWallCollision()) {
				if (sign<0) setCanMove(UP, false);
				if (sign>0) setCanMove(DOWN, false);
				y = Math.round(y);
				break;
			}
			remaining-=inc;
		}
	}

	protected boolean checkWallCollision() {
		final Rectangle2D hitbox = new Rectangle2D.Float(x, y, PLAYER_SIZE, PLAYER_SIZE);
		for (int r = getYTile()-1;r<getYTile()+2;r++) {//for each row within the radius
			for (int c = getXTile()-1;c<getXTile()+2;c++) {//for each collumn within the radius
				if (r>=0&&c>=0&&r<Level.getHeight()&&c<Level.getWidth()&&Level.getTile(c, r).isSolid(SOLID_WALLS)) {//bounds check and if tile is solid
					if (Level.getTile(c, r).getBounds().intersects(hitbox)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	protected boolean speedLimitCheck() {//returns true if it had to enforce the limit (not in use), affects dX and dY
		boolean enforced = false;//if it enforced the limit
		if (Math.abs(dX)>PLAYER_SPEED_LIMIT) {//if over the limit
			dX = Math.signum(dX)*PLAYER_SPEED_LIMIT;//set to the limit
			enforced = true;
		}
		if (Math.abs(dY)>PLAYER_SPEED_LIMIT) {
			dY = Math.signum(dY)*PLAYER_SPEED_LIMIT;
			enforced = true;
		}
		return enforced;
	}

	protected void notMovingCheck() {//affects dX and dY
		if (Math.abs(dY)<0.0001d||(!canMove(UP)&&dY<0)||(!canMove(DOWN)&&dY>0)) {//if velocity should be rounded to 0 or if cant move in the direction the velocity is set to
			dY = 0;//set velocity to 0
		}
		if (Math.abs(dX)<0.0001d||(!canMove(LEFT)&&dX<0)||(!canMove(RIGHT)&&dX>0)) {
			dX = 0;
		}
	}



	protected void accelerationLimitCheck() {//affects ddX and ddY
		if (Math.abs(ddX)>PLAYER_ACCELERATION_LIMIT) {//if over the limit
			ddX = Math.signum(ddX)*PLAYER_ACCELERATION_LIMIT;//set to the limit
		}
		if (Math.abs(ddY)>PLAYER_ACCELERATION_LIMIT) {
			ddY = Math.signum(ddY)*PLAYER_ACCELERATION_LIMIT;
		}
	}

	protected void deccelerate() {//affects dX and dY
		if (Math.abs(dX)>0) {//if velocity>0
			dX*=PLAYER_DECCELERATION;//multiply velocity by PLAYER_DECCELERATION (less than 1)
		}
		if (Math.abs(dY)>0) {
			dY*=PLAYER_DECCELERATION;
		}
	}

	public void recoil(float magnitude) {
		this.dX+=-Util.getXComp(Game.getPlayer().getFacing(), magnitude);
		this.dY+=Util.getYComp(Game.getPlayer().getFacing(), magnitude);
	}

	@Override
	public boolean tick() {
		turn();
		move();
		for (Gun gun:guns) {
			gun.tick();
		}
		return false;
	}

	protected abstract void move();
	protected abstract void turn();
	protected abstract void accelerate();

	protected boolean canMove(int side) {
		return canMove[side];
	}

	protected void setCanMove(int side, boolean value) {
		canMove[side] = value;
	}

	protected void setAllCanMove(boolean value) {
		for (int i = 0;i<4;i++) {
			canMove[i] = value;
		}
	}

	public float getFacing() {
		return facing;
	}

	protected void setFacing(float facing) {
		this.facing = facing;
	}

	public Color getColor() {
		return color;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getXCenter() {
		return x+HALF_PLAYER_SIZE;
	}

	public float getYCenter() {
		return y+HALF_PLAYER_SIZE;
	}

	public int getXTile() {
		return Math.round(x);
	}

	public int getYTile() {
		return Math.round(y);
	}

	public float getdX() {
		return dX;
	}

	public float getdY() {
		return dY;
	}

	public float getddX() {
		return ddX;
	}

	public float getddY() {
		return ddY;
	}

	public Gun getActiveGun() {
		return activeGun;
	}

	public void setActiveGun(Gun activeGun) {
		this.activeGun = activeGun;
	}

	public List<Gun> getGuns() {
		return guns;
	}

	public void selectGun(int gun) {
		if (gun>=0&&gun<guns.size()) {
			activeGun = guns.get(gun);
		}
	}

	public void addGun(GunType type) {
		guns.add(new Gun(this, type));
	}

	public void clearGuns() {
		guns.clear();
	}
}
