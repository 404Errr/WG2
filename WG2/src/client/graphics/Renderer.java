package client.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.JPanel;

import client.entity.Entity;
import client.game.Game;
import client.level.Level;
import client.player.AIPlayer;
import client.player.Player;
import client.weapon.GunType;
import client.weapon.Hitscan;
import client.weapon.Projectile;
import data.ColorData;
import data.GraphicsData;
import data.PlayerData;
import main.Debug;
import util.Util;

@SuppressWarnings("serial")
public class Renderer extends JPanel implements ColorData, PlayerData, GraphicsData {
	private static Graphics2D g;

	@Override
	public void paintComponent(Graphics g0) {
		g = (Graphics2D) g0;
		setBackground(COLOR_BACKROUND);
		super.paintComponent(g);
		try {
			drawTiles();
			Debug.drawDebug();
			drawEntities();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void drawEntities() {
		List<Entity> entities = Game.getEntities();
		for (int i = entities.size()-1;i>=0;i--) {//reverse order so players will be drawn last (the player last too)
			Entity entity = entities.get(i);
			if (entity instanceof Projectile) {
				drawProjectile((Projectile)entity);
			}
			if (entity instanceof Hitscan) {
				drawHitscan((Hitscan)entity);
			}
			if (entity instanceof Player) {
				drawPlayer((Player)entity);
				if (entity instanceof AIPlayer) {
					Debug.drawPath(((AIPlayer)entity).getCurrentPath(), ((Player)entity).getColor(), 2);
					if (Debug.isDrawSightLines()) {
						g.setStroke(new BasicStroke(2));
						for (int j = 0;j<((AIPlayer)entity).getSightLines().size();j++) {
							Line2D line = ((AIPlayer)entity).getSightLines().get(j).getLine();
							if (((AIPlayer)entity).getSightLines().get(j).getCanSee()) g.setColor(Color.GREEN);
							else g.setColor(Color.RED);
							g.drawLine(gridX((float)line.getX1()), gridY((float)line.getY1()), gridX((float)line.getX2()), gridY((float)line.getY2()));
						}
					}
				}
				//debug \/
				g.setColor(Color.BLACK);
				g.setFont(new Font("Helvetica", Font.BOLD, Camera.getScale()/3));
				g.drawString((int)(((Player)entity).getHealth()*100)+"", gridX(((Player)entity).getX())+Camera.getScale()/8, gridY(((Player)entity).getY())+Camera.getScale()*5/8);
			}
		}
	}

	private void drawHitscan(Hitscan hitscan) {
		g.setColor(Util.colorOpacity(hitscan.getColor(), hitscan.getOpacity()));
		g.setStroke(new BasicStroke((int)(hitscan.getWidth()*Camera.getScale())));
		g.drawLine(gridX(hitscan.getiX()), gridY(hitscan.getiY()), gridX(hitscan.getfX()), gridY(hitscan.getfY()));
	}

	private void drawProjectile(Projectile projectile) {
		g.setColor(projectile.getColor());
		g.fill(new Ellipse2D.Double(gridX(projectile.getX())-projectile.getSize()/2, gridY(projectile.getY())-projectile.getSize()/2, Camera.getScale()*projectile.getSize(), Camera.getScale()*projectile.getSize()));
	}

	private void drawGun(Player player) {
		GunType gun = player.getActiveGun().getType();
		g.setColor(player.getColor());
		g.setStroke(new BasicStroke(gun.getWidth()*Camera.getScale()));
		float angle = player.getFacing(), wangLength = gun.getLength()+HALF_PLAYER_SIZE;
		g.drawLine(gridX(player.getXCenter()), gridY(player.getYCenter()), gridX(player.getXCenter())+(int)(Util.getXComp(angle, wangLength)*Camera.getScale()), gridY(player.getYCenter())+(int)(-Util.getYComp(angle, wangLength)*Camera.getScale()));
	}

	private void drawPlayer(Player player) {
		g.setColor(player.getColor());
		if (Debug.isDrawWeapons()) drawGun(player);
		g.fillRect(gridX(player.getX()), gridY(player.getY()), (int)getPlayerSize(), (int)getPlayerSize());
	}

	private void drawTiles() {
		for (int r = Camera.getYTile()-GraphicsData.getRenderDistanceY();r<=Camera.getYTile()+GraphicsData.getRenderDistanceY();r++) {
			for (int c = Camera.getXTile()-GraphicsData.getRenderDistanceX();c<=Camera.getXTile()+GraphicsData.getRenderDistanceX();c++) {
				if (r>=0&&c>=0&&r<Level.getHeight()&&c<Level.getWidth()&&Level.getTile(c, r).getColor()!=null) {
					g.setColor(Level.getTile(c, r).getColor());
					g.fillRect(gridX(c), gridY(r), Camera.getScale(), Camera.getScale());
				}
			}
		}
	}

	public static int gridX(float x) {
		return (int)(x*Camera.getScale()+getXOrigin());
	}

	public static int gridY(float y) {
		return (int)(y*Camera.getScale()+getYOrigin());
	}

	private static float getXOrigin() {
		return (-Camera.getX()-HALF_PLAYER_SIZE)*Camera.getScale()+Window.centerX();
	}

	private static float getYOrigin() {
		return (-Camera.getY()-HALF_PLAYER_SIZE)*Camera.getScale()+Window.centerY();
	}

	public static float getPlayerSize() {
		return PLAYER_SIZE*Camera.getScale();
	}

	public static float getHalfPlayerSize() {
		return HALF_PLAYER_SIZE*Camera.getScale();
	}

	public static Graphics2D getG() {
		return g;
	}
}
