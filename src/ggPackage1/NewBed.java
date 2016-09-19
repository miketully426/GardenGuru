package ggPackage1;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

public class NewBed extends JPanel {
	public JFrame frame;
	private JTextField nameInput;
	private JFormattedTextField size1;
	private JFormattedTextField size2;
	private GardenDB database;

	/**
	 * Create the panel.
	 */
	public NewBed(String username) {

		setBounds(100, 100, 399, 392);
		setLayout(null);
		
		database = new GardenDB();
		
		JLabel lblCreateNewBed = new JLabel("Create New Bed");
		lblCreateNewBed.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateNewBed.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCreateNewBed.setBounds(44, 11, 287, 38);
		add(lblCreateNewBed);
		
		JLabel lblBedName = new JLabel("Bed Name: ");
		lblBedName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBedName.setBounds(54, 60, 84, 34);
		add(lblBedName);
		
		JLabel lblHeight = new JLabel("Height: ");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHeight.setBounds(54, 105, 84, 20);
		add(lblHeight);
		
		JLabel lblWidth = new JLabel("Width: ");
		lblWidth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWidth.setBounds(54, 143, 76, 20);
		add(lblWidth);
		
		nameInput = new JTextField();
		nameInput.setBounds(129, 69, 157, 20);
		add(nameInput);
		nameInput.setColumns(10);
		
		size1 = new JFormattedTextField();
		size1.setBounds(129, 107, 86, 20);
		size1.setValue(new Double(1));
		add(size1);
		size1.setColumns(10);
		
		size2 = new JFormattedTextField();
		size2.setBounds(129, 145, 86, 20);
		size2.setValue(new Double(1));
		add(size2);
		size2.setColumns(10);
		
		JButton btnCreateBed = new JButton("Create Bed");
		btnCreateBed.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCreateBed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			if(!checkBedName(nameInput.getText(), username)) {	
				if(nameInput.getText().length() > 1){
					double size = (Double)size1.getValue() * (Double)size2.getValue();
					database.addBed(username, nameInput.getText(), size);
					setVisible(false);
					
				}
				else {
					JOptionPane.showMessageDialog(frame, "Please name your bed.");
				}
			}
			else {
				JOptionPane.showMessageDialog(frame, "A bed already exists with this name");
			}
			}
		});
		btnCreateBed.setBounds(44, 200, 111, 38);
		add(btnCreateBed);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setBounds(175, 200, 111, 38);
		add(btnCancel);
		
	}
	
	private boolean checkBedName(String bed, String username){
		ArrayList<Bed> beds = database.getBeds(username);
		for(int i = 0; i < beds.size(); i++){
			if(bed.equals(beds.get(i))){
				return true;
			}
		}
		return false;
	}
	
}
