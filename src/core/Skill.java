/**
 * A lemmingeknek adhato kepessegek absztrakt ososztalya.
 * Kepes eltarolni a objektumot, amihez hozzarendeltek, illetve
 * rendelkezik a megfelelo metodussal, amelyel ezt be lehet allitani.
 */
public abstract class Skill implements Comparable {
	protected WorldObject myObject;
	protected int priority;
	protected int executed;
	protected BaseDrawer drawer;

	public void decreaseUsability() {}

	public int hasExecuted() {
		return executed;
	}

	public void setExecuted(int e) {
		executed = e;
	}

	public BaseDrawer getDrawer() {
		return drawer;
	}

	/**
	 * A kepesseg vegrehajtasa.
	 * Absztrakt metodus, amelyet az egyes kepessegeknek kell implementalni.
	 */
	abstract boolean doIt();
	
	/**
	 * A kepesseget tartalmazo objektum beallitasanak metodusa.
	 *
	 * @param object Az objektum, amihez hozza kell rendelni a kepesseget.
	 */
	public void setObject(WorldObject object) {
		myObject = object;
	}

	public WorldObject getObject() {
		return myObject;
	}

	public int getPriority() {
		return priority;
	}

	public int compareTo(Object o) {
		Skill skill = (Skill)o;
		return (priority <= skill.getPriority())?1:-1;
	}
}
