package data;

public interface TileData {
	public static boolean getSolid(int type) {
		boolean solid;
		
		switch (type) {
		case 0:
			solid = false;
			break;
		case 1:
			solid = true;
			break;
		case 2:
			solid = false;
			break;
		case 3:
			solid = false;
			break;
		case 4:
			solid = false;
			break;
		case 5:
			solid = false;
			break;
		case 6:
			solid = false;
			break;
		case 7:
			solid = false;
			break;
		case 8:
			solid = false;
			break;
		case 9:
			solid = false;
			break;
		default:
			solid = false;
		}
		
		
		
		return solid;
	}
}
