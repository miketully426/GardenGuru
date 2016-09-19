package ggPackage1;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class TestPanels extends JPanel {

	/**
	 * Create the panel.
	 */
	public TestPanels() {
		setLayout(null);
		
		JLabel lblCheckMeOut = new JLabel("Check me out, dawg");
		lblCheckMeOut.setBounds(130, 102, 123, 23);
		add(lblCheckMeOut);

	}

}
