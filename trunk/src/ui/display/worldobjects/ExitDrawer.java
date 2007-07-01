import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * Egy kijarat kirajzolasaert felelos osztaly.
 */
public class ExitDrawer extends WorldObjectDrawer {
	private static ImageIcon icon = null;

	public ExitDrawer(Exit exit) {
		myObject = exit;
		icon = loadIcon(icon, "exit.png");
	}

	public boolean draw(Graphics2D g) {
		icon.paintIcon(null, g, 0, 0);
		return true;
	}
}
