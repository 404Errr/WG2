package data;

public interface WeaponData {
	boolean ALL_GUNS_AT_START = true, RECOIL = false, DAMAGE = false;
	int STARTING_GUN = 0;

	float WEAPON_SWITCH_COOLDOWN = 30f;

	float BASICGUN_LEGNTH = 0.4f;
	float BASICGUN_WIDTH = 0.25f;
	float BASICGUN_SIZE = 0.15f;
	float BASICGUN_SPEED = 2.1f;
	float BASICGUN_COF = 1.8f;
	float BASICGUN_SPEED_SPREAD = 0.3f;
	float BASICGUN_RECOIL = 0.015f;
	float BASICGUN_COOLDOWN = 10f;
	float BASICGUN_DAMAGE = 0.22f;

	float SHOTGUN_LEGNTH = 0.08f;
	float SHOTGUN_WIDTH = 0.55f;
	float SHOTGUN_SIZE = 0.15f;
	float SHOTGUN_SPEED = 1.9f;
	float SHOTGUN_COF = 2f;
	float SHOTGUN_SPEED_SPREAD = 1f;
	float SHOTGUN_SPREAD = 15f;
	int SHOTGUN_PELLET_COUNT = 30;
	float SHOTGUN_RECOIL = 0.15f;
	float SHOTGUN_COOLDOWN = 35f;
	float SHOTGUN_DAMAGE = 0.8f/SHOTGUN_PELLET_COUNT;//per pellet

	float MACHINEGUN_LEGNTH = 1.2f;
	float MACHINEGUN_WIDTH = 0.5f;
	float MACHINEGUN_SIZE = 0.15f;
	float MACHINEGUN_SPEED = 1.8f;
	float MACHINEGUN_COF = 8f;
	float MACHINEGUN_SPEED_SPREAD = 0.8f;
	float MACHINEGUN_RECOIL = 0.01f;
	float MACHINEGUN_COOLDOWN = 1f;
	float MACHINEGUN_DAMAGE = 0.04f;

	float RAILGUN_LEGNTH = 2.3f;
	float RAILGUN_WIDTH = 0.2f;
	float RAILGUN_LINE_INITIAL_WIDTH = 0.18f;
	float RAILGUN_LINE_DURATION = 0.6f*Data.UPS;
	float RAILGUN_RECOIL = 0.3f;
	float RAILGUN_COOLDOWN = 50f;
	float RAILGUN_DAMAGE = 0.75f;

	float FRAGGRENADE_GRENADE_SIZE = 0.3f;
	float FRAGGRENADE_SIZE = 0.5f;
	float FRAGGRENADE_GRENADE_SPEED_HI = 0.8f;
	float FRAGGRENADE_GRENADE_SPEED_LO = 0.05f;
	float FRAGGRENADE_GRENADE_DECCELERATION = 0.035f;
	float FRAGGRENADE_SPEED = 0.6f;
	float FRAGGRENADE_SPREAD = 0.4f;
	float FRAGGRENADE_RANGE = 6.5f;
	float FRAGGRENADE_COOLDOWN = 40f;
	int FRAGGRENADE_TIMER = 1200;
	int FRAGGRENADE_SHARD_COUNT = 200;
//	int FRAGGRENADE_SHARD_COUNT = 0;
	float FRAGGRENADE_RECOIL = 0.3f;
	float FRAGGRENADE_DAMAGE = 7.5f/FRAGGRENADE_SHARD_COUNT;

	float COOLDOWN_INCREMENT = 0.01667f*Data.UPS;//don't change (or do, I don't care)
}