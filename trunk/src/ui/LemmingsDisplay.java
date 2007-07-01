import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

/**
 * A jatektert megjeleniteseert felelos komponens.
 */
class LemmingsDisplay extends JComponent {
	private Game game;

	public LemmingsDisplay(Game game) {
		Cell[][] cells = game.getCells();
		setPreferredSize(new Dimension(cells.length * Constants.TILE_SIZE, 
					cells[0].length * Constants.TILE_SIZE));
		this.game = game;
	}
	
	/**
	 * A kirajzolas vegrehajtasa.
	 * Meghivja a sajat graphics objektumara a Game kirajzolojanak
	 * rajzolas metodusat.
	 */
	protected void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		Graphics2D g2d = (Graphics2D)g.create();
		game.getDrawer().draw(g2d);
		g2d.dispose();
	}

	/**
	 * Megadott fajlneven talalhato fajlbol betolt es aktival egy kurzurt.
	 */
	public void setCursorFromImage(String fileName) {
		URL url = this.getClass().getResource(fileName);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImg = toolkit.getImage(url);
		Cursor cursor = toolkit.createCustomCursor(cursorImg, new Point(0,0), "Mycursor");
		setCursor(cursor);
	}
}
