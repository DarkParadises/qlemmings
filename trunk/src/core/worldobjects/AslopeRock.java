/**
 * Egy ferde kovet reprezentalo osztaly.
 */
public class AslopeRock extends WorldObject {
	/**
	 * Az osztaly konstruktora.
	 * Beallitja az objektum azonositot, a parameterkent megkapott iranyt,
	 * amylre lejteni fog, illetve blokkolora allitja sajat magat.
	 *
	 * @param direction Az irany, amerre a ferde konek lejtenie kell.
	 */
	public AslopeRock(int direction) {
		setAttribute(Constants.SLOPE_KEY, direction);
		setAttribute(Constants.NOT_BLOCKING_KEY, Direction.None);
	
		drawer = new AslopeRockDrawer(this);
	}
}
