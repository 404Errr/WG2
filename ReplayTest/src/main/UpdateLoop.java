package main;

import player.Cursor;

public class UpdateLoop implements Runnable {

	@Override
	public void run() {
		System.out.println("UPS: "+Main.UPS);
		final long updateSpeed = 1000000000/Main.UPS;
		long startTime = 0, wait = 0;
		while (Main.RUNNING) {
			startTime = System.nanoTime();

			update();//update
			Window.renderer.repaint();//refresh the screen

			wait = (updateSpeed-(System.nanoTime()-startTime))/1000000;
			try {
				if (wait>0) Thread.sleep(wait);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void update() {
		try {
			System.out.println(Cursor.getX()+","+Cursor.getY());
		}
		catch (Exception e) {
			System.out.println("-UPDATE ERROR");
			e.printStackTrace();
		}
	}
}
