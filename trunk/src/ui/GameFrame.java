import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;

/**
 * A jatek foablaka.
 */
class GameFrame extends JFrame {
	private LemmingsDisplay display;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private javax.swing.JButton jButton7;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenuBar jMenuBar1;
	private JMenuItem jMenuItem1;
	private JMenuItem jMenuItem2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JToolBar jToolBar1;
	private JPanel panel;

	private Game game;
	private GameFrame myself;
	private Skill selectedSkill;

	/**
	 * A repules gomb lenyomasakor lefuto delegalt fuggvenyt tartalmazo osztaly.
	 */
	private class FlyingPressed implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			display.setCursorFromImage("cursor_flying.gif");
			selectedSkill = new Flying();
		}
	}

	/**
	 * Az asas gomb lenyomasakor lefuto delegalt fuggvenyt tartalmazo osztaly.
	 */
	private class DiggingPressed implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			display.setCursorFromImage("cursor_digging.gif");
			selectedSkill = new Digging();
		}
	}

	/**
	 * Az epites gomb lenyomasakor lefuto delegalt fuggvenyt tartalmazo osztaly.
	 */
	private class BuildingPressed implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			display.setCursorFromImage("cursor_building.gif");
			selectedSkill = new Building();
		}
	}

	/**
	 * A robbanas gomb lenyomasakor lefuto delegalt fuggvenyt tartalmazo osztaly.
	 */
	private class BlowingPressed implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			display.setCursorFromImage("cursor_blowing.gif");
			selectedSkill = new Blowing();
		}
	}

	/**
	 * A blokkolas gomb lenyomasakor lefuto delegalt fuggvenyt tartalmazo osztaly.
	 */
	private class BlockingPressed implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			display.setCursorFromImage("cursor_blocking.gif");
			selectedSkill = new Blocking();
		}
	}

	/**
	 * A mindenkit felrobbantas gomb lenyomasakor lefuto delegalt fuggvenyt tartalmazo osztaly.
	 */
	private class BlowUpAllPressed implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			game.blowUpEveryLemming();
		}
	}

	/**
	 * A pillanatallj gomb lenyomasakor lefuto delegalt fuggvenyt tartalmazo osztaly.
	 */
	private class PausePressed implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(game.isPaused()) {
				game.unpause();
				jButton6.setText("Pause");
			} else {
				game.pause();
				jButton6.setText("UnPause");
			}
		}
	}

	/**
	 * A palyavalaszto menupontra kattintaskor lefuto delegalt fuggvenyt tartalmazo osztaly.
	 */
	private class LevelSelectPressed implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			game.pause();
			int answer = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to load a new level?", "Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if(answer == JOptionPane.YES_OPTION) {
				new LevelSelector(myself);
			} else {
				game.unpause();
			}
		}
	}

	/**
	 * A kilepes menupontra kattintaskor lefuto delegalt fuggvenyt tartalmazo osztaly.
	 */
	private class QuitPressed implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			game.pause();
			int answer = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to quit?", "Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if(answer == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else {
				game.unpause();
			}
		}
	}

	private class MyMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if(selectedSkill == null)
				return;
			
			if(e.getButton() == MouseEvent.BUTTON1) {
				Point viewPos = jScrollPane1.getViewport().getViewPosition();
				int x = (e.getX() + viewPos.x) / Constants.TILE_SIZE;
				int y = (e.getY() + viewPos.y) / Constants.TILE_SIZE;
			
				try {
					if(game.addSkillAt(selectedSkill, x, y)) {
						display.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						selectedSkill.decreaseUsability();
						selectedSkill = null;
						setButtonLabels();
						return;
					}
				} catch(Exception f) {
					System.out.println(f.getMessage());
					f.printStackTrace();
				}
			} else if(e.getButton() == MouseEvent.BUTTON3) {
				display.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				selectedSkill = null;
			}
		}
	}

	/**
	 * Egy uj palyat kivalaszto metodus.
	 * Egy LevelSelector segitsegevel kivalasztja a megfelelo palyat.
	 */
	public void selectLevel(String f) {
		try {
			game = new Game();
			game.loadMap(f);
			reInitComponents();
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
				"Error loading map: "+e.getMessage(), "Error loading map",
				JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * A jateko kirajzolo ciklusat tartalmazo osztaly.
	 * A jatek kirajzolasat vezerlo ciklus kulon szalon fut.
	 */
	private class PaintLoop extends Thread {
		private int count;

		public void run() {
			count = 0;
			while(true) {
				if((game != null) && (!game.isPaused())) {
					if(count-- == 0) { 
						game.step();
						count = 9;
					}
					game.getDrawer().step();
					if(display != null) display.repaint();
					if(Lemming.lemmingCount == 0) {
						if(Game.gameWon)
							JOptionPane.showMessageDialog(null, 
								"Congratulations! You have successfully completed the level!",
								"Level Completed", JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, 
								"You have failed to complete the level!",
								"Level Failed", JOptionPane.INFORMATION_MESSAGE);
						new LevelSelector(myself);
						Lemming.lemmingCount = -1;
					}
				}
				try {
					sleep(50);
				} catch(Exception e) {
				}
			}
		}
	}

	/**
	 * Az ablak gombjainak cimkeit beallito metodus.
	 * Minden tulajdonsag adas gombot frissit az aktualis maradek 
	 * felhasznalhatosagi adatokkal, illetve deaktivalja azokat a gombokat,
	 * amelyek olyan tulajdonsagokat adnak, amelyek mar elfogytak.
	 */
	private void setButtonLabels() {
		jButton3.setText("Fly ("+Flying.usability+")");
		if(Flying.usability < 1) jButton3.setEnabled(false);
			else jButton3.setEnabled(true);

		jButton1.setText("Dig ("+Digging.usability+")");
		if(Digging.usability < 1) jButton1.setEnabled(false);
			else jButton1.setEnabled(true);
		
		jButton4.setText("Build ("+Building.usability+")");
		if(Building.usability < 1) jButton4.setEnabled(false);
			else jButton4.setEnabled(true);
		
		jButton7.setText("Block ("+Blocking.usability+")");
		if(Blocking.usability < 1) jButton7.setEnabled(false);
			else jButton7.setEnabled(true);
		
		jButton2.setText("Blow Up ("+Blowing.usability+")");
		if(Blowing.usability < 1) jButton2.setEnabled(false);
			else jButton2.setEnabled(true);
	}

	/**
	 * Egy uj palya betoltesekor valo ujrainicializalast vegzo metodus.
	 */
	public void reInitComponents() {
		if(display != null)
			jScrollPane1.getViewport().remove(display);
		display = new LemmingsDisplay(game);
		jScrollPane1.getViewport().add(display);
		setButtonLabels();
		pack();
	}

	/**
	 * Az ablak komponenseit inicializalo metodus.
	 */
	public void initComponents() {
		myself = this;
		jMenuBar1 = new JMenuBar();
		jMenu1 = new JMenu();
		jMenuItem1 = new JMenuItem("Load new level");
		jMenuItem2 = new JMenuItem("Quit");
		jToolBar1 = new JToolBar();
		jButton3 = new JButton();
		jButton1 = new JButton();
		jButton4 = new JButton();
		jButton7 = new JButton();
		jButton2 = new JButton();
		jButton5 = new JButton();
		jButton6 = new JButton();
		jScrollPane1 = new JScrollPane();
		
		new LevelSelector(this);
	
		game = null;	
		//display = new LemmingsDisplay(game);

		jMenu1.setText("Game");
		jMenuBar1.add(jMenu1);
		jMenuItem1.addActionListener(new LevelSelectPressed());
		jMenuItem2.addActionListener(new QuitPressed());
		jMenu1.add(jMenuItem1);
		jMenu1.add(jMenuItem2);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Quercus Lemmings");
		URL url = this.getClass().getResource("lemming_flying.png");
		jButton3.setIcon(new ImageIcon(url));
		jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton3.addActionListener(new FlyingPressed());
		jToolBar1.add(jButton3);

		url = this.getClass().getResource("lemming_digging.png");
		jButton1.setIcon(new ImageIcon(url));
		jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton1.addActionListener(new DiggingPressed());
		jToolBar1.add(jButton1);

		url = this.getClass().getResource("lemming_building_right.png");
		jButton4.setIcon(new ImageIcon(url));
		jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton4.addActionListener(new BuildingPressed());
		jToolBar1.add(jButton4);

		url = this.getClass().getResource("lemming_blocking.png");
		jButton7.setIcon(new ImageIcon(url));
		jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton7.addActionListener(new BlockingPressed());
		jToolBar1.add(jButton7);

		url = this.getClass().getResource("lemming_blowing.png");
		jButton2.setIcon(new ImageIcon(url));
		jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton2.addActionListener(new BlowingPressed());
		jToolBar1.add(jButton2);

		setButtonLabels();

		url = this.getClass().getResource("blowupall.png");
		jButton5.setText("Blow Up All");
		jButton5.setIcon(new ImageIcon(url));
		jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton5.addActionListener(new BlowUpAllPressed());
		jToolBar1.add(jButton5);

		url = this.getClass().getResource("pause.png");
		jButton6.setText("Pause");
		jButton6.setIcon(new ImageIcon(url));
		jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton6.addActionListener(new PausePressed());
		jToolBar1.add(jButton6);

		jScrollPane1.addMouseListener(new MyMouseAdapter());
		jScrollPane1.getViewport().add(display);

		setJMenuBar(jMenuBar1);

		BorderLayout layout = new BorderLayout();
		panel = new JPanel(layout);
		getContentPane().add(panel);
		panel.add(jScrollPane1, BorderLayout.CENTER);
		panel.add(jToolBar1, BorderLayout.PAGE_END);
	
		pack();
	
	}

	/**
	 * A kirajzolas ciklus inditasa.
	 */
	public void start() {
		new PaintLoop().start();
	}
}
