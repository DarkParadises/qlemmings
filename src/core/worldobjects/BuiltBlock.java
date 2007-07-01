/**
 * A {@link Building} altal, az epites soran letrehozott blokkokat reprezentalo osztaly.
 */
public class BuiltBlock extends WorldObject {
	/**
	 * A BuiltBlock osztaly konstruktora. 
	 * Beallitja az objektum azonositot es az epitett elem iranyat, illetve
	 * meghatarozza azt az iranyt, amerrol az elemen at lehet haladni.
	 */
	public BuiltBlock(int direction) {
                setAttribute(Constants.SLOPE_KEY, direction);
                setAttribute(Constants.NOT_BLOCKING_KEY, Direction.getOpposite(direction));
	
		drawer = new BuiltBlockDrawer(this);
	}
}
