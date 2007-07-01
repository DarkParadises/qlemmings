import java.awt.*;
import java.awt.image.*;
import java.util.*;

/**
 * A jatekter kirajzolasaert felelos osztaly.
 * A jatekteren talalhato osszes cellat kirajzolja.
 */
public class GameDrawer extends BaseDrawer {
	private Game game;

	public GameDrawer(Game game) {
		this.game = game;
	}

	/**
	 * A jatekter kirajzolasa.
	 * A jatekteren talalhato osszes cellan vegiglep, es meghivja
	 * a kirajzolas fuggvenyeiket megfelelo koordinatakkal eltolt
	 * grafikus lapra.
	 */
	public boolean draw(Graphics2D g) {
		Cell[][] cells = game.getCells();
		
		g.setPaint(new Color(148, 184, 255));
		g.fill(new Rectangle(0, 0, cells.length * Constants.TILE_SIZE, 
					cells[0].length * Constants.TILE_SIZE));
		
		for(int y = 0; y < cells[0].length; y++) {
			for(int x = 0; x < cells.length; x++) {
				cells[x][y].getDrawer().draw(g);
				g.translate(Constants.TILE_SIZE, 0);
			}
			g.translate(-1 * cells.length * Constants.TILE_SIZE, 
					Constants.TILE_SIZE);
		}
		return true;
	}

	public void step() {
		if(game == null) return;
		Cell[][] cells = game.getCells();
		if(cells == null) return;
		for(int y = 0; y < cells[0].length; y++) {
			for(int x = 0; x < cells.length; x++) {
				cells[x][y].getDrawer().step();
			}
		}
	}
}
