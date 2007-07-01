import java.awt.*;
import javax.swing.*;

/**
 * A robbano tulajdonsag kirajzolasaert felelos osztaly.
 * Megjelenit a lemming feje felett egy kis szamlalot, amely
 * megmutatja hany lepes van meg hatra a robbanasig.
 */
public class BlowingDrawer extends BaseDrawer {
	private Blowing blowing;
	
	public BlowingDrawer(Blowing blowing) {
		this.blowing = blowing;
	}

	/**
	 * A robbanas szamlalo kirajzolasat vegzio metodus.
	 */
	public boolean draw(Graphics2D g) {
		g.setColor(Color.red);
		g.drawString(String.valueOf(blowing.getCount()), 20, -10);
		return true;
	}
}
