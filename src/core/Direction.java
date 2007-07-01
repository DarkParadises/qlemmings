import java.awt.*;

public class Direction {
	public static final int East = 0;
	public static final int SouthEast = 1;
	public static final int South = 2;
	public static final int SouthWest = 3;
	public static final int West = 4;
	public static final int NorthWest = 5;
	public static final int North = 6;
	public static final int NorthEast = 7;
	public static final int All = 8;
	public static final int None = 9;

	/**
	 * A parameterkent megadott irany ellenkezojet adja vissza.
	 *
	 * @param direction az irany, amelynek az ellenkezojet szeretnenk megtudni
	 * @return a megforditott irany.
	 */
	public static int getOpposite(int direction) {
		switch(direction) {
			case East: return West;
			case West: return East;
			case North: return South;
			case South: return North;
			case NorthEast: return SouthWest;
			case NorthWest: return SouthEast;
			case SouthEast: return NorthWest;
			case SouthWest: return NorthEast;
			default: return None;
		}
	}	

	/**
	 * A paramterkent megadott irany szoveges reprezentaciojat adja vissza.
	 *
	 * @param direction az irany, amelynek a szoveges reprezentaciojara vagyunk kivancsiak.
	 * @return az irany szoveges reprezentacioja.
	 */
	public static String getString(int direction) {
		switch(direction) {
			case East: return "East";
			case West: return "West";
			case North: return "North";
			case South: return "South";
			case NorthWest: return "North West";
			case NorthEast: return "North East";
			case SouthWest: return "South West";
			case SouthEast: return "South East";
			case All: return "All";
			default: return "None";
		}
	}

	/**
	 * A megadott koordinatak fele nezo iranyt adja vissza.
	 *
	 * @param x a megadott irany x koordinataja
	 * @param y a megadott irany y koordinataja
	 * @return a megadott koordinatak fele nezo irany
	 */
	public static int directionAt(int x, int y) {
		if((x == -1) && (y == -1))
			return NorthWest;
		if((x == 0) && (y == -1))
			return North;
		if((x == 1) && (y == -1))
			return NorthEast;
		if((x == 1) && (y == 0))
			return East;
		if((x == -1) && (y == 0))
			return West;
		if((x == -1) && (y == 1))
			return SouthWest;
		if((x == 0) && (y == 1))
			return South;
		if((x == 1) && (y == 1))
			return SouthEast;
		return -1;
	}

	/**
	 * Egy adott iranyt fejt vissza koordinatakka.
	 *
	 * @param dir az irany, amit vissza kell fejteni.
	 * @return az irany koordinatait reprezentalo pont.
	 */
	public static Point directionFrom(int dir) {
		switch(dir) {
			case East: return new Point(1, 0);
			case West: return new Point(-1, 0);
			case North: return new Point(0,- 1);
			case South: return new Point(0, 1);
			case NorthEast: return new Point(1, -1);
			case NorthWest: return new Point(-1, -1);
			case SouthEast: return new Point(1, 1);
			case SouthWest: return new Point(-1, 1);
			default: return new Point(0, 0);
		}
	}
}
