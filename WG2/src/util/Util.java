package util;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public final class Util {
	private static final int INSERTIONSORT_THRESHOLD = 7;
	private static final int RIGHT = 0, DOWN = 1, LEFT = 2, UP = 3;

	private static Random rand;

	static {
		rand = new Random();
	}

	public static int[] getReverseOrder(int[] array) {
		int[] temp = getCopy(array);
		for (int i = temp.length-1, j = 0;i>=0;i--,j++) {
			temp[i] = array[j];
		}
		return temp;
	}

	public static char[] getReverseOrder(char[] array) {
		char[] temp = array.clone();
		for (int i = temp.length-1, j = 0;i>=0;i--,j++) {
			temp[i] = array[j];
		}
		return temp;
	}

	public static void addToEnd(String str, String toAdd) {
		str = str+toAdd;
	}

	public static void addToBeginning(String str, String toAdd) {
		str = toAdd+str;
	}

	public static boolean containsOnly(String string, char[] potentialcontent) {
		for (char character:string.toCharArray()) {
			boolean contains = false;
			for (char potentialMatch:potentialcontent) {
				if (character==potentialMatch) {
					contains = true;
				}
			}
			if (!contains) return false;
		}
		return true;
	}

	public static boolean isNumeric(String string) {
		for (char character:string.toCharArray()) {
			if (!Character.isDigit(character)) return false;
		}
		return true;
	}

	public static boolean withinAngle(float a1, float a2, float range) {//radians, radians, degrees//FIXME
		double temp1 = Math.toDegrees(a1), temp2 = Math.toDegrees(a2);
		return Math.abs(temp1-temp2)<range;

	}

	public static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.hypot(x1-x2, y1-y2);
	}

	public static double getDistance(double x1, double y1, int x2, int y2) {
		return Math.hypot(x1-x2, y1-y2);
	}

	public static double getDistance(int x1, int y1, double x2, double y2) {
		return Math.hypot(x1-x2, y1-y2);
	}

	public static double getDistance(int x1, int y1, int x2, int y2) {
		return Math.hypot(x1-x2, y1-y2);
	}

	public static float getDistance(float x1, float y1, float x2, float y2) {
		return (float)Math.hypot(x1-x2, y1-y2);
	}

	public static float getDistance(float x1, float y1, int x2, int y2) {
		return (float)Math.hypot(x1-x2, y1-y2);
	}

	public static float getDistance(int x1, int y1, float x2, float y2) {
		return (float)Math.hypot(x1-x2, y1-y2);
	}

	public static double getDistance(double x1, double y1, float x2, float y2) {
		return Math.hypot(x1-x2, y1-y2);
	}

	public static double getDistance(float x1, float y1, double x2, double y2) {
		return Math.hypot(x1-x2, y1-y2);
	}

	public static float getBounceAngle(float angle, boolean yAxis) {
		if (yAxis) return (float)(Math.PI-angle);
		else return -angle;
	}

	public static double getAngleDegrees(double x1, double y1, double x2, double y2) {
		double result = Math.toDegrees(Math.atan2(-(x2-x1), -(y2-y1)))+90;
		if (result<0) {
			result+=360;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T avg(List<T> nums) {
		if (nums.get(0) instanceof Float) {
			float total = 0;
			for (float num:(List<Float>)nums) total+=num;
			return (T)new Float(total/nums.size());
		}
		if (nums.get(0) instanceof Double) {
			double total = 0;
			for (double num:(List<Double>)nums) total+=num;
			return (T)new Double(total/nums.size());
		}
		if (nums.get(0) instanceof Integer) {
			int total = 0;
			for (int num:(List<Integer>)nums) total+=num;
			return (T)new Integer(total/nums.size());
		}
		if (nums.get(0) instanceof Long) {
			long total = 0;
			for (long num:(List<Long>)nums) total+=num;
			return (T)new Long(total/nums.size());
		}
		if (nums.get(0) instanceof Short) {
			short total = 0;
			for (short num:(List<Short>)nums) total+=num;
			return (T)new Short((short)(total/nums.size()));
		}
		throw new IllegalArgumentException("unknown number type");
	}

	public static float avg(float... nums) {
		float total = 0;
		for (float num:nums) total+=num;
		return total/nums.length;
	}

	public static double avg(double... nums) {
		double total = 0;
		for (double num:nums) total+=num;
		return total/nums.length;
	}

	public static int avg(int... nums) {
		int total = 0;
		for (int num:nums) total+=num;
		return total/nums.length;
	}

	public static long avg(long... nums) {
		long total = 0;
		for (long num:nums) total+=num;
		return total/nums.length;
	}

	public static short avg(short... nums) {
		short total = 0;
		for (short num:nums) total+=num;
		return (short)(total/nums.length);
	}

	public static int randomInt(int upperBound) {
		return new Random().nextInt(upperBound);
	}

	public static int randomInt(int lowerBound, int upperBound) {
		return new Random().nextInt(upperBound-lowerBound)+lowerBound;
	}

	public static boolean[][] getBooleanArrayContains(int[][] layout, int[] values) {
		boolean[][] array = new boolean[layout.length][layout[0].length];
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				for (int value:values) {
					if (layout[r][c]==value) {
						array[r][c] = true;
						break;
					}
				}
			}
		}
		return array;
	}

	public static boolean arrayContains(int[][] array, int value) {
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				if (array[r][c]==value) return true;
			}
		}
		return false;
	}

	public static boolean arrayContains(int[] array, int value) {
		for (int i = 0;i<array.length;i++) {
			if (array[i]==value) return true;
		}
		return false;
	}

	public static void addEdgeToArray(int[][] array, int val) {
		int[][] newArray = new int[array.length+2][array[0].length+2];//new array size of map +1 on every side
		for (int r = 0;r<newArray.length;r++) {
			for (int c = 0;c<newArray[0].length;c++) {
				if (r==0||c==0||r==newArray.length-1||c==newArray[0].length-1) {
					newArray[r][c] = val;
				}
				else {
					newArray[r][c] = array[r-1][c-1];
				}
			}
		}
		array = newArray;
	}

	public static boolean equalArrays(int[][] array1, int[][] array2) {
		if (array1.length!=array2.length||array1[0].length!=array2[0].length) return false;
		for (int r = 0;r<array1.length;r++) {
			for (int c = 0;c<array1[0].length;c++) {
				if (array1[r][c]!=array2[r][c]) return false;
			}
		}
		return true;
	}

	public static boolean equalArrays(int[] array1, int[] array2) {
		if (array1.length!=array2.length) return false;
		for (int i = 0;i<array1.length;i++) {
			if (array1[i]!=array2[i]) return false;
		}
		return true;
	}

	public static boolean equalArraysType(int[] array1, int[] array2, int type) {
		if (array1.length!=array2.length) return false;
		for (int i = 0;i<array1.length;i++) {
			if (array1[i]==type||array2[i]==type&&array1[i]!=array2[i]) return false;
		}
		return true;
	}

	public static void floodFill(int iX, int iY, int from, int to, int[][] array) {
		Stack<Point> points = new Stack<>();
		points.add(new Point(iX, iY));
		while (!points.isEmpty()) {
			Point currentPoint = points.pop();
			int x = currentPoint.x, y = currentPoint.y;
			if (x<0||y<0||y>=array.length||x>=array[0].length) continue;
			if (from==array[y][x]) {
				array[y][x] = to;
				points.push(new Point(x+1, y));
				points.push(new Point(x-1, y));
				points.push(new Point(x, y+1));
				points.push(new Point(x, y-1));
			}
		}
	}

	public static List<int[]> countValuesInIntArray(int[][] array, int lower, int upper) {
		List<int[]> counted = new ArrayList<>();
		for (int currentValue = lower;currentValue<=upper;currentValue++) {
			int count = 0;
			for (int r = 0;r<array.length;r++) {
				for (int c = 0;c<array[0].length;c++) {
					if (array[r][c]==currentValue) {
						count++;
					}
				}
			}
			if (count>0) {
				counted.add(new int[] {currentValue, count});
			}
		}
		return counted;
	}

	public static boolean arrayAdjacentValuesCheck(int[][] array, int val1, int val2) {
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				if (array[r][c]==val1) {
					if (r>0&&array[r+1][c]==val2) return true;
					if (r<array.length-1&&array[r-1][c]==val2) return true;
					if (c>0&&array[r][c+1]==val2) return true;
					if (c<array[0].length&&array[r][c-1]==val2) return true;
				}
			}
		}
		return false;
	}

	public static int[][] getRandomArray(int sizeX, int sizeY, int lowerBound, int upperBound) {
		int[][] array = new int[sizeY][sizeX];
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				array[r][c] = randomInt(lowerBound, upperBound);
			}
		}
		return array;
	}

	public static int[][] getRandomArray(int sizeX, int sizeY, int upperBound) {
		int[][] array = new int[sizeY][sizeX];
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				array[r][c] = randomInt(upperBound);
			}
		}
		return array;
	}

	public static <T> void printArray(List<T> array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.size();i++) {
			str.append(array.get(i)+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}

	public static void printArray(Object[][] array) {
		for (int r = 0;r<array.length;r++) {
			printArray(array[r]);
		}
	}

	public static void printArray(Object[] array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.length;i++) {
			str.append(array[i].toString()+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}

	public static void printArray(int[][] array) {
		for (int r = 0;r<array.length;r++) {
			printArray(array[r]);
		}
	}

	public static void printArray(int[] array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.length;i++) {
			str.append(array[i]+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}

	public static void printArray(long[][] array) {
		for (int r = 0;r<array.length;r++) {
			printArray(array[r]);
		}
	}

	public static void printArray(long[] array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.length;i++) {
			str.append(array[i]+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}

	public static void printArray(short[][] array) {
		for (int r = 0;r<array.length;r++) {
			printArray(array[r]);
		}
	}

	public static void printArray(short[] array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.length;i++) {
			str.append(array[i]+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}

	public static void printArray(float[][] array) {
		for (int r = 0;r<array.length;r++) {
			printArray(array[r]);
		}
	}

	public static void printArray(float[] array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.length;i++) {
			str.append(array[i]+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}


	public static void printArray(double[][] array) {
		for (int r = 0;r<array.length;r++) {
			printArray(array[r]);
		}
	}

	public static void printArray(double[] array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.length;i++) {
			str.append(array[i]+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}

	public static void printArray(char[][] array) {
		for (int r = 0;r<array.length;r++) {
			printArray(array[r]);
		}
	}

	public static void printArray(char[] array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.length;i++) {
			str.append(array[i]+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}

	public static void printArray(String[][] array) {
		for (int r = 0;r<array.length;r++) {
			printArray(array[r]);
		}
	}

	public static void printArray(String[] array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.length;i++) {
			str.append(array[i]+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}

	public static <T> void printAsCharArray(List<T> array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.size();i++) {
			str.append((char)array.get(i)+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}

	public static void printAsCharArray(int[][] array) {
		for (int r = 0;r<array.length;r++) {
			printAsCharArray(array[r]);
		}
	}

	public static void printAsCharArray(int[] array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.length;i++) {
			str.append((char)array[i]+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}

	public static void printAsCharArray(short[][] array) {
		for (int r = 0;r<array.length;r++) {
			printAsCharArray(array[r]);
		}
	}

	public static void printAsCharArray(short[] array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.length;i++) {
			str.append((char)array[i]+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}

	public static void printAsCharArray(long[][] array) {
		for (int r = 0;r<array.length;r++) {
			printAsCharArray(array[r]);
		}
	}

	public static void printAsCharArray(long[] array) {
		StringBuilder str = new StringBuilder();
		for (int i = 0;i<array.length;i++) {
			str.append((char)array[i]+",");
		}
		str.replace(str.length()-1, str.length(), "\n");
		System.out.print(str);
	}

	public static int maxInArray(short[] values) {
		int max = 0;
		for (int i = 0;i<values.length;i++) {
			if (values[i]>values[max]) max = i;
		}
		return max;
	}

	public static int minInArray(short[] values) {
		int min = 0;
		for (int i = 0;i<values.length;i++) {
			if (values[i]<values[min]) min = i;
		}
		return min;
	}

	public static int maxInArray(long[] values) {
		int max = 0;
		for (int i = 0;i<values.length;i++) {
			if (values[i]>values[max]) max = i;
		}
		return max;
	}

	public static int minInArray(long[] values) {
		int min = 0;
		for (int i = 0;i<values.length;i++) {
			if (values[i]<values[min]) min = i;
		}
		return min;
	}

	public static int maxInArray(int[] values) {
		int max = 0;
		for (int i = 0;i<values.length;i++) {
			if (values[i]>values[max]) max = i;
		}
		return max;
	}

	public static int minInArray(int[] values) {
		int min = 0;
		for (int i = 0;i<values.length;i++) {
			if (values[i]<values[min]) min = i;
		}
		return min;
	}

	public static int maxInArray(double[] values) {
		int max = 0;
		for (int i = 0;i<values.length;i++) {
			if (values[i]>values[max]) max = i;
		}
		return max;
	}

	public static int minInArray(double[] values) {
		int min = 0;
		for (int i = 0;i<values.length;i++) {
			if (values[i]<values[min]) min = i;
		}
		return min;
	}

	public static int maxInArray(float[] values) {
		int max = 0;
		for (int i = 0;i<values.length;i++) {
			if (values[i]>values[max]) max = i;
		}
		return max;
	}

	public static int minInArray(float[] values) {
		int min = 0;
		for (int i = 0;i<values.length;i++) {
			if (values[i]<values[min]) min = i;
		}
		return min;
	}

	public static double getAngle(double x, double y, double xT, double yT) {
		return Math.atan2(x-xT, y-yT)+1.57079632679d;
	}

	public static float getAngle(float x, float y, float xT, float yT) {
		return (float)(Math.atan2(x-xT, y-yT)+1.57079632679d);
	}

	public static double getSpread(double value, double spread) {
		return value+(Math.random()-0.5)*spread;
	}

	public static float getSpread(float value, float spread) {
		return (float)(value+(Math.random()-0.5)*spread);
	}

	public static double getAngleSpread(double angle, double spread) {//returns radians (radian angle input, degree spread input) spread includes both directions
		return angle+(Math.random()-0.5)*Math.toRadians(spread);
	}

	public static float getAngleSpread(float angle, float spread) {//returns radians (radian angle input, degree spread input) spread includes both directions
		return (float) (angle+(Math.random()-0.5)*Math.toRadians(spread));
	}

	public static Ellipse2D getCircle(int x, int y, int size, boolean center) {
		if (center) return new Ellipse2D.Double(x-size/2, y-size/2, size, size);
		else return new Ellipse2D.Double(x, y, size, size);
	}

	public static Polygon getPoly(int x, int y, int sides, int size) {//for TD
		Polygon poly = new Polygon();
		double a, shift = 0;
		for (int i = 0;i<=sides;i++) {
			if (sides%2!=0) shift = Math.PI;
			else shift = Math.PI/sides;
			a = Math.PI/(sides/2d)*i+shift;
			poly.addPoint((int)(Math.round(x+Math.sin(a)*size)),(int)(Math.round(y+Math.cos(a)*size)));
		}
		return poly;
	}

	public static Rectangle2D getRect(int x, int y, int size) {
		return new Rectangle(x, y, size, size);
	}

	public static Rectangle2D getRect(int x, int y, int sizeX, int sizeY) {
		return new Rectangle(x, y, sizeX, sizeY);
	}

	public static double getXCompDegrees(double angle, double magnitude) {
		return Math.cos(Math.toRadians(angle))*magnitude;
	}

	public static double getYCompDegrees(double angle, double magnitude) {
		return Math.sin(Math.toRadians(angle))*magnitude;
	}

	public static float getXComp(float angle, float magnitude) {
		return (float)(Math.cos(angle)*magnitude);
	}

	public static float getYComp(float angle, float magnitude) {
		return (float)(Math.sin(angle)*magnitude);
	}

	public static double getXComp(double angle, double magnitude) {
		return Math.cos(angle)*magnitude;
	}

	public static double getYComp(double angle, double magnitude) {
		return Math.sin(angle)*magnitude;
	}

	public static Color getRedGreenColorShift(float value) {//0f = red, 1f = green (probably)
		if (value<0) return new Color(1,0,0,1);
		if (value>1) return new Color(0,1,0,0);
		return new Color(1-value, value, 0, 1);
	}

	public static Color colorOpacity(Color color, int opacity) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
	}

	public static Color colorOpacity(Color color, float opacity) {
		float[] colorComps = color.getRGBComponents(null);
		return new Color(colorComps[0], colorComps[1], colorComps[2], opacity);
	}

	public static int[] StringTo1DArray(String string) {//, (and ;)
		if (string.charAt(string.length()-1)==';') string = string.split(";")[0];
		int[] array = new int[string.split(",").length];
		String[] arrayStr = string.split(",");
		for (int i = 0;i<arrayStr.length;i++) {
			try {
				array[i] = Integer.parseInt(arrayStr[i]);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		return array;
	}

	public static int[][] StringTo2DArray(String string) {//, and ;
		String[] rows = string.split(";");
		int[][] array = new int[rows.length][rows[0].split(",").length];
		for (int r = 0;r<rows.length;r++) {
			String[] collumns = rows[r].split(",");
			for (int c = 0;c<collumns.length;c++) {
				try {
					array[r][c] = Integer.parseInt(collumns[c]);
				}
				catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
		return array;
	}

	public static int[][] parseIntArrayFromFile(String path) {
		String in = Util.fileToString(path);
		System.out.println(path+" loaded");
		String[] rawRows = in.split(";");
		String[][] raw = new String[rawRows.length][rawRows[0].length()/2+1];
		for (int r = 0;r<rawRows.length;r++) {
			raw[r] = rawRows[r].split(",");
		}
		int[][] array = new int[raw.length][raw[0].length];
		for (int r = 0;r<raw.length;r++) {
			for (int c = 0;c<raw[0].length;c++) {
				try {
					array[r][c] = raw[r][c].charAt(0);
				}
				catch (Exception e) {
					System.err.println("error at: "+r+","+c);
					array[r][c] = -1;
				}
			}
		}
		return array;
	}

	public static String fileToString(String path) {
		try {
			File theFile = new File(path);
			Scanner scan = new Scanner(theFile);
			StringBuilder output = new StringBuilder();
			while (scan.hasNextLine()) {
				output.append(scan.nextLine());
			}
			try {
				return output.toString();
			}
			finally {
				scan.close();
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Can't find file at: "+path);
			System.exit(0);
		}
		return null;
	}

	public static int[] getArraySlice(int[][] array, int side) {//from top or left
		int col = 0;
		switch (side) {
		case RIGHT:
			col = array[0].length-1;
			break;
		case DOWN:
			return array[array.length-1];
		case LEFT:
			col = 0;
			break;
		case UP:
			return array[0];
		}
		int[] layoutSide = new int[array.length];
		for (int i = 0;i<array.length;i++) layoutSide[i] = array[i][col];
		return layoutSide;
	}

	public static int[][] getNewfilledArray(int sizeX, int sizeY, int type) {
		int[][] array = new int[sizeY][sizeX];
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				array[r][c] = type;
			}
		}
		return array;
	}

	public static void fillArray(int[][] array, int type) {
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				array[r][c] = type;
			}
		}
	}

	public static final float byteArray2Char(byte[] in) {
		return ByteBuffer.wrap(in).getChar();
	}

	public static final float byteArray2Float(byte[] in) {
		return ByteBuffer.wrap(in).getFloat();
	}

	public static final short byteArray2Short(byte[] in) {
		return ByteBuffer.wrap(in).getShort();
	}

	public static final int byteArray2Int(byte[] in) {
		return ByteBuffer.wrap(in).getInt();
	}

	public static final long byteArray2Long(byte[] in) {
		return ByteBuffer.wrap(in).getLong();
	}

	public static final double byteArray2Double(byte[] in) {
		return ByteBuffer.wrap(in).getDouble();
	}

	public static byte[] char2ByteArray (char value) {
		return ByteBuffer.allocate(8).putChar(value).array();
	}

	public static byte[] short2ByteArray (short value) {
		return ByteBuffer.allocate(8).putShort(value).array();
	}

	public static byte[] double2ByteArray (double value) {
		return ByteBuffer.allocate(8).putDouble(value).array();
	}

	public static byte[] int2ByteArray (int value) {
		return ByteBuffer.allocate(8).putInt(value).array();
	}

	public static byte[] long2ByteArray (long value) {
		return ByteBuffer.allocate(8).putLong(value).array();
	}

	public static byte[] float2ByteArray (float value) {
		return ByteBuffer.allocate(4).putFloat(value).array();
	}

	public static boolean inArrayBounds(float x, float y, int[][] array) {
		return y>=0&&x>=0&&y<array.length&&x<array[0].length;
	}

	public static boolean inArrayBounds(double x, double y, int[][] array) {
		return y>=0&&x>=0&&y<array.length&&x<array[0].length;
	}

	public static boolean inArrayBounds(int x, int y, int[][] array) {
		return y>=0&&x>=0&&y<array.length&&x<array[0].length;
	}

	public static String[] getCopy(String[] array) {
		String[] newArray = new String[array.length];
		for (int i = 0;i<array.length;i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static String[][] getCopy(String[][] array) {
		String[][] newArray = new String[array.length][array[0].length];
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				newArray[r][c] = array[r][c];
			}
		}
		return newArray;
	}

	public static byte[] getCopy(byte[] array) {
		byte[] newArray = new byte[array.length];
		for (int i = 0;i<array.length;i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static byte[][] getCopy(byte[][] array) {
		byte[][] newArray = new byte[array.length][array[0].length];
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				newArray[r][c] = array[r][c];
			}
		}
		return newArray;
	}

	public static short[] getCopy(short[] array) {
		short[] newArray = new short[array.length];
		for (int i = 0;i<array.length;i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static short[][] getCopy(short[][] array) {
		short[][] newArray = new short[array.length][array[0].length];
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				newArray[r][c] = array[r][c];
			}
		}
		return newArray;
	}

	public static long[] getCopy(long[] array) {
		long[] newArray = new long[array.length];
		for (int i = 0;i<array.length;i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static long[][] getCopy(long[][] array) {
		long[][] newArray = new long[array.length][array[0].length];
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				newArray[r][c] = array[r][c];
			}
		}
		return newArray;
	}

	public static char[] getCopy(char[] array) {
		char[] newArray = new char[array.length];
		for (int i = 0;i<array.length;i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static char[][] getCopy(char[][] array) {
		char[][] newArray = new char[array.length][array[0].length];
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				newArray[r][c] = array[r][c];
			}
		}
		return newArray;
	}

	public static float[] getCopy(float[] array) {
		float[] newArray = new float[array.length];
		for (int i = 0;i<array.length;i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static float[][] getCopy(float[][] array) {
		float[][] newArray = new float[array.length][array[0].length];
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				newArray[r][c] = array[r][c];
			}
		}
		return newArray;
	}

	public static double[] getCopy(double[] array) {
		double[] newArray = new double[array.length];
		for (int i = 0;i<array.length;i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static double[][] getCopy(double[][] array) {
		double[][] newArray = new double[array.length][array[0].length];
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				newArray[r][c] = array[r][c];
			}
		}
		return newArray;
	}

	public static int[] getCopy(int[] array) {
		int[] newArray = new int[array.length];
		for (int i = 0;i<array.length;i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static int[][] getCopy(int[][] array) {
		int[][] newArray = new int[array.length][array[0].length];
		for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				newArray[r][c] = array[r][c];
			}
		}
		return newArray;
	}

	public static void appendArrayToArray(int x, int y, int[][] toAppend, int[][] array) {//add toAppend to array starting at x, y
		for (int r = 0;r<toAppend.length&&r<array.length-y;r++) {
			for (int c = 0;c<toAppend[r].length&&c<array[r].length-x;c++) {
				if (r+y>=0&&c+x>=0) {
					array[r+y][c+x] = toAppend[r][c];
				}
			}
		}
	}

	public static void replaceAllInArray(int[][] array, int from, int to) {
		for (int r = 0;r<array.length;r++) {
			replaceAllInArray(array[r], from, to);
		}
	}

	public static void replaceAllInArray(int[] array, int from, int to) {
		for (int i = 0;i<array.length;i++) {
			if (array[i]==from) {
				array[i] = to;
			}
		}
	}

	public static int[][] rotateArray(int[][] array, int rotations) {//90 degrees clockwise
		for (int rotationCount = 0;rotationCount<rotations;rotationCount++) {
			int[][] tempArray = new int[array[0].length][array.length];
			for (int r = 0;r<tempArray.length;r++) {
				for (int c = 0;c<tempArray[0].length;c++) {
					tempArray[r][c] = array[array.length-1-c][r];
				}
			}
			array = tempArray;
		}
		return array;
	}

	public static void mirrorArrayDiag(int[][] array, boolean tLBR, boolean tRBL) {
		if (tLBR) for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				array[c][r] = array[r][c];
			}
		}
		if (tRBL) for (int r = 0;r<array.length;r++) {
			for (int c = 0;c<array[0].length;c++) {
				array[c][r] = array[array.length-1-r][array[0].length-1-c];
			}
		}
	}

	public static void mirrorArray(int[][] array, boolean horz, boolean vert) {//copies top/left into bottom/right
		if (horz) for (int r = 0;r<array.length;r++) {
			for (int cR = array[0].length-1, cL = 0;cL<=cR;cR--, cL++) {
				array[r][cR] = array[r][cL];
			}
		}
		if (vert) for (int rR = array.length-1, rL = 0;rL<=rR;rR--, rL++) {
			for (int c = 0;c<array[0].length;c++) {
				array[rR][c] = array[rL][c];
			}
		}
	}

	public static int[][] flipArray(int[][] array, boolean horz, boolean vert) {
		int[][] refArray = getCopy(array);
		if (horz) for (int r = 0;r<array.length;r++) {
			for (int cH = array[0].length-1, cL = 0;cL<=cH;cH--, cL++) {
				array[r][cL] = refArray[r][cH];
				array[r][cH] = refArray[r][cL];
			}
		}
		if (vert) for (int rH = array.length-1, rL = 0;rL<=rH;rH--, rL++) {
			for (int c = 0;c<array[0].length;c++) {
				array[rL][c] = refArray[rH][c];
				array[rH][c] = refArray[rL][c];
			}
		}
		return array;
	}

	public static boolean continuousCheck(int[][] array, int type) {
		int[][] arr = getCopy(array);
		int iX = -1, iY = -1;
		for (int r = 0;r<arr.length;r++) {
			for (int c = 0;c<arr[0].length;c++) {
				if (arr[r][c]==type) {
					iX = c;
					iY = r;
				}
			}
		}
		if (iX==-1||iY==-1) return false;
		final int tempFillType = 1000;
		floodFill(iX, iY, type, tempFillType, arr);
		for (int r = 0;r<arr.length;r++) {
			for (int c = 0;c<arr[0].length;c++) {
				if (arr[r][c]==type) {
					return false;
				}
			}
		}
		return true;
	}

	public static List<int[][]> getAllArrayOrientations(int[][] array) {
		List<int[][]> orientations = new ArrayList<>();
		boolean h = false, v = false;
		for (int p = 0;p<16;p++) {
			int[][] tempArray = getCopy(array);
			tempArray = flipArray(tempArray, (v)?h=!h:h, v=!v);
			tempArray = rotateArray(tempArray, p/4);
			orientations.add(tempArray);
		}
		for (int i = 0;i<orientations.size()-1;i++) {
			for (int j = i+1;j<orientations.size();) {
				if (equalArrays(orientations.get(i), orientations.get(j))) {
					orientations.remove(j);
				}
				else j++;
			}
		}
		return orientations;
	}

	public interface HasWeight extends Comparable<HasWeight> {
		public float getWeight();

		@Override
		default int compareTo(HasWeight other) {
			if (getWeight()==other.getWeight()) return 0;
			return (getWeight()>other.getWeight())?-1:1;//higher weight -1
		}
	}

	public static void weighedShuffle(List<HasWeight> array, float shuffledness) {//0 = completely sorted, 1 = complelety randomized
		sort(array);
		for (int i = array.size();i>1;i--) {
			if (rand.nextFloat()<=shuffledness) swap(array, i-1, rand.nextInt(i/2));
		}
	}

	public static <T> void shuffle(List<T> array) {
		for (int i = array.size();i>1;i--) {
			swap(array, i-1, rand.nextInt(i));
		}
	}

	public static <T> void swap(List<T> array, int i, int j) {
		array.set(i, array.set(j, array.get(i)));
	}

	public static void swap(Object[] array, int i, int j) {
		Object temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		Object[] a = list.toArray();
		sort(a);
		ListIterator<T> i = list.listIterator();
		for (int j=0; j<a.length; j++) {
			i.next();
			i.set((T)a[j]);
		}
	}

	public static void sort(Object[] a) {
		Object[] aux = a.clone();
		mergeSort(aux, a, 0, a.length, 0);
	}

	public static void sort(Object[] a, int fromIndex, int toIndex) {
		rangeCheck(a.length, fromIndex, toIndex);
		Object[] aux = copyOfRange(a, fromIndex, toIndex);
		mergeSort(aux, a, fromIndex, toIndex, -fromIndex);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void mergeSort(Object[] src, Object[] dest, int low, int high, int off) {
		int length = high-low;
		if (length<INSERTIONSORT_THRESHOLD) {
			for (int i = low;i<high;i++) {
				for (int j = i;j>low&&((Comparable) dest[j-1]).compareTo(dest[j])>0;j--) {
					swap(dest, j, j-1);
				}
			}
			return;
		}
		int destLow  = low;
		int destHigh = high;
		low+=off;
		high+=off;
		int mid = (low+high)>>>1;
			mergeSort(dest, src, low, mid, -off);
			mergeSort(dest, src, mid, high, -off);
			if (((Comparable)src[mid-1]).compareTo(src[mid]) <= 0) {
				System.arraycopy(src, low, dest, destLow, length);
				return;
			}
			for (int i = destLow, p = low, q = mid;i<destHigh;i++) {
				if (q>=high||p<mid&&((Comparable)src[p]).compareTo(src[q])<=0) {
					dest[i] = src[p++];
				}
				else {
					dest[i] = src[q++];
				}
			}
	}

	private static void rangeCheck(int arrayLen, int fromIndex, int toIndex) {
		if (fromIndex>toIndex) throw new IllegalArgumentException("fromIndex("+fromIndex+") > toIndex("+toIndex+")");
		if (fromIndex<0) throw new ArrayIndexOutOfBoundsException(fromIndex);
		if (toIndex>arrayLen) throw new ArrayIndexOutOfBoundsException(toIndex);
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] copyOfRange(T[] original, int from, int to) {
		return copyOfRange(original, from, to, (Class<T[]>) original.getClass());
	}

	@SuppressWarnings("unchecked")
	public static <T,U> T[] copyOfRange(U[] original, int from, int to, Class<? extends T[]> newType) {
		int newLength = to-from;
		if (newLength<0) {
			throw new IllegalArgumentException(from+" > "+to);
		}
		T[] copy = ((Object)newType==(Object) Object[].class)?(T[]) new Object[newLength]:(T[]) Array.newInstance(newType.getComponentType(), newLength);
		System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
		return copy;
	}
}

