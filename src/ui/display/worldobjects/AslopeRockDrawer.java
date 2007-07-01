import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * Egy ferdeko kirajzolasaert felelos osztaly.
 */
public class AslopeRockDrawer extends WorldObjectDrawer {
	private static ImageIcon leftIcon = null;
	private static ImageIcon rightIcon = null;

	public AslopeRockDrawer(AslopeRock arock) {
		myObject = arock;
		leftIcon = loadIcon(leftIcon, "leftarock.png");
		rightIcon = loadIcon(rightIcon, "rightarock.png");
	}

	/**
	 * A kirajzolas vegrehajtasa.
	 * Eldonti melyik iranyba kell rajzolni, es az annak megfelelo
	 * icont festi a kepernyore.
	 */
	public boolean draw(Graphics2D g) {
		AslopeRock arock = (AslopeRock)myObject;
		int direction = arock.getAttribute(Constants.SLOPE_KEY);
		if(direction == Direction.East) {
			rightIcon.paintIcon(null, g, 0, 0);
		} else {
			leftIcon.paintIcon(null, g, 0, 0);
		}
		return true;
	}
}
