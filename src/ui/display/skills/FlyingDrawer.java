import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * A repules tulajdonsag kirajzolasaert felelos osztaly.
 */
public class FlyingDrawer extends BaseDrawer {
	private static ImageIcon icon = null;
	private Flying flying;

	public FlyingDrawer(Flying flying) {
		this.flying = flying;
		icon = loadIcon(icon, "lemming_flying.png");
	}

	public boolean draw(Graphics2D g) {
		icon.paintIcon(null, g, 0, 0);
		return false;
	}
}
