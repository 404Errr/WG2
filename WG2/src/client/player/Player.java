package client.player;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import client.entity.Entity;
import client.level.Level;
import client.level.SpawnPoint;
import client.player.controlled.ControlledPlayer;
import client.weapon.PlayerWeapon;
import client.weapon.weapon.BasicGun;
import client.weapon.weapon.FragGrenade;
import client.weapon.weapon.MachineGun;
import client.weapon.weapon.RailGun;
import client.weapon.weapon.ShotGun;
import data.ControlData;
import data.Data;
import data.PlayerData;
import data.TileData;
import data.WeaponData;
import util.Util;

public abstract class Player extends Entity implements WeaponData, PlayerData, Data, TileData, ControlData {
	protected float health, facing;//facing is in radians
	protected boolean[] canMove, movementControl, mouseControl;//r,d,l,u. l, m, r
	protected boolean highPowerGrenade;
	protected Color color;
	protected List<PlayerWeapon> weapons;
	protected PlayerWeapon activeWeapon;

	public Player(Color color, float x, float y) {
		super(color, x, y);
		this.color = color;
		health = PLAYER_INITIAL_HEALTH;
		movementControl = new boolean[4];
		canMove = new boolean[4];
		mouseControl = new boolean[3];
		weapons = new ArrayList<>();
		if (ALL_GUNS_AT_START) {
			addWeapon(new PlayerWeapon(this, new BasicGun(this)));
			addWeapon(new PlayerWeapon(this, new ShotGun(this)));
			addWeapon(new PlayerWeapon(this, new MachineGun(this)));
			addWeapon(new PlayerWeapon(this, new RailGun(this)));
			addWeapon(new PlayerWeapon(this, new FragGrenade(this)));

			selectWeapon(STARTING_GUN);
		}
	}

	public void respawn(SpawnPoint spawnPoint) {
		health = PLAYER_INITIAL_HEALTH;
		move((float)spawnPoint.getX(), (float)spawnPoint.getY());
	}

	public void move(float x, float y, float facing) {
		super.move(x, y);
		setFacing(facing);
	}

