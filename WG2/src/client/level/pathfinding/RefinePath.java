package client.level.pathfinding;

import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import data.TileData;
import util.Util;

public class RefinePath {

	public static LinkedList<Point> refinePath(List<Point> pathPoints) {
		if (pathPoints.isEmpty()) return (LinkedList<Point>) pathPoints;
		if (canWalkBetween(pathPoints.get(0), pathPoints.get(pathPoints.size()-1))) return new LinkedList<>(Arrays.asList(pathPoints.get(0), pathPoints.get(pathPoints.size()-1)));
		for (int i = pathPoints.size()-1;i>1;i--) {
			if (canWalkBetween(pathPoints.get(i), pathPoints.get(i-2))) {
				pathPoints.remove(i-1);
			}
		}
		Util.removeRepeatsFromEnds(pathPoints);
		RefinePath.removeLines(pathPoints);
		return (LinkedList<Point>) pathPoints;
	}

	public static LinkedList<Point> refinePath(List<Point> pathPoints, int buffer) {
		if (buffer<1) return refinePath(pathPoints);
		if (pathPoints.isEmpty()) return (LinkedList<Point>) pathPoints;
		for (int i = 0;i<buffer;i++) {
			pathPoints.add(0, pathPoints.get(0));
			pathPoints.add(pathPoints.get(pathPoints.size()-1));
		}
		for (int i = pathPoints.size()-1;i>buffer*2-1;i--) {
			if (canWalkBetween(pathPoints.get(i), pathPoints.get(i-buffer*2))) {
				pathPoints.remove(i-buffer);
			}
		}
		Util.removeRepeatsFromEnds(pathPoints);
		RefinePath.removeLines(pathPoints);
		return (LinkedList<Point>) pathPoints;
	}

	public static LinkedList<Point> refinePath(List<Point> pathPoints, int buffer, int maxChain) {//TODO
		if (buffer<1) return refinePath(pathPoints);
		if (pathPoints.isEmpty()) return (LinkedList<Point>) pathPoints;
		for (int i = 0;i<buffer;i++) {
			pathPoints.add(0, pathPoints.get(0));
			pathPoints.add(pathPoints.get(pathPoints.size()-1));
		}
		int chainCount = 0;
		for (int i = pathPoints.size()-1;i>buffer*2-1;i--) {
			if (canWalkBetween(pathPoints.get(i), pathPoints.get(i-buffer*2))) {
				if (chainCount<=maxChain) {
					pathPoints.remove(i-buffer);
					chainCount++;
				}
				else chainCount = 0;
			}
			else chainCount = 0;
		}
		Util.removeRepeatsFromEnds(pathPoints);
		RefinePath.removeLines(pathPoints);
		return (LinkedList<Point>) pathPoints;
	}

	public static void removeLines(List<Point> pathPoints) {
		if (pathPoints.isEmpty()) return;
		Point p1, p2, p3;
		for (int i = pathPoints.size()-1;i>1;i--) {
			p1 = pathPoints.get(i);
			p2 = pathPoints.get(i-1);
			p3 = pathPoints.get(i-2);
			if (Util.getAngle(p1.x, p1.y, p2.x, p2.y)==Util.getAngle(p2.x, p2.y, p3.x, p3.y)) {
				pathPoints.remove(i-1);
			}
		}
	}

	private static boolean canWalkBetween(Point p1, Point p2) {//true if there are no obstacles between p1 and p2
		if (Util.lineIsBrokenByBooleanArray(p1.x, p1.y, p2.x, p2.y, Util.negateArray(TileData.getUseable()))) return false;
		if (Util.lineIsBrokenByBooleanArray(p1.x+1, p1.y, p2.x+1, p2.y, Util.negateArray(TileData.getUseable()))) return false;
		if (Util.lineIsBrokenByBooleanArray(p1.x, p1.y+1, p2.x, p2.y+1, Util.negateArray(TileData.getUseable()))) return false;
		if (Util.lineIsBrokenByBooleanArray(p1.x+1, p1.y+1, p2.x+1, p2.y+1, Util.negateArray(TileData.getUseable()))) return false;
//		if (Util.lineIsBrokenByBooleanArray(p1.x+0.5f, p1.y+0.5f, p2.x+0.5f, p2.y+0.5f, Util.negateArray(TileData.getUseable()))) return false;
		return true;
	}
}
