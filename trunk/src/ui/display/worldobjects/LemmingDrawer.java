import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * Egy lemming kirajzolasaert felelos osztaly.
 */
public class LemmingDrawer extends WorldObjectDrawer {
	public LemmingDrawer(Lemming lemming) {
		myObject = lemming;
	}

	/**
	 * A kirajzolast vegzo metodus.
	 * Vegiglep az osszes tartalmazott kirajzolon, es megall, ha olyan
	 * rajzolohoz er, amely nem kivanaja, hogy a tobbi rajzolo is lefusson.
	 */
	public boolean draw(Graphics2D g) {
	    	Point p = getAnimCoords();
		Lemming myLemming = (Lemming)myObject;
		g.translate(p.x, p.y);
		for(Iterator i = myLemming.getSkills(); i.hasNext();) {
			Skill skill = (Skill)i.next();
			if(!skill.getDrawer().draw(g)) {
				g.translate(-1 * p.x, -1 * p.y);
				return true;
			}
		}
		g.translate(-1 * p.x, -1 * p.y);
		return true;
	}
}
