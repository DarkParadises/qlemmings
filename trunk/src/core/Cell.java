import java.util.*;

/**
 * A palya mezoit reprezentalo osztaly.
 * Tartalmazhat tetszoleges szamu {@link WorldObject} objektumot, es ismeri
 * a szomszedait a palyan.
 */
public class Cell {
	private ArrayList containedObjects;
	private Cell[] neighbours;
	private CellDrawer drawer;
	
	public CellDrawer getDrawer() {
		return drawer;
	}

        /**
	 * A Cell osztaly konstruktora. 
	 * Beallitja a cella objektum azonositojat, es inicializalja  a
	 * szukseges valtozokat.
	 */
	public Cell() {
		containedObjects = new ArrayList();
		neighbours = new Cell[8];
		drawer = new CellDrawer(this);
	}

        /**
	 * A visszaadja a mezo egy szabad szomszedjat a parameterkent megkapott iranyban.
	 * Azt a mezot adja vissza, amerre egy objektumnak haladnia kell, ha a
	 * parameterkent megadott iranyba szeretne menni. Vagyis ha utkozben felfele,
	 * vagy lefele lejton kell haladnia, ez a metodus azt is figyelembe veszi.
	 *
	 * @param direction Az irany, amerrol a mezot kivanjunk megkapni.
	 * @return A szabad cella, vagy null, ha nincs szabad.
	 */
	public Cell getAvailableCellAt(int direction) {
		Cell below = neighbours[Direction.South];
		Cell next;

		if((neighbours[direction] != null) && (neighbours[direction].isSlope(direction))) {
			int nextPos = (direction == Direction.East)?
				Direction.NorthEast:Direction.NorthWest;
			Cell nextCell = neighbours[nextPos];
			if((nextCell != null) && (!nextCell.isBlocked(direction)))
				return neighbours[nextPos];
		}

		if((below != null) && (below.isSlope(Direction.getOpposite(direction)))) {
			int nextPos = (direction == Direction.East)?
				Direction.SouthEast:Direction.SouthWest;
			next = neighbours[nextPos];
		} else {
			next = neighbours[direction];
		}
		if((next != null) && (!next.isBlocked(direction))) {
			return next;
		}
		if((next != null) && (next.isSlope(direction))) {
			int nextPos = (direction == Direction.East)?
				Direction.NorthEast:Direction.NorthWest;
			next = neighbours[nextPos];
			if((next != null) && (!next.isBlocked(direction))) {
				return next;
			}
		}
		return null;
	}

	/**
	 * Megadja, hogy a cella elemei kozul mindegyik megsemmisitheto-e.
	 * Vegigmegy a cella osszes tartalmazott elemen, es ha csak egyetlen
	 * elem nem megsemmisitheto, akkor az egesz cellat megsemmisithetetlenne
	 * nyilvanitja.
	 *
	 * @return A megsemmisithetoseg logikai erteke
	 */
	public boolean isDestroyable() {
		for(Iterator i = containedObjects.iterator();i.hasNext();) {
			WorldObject obj = (WorldObject)i.next();
			if(!obj.isAttributeTrue(Constants.DESTROYABLE_KEY))
				return false;
		}
		return true;
	}

	/**
	 * Megadja, hogy az adott iranyba tartalmaz-e a cella lejtot.
	 * Vegigmegy a cella osszes tartalmazott elemen, es ha talal koztuk
	 * a paramterkent megadott iranyba nezo lejtot igazzal ter vissza.
	 *
	 * @param direction Az irany, amerre a keresett lejto nez
	 * @return Igazzal ter vissza ha van lejto, hamissal ha nincs
	 */
	public boolean isSlope(int direction) {
		for(Iterator i = containedObjects.iterator();i.hasNext();) {
			WorldObject obj = (WorldObject)i.next();
			int slopeDirection = obj.getAttribute(Constants.SLOPE_KEY);
			if(slopeDirection == direction)
				return true;
		}
		return false;
	}

        /**
	 * A feltetel nelkul visszaadja a mezo adott iranyban levo szomszedjat.
	 * A parameterkent megkapott iranyban levo szomszedjaval ter vissza, 
	 * fuggetlenul attol, hogy szabad-e.
	 *
	 * @param direction Az irany, amerrol a mezot kivanjunk megkapni.
	 * @return A megkapott mezo
	 */
	public Cell getCellAt(int direction) {
		return neighbours[direction];
	}

