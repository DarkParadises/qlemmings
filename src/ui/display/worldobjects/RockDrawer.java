import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * Egy ko kirajzolasaert felelos osztaly.
 */
public class RockDrawer extends WorldObjectDrawer {
	private static ImageIcon icon;

	public RockDrawer(Rock rock) {
		myObject = rock;
		icon = loadIcon(icon, "rock.png");
	}

	public boolean draw(Graphics2D g) {
		icon.paintIcon(null, g, 0, 0);
		return true;
	}
}
