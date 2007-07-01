import java.util.HashMap;
/**
 * A palyan elhelyezheto objektumok absztrakt ososztalya.
 * Ismeri a cellat, amelyben elhelyezkedik, illetve rendelkezik
 * a megfelelo utkozes kezelo metodusokkal is.
 */
public abstract class WorldObject {
        private HashMap attributes;
	private Cell prevCell = null;
	protected int lastMoveDirection = Direction.None;
	protected Cell myCell = null;
	protected WorldObjectDrawer drawer;

	public WorldObjectDrawer getDrawer() {
		return drawer;
	}

	public int getLastMoveDirection() {
		return lastMoveDirection;
	}

	public WorldObject() {
		attributes = new HashMap();
		setAttribute(Constants.DESTROYABLE_KEY, true);
		setAttribute(Constants.SLOPE_KEY, Direction.None);
		setAttribute(Constants.DEAD_KEY, false);
		setAttribute(Constants.NOT_BLOCKING_KEY, Direction.All);
		setAttribute(Constants.DirectionKey, Direction.East);
	}

	/**
	 * Beallitja az objektumot tartalmazo cellat.
	 *
	 * @param cell Az objektumot tartalmazo cella
	 */
	public void setCell(Cell cell) {
		if(prevCell != null) {
			for(int i = 0; i < Direction.All; i++) {
				if(prevCell.getCellAt(i) == cell) {
					lastMoveDirection = i;
				}
			}
		}
		prevCell = myCell;
		myCell = cell;
	}
	
	/**
	 * Visszaadja az objektumot tartalmazo cellat.
	 *
	 * @return Az objektumot tartalmazo cella.
	 */
	public Cell getCell() {
		return myCell;
	}
	
	/**
	 * Hozzaad az objektumhoz egy kepesseget.
	 * Az ososztalyban nem csinal semmit, az utodok eldonthetik,
	 * hogy felulirjak-e, vagy sem.
	 *
	 * @param skill A hozzaadando kepesseg
	 */
	public boolean addSkill(Skill skill) {
		return false;
	}
	
        /**
	 * Elvesz az objektumtol egy kepesseget.
         *
	 * @param skill A torlendo kepesseg
	 */
        public boolean removeSkill(Skill skill) {
		return false;
        }
        
	/**
	 * Lepteti az objektumot.
	 * Az ososztalyban nem csinal semmit, az utodok felulirhatjak.
	 */
	public void step() {
		lastMoveDirection = Direction.None;	
	}

	/**
	 * Az altalanos utkozes kezelo metodus.
	 *
	 * @param o Az objektum, amivel utkozott.
	 */
	public void doCollision(WorldObject o) {
		o.collideWith(this);
	}

	/**
	 * Le nem kezelt utkozes eseten vegrehajtodo metodus.
	 *
	 * @param o Az objektum, amivel utkozott.
	 */
	public void collideWith(WorldObject o) { 
		//Unhandled collision 
	}

	/**
	 * Egy lemmingel tortent utkozest kezelo metodus.
	 * Az ososztalyban meghivja az altalanos utkozes kezelo metodust.
	 *
	 * @param o A lemming, amivel utkozott.
	 */
	public void collideWith(Lemming o) {
		collideWith((WorldObject)o);
	}
	
	/**
	 * Egy kijarattal tortent utkozest kezelo metodus.
	 * Az ososztalyban meghivja az altalanos utkozes kezelo metodust.
	 *
	 * @param o A kijarat, amivel utkozott.
	 */
	public void collideWith(Exit o) {
		collideWith((WorldObject)o);
	}

	public void blowUp(int direction) {
		myCell.delContainedObject(this);
		setAttribute(Constants.DEAD_KEY, true);
	}

	public int getAttribute(String key) {
		String value = (String)attributes.get(key);
		return Integer.parseInt(value);
	}

	public boolean isAttributeTrue(String key) {
		String value = (String)attributes.get(key);
		if(value.equals(Constants.True)) {
			return true;
		} else if (value.equals(Constants.False)) {
			return false;
		}
		return false;
	}
	
	public void setAttribute(String key, boolean value) {
		String boolValue = (value)?(Constants.True):(Constants.False);
		attributes.put(key, boolValue);
	}

	public void setAttribute(String key, int value) {
		attributes.put(key, String.valueOf(value));
	}
}
