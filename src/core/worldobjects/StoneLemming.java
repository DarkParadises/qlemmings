/**
 * A lemming esest koveto halala utan megmarado ko-lemming.
 */
public class StoneLemming extends WorldObject {
	public StoneLemming() {
		setAttribute(Constants.NOT_BLOCKING_KEY, Direction.None);              
		drawer = new StoneLemmingDrawer(this);
	}
}
