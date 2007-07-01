/**
 * Az asas kepesseget reprezentalo osztaly.
 */
public class Digging extends Skill { 
	public static int usability = 0;
	
	public void decreaseUsability() {
		usability--;
	}

	private int cellsToDig;

	/**
	 * A Digging osztaly konstruktora.
	 * Beallitja a skill prioritasat es inicializalja az asando cellak szamat.
	 */
	public Digging() {
		priority = 4;
		cellsToDig = 5;
	
		drawer = new DiggingDrawer(this);
	}

	public void setObject(WorldObject obj) {
		super.setObject(obj);

		Cell cell = myObject.getCell().getCellAt(Direction.South);
		if((cell != null) && (!cell.isBlocked(Direction.South)))
			myObject.removeSkill(this);
	}


	/**
	 * Az asas tenyleges megvalositasat vegzo metodus.
	 * A lemming osszesen cellsToDig cellat tud kiasni.
	 * Ha cellsToDig == 0 az asas funkcio lejart.
	 * Miutan a lemming kiassa a maga alatti cellat egyszeruen 
	 * leesik a kovetkezo cellara az alacsonyabb prioritasu {@link Falling} skill hasznalataval.
	 *
	 * @return kell-e folytatni a tulajdonos lemmingnek a kepessegek futtatasat.
	 */
	public boolean doIt() {
		Cell myCell = myObject.getCell();
		Cell cellBelow = myCell.getCellAt(Direction.South);

		if((cellBelow != null) && (cellBelow.isBlocked(Direction.South))) {
			if(cellBelow.isDestroyable()) {
				cellBelow.delAllContainedObjects();
				cellsToDig--;
				if(cellsToDig != 0) 
					return true;
			}
		}

		myObject.removeSkill(this);                

		return true;
	}
}
