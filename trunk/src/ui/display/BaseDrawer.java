import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * Az objektumok kirajzolasaert felelos absztrakt osztaly.
 * Tartalmazhat tetszoleges szamu gyermeket, amelyek 
 * segitsegevel a rajzolok hierarchiaba szervezhetok
 */
abstract public class BaseDrawer {
	protected WorldObject myObject;

	protected ImageIcon loadIcon(ImageIcon icon, String fileName) {
		if(icon == null) {
			URL url = this.getClass().getResource(fileName);
			return new ImageIcon(url);
		} else {
			return icon;
		}
	}

	/**
	 * A kirajzolast vegzo metodus.
	 * Ezt kotelezo felulirnia minden rajzolonak.
	 */
	abstract public boolean draw(Graphics2D g);
	public void step() {}
}
