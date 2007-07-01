/**
 * A repules kepesseget reprezentalo osztaly.
 */
public class Flying extends Skill {
	public static int usability = 0;

	public void decreaseUsability() {
		usability--;
	}

	/**
	 * Az osztaly konstruktora.
	 * Az objektumazonositot es a prioritast allitja be.
	 */
	public Flying() {
		priority = 2;
	
		drawer = new FlyingDrawer(this);
	}

	/**
	 * A repules tulajdonsag vegrehajtasa.
	 * Ha maga alatt ures cellat talal, atteszi magat oda.
	 *
	 * @return kell-e folytatni a tulajdonos lemmingnek a kepessegek futtatasat.
	 */
	public boolean doIt() {
		Lemming myLemming = (Lemming)myObject;
		myLemming.setFallingStatus(Lemming.NOT_FALLING);
		Cell myCell = myObject.getCell();
		Cell cellBelow = myCell.getCellAt(Direction.South);
		if((cellBelow != null) && (!cellBelow.isBlocked(Direction.South))) {
			myCell.delContainedObject(myObject);
			cellBelow.addContainedObject(myObject);
		}
		return false;
	}
}
