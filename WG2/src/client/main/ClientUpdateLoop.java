package client.main;

import client.game.Game;
import client.graphics.Camera;
import client.graphics.Window;
import data.Data;

public class ClientUpdateLoop implements Data {
	static float currentUpdateTime;

	public static void run() {
		System.out.println("UPS: "+UPS);
		final float updateSpeed = 1000000000/UPS;
		float wait;
		long startTime = 0;
		currentUpdateTime = 0;
		while (true) {
			startTime = System.nanoTime();
			update();//update
			Window.getRendererer().repaint();//refresh the screen
			wait = (updateSpeed-(System.nanoTime()-startTime))/1000000;
			currentUpdateTime = UPS-(UPS*((updateSpeed-(System.nanoTime()-startTime))/updateSpeed));
			if (wait>=1) try {
				Thread.sleep((long)wait);
			}
			catch (Exception e) {}
		}
	}

//	public static void run() {
//		System.out.println("UPS: "+UPS);//prints ups
//		double delay = 1000000000d/UPS;//gets nanosecond delay per frame
//		long currentTime, lastTime = 0;
//		while (true) {
//			currentTime = System.nanoTime();//makes currentTime the current time
//			if (currentTime-lastTime>=delay) {//if the difference between the current time and the last time is greater than delay*speedfactor
//				lastTime = currentTime;//makes lastTime the current time
//				update();//do the updates
//				Window.getRendererer().repaint();//refresh the screen
//			}
//		}
//	}

	private static void update() {
		try {
			for (int i = 0;i<Game.getEntities().size();) {
				if (Game.getEntities().get(i).tick()) Game.getEntities().remove(i);
				else i++;
			}
			Camera.tick();
		}
		catch (Exception e) {
			System.err.println("-UPDATE ERROR");
			e.printStackTrace();
		}
	}

	public static float getCurrentUpdateTime() {
		return currentUpdateTime;
	}
}