/**
 * A Blowing osztaly, amely a lemmingek robbanasi kepesseget reprezentalja. 
 * A szkeleton verzioban amint a robbanas funkciot megkapja egy lemming,
 * egybol felrobban.
 */
public class Blowing extends Skill {
	public static int usability = 0;

	public void decreaseUsability() {
		usability--;
	}

	private int count;

	/**
	 * A Blowing osztaly konstruktora. 
	 * Eltarolja az objektum azonositojat, es a szkeleton celjanak megfeleloen 
	 * itt kiirja a kepernyore, ha kivalasztjuk a megfelelo funkciot.
	 */
	public Blowing() {
		count = 3;
		priority = 6;
		
		drawer = new BlowingDrawer(this);
	}

	/**
	 * A robbanast eloidezo metodus.
	 * Ertesiti az ot tartalmazo cellat, hogy robbanjon fel, aminek hatasara
	 * a cellaban levo osszes objektum torlodik, illetve a cella kozvetlen 
	 * szomszedainak is meghivja a blowUp metodusat.
	 */
	public boolean doIt() {
		if(--count < 0) {
			Cell myCell = myObject.getCell();
			myCell.blowUp(1, 0);
			return false;
		}

		return true;
	}

	public int getCount() {
		return count;
	}
}
