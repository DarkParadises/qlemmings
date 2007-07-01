import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * A blokkolo tulajdonsag kirajzolasaert felelos osztaly.
 */
public class BlockingDrawer extends BaseDrawer {
	private Blocking blocking;
	private static ImageIcon icon = null;

	public BlockingDrawer(Blocking blocking) {
		this.blocking = blocking;
		icon = loadIcon(icon, "lemming_blocking.png");
	}

	public boolean draw(Graphics2D g) {
		icon.paintIcon(null, g, 0, 0);
		return false;
	}
}
