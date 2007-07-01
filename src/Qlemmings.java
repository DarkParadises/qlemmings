import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;

public class Qlemmings {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameFrame win = new GameFrame();
				win.setVisible(true);
				win.initComponents();
				win.start();
			}
		});

	}
}
