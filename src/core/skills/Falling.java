/**
 * a Falling osztaly, amely az eses kepesseget reprezentalja.
 */
public class Falling extends Skill {
	private int fallHeight = 0;

	/**
	 * A Falling osztaly konstruktora.
	 * Az objektum azonositot es a prioritast allitja be.
	 */
	public Falling() {
		drawer = new FallingDrawer(this);
		priority = 1;
	}

	/**
	 * Az eses tenyleges megvalositasat vegzo metodus.
	 * A metodus eloszor megvizsgalja, hogy az alatta levo cella szabad-e,
	 * majd ha ugy talalja igen, megvizsgalja az az alatti cellat is.
	 * Amennyiben ez utobbi is szabadnak bizonyult egyszeruen atteszi a
	 * tartalmazott objektumot az elobbibe, ha nem, akkor viszont az eses
	 * veget ert. Ilyenkor megvizsgalja, hogy eleg nagyott esett-e ahhoz,
	 * hogy a kepesseg tulajdonosa meghaljon. Ha igen, akkor onmaga helyett
	 * egy {@link StoneLemming} objektumot helyez az alatta levo cellaba, ha nem,
	 * akkor egyszeruen athelyezi a tulajdonos objektumot.
	 */
	public boolean doIt() {
		Lemming myLemming = (Lemming)myObject;
		Cell myCell = myObject.getCell();
		Cell cellBelow = myCell.getCellAt(Direction.South);

		int status = myLemming.getFallingStatus();

		if((cellBelow == null) || (cellBelow.isBlocked(Direction.South))) {
			if(status == Lemming.NOT_FALLING) {
				return true;
			} else {
				/* Ez az ag csak azon ritka esetben teljesulhet, ha az elozo lepes
				 * ota valami blokkolo dolog pont a lemming ala maszott, es igy
				 * varatlanul befejezodik az eses. */
				if(status == Lemming.HIGH_FALL) {
					myCell.delContainedObject(myObject);
					Lemming.lemmingCount--;
					return false;
				} else {
					myLemming.setFallingStatus(Lemming.NOT_FALLING);
					fallHeight = 0;			
				}
			}
		} else {
			if(status == Lemming.NOT_FALLING) {
				myLemming.setFallingStatus(Lemming.LOW_FALL);
			}
			Cell cellTwoBelow = cellBelow.getCellAt(Direction.South);
			if((cellTwoBelow == null) || (cellTwoBelow.isBlocked(Direction.South))) {
				if(status == Lemming.HIGH_FALL) {
					myCell.delContainedObject(myObject);
					StoneLemming slemming = new StoneLemming();
					cellBelow.addContainedObject(slemming);
					Lemming.lemmingCount--;
					return false;
				} else {
					myLemming.setFallingStatus(Lemming.NOT_FALLING);
					fallHeight = 0;			
				}
			}
			myCell.delContainedObject(myObject);
			cellBelow.addContainedObject(myObject);
			fallHeight++;
			if(fallHeight > 3)
				myLemming.setFallingStatus(Lemming.HIGH_FALL);
		}
		
		return false;
	}
}
