import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 * Egy epitett ko kirajzolasaert felelos osztaly.
 */
public class BuiltBlockDrawer extends WorldObjectDrawer {
	private static ImageIcon leftIcon = null;
	private static ImageIcon rightIcon = null;

	public BuiltBlockDrawer(BuiltBlock builtBlock) {
		myObject = builtBlock;
		leftIcon = loadIcon(leftIcon, "builtblock_left.png");
		rightIcon = loadIcon(rightIcon, "builtblock_right.png");	
	}

	/**
	 * Az epitett ko kirajzolasa.
	 * Megvizsgalja, hogy milyen iraynba all az epitett ko, es a megfelelo
	 * ikont festi a kepernyore.
	 */
	public boolean draw(Graphics2D g) {
		BuiltBlock builtBlock = (BuiltBlock)myObject;
		int direction = builtBlock.getAttribute(Constants.SLOPE_KEY);
		if(direction == Direction.East) {
			rightIcon.paintIcon(null, g, 0, 0);
		} else {
			leftIcon.paintIcon(null, g, 0, 0);
		}
		return true;
	}
}
