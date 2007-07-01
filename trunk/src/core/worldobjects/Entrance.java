/**
 * A palya belepesi pontjat reprezentalo osztaly.
 * Az objektum a kozvetlenul alatta levo cellaba hoz letre uj {@link Lemming}
 * osztalyu objektumokat.
 */
public class Entrance extends WorldObject {
	private int lemmingsToEnter;
	private int maxWaitCount;
	private int waitCount;

	/**
	 * Az Entrance osztaly konstruktora. 
	 * Beallitja az objektum azonositot, a beleptetendo lemmingek szamat,
	 * illetve megsemmisithetetlenne teszi onmagat.
	 *
	 * @param maxLemmings a maximalisan palyara belepo lemmingek szama
	 * @param maxWaitCount ket lemming belepese kozott ennyi lepest var
	 */
	public Entrance(int maxLemmings, int maxWaitCount) {
		Lemming.lemmingCount = maxLemmings;

		lemmingsToEnter = maxLemmings;
		waitCount = this.maxWaitCount = maxWaitCount;
	
                setAttribute(Constants.DESTROYABLE_KEY, false);
		
		drawer = new EntranceDrawer(this);
	}

	/**
	 * A lemming tenyleges hozzaadasa az aktualis palyahoz.
	 * Megvizsgalka, hogy a megelelo iranyban elhelyezkedo cella szabad-e,
	 * majd elhelyez benne egy uj {@link Lemming}-et.
	 */
	public void step() {
		if((lemmingsToEnter > 0) && (waitCount-- == 0)) {
			waitCount = maxWaitCount;
			Cell nextCell = myCell.getCellAt(Direction.South);
			if((nextCell != null) && (!nextCell.isBlocked(Direction.South))) {
				Lemming lemming = new Lemming();
				nextCell.addContainedObject(lemming);
				lemmingsToEnter--;
			}
		}
	}

	
}
