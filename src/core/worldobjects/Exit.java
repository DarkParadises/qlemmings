/**
 * Az Exit osztaly, amely a jatek kilepesi pontjat reprezentalja.
 * Ha egy lemming ezzel a tipusu objektummal utkozik torlodik a palyarol.
 */
public class Exit extends WorldObject {
	private int minLemmings;
	private int lemmingsExited = 0;

	/** 
	 * Az osztaly konstruktora.
	 * Beallitja az objektum azonositot, a kileptendo lemmingek szamat, illetve
	 * megsemmisithetetlenne teszi magat.
	 *
	 * @param minLemmings a palya sikeres teljesitesehez szukseges lemmingek szama.
	 */
	public Exit(int minLemmings) {
		this.minLemmings = minLemmings;
		setAttribute(Constants.DESTROYABLE_KEY, false);
		drawer = new ExitDrawer(this);
	}

	/**
	 * Az altalanos utkozes kezelo metodus.
	 * A parameterkent atadott {@link WorldObject} objektumot ertesiti a vele tortent utkozesrol.
	 *
	 * @param o Az objektum, amelyet ertesiteni kell az utkozesrol.
	 */
	public void doCollision(WorldObject o) {
		o.collideWith(this);
	}

	/**
	 * Egy {@link Lemming} objektummal torento utkozest kezelo metodus.
	 * Ha az Exit {@link Lemming} tipusu objektummal utkozik, akkor ez a fuggveny hivodik meg.
	 * A szkeleton verzioban a kepernyore irason kivul mast nem csinal.
	 *
	 * @param o A lemming objektum, amellyel utkozott
	 */
	public void collideWith(Lemming o) {
		lemmingsExited++;
		if(lemmingsExited == minLemmings)
			Game.gameWon = true;
	}
}
