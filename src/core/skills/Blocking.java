/**
 * A Blocking osztaly, amely a lemmingek blokkolasi kepesseget reprezentalja. 
 */
public class Blocking extends Skill{
	public static int usability = 0;

	public void decreaseUsability() {
		usability--;
	}

	/**
	 * Az osztaly konstruktora.
	 * Beallitja az objektum azonositot es a prioritast.
	 */
	public Blocking() {
		priority = 5;
	
		drawer = new BlockingDrawer(this);
	}

	/**
	 * A kepesseg tulajdonos objektumanak beallitasa.
	 *
	 * @param obj A kepesseg tulajdonos objektuma.
	 */
	public void setObject(WorldObject obj) {
		super.setObject(obj);
		myObject.setAttribute(Constants.NOT_BLOCKING_KEY, Direction.None);
	
		Cell cell = myObject.getCell().getCellAt(Direction.South);
		if((cell != null) && (!cell.isBlocked(Direction.South)))
			myObject.removeSkill(this);
	}

	/**
	 * A kepesseg vegrehajtasa.
	 * A blokkolas kepesseg semmit sem csinal, igy ez a metodus ures.
	 *
	 * @return kell-e folytatni a tulajdonos lemmingnek a kepessegek futtatasat.
	 */
	public boolean doIt() {
		return false;
	}
}
