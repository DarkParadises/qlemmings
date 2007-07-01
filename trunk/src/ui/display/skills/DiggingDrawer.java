import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * Az asas kepesseg kirajzolasaert felelos osztaly.
 */
public class DiggingDrawer extends BaseDrawer {
	private static ImageIcon icon = null;
	private Digging digging;

	public DiggingDrawer(Digging digging) {
		this.digging = digging;
		icon = loadIcon(icon, "lemming_digging.png");
	}

	public boolean draw(Graphics2D g) {
		icon.paintIcon(null, g, 0, 0);
		return false;
	}
}