	protected void dPosition(float dX, float dY) {
		setAllCanMove(true);//sets all values in canMove to true
		float inc = 0.025f, remaining, sign;//inc - the increment between collision checks
		remaining = Math.abs(dX);//the magnitude of dX
		sign = Math.signum(dX);//the sign of dX
		while (remaining>0) {
			if (remaining>=inc) x+=inc*sign;//if remaining isnt smaller than increment, change x by increment
			else x+=remaining*sign;//if it is, change x by remaining
			if (/*!Edit.editMode&&*/checkWallCollision()) {//if hit something
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
			if (/*!Edit.editMode&&*/checkWallCollision()) {
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
		for (int y = getYTile()-1;y<getYTile()+2;y++) {//for each row within the radius
			for (int x = getXTile()-1;x<getXTile()+2;x++) {//for each collumn within the radius
				if (Level.tileExists(x, y)&&Level.getTile(x, y).isSolid(SOLID_WALLS)&&Level.getTile(x, y).getBounds().intersects(hitbox)) {//bounds check and if tile is solid and if intersects
					return true;
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

	public void recoil(float angle, float magnitude) {
		this.dX+=Util.getXComp(angle, magnitude);
		this.dY+=-Util.getYComp(angle, magnitude);
	}

	@Override
	public boolean tick() {
		turn();
		moveTick();
		for (PlayerWeapon weapon:weapons) {
			weapon.tick();
		}
		if (/*!Edit.editMode&&*/isDead()) respawn(null);
		return false;
	}

	private boolean isDead() {
		return health<=0;
	}

	protected void moveTick() {
		accelerate();//accelerate based on if movement is avtive
		accelerationLimitCheck();//checks if acceleration is over the limit, sets it to the limit if it is
		notAcceleratingCheck();//checks if player shouldn't be accelerating or if ddX/ddY need to be rounded to 0
		dX+=ddX;
		dY+=ddY;
		deccelerate();//deccelerate the player
		speedLimitCheck();//checks if velocity is over the limit, sets it to the limit if it is
		notMovingCheck();//chceks if player shouldn't be able to move or if dX/dY need to be rounded to 0
		dPosition(dX, dY);//move the player by the velocity while doing collision checks
	}

	protected void accelerate() {//affects ddX and ddY
		if (isMovementControl(UP)) {//if the movement is active
			ddY-=PLAYER_ACCELERATION;//change acceleration by PLAYER_ACCELERATION
			if (ddY>0) ddY = 0;//if acceleration is in the opposite direction of the movement control, reset it to 0
		}
		if (isMovementControl(DOWN)) {
			ddY+=PLAYER_ACCELERATION;
			if (ddY<0) ddY = 0;
		}
		if (isMovementControl(LEFT)) {
			ddX-=PLAYER_ACCELERATION;
			if (ddX>0) ddX = 0;
		}
		if (isMovementControl(RIGHT)) {
			ddX+=PLAYER_ACCELERATION;
			if (ddX<0) ddX = 0;
		}
	}

	protected void notAcceleratingCheck() {//affects ddX and ddY
		if ((isMovementControl(UP)==isMovementControl(DOWN)||(!canMove(UP)&&ddY<0)||(!canMove(DOWN)&&ddY>0))) {//if opposite movement controls have the same value or if can't move in the direction acceleration is set to
			ddY = 0;//stop accelerating
		}
		if ((isMovementControl(LEFT)==isMovementControl(RIGHT)||(!canMove(LEFT)&&ddX<0)||(!canMove(RIGHT)&&ddX>0))) {
			ddX = 0;
		}
	}

	protected abstract void turn();

	public boolean isMovementControl(int direction) {
		return movementControl[direction];
	}

	public void setMovementControl(int direction, boolean value ) {
		movementControl[direction] = value;
	}

	public void setAllMovementControl(boolean value) {
		for (int i = 0;i<4;i++) {
			movementControl[i] = value;
		}
	}

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

	public boolean getMouseControl(int control) {
		return mouseControl[control];
	}

	public void setMouseControl(int control, boolean value) {
		this.mouseControl[control] = value;
	}

	public void setAllMouseControl(boolean value) {
		for (int i = 0;i<4;i++) {
			mouseControl[i] = value;
		}
	}

	public float getFacing() {
		return facing;
	}

	protected void setFacing(float facing) {
		this.facing = facing;
	}

	public Rectangle2D getBounds() {
		return new Rectangle2D.Double(x, y, PLAYER_SIZE, PLAYER_SIZE);
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

	public Point getPoint() {
		return new Point(getXTile(), getYTile());
	}

	public PlayerWeapon getActiveWeapon() {
		return activeWeapon;
	}

	public void setActiveWeapon(PlayerWeapon activeGun) {
		this.activeWeapon = activeGun;
	}

	public List<PlayerWeapon> getWeapons() {
		return weapons;
	}

	public void selectWeapon(int weapon) {
		if (weapon>=0&&weapon<weapons.size()) {
			activeWeapon = weapons.get(weapon);
			activeWeapon.setCooldown(WEAPON_SWITCH_COOLDOWN);
		}
	}

	private void addWeapon(PlayerWeapon weapon) {
		weapons.add(weapon);
	}

	public void clearWeapons() {
		weapons.clear();
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public void changeHealth(float dHealth) {
		if (!(DAMAGE!=new Boolean(true)||(this instanceof ControlledPlayer&&CONTROLLED_PLAYER_DAMAGE!=new Boolean(true)))) this.health+=dHealth;
	}

	public boolean highPowerGrenade() {
		return highPowerGrenade;
	}

	public void setHighPowerGrenade(boolean highPowerGrenade) {
		this.highPowerGrenade = highPowerGrenade;
	}

}
