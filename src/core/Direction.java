import java.awt.*;

public enum Direction {
	EAST (1, 0), 
	SOUTH_EAST (1, 1), 
	SOUTH (0, 1), 
	SOUTH_WEST (-1, 1), 
	WEST (-1, 0), 
	NORTH_WEST (-1, -1), 
	NORTH (0, -1), 
	NORTH_EAST (1, -1), 
	ALL (2, 2), 
	NONE (0, 0);

	private int x;
	private int y;

	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Direction getOpposite() {
		if(this == ALL) return NONE;
		if(this == NONE) return ALL;
		return directionAt(x * -1, y * -1);
	}	

	public Point getPoint() {
		return new Point(x, y);
	}

	public static Direction directionAt(int x, int y) {
		Point myPoint = new Point(x, y);
		for(Direction dir : Direction.values()) {
			if(dir.getPoint().equals(myPoint)) {
				return dir;
			}
		}

		return NONE;
	}
}
