import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * Egy ko-lemming kirajzolasaert felelos osztaly.
 */
public class StoneLemmingDrawer extends WorldObjectDrawer {
	private static ImageIcon icon;

	public StoneLemmingDrawer(StoneLemming stoneLemming) {
		myObject = stoneLemming;
		icon = loadIcon(icon, "stonelemming2.png");
	}

	public boolean draw(Graphics2D g) {
		icon.paintIcon(null, g, 0, 0);
		return true;
	}
}
