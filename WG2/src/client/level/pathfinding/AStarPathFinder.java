package client.level.pathfinding;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import client.level.Level;

public class AStarPathFinder {
	private List<PathfindingTile> openList, closedList;
	private PathfindingTile[][] tiles;

	public AStarPathFinder() {
		tiles = new PathfindingTile[Level.getHeight()][Level.getWidth()];
		for (int r = 0;r<Level.getHeight();r++) {
			for (int c = 0;c<Level.getWidth();c++) {
				tiles[r][c] = new PathfindingTile(c, r);
			}
		}
	}

	public ArrayList<Point> getPath(int x1, int y1, int x2, int y2) {
		List<PathfindingTile> path = findPath(x1, y1, x2, y2);
		ArrayList<Point> pathPoints = new ArrayList<>();
		pathPoints.add(new Point(x1, y1));
		for (PathfindingTile tileNode:path) {
			pathPoints.add(new Point(tileNode.getC(), tileNode.getR()));
		}
		return pathPoints;
	}

	public List<PathfindingTile> findPath(int iC, int iR, int nC, int nR) {//initial and new
		openList = new LinkedList<>();
		closedList = new LinkedList<>();
		openList.add(tiles[iR][iC]);//add first

		PathfindingTile currentPathfindingTile;
		while (true) {
			currentPathfindingTile = getLowestCombinedInOpen();//get node with lowest combined Costs from openList
			closedList.add(currentPathfindingTile);//add current node to closed
			openList.remove(currentPathfindingTile); //delete current node from open
			if ((currentPathfindingTile.getC()==nC)&&(currentPathfindingTile.getR()==nR)) {//found goal
				return calcPath(tiles[iR][iC], currentPathfindingTile);
			}
			List<PathfindingTile> adjacentTiles = getAdjacents(currentPathfindingTile);
			for (int i = 0;i<adjacentTiles.size();i++) {
				PathfindingTile currentAdjacent = adjacentTiles.get(i);
				if (!openList.contains(currentAdjacent)) {//node is not in openList
					currentAdjacent.setPrevious(currentPathfindingTile);//set current node as previous for this node
					currentAdjacent.setDistanceCost(tiles[nR][nC]);//set distance
					currentAdjacent.setTotalCost(currentPathfindingTile);//set total
					openList.add(currentAdjacent);//add node to openList
				}
				else {//node is in openList
					if (currentAdjacent.getTotalCost()>currentAdjacent.calculateTotalCost(currentPathfindingTile)) {//costs from current node are cheaper than previous costs
						currentAdjacent.setPrevious(currentPathfindingTile);//set current node as previous for this node
						currentAdjacent.setTotalCost(currentPathfindingTile);//set total
					}
				}
			}
			if (openList.isEmpty()) return new LinkedList<>();//no path exists; return empty list
		}
	}

	private List<PathfindingTile> calcPath(PathfindingTile start, PathfindingTile goal) {//starts at goal, doesnt include start
		LinkedList<PathfindingTile> path = new LinkedList<>();
		PathfindingTile curr = goal;
		while (true) {
			path.addFirst(curr);
			if (curr==start) break;
			curr = curr.getPrevious();
		}
		return path;
	}

	private PathfindingTile getLowestCombinedInOpen() {
		PathfindingTile min = openList.get(0);
		for (int i = 0;i<openList.size();i++) {
			if (openList.get(i).getCombinedCosts()<min.getCombinedCosts()) {
				min = openList.get(i);
			}
		}
		return min;
	}

	public PathfindingTile getHighestCombined() {
		PathfindingTile max = tiles[0][0];
		for (int r = 0;r<Level.getHeight();r++) {
			for (int c = 0;c<Level.getWidth();c++) {
				if (tiles[r][c].getCombinedCosts()>max.getCombinedCosts()) {
					max = tiles[r][c];
				}
			}
		}
		return max;
	}

	private List<PathfindingTile> getAdjacents(PathfindingTile tile) {
		int x = tile.getC(), y = tile.getR();
		List<PathfindingTile> adjacent = new LinkedList<>();
		PathfindingTile temp;
		if (x>0) {
			temp = tiles[y-1][x];
			if (temp.isUsable()&&!closedList.contains(temp)) {
				temp.setIsDiagonal(false);
				adjacent.add(temp);
			}
		}
		if (x<Level.getWidth()-1) {
			temp = tiles[y+1][x];
			if (temp.isUsable()&&!closedList.contains(temp)) {
				temp.setIsDiagonal(false);
				adjacent.add(temp);
			}
		}
		if (y>0) {
			temp = tiles[y][x-1];
			if (temp.isUsable()&&!closedList.contains(temp)) {
				temp.setIsDiagonal(false);
				adjacent.add(temp);
			}
		}
		if (y<Level.getHeight()) {
			temp = tiles[y][x+1];
			if (temp.isUsable()&&!closedList.contains(temp)) {
				temp.setIsDiagonal(false);
				adjacent.add(temp);
			}
		}
		if (x<Level.getWidth()&&y<Level.getHeight()) {
			temp = tiles[y+1][x+1];
			if (temp.isUsable()&&!closedList.contains(temp)) {
				temp.setIsDiagonal(true);
				adjacent.add(temp);
			}
		}
		if (x>0&&y>0) {
			temp = tiles[y-1][x-1];
			if (temp.isUsable()&&!closedList.contains(temp)) {
				temp.setIsDiagonal(true);
				adjacent.add(temp);
			}
		}
		if (x>0&&y<Level.getHeight()) {
			temp = tiles[y-1][x+1];
			if (temp.isUsable()&&!closedList.contains(temp)) {
				temp.setIsDiagonal(true);
				adjacent.add(temp);
			}
		}
		if (x<Level.getWidth()&&y>0) {
			temp = tiles[y+1][x-1];
			if (temp.isUsable()&&!closedList.contains(temp)) {
				temp.setIsDiagonal(true);
				adjacent.add(temp);
			}
		}
		return adjacent;
	}
}
