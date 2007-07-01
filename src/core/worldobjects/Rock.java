/**
 * A jatek egy kovet reprezentalo osztaly.
 */
public class Rock extends WorldObject {
	public Rock() {
		setAttribute(Constants.NOT_BLOCKING_KEY, Direction.None);
		drawer = new RockDrawer(this);
	}

	/**
	 * A ko felrobbantasa.
	 * Amennyiben a kovet ferde iranybol robbantjuk fel nem robban fel
	 * teljesen, hanem megfelelo iranyba nezo ferde kove valik.
	 *
	 * @param direction a robbanas eredetenek iranya.
	 */
	public void blowUp(int direction) {
		Cell cell = myCell;
		myCell.delContainedObject(this);
		AslopeRock arock;
		switch(direction) {
			case Direction.SouthEast:
				arock = new AslopeRock(Direction.East);
				cell.addContainedObject(arock);
				break;
			case Direction.SouthWest:
				arock = new AslopeRock(Direction.West);
				cell.addContainedObject(arock);
				break;
		}
	}
}
