import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * Az eses tulajdonsag kirajzolasaert felelos osztaly.
 */
public class FallingDrawer extends BaseDrawer {
	private static ImageIcon icon = null;
	private Falling falling;

	public FallingDrawer(Falling falling) {
		this.falling = falling;
		icon = loadIcon(icon, "lemming_falling.png");
	}

	public boolean draw(Graphics2D g) {
		icon.paintIcon(null, g, 0, 0);
		return false;
	}
}
