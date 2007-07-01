import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * A jatek foosztalya.
 * A kulvilag ezen keresztul adhat parancsokat a jateknak.
 */
public class Game {
	public static boolean gameWon = false;

	private Cell[][] cells = null;
	private boolean paused = false;

	/* Begin Drawing Code */
	private GameDrawer drawer;

	public Game() {
		drawer = new GameDrawer(this);
		gameWon = false;
	}

	public GameDrawer getDrawer() {
		return drawer;
	}

	public Cell[][] getCells() {
		return cells;
	}
	/* End Drawing Code */

	/**
	 * A cellak letrehozaasaert felelos palya-leiro parancs.
	 */
	private class CreateCells implements ParserCommand {
		/**
		 * A cellakat letrehozo metodus.
		 * Leellenorzi a parametereket, majd a parametereknek megfeleloen
		 * letrehozza a cellakat. Ezutan a letrejott cellaban beallitja
		 * a szomszedokat.
		 *
		 * @param args a parancs argumentumai
		 */
		public void doCommand(String[] args) throws Exception {
			if(cells != null)
				throw new Exception("CreateCells: map already initialized!");
			
			if(args.length != 2)
				throw new Exception("CreateCells: exactly 2 parameters needed!");
			
			int width = Integer.parseInt(args[0]);
			int height = Integer.parseInt(args[1]);
			cells = new Cell[width][height];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					cells[j][i] = new Cell();
				}
			}

