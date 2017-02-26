package client.weapon.weapon;

import client.game.Game;
import client.player.Player;
import client.weapon.Weapon;
import client.weapon.entity.Projectile;
import data.WeaponData;
import util.Util;

public class BasicGun extends Weapon implements WeaponData {

	public BasicGun(Player owner) {
		super(owner, BASICGUN_COOLDOWN, BASICGUN_LEGNTH, BASICGUN_WIDTH);
	}

	@Override
	protected void use() {
		float angle = Util.getAngleSpread(owner.getFacing(), BASICGUN_COF), speed = Util.getSpread(BASICGUN_SPEED, BASICGUN_SPEED_SPREAD);
		float dX = owner.getdX()+Util.getXComp(angle, speed), dY = owner.getdY()-Util.getYComp(angle, speed);
		Game.addEntity(new Projectile(BASICGUN_DAMAGE, BASICGUN_RECOIL, BASICGUN_SIZE, owner.getColor(), owner.getXCenter(), owner.getYCenter(), dX, dY));
		owner.recoil(Util.getAngle(0, 0, dX, dY), -BASICGUN_RECOIL);
	}

}
