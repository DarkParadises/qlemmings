import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A palyak kivalasztasaert felelos osztaly.
 */
public class LevelSelector extends JFrame {
	private JList jList1;
	private JScrollPane jScrollPane1;
	private GameFrame rootFrame;

	public LevelSelector(GameFrame rootFrame) {
		this.rootFrame = rootFrame;
		rootFrame.setVisible(false);
		initComponents();
		setVisible(true);
	}

	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Quercus Lemmings Level Selector");

		BorderLayout layout = new BorderLayout();
		JPanel panel = new JPanel(layout);
		getContentPane().add(panel);

		jList1 = new JList();
		jScrollPane1 = new javax.swing.JScrollPane();
		
		jList1.setModel(new javax.swing.AbstractListModel() {
				String[] strings = {"level1.lvl", "test.lvl"};
				public int getSize() { return strings.length; }
				public Object getElementAt(int i) { return strings[i]; }
				});
		jScrollPane1.setViewportView(jList1);

		JButton jButton1 = new JButton("Play");
		JButton jButton2 = new JButton("Quit");

		jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					System.exit(0);
				}
				});

		jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					rootFrame.selectLevel((String)jList1.getSelectedValue());
					setVisible(false);
					rootFrame.setVisible(true);
				}
				});

		JPanel panel2 = new JPanel(new FlowLayout());
		panel2.add(jButton1);
		panel2.add(jButton2);

		panel.add(jScrollPane1, BorderLayout.CENTER);
		panel.add(panel2, BorderLayout.PAGE_END);
	
		pack();
	}
}
