import java.util.*;

/**
 * A Lemming osztaly, amely a palyan setallo allatkakat reprezentalja.
 * Egy lemming {@link Skill} objektumok segitsegevel kepes cselekvesre. Ilyen
 * tipusu objektumokbol a szekeleton egyszerre egyet kepes tarolni, mivel ennel
 * tobbre nincs szukseg a fuggvenyhivasok bemutatasara. A vegleges verzioban 
 * barmennyit kepes lesz tarolni kozuluk.
 * 
 */
public class Lemming extends WorldObject{
	public static final int NOT_FALLING = 0;
	public static final int LOW_FALL = 1;
	public static final int HIGH_FALL = 2;

	public static int lemmingCount = -1;

	private Vector skills;
	private int fallingStatus = NOT_FALLING;

	public int getFallingStatus() {
		return fallingStatus;
	}

	public void setFallingStatus(int status) {
		fallingStatus = status;
	}

	/**
	 * A Lemming osztaly konstruktora.
	 * A szkeletonban a kepernyore irason es az objektum egyedi azonosito
	 * szamanak eltarolasan kivul mast nem csinal. A vegleges valtozatban
	 * itt inicializalodnak majd a lemming tagvaltozoi.
	 */
	public Lemming() {
		lastMoveDirection = Direction.South;
	
		drawer = new LemmingDrawer(this);
		
		skills = new Vector();
		
		Walking walking = new Walking();
		addSkill(walking);

		Falling falling = new Falling();
		addSkill(falling);
	
		setAttribute(Constants.NOT_BLOCKING_KEY, Direction.All);
	}

	public void blowUp(int direction) {
		super.blowUp(direction);
		lemmingCount--;
	}

	/**
	 * A lemming lep egyet.
	 * A lemming az eltarolt {@link Skill} objektumoknak meghivja megfelelo
	 * metodusait. Ennek hatasara vegrehajtodik a megfelelo cselekdves, pl
	 * egy egysegnyit lep elore, vagy esik lefele, stb.
	 */
	public void step() {
		super.step();
		
		if(isAttributeTrue(Constants.DEAD_KEY)) 
			return;
		Vector skillsCopy = (Vector)skills.clone();
		for(Iterator i = skillsCopy.iterator();i.hasNext();) {
			Skill skill = (Skill)i.next();
			if(!skill.doIt())
				return;
		}
	}

	/**
	 * A lemming altalanos utkozeskezelo fuggvenye.
	 * A parameterkent kapott {@link WorldObject} objektum megfelelo
	 * metodusat meghivja sajat magat atadva parameterkent, ezzel 
	 * ertesiti a vele tortent utkozesrol.
	 *
	 * @param o Az objektum, amelyet ertesiteni kell az utkozesrol
	 */
	public void doCollision(WorldObject o) {
		o.collideWith(this);
	}

	/**
	 * A lemming egy {@link Exit} osztalyu objektummal tortent utkozeset kezeli.
	 * Ennek hatasara ertesiti az ot tartalmazo {@link Cell} objektumot, hogy
	 * torolje ki ot a tartalmazott objektumok kozul, hiszen kielepett a 
	 * kijaraton.
	 *
	 * @param o A kijarat, amivel a lemming utkozott.
	 */
	public void collideWith(Exit o) {
		myCell.delContainedObject(this);
		setAttribute(Constants.DEAD_KEY, true);
		lemmingCount--;
	}

	/**
	 * A lemming egy uj {@link Skill} objektumot ad hozza a meglevokhoz.
	 * A szkeletonban az egyszeruseg kedveert csak egyetlen kepesseget tud
	 * tarolni egy lemming, a veglegesben egyszerre egynel tobb is lehet.
	 *
	 * @param skill Az uj kepesseg, amelyet a lemming hozza ad a meglevokhoz.
	 */
	public boolean addSkill(Skill skill) {
		skills.add(skill);
		skill.setObject(this);
		Collections.sort(skills);
		return true;
	}
        
	public Iterator getSkills() {
		//BUG? TODO!
		//return ((Vector)skills.clone()).iterator();
		return skills.iterator();
	}
	
        public boolean removeSkill(Skill skill) {
		skills.remove(skill);
		return true;
        }
}
