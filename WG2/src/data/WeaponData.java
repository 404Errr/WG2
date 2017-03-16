package data;

public interface WeaponData {
	float WEAPON_SWITCH_COOLDOWN = 25f;

	float BASICGUN_LEGNTH = 0.4f;
	float BASICGUN_WIDTH = 0.25f;
	float BASICGUN_SIZE = 0.15f;
	float BASICGUN_SPEED = 2.1f;
	float BASICGUN_SPEED_SPREAD = 0.3f;
	float BASICGUN_COF = 1.8f;
	float BASICGUN_RECOIL = 0.015f;
	float BASICGUN_RPM = 400f;
	float BASICGUN_DAMAGE = 0.22f;

	float SHOTGUN_LEGNTH = 0.08f;
	float SHOTGUN_WIDTH = 0.55f;
	float SHOTGUN_PELLET_SIZE = 0.15f;
	float SHOTGUN_PELLET_SPEED = 1.9f;
	float SHOTGUN_SPEED_SPREAD = 1.2f;
	float SHOTGUN_COF = 2f;
	float SHOTGUN_PELLET_COF = 15f;
	int SHOTGUN_PELLET_COUNT = 30;
	float SHOTGUN_RECOIL = 0.15f;
	float SHOTGUN_RPM = 200f;
	float SHOTGUN_DAMAGE = 0.8f/SHOTGUN_PELLET_COUNT;//per pellet

	float MACHINEGUN_LEGNTH = 1.2f;
	float MACHINEGUN_WIDTH = 0.5f;
	float MACHINEGUN_SIZE = 0.15f;
	float MACHINEGUN_SPEED = 1.8f;
	float MACHINEGUN_COF = 8f;
	float MACHINEGUN_SPEED_SPREAD = 0.8f;
	float MACHINEGUN_RECOIL = 0.01f;
	float MACHINEGUN_INITIAL_RPM = 300f;
	float MACHINEGUN_MAX_RPM = 15000;
//	float MACHINEGUN_MAX_RPM = 100000;
	float MACHINEGUN_RPM_PS_PS = 0.05f;
	float MACHINEGUN_DAMAGE = 0.005f;

	float RAILGUN_LEGNTH = 2.3f;
	float RAILGUN_WIDTH = 0.2f;
	float RAILGUN_LINE_INITIAL_WIDTH = 0.18f;
	float RAILGUN_LINE_DURATION = 0.65f*Data.UPS;
	float RAILGUN_RECOIL = 0.3f;
	float RAILGUN_RPM = 69f;
	float RAILGUN_DAMAGE = 0.75f;

	float FRAGGRENADE_GRENADE_SIZE = 0.3f;
	float FRAGGRENADE_SHARD_SIZE = 0.5f;
	float FRAGGRENADE_GRENADE_SPEED_HI = 0.8f;
	float FRAGGRENADE_GRENADE_SPEED_LO = 0.05f;
	float FRAGGRENADE_GRENADE_DECCELERATION = 0.035f;
	float FRAGGRENADE_SHARD_SPEED = 0.6f;
	float FRAGGRENADE_SHARD_SPREAD = 0.5f;
	float FRAGGRENADE_SHARD_RANGE = 6.5f;
	float FRAGGRENADE_GRENADE_RPM = 85f;
	int FRAGGRENADE_GRENADE_TIMER = 1200;
	int FRAGGRENADE_SHARD_COUNT = 350;
//	int FRAGGRENADE_SHARD_COUNT = 200;
//	int FRAGGRENADE_SHARD_COUNT = 0;
	float FRAGGRENADE_SHARD_RECOIL = 0.3f;
	float FRAGGRENADE_SHARD_DAMAGE = 8.5f/FRAGGRENADE_SHARD_COUNT;
}