			/* Szomszedok beallitasa. Igen, csunya. Irj szebbet. :P */
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					for(int k = -1; k < 2; k++) {
						for(int l = -1; l < 2; l++) {
							if((k == 0) && (l == 0))
								continue;
							int nx = j + k;
							int ny = i + l;
							if((nx >= 0) && (ny >= 0) && (nx < width) && (ny < height)) {
								int d = Direction.directionAt(-1 * k, -1 * l);
								cells[nx][ny].setNeighbourAt(d, cells[j][i]);
							}
						}
					}
				}
			}
		}
	}

	private class SetSkillNumbers implements ParserCommand {
		public void doCommand(String[] args) throws Exception {
			if(args.length != 5)
				throw new Exception("SetSkillNumbers: exactly 5 parameters needed!");
		
			for(int i = 0; i < 5; i++) {
				if(Integer.parseInt(args[i]) < 0)
					throw new Exception("SetSkillNumbers: numbers must be positive!");
			}

			Flying.usability = Integer.parseInt(args[0]);
			Digging.usability = Integer.parseInt(args[1]);
			Building.usability = Integer.parseInt(args[2]);
			Blocking.usability = Integer.parseInt(args[3]);
			Blowing.usability = Integer.parseInt(args[4]);
		}
	}

	/**
	 * Egy ko letrehozasaert felelos palyaleiro-parancs.
	 */
	private class CreateRock implements ParserCommand {
		/**
		 * Egy kovet letrehozo metodus.
		 * Leellenorzi, hogy megfelelo szamu argumentumot kapott-e,
		 * majd megprobalja a megadott parameterekkel letrehozni a 
		 * kovet.
		 *
		 * @param args a parancs argumentumai
		 */
		public void doCommand(String[] args) throws Exception {
			if(args.length != 2)
				throw new Exception("CreateRock: exactly 2 parameters needed!");

			int x = Integer.parseInt(args[0]);
			int y = Integer.parseInt(args[1]);
		
			try {
				Rock rock = new Rock();
				addObjectAt(rock, x, y);
			} catch(Exception e) {
				throw new Exception("CreateRock: "+e.getMessage());
			}
		}
	}

	/**
	 * Egy bejarati pont letrehozasaert felelos palyaleiro-parancs.
	 */
	private class CreateEntrance implements ParserCommand {
		/**
		 * Egy belepesi pontot letrehozo metodus.
		 * Leellenorzi, hogy megfelelo szamu argumentumot kapott-e,
		 * majd megprobalja a megadott parameterekkel letrehozni a 
		 * bejaratot.
		 *
		 * @param args a parancs argumentumai.
		 */
		public void doCommand(String[] args) throws Exception {
			if(args.length != 4)
				throw new Exception("CreateEntrance: exactly 4 parameters needed!");
			
			int maxLemmings = Integer.parseInt(args[0]);
			int maxCount = Integer.parseInt(args[1]);
			int x = Integer.parseInt(args[2]);
			int y = Integer.parseInt(args[3]);

			try {
				Entrance entrance = new Entrance(maxLemmings, maxCount);
				addObjectAt(entrance, x, y);
			} catch(Exception e) {
				throw new Exception("CreateEntrance: "+e.getMessage());
			}
		}
	}
	

	/**
	 * Egy kijarati pont letrehozasaert felelos palyaleiro-parancs.
	 */
	private class CreateExit implements ParserCommand {
		/**
		 * Egy kijarati pontot letrehozo metodus.
		 * Leellenorzi, hogy megfelelo szamu argumentumot kapott-e,
		 * majd megprobalja a megadott parameterekkel letrehozni a 
		 * kijaratot.
		 *
		 * @param args a parancs argumentumai.
		 */
		public void doCommand(String[] args) throws Exception {
			if(args.length != 3)
				throw new Exception("CreateExit: exactly 3 parameters needed!");

			int minLemmings = Integer.parseInt(args[0]);
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);

			try {
				Exit exit = new Exit(minLemmings);
				addObjectAt(exit, x, y);
			} catch(Exception e) {
				throw new Exception("CreateExit: "+e.getMessage());
			}
		}
	}

	/**
	 * Egy ferdeko letrehozasaert felelos palyaleiro-parancs.
	 */
	private class CreateAslopeRock implements ParserCommand {
		/**
		 * Egy ferde kovet hoz letre.
		 * Leellenorzi a parametereket, majd megprobalja letrehozni
		 * a kivant ferde kovet.
		 *
		 * @param args a parancs argumentumai
		 */
		public void doCommand(String[] args) throws Exception {
			if(args.length != 3)
				throw new Exception("CreateAslopeRock: exactly 3 parameters needed!");

			int dir;
			if(args[0].equals("east")) {
				dir = Direction.East;
			} else if(args[0].equals("west")) {
				dir = Direction.West;
			} else {
				throw new Exception("CreateAslopeRock: first parameter must be 'east' or 'west'!");
			}

			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);

			try {
				AslopeRock arock = new AslopeRock(dir);
				addObjectAt(arock, x ,y);
			} catch(Exception e) {
				throw new Exception("CreateAslopeRock: "+e.getMessage());
			}
		}
	}

	/**
	 * Betolti a parameterben megadott fajlbol a palyat.
	 * Letrehoz egy parser objektumot, amelynek parancsai koze hozzaadja
	 * a palya letrehozasahoz szukseges parancs objekumokat.
	 *
	 * @param fileName a betoltendo fajl neve
	 */
	public void loadMap(String fileName) throws Exception {
		Parser p = new Parser();
		p.addCommand("SetSkillNumbers", new SetSkillNumbers());
		p.addCommand("CreateCells", new CreateCells());
		p.addCommand("CreateRock", new CreateRock());
		p.addCommand("CreateAslopeRock", new CreateAslopeRock());
		p.addCommand("CreateEntrance", new CreateEntrance());
		p.addCommand("CreateExit", new CreateExit());
		p.parse(getClass().getResourceAsStream(fileName));
	}

	/**
	 * Letrehoz egy objektumot a kivant pozicion.
	 *
	 * @param obj A palyahoz hozzaadando objektum.
	 * @param x Az objektum x koordinataja
	 * @param y Az objektum y koordinataja.
	 */
	private void addObjectAt(WorldObject obj, int x, int y) throws Exception {
		Cell cell = getCellAt(x, y);
		
		if(cell.isBlocked(Direction.All)) 
			throw new Exception("Cell is blocked");

		cell.addContainedObject(obj);
	}
		
	/**
	 * Megadott koordinatakon levo cellaval ter vissza.
	 *
	 * @param x A cella x koordinataja
	 * @param y A cella y koordinataja
	 * @return A visszakapott cella.
	 */
	public Cell getCellAt(int x, int y) throws Exception {
		if(cells == null)
			throw new Exception("Map not initialized!");
		
		if((x < 0) || (y < 0) || (x > cells.length - 1) || (y > cells[0].length - 1))
			throw new Exception("Coordinates out of bounds!");
		
		return cells[x][y];
	}

	public void step(int n) {
		for(int i = 0; i < n; i++)
			step();
	}

	public void pause() {
		paused = true;
	}

	public void unpause() {
		paused = false;
	}
	
	public boolean isPaused() {
		return paused;
	}

	/**
	 * A palyan levo osszes lemminget felrobbantja.
	 * Vegigleptet a palyan levo osszes cella osszes tartalmazott objektuman,
	 * es egyesevel robbanas tulajdonsaggal latja el oket.
	 */
	public void blowUpEveryLemming() {
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[0].length; j++) {
				for(Iterator it = cells[i][j].getContainedObjects();it.hasNext();) {
					WorldObject obj = (WorldObject)it.next();
					obj.addSkill(new Blowing());
				}
			}
		}
	}

	/**
	 * Megadott koordinatakra elhelyez egy tulajdonsagot.
	 * Amennyiben egynel tobb tulajdonsag-felhasznalo tartozkodik a koordinatan,
	 * csak a legelsonek adja oda.
	 *
	 * @param skill a hozzaadando tulajdonsag
	 * @param x a celobjektum x koordinataja
	 * @param y a celobjektum y koordinataja
	 * @return a hozzaadas sikeressege
	 */
	public boolean addSkillAt(Skill skill, int x, int y) throws Exception {
		for(Iterator i = getCellAt(x, y).getContainedObjects();i.hasNext();) {
			WorldObject obj = (WorldObject)i.next();
			if(obj.addSkill(skill))
				return true;
		}
		return false;
	}

	/**
	 * Lepteti egyel a jatekot.
	 * Eloszor vegigleptet a cellakon, es leptetes uzenetet kuld nekik.
	 * Ekozben a bejart cellakon levo objektumokat eltarolja egy listaba, amin
	 * a ciklus befejezte utan szinten vegigleptet.
	 */
	public void step() {
		if(paused)
			return;
		if(cells != null) {
			Vector objects = new Vector();
			for(int i = 0; i < cells.length; i++) {
				for(int j = 0; j < cells[0].length; j++) {
					/* A cellaban tartozkodo osszes objektumot hozzaadjuk az
					 * objects-hez, igy mire az osszes cellan vegigmegyunk a
					 * palyan tartozkodo osszes objektum benne lesz. */
					for(Iterator it = cells[i][j].getContainedObjects();it.hasNext();)
						objects.add(it.next());

					/* Itt tortenik a cella leptetese, ami csak az utkozes 
					 * kezelest vegzi, a rajta levo objektumokat nem lepteti. */
					cells[i][j].step();
				}
			}

			/* A vegen vegigmegyunk a palyan levo osszes objektumon, es 
			 * leptetjuk oket. Igy elkerulheto, hogy egy objektumot esetleg
			 * egynel tobbszor leptessunk, ha pl arreb lep egy masik cellaba. */
			for(Iterator it = objects.iterator();it.hasNext();)
				((WorldObject)it.next()).step();
		}
	}
}
