import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * Az epites tulajdonsag kirajzolasaert felelos osztaly.
 */
public class BuildingDrawer extends BaseDrawer {
	private Building building;
	private static ImageIcon leftIcon;
	private static ImageIcon rightIcon;

	public BuildingDrawer(Building building) {
		this.building = building;
		leftIcon = loadIcon(leftIcon, "lemming_building_left.png");
		rightIcon = loadIcon(rightIcon, "lemming_building_right.png");
	}

	/**
	 * Az epites kirajzolasa.
	 * Megvizsgalja milyen iranyban epit a lemming, es az annak megfelelo
	 * ikont festi a kepernyore.
	 */
	public boolean draw(Graphics2D g) {
		WorldObject myObject = building.getObject();
		int direction = myObject.getAttribute(Constants.DirectionKey);
		if(direction == Direction.East) {
			rightIcon.paintIcon(null, g, 0, 0);
		} else {
			leftIcon.paintIcon(null, g, 0, 0);
		}
		return false;
	}
}
