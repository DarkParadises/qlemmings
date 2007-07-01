/**
 * A setalas kepesseget reprezentalo osztaly.
 * Vegrehajtaskor megprobal elore lepni egy cellat, vagy ha nem sikerul,
 * megfordul.
 */
public class Walking extends Skill {
	/**
	 * A Walking osztaly konstruktora.
	 * A szkeleton verzioban a kepernyore irason, es az objektum egyedi
	 * azonsitojanak eltarolasan kivul mast nem csinal.
	 */
	public Walking() {
		priority = 3;
		drawer = new WalkingDrawer(this);
		executed = -1;
	}

	/**
	 * Megforditja a kepesseget tartalamzo objektum iranyat.
	 * Ez a metodus a szkeleton verzioban meg nincs implementalva.
	 */
	private void turnAround() {
		int direction = myObject.getAttribute(Constants.DirectionKey);
		if(direction == Direction.East) {
			direction = Direction.West;
		} else {
			direction = Direction.East;
		}
		myObject.setAttribute(Constants.DirectionKey, direction);
	}

	/**
	 * A setalast vegzo metodus.
	 * Megvizsgalja, hogy elerheto-e a kovetkezo cella, ha igen
	 * atmozgatja a hozzarendelt objektumot oda. Ha nem, megfordul.
	 */
	public boolean doIt() {
		Lemming myLemming = (Lemming)myObject;
		Cell myCell = myObject.getCell();
		
		Cell cellBelow = myCell.getCellAt(Direction.South);
		if((cellBelow != null) && (!cellBelow.isBlocked(Direction.South))) {
			executed = -1;
			return true;
		}

		if(myLemming.getFallingStatus() != Lemming.NOT_FALLING) {
			executed = -1;
			return true;
		}

		executed = Direction.None;
		
		int direction = myObject.getAttribute(Constants.DirectionKey);
		Cell nextCell = myCell.getAvailableCellAt(direction);
		if(nextCell != null) {
			for(int dir = 0; dir < Direction.All; dir++) {
				if(nextCell.getCellAt(dir) == myCell) {
					executed = Direction.getOpposite(dir);
				}
			}
			
			myCell.delContainedObject(myObject);
			nextCell.addContainedObject(myObject);
		} else {
			turnAround();
		}

		return false;
	}
}