        /**
	 * Hozzaadja a kapott {@link WorldObject} objektumot a tarolt objektumokhoz.
	 *
	 * @param object A hozzaadott objektum.
	 */
	public void addContainedObject(WorldObject object) {
		containedObjects.add(object);
		object.setCell(this);
	}

        /**
	 * Kitorli a parameterkent kapott {@link WorldObject} objektumot a tarolt objektumok listajarol.
	 *
	 * @param object A torolni kivant objektum.
	 */
	public void delContainedObject(WorldObject object) {
		containedObjects.remove(object);
		object.setCell(null);
	}
	
        /**
         * Visszaadja a cellaban tarolt {@link WorldObject} objektumokat.
	 *
	 * @return Az tartalmazott objektumok kollekciojara mutato iterator.
         */
	public Iterator getContainedObjects() {
		//BUG? TODO!
		//return ((ArrayList)containedObjects.clone()).iterator();
		return containedObjects.iterator();
	}

	/**
	 * Megallapitja, hogy a cella teljesen ures-e.
	 *
	 * @return Az uresseg logikai erteke.
	 */
	public boolean isFree() {
		return containedObjects.isEmpty();
	}

	/**
	 * Megallapitja, hogy a cella tartalmaz-e az adott iranyba blokkolo {@link WorldObject} objektumot.
	 *
	 * @return A blokkolas logikai erteke.
	 */
	public boolean isBlocked(int direction) {
		for(Iterator i = containedObjects.iterator();i.hasNext();) {
			WorldObject obj = (WorldObject)i.next();
			int notBlocking = obj.getAttribute(Constants.NOT_BLOCKING_KEY);
			if((notBlocking != Direction.All) && (notBlocking != direction))
				return true;
		}
		return false;
	}

        /**
	 * Kitorli az osszes {@link WorldObject} objektumot a tarolt objektumok listajarol.
	 */
	public void delAllContainedObjects() {
		for(Iterator i = containedObjects.iterator();i.hasNext();) {
			WorldObject obj = (WorldObject)i.next();
			obj.setCell(null);
		}
		containedObjects.clear();
	}
        
        /**
	 * A cella es szomszedai felrobbantasat vegzi.
	 * A robbanas soran kitorli a cellaban levo osszes tartalmazott objektumot,
	 * majd ha a radius nem nulla, meghivja a szomszedai blowUp metodusat is.
	 * 
	 * @param radius A robbanas hatosuga. 
	 * @param direction Az irany, ahonnan a robbanas erkezik.
	 */
	public void blowUp(int radius, int direction) {
		ArrayList containedObjectsCopy = (ArrayList)containedObjects.clone();
		for(Iterator i = containedObjectsCopy.iterator();i.hasNext();) {
			WorldObject obj = (WorldObject)i.next();
			if(obj.isAttributeTrue(Constants.DESTROYABLE_KEY))
				obj.blowUp(direction);
		}
		if(radius > 0) {
			for(int i = -1; i < 2; i++) {
				for(int j = -1; j < 2; j++) {
					if((i == 0) && (j == 0)) 
						continue;
					int dir = Direction.directionAt(i, j);
					if(neighbours[dir] != null)
						neighbours[dir].blowUp(radius - 1, dir);
				}
			}
		}
	}

        /**
	 * A cella direction iranyban talalhato szoszedjat lehet beallitani a fuggveny segitsegevel.
	 *
	 * @param direction Az irany, amelyre beallitani kivanjuk a szomszedot.
	 * @param cell A cella, amit a kivant iranyba szeretnenk szomszednak jelolni.
	 */
	public void setNeighbourAt(int direction, Cell cell) {
		neighbours[direction] = cell;
	}

        
        /**
	 * A cellan egyszere tartozkodo {@link WorldObject} objektumok utkzteteset vegzi.
	 * Vegigmegy a cellaban tartozkodo osszes objektumon, es ertesiti oket,
	 * hogy utkoztek egymassal. Mindezt a tarolt objektumok listajanak egy masolatan
	 * vegzi, hogy a konkurrens modositasi hibat elkeruljuk.
	 */
	public void step() {
		ArrayList containedObjectsCopy = (ArrayList)containedObjects.clone();
		for(Iterator i = containedObjectsCopy.iterator(); i.hasNext();) {
			WorldObject obj1 = (WorldObject)i.next();
			for(Iterator j = containedObjectsCopy.iterator(); j.hasNext();) {
				WorldObject obj2 = (WorldObject)j.next();
				if(obj1 != obj2) {
					obj1.doCollision(obj2);
				}
			}
		}
	}
}
