import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * A setallas tulajdonsag kirajzolasaert felelos osztaly.
 */
public class WalkingDrawer extends BaseDrawer {
	Walking walking;
	private static ImageIcon leftIcon = null;
	private static ImageIcon rightIcon = null;

	public WalkingDrawer(Walking walking) {
		this.walking = walking;
		leftIcon = loadIcon(leftIcon, "lemming_walking_left.png");
		rightIcon = loadIcon(rightIcon, "lemming_walking_right.png");
	}

	/**
	 * Kirajzolja a setallast.
	 * Megvizsgalja, hogy melyik iranyba nez a lemming, es kirajzolja, de csak
	 * akkor, ha a setallas tulajdonsag elozoleg vegrehajtodott.
	 */
	public boolean draw(Graphics2D g) {
		if(walking.hasExecuted() != -1) {
			WorldObject object = walking.getObject();
			int dir = object.getAttribute(Constants.DirectionKey);
			if(dir == Direction.East) {
				rightIcon.paintIcon(null, g, 0, 0);
			} else { 
				leftIcon.paintIcon(null, g, 0, 0);
			}
			return false;
		} else {
			return true;
		}
	}
}
