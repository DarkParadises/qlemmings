import java.awt.*;
import java.util.*;

/**
 * Egy cella kirajzolasaert felelos osztaly.
 */
public class CellDrawer extends BaseDrawer {
	Cell cell;

	public CellDrawer(Cell cell) {
		this.cell = cell;
	}

	/**
	 * Egy cella kirajzolasa.
	 * Az osszes tartalmazott rajzolon vegiglepked, es meghivja az o
	 * kirajzolo metodusukat.
	 */
	public boolean draw(Graphics2D g) {
		for(Iterator i = cell.getContainedObjects(); i.hasNext();) {
			WorldObject obj = (WorldObject)i.next();
			obj.getDrawer().draw(g);
		}
		return true;
	}
	
	public void step() {
		for(Iterator i = cell.getContainedObjects(); i.hasNext();) {
			WorldObject obj = (WorldObject)i.next();
			obj.getDrawer().step();
		}
	}
}
