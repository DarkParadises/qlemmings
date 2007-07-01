import java.util.*;
import java.awt.*;

/**
 * Az objektumok kirajzolasaert felelos absztrakt osztaly.
 * Tartalmazhat tetszoleges szamu gyermeket, amelyek 
 * segitsegevel a rajzolok hierarchiaba szervezhetok
 */
abstract public class WorldObjectDrawer extends BaseDrawer {
	protected WorldObject myObject;
	private Point animStep;

	public Point getAnimCoords() {
		Point coord = new Point(animStep.x * (Constants.TILE_SIZE / 10),
					animStep.y * (Constants.TILE_SIZE / 10));
		return coord;
	}

	public WorldObjectDrawer() {
		animStep = new Point(0, 0);
	}

	public void step() {
		if(myObject == null) System.out.println("WORLDOBJECTSTEP: myObject nulla.");
		if((animStep.x == 0) && (animStep.y == 0)) {
			Point direction = Direction.directionFrom(myObject.getLastMoveDirection());
			animStep.x = -1 * direction.x * 10;
			animStep.y = -1 * direction.y * 10;
		}
		if(animStep.x < 0) animStep.x++;
		if(animStep.x > 0) animStep.x--;
		if(animStep.y < 0) animStep.y++;
		if(animStep.y > 0) animStep.y--;
	}
}
