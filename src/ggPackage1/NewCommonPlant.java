package ggPackage1;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class NewCommonPlant extends JDialog {
	private JTextField name;
	private JComboBox vegge;
	private JTextArea helpers;
	private JTextArea hurtby;
	private JFormattedTextField days;
	private JFormattedTextField size;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewCommonPlant dialog = new NewCommonPlant();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewCommonPlant() {
		setTitle("Add new Common Plant");
		setBounds(100, 100, 425, 360);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 0, 0);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel lblNameOfPlant = new JLabel("Name of Plant: ");
		lblNameOfPlant.setBounds(10, 11, 86, 26);
		getContentPane().add(lblNameOfPlant);
		
		JLabel lblHerbOrVegetable = new JLabel("Herb or Vegetable: ");
		lblHerbOrVegetable.setBounds(10, 48, 119, 26);
		getContentPane().add(lblHerbOrVegetable);
		
		JComboBox vegge = new JComboBox();
		vegge.setBounds(139, 51, 86, 20);
		vegge.addItem("Vegetable");
		vegge.addItem("Herb");
		getContentPane().add(vegge);
		
		JLabel lblCompanions = new JLabel("Companions: ");
		lblCompanions.setBounds(10, 119, 86, 26);
		getContentPane().add(lblCompanions);
		
		name = new JTextField();
		name.setBounds(104, 14, 224, 20);
		getContentPane().add(name);
		name.setColumns(10);
		
		JLabel lblHurtBy = new JLabel("Hurt by: ");
		lblHurtBy.setBounds(10, 192, 46, 14);
		getContentPane().add(lblHurtBy);
		
		JLabel lblTypicalDaysTo = new JLabel("Typical days to harvest: ");
		lblTypicalDaysTo.setBounds(10, 82, 159, 26);
		getContentPane().add(lblTypicalDaysTo);
		
		JLabel lblNumberPerSqft = new JLabel("Number per sq/ft: ");
		lblNumberPerSqft.setBounds(205, 82, 101, 26);
		getContentPane().add(lblNumberPerSqft);
		
		helpers = new JTextArea();
		helpers.setRows(2);
		helpers.setLineWrap(true);
		helpers.setBounds(93, 120, 249, 56);
		getContentPane().add(helpers);
		
		hurtby = new JTextArea();
		hurtby.setRows(2);
		hurtby.setLineWrap(true);
		hurtby.setBounds(93, 187, 249, 63);
		getContentPane().add(hurtby);
		
		days = new JFormattedTextField();
		days.setValue(new Integer(0));
		days.setBounds(149, 85, 46, 20);
		getContentPane().add(days);
		
		size = new JFormattedTextField();
		size.setValue(new Double (0.0));
		size.setBounds(299, 84, 46, 23);
		getContentPane().add(size);
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 288, 409, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("Add Plant");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						boolean veg = veggeHerb((String) vegge.getSelectedItem());
						CommonPlant newCommon = new CommonPlant(name.getText(), veg, (double)size.getValue(), (int)days.getValue());
						newCommon.addCompanion(helpers.getText());
						newCommon.addAnticompanion(hurtby.getText());
						System.out.println(newCommon.toString());
						GardenDB store = new GardenDB();
						store.addCommonPlant(newCommon);
						setVisible(false);

					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				dispose();
			}
		}
	}
	public boolean veggeHerb(String b){
		if(b == "Vegetable"){
			return true;
		}
		else if(b == "Herb"){
			return false;
		}
		else {
			System.out.println("Vegge/Herb Error");
			return false;
		}
	}

}
