/**
 * Az epites kepesseget megvalosito osztaly.
 */
public class Building extends Skill {
	public static int usability = 0;

	/**
	 * A tulajdonsag felhasznalhatosagat csokkenti egyel.
	 */
	public void decreaseUsability() {
		usability--;
	}

	private int blocksToBuild;

	/**
	 * A Building osztaly konstruktora.
	 * Beallitja az objektum azonositojat, prioritasat, illetve a
	 * maximalisan epitendo elemek szamat.
	 */
	public Building() {
		blocksToBuild = 4;
		priority = 4;
	
		drawer = new BuildingDrawer(this);
	}

	public void setObject(WorldObject obj) {
		super.setObject(obj);

		Cell cell = myObject.getCell().getCellAt(Direction.South);
		if((cell != null) && (!cell.isBlocked(Direction.South)))
			myObject.removeSkill(this);
	}


	/**
	 * Az epitest vegzo metodus.
	 * Megvizsgalja, hogy a {@link Lemming} iranyaban levo kovetkoezo {@link Cell}
	 * szabad-e, es ha igen elhelyez rajta egy uj {@link BuiltBlock} elemet.
	 *
	 * @return kell-e folytatni a tulajdonos lemmingnek a kepessegek futtatasat.
	 */
	public boolean doIt() {
		Cell myCell = myObject.getCell();
		
		int direction = myObject.getAttribute(Constants.DirectionKey);
		Cell nextCell = myCell.getCellAt(direction);
		Cell cellBelow = myCell.getCellAt(Direction.South);
		if((nextCell != null) && (!nextCell.isBlocked(direction))) { 
			nextCell.addContainedObject(new BuiltBlock(direction));
			
			int walkDirection = Direction.None;
			if(direction == Direction.East)
				walkDirection = Direction.NorthEast;
			else
				walkDirection = Direction.NorthWest;

			nextCell = myCell.getCellAt(walkDirection);

			if((nextCell != null) && (!nextCell.isBlocked(walkDirection))) {
				myCell.delContainedObject(myObject);
				nextCell.addContainedObject(myObject);

				executed = walkDirection;

				if(--blocksToBuild > 0)
					return false;
			} else {
				executed = Direction.None;
			}
		}

		executed = Direction.None;
		myObject.removeSkill(this);

		return false;	
	}
}
