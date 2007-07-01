import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * Egy bejarat kirajzolasaert felelos osztaly.
 */
public class EntranceDrawer extends WorldObjectDrawer {
	private static ImageIcon icon = null;

	public EntranceDrawer(Entrance entrance) {
		myObject = entrance;
		icon = loadIcon(icon, "entrance.png");
	}

	public boolean draw(Graphics2D g) {
		icon.paintIcon(null, g, 0, 0);
		return true;
	}
}
