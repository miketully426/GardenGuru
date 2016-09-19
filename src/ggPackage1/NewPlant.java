package ggPackage1;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class NewPlant extends JPanel {
	
	public JFrame frame;
	private JTextField txtplantName;
	private JTextField dateplanted;
	private JLabel lblMature;
	private GardenDB database = new GardenDB();
	private JFormattedTextField DaysField;
	private JFormattedTextField SizeField;
	private JComboBox<String> commonbox;
	//private JComboBox bedbox;
	private JTextField bedbox;
	private JFormattedTextField numplantedfield;
	private JComboBox vegge;



	/**
	 * Create the dialog.
	 */
	public NewPlant(String user, String bed) {
		setBounds(100, 100, 399, 392);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Plant:");
		lblNewLabel.setBounds(10, 60, 50, 26);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Common Type: ");
		lblNewLabel_1.setBounds(10, 97, 89, 28);
		add(lblNewLabel_1);
		
		DaysField = new JFormattedTextField();
		DaysField.setValue(new Integer(0));
		DaysField.setBounds(126, 197, 46, 28);
		add(DaysField);
		
		JLabel lblNewLabel_2 = new JLabel("Days to Maturity");
		lblNewLabel_2.setBounds(10, 197, 100, 28);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Plants per sq/ft: ");
		lblNewLabel_3.setBounds(10, 158, 94, 28);
		add(lblNewLabel_3);
		
		txtplantName = new JTextField();
		txtplantName.setText("(Plant Name)");
		txtplantName.setBounds(52, 63, 120, 20);
		add(txtplantName);
		txtplantName.setColumns(10);
		
		SizeField = new JFormattedTextField();
		SizeField.setValue(new Double(0.0));
		SizeField.setBounds(126, 162, 50, 20);
		add(SizeField);

		
		JButton btnCreate = new JButton("Create!");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!checkDate(dateplanted.getText())){
					JOptionPane.showMessageDialog(frame, "Please enter a valid date(MM/DD/YYYY).");
				}
				else{
				Plant temp = new Plant(txtplantName.getText(), (String)commonbox.getSelectedItem(), toVegge((String)vegge.getSelectedItem()), (int) DaysField.getValue(), 
						dateplanted.getText(), (Double)SizeField.getValue(), (int)numplantedfield.getValue());
						database.addPlant(temp, user, (String)bedbox.getText());
				setVisible(false);
				}

			}
		});
		btnCreate.setBounds(10, 318, 120, 53);
		add(btnCreate);
		
		JLabel lblPlanted = new JLabel("Date Planted: ");
		lblPlanted.setBounds(11, 250, 99, 17);
		add(lblPlanted);
		
		dateplanted = new JTextField();
		dateplanted.setText(currentDate());
		dateplanted.setBounds(94, 248, 100, 20);
		add(dateplanted);
		dateplanted.setColumns(10);
		
		ArrayList<CommonPlant> common = database.returnCommonPlants();
		
		commonbox = new JComboBox<String>();
		commonbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				CommonPlant displayPlant = getPlantData((String) commonbox.getSelectedItem(), common);
				DaysField.setValue(displayPlant.getDays());
				SizeField.setValue(displayPlant.getSize());
				txtplantName.setText((String) commonbox.getSelectedItem());
			}
		});
		commonbox.setBounds(113, 101, 120, 20);
		
		for(int i = 0; i < common.size(); i++){
			commonbox.addItem(common.get(i).getName());
		}
		add(commonbox);
		
		JButton filldefault = new JButton("Close");
		filldefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		filldefault.setBounds(291, 325, 106, 46);
		add(filldefault);

		
		//ArrayList<Bed> beds = database.getBeds(user);
		
		JLabel lblBed = new JLabel("Bed: ");
		lblBed.setBounds(10, 11, 46, 26);
		add(lblBed);
		//bedbox = new JComboBox();
		//bedbox.setBounds(66, 14, 106, 20);
		//for(int i = 0; i < beds.size(); i++){
		//	bedbox.addItem(beds.get(i).getName());
		//}
		//add(bedbox);
		
		bedbox = new JTextField(bed);
		bedbox.setBounds(66, 14, 106, 20);
		add(bedbox);
		
		JLabel lblNumberPlanted = new JLabel("Number Planted: ");
		lblNumberPlanted.setBounds(10, 131, 100, 21);
		add(lblNumberPlanted);
		
		numplantedfield = new JFormattedTextField();
		numplantedfield.setValue(new Integer(1));
		numplantedfield.setBounds(126, 131, 46, 20);
		add(numplantedfield);
		
		vegge = new JComboBox();
		vegge.setBounds(193, 60, 89, 26);
		vegge.addItem("Vegetable");
		vegge.addItem("Herb");
		add(vegge);
		
		JLabel usertest = new JLabel(user);
		usertest.setBounds(177, 327, 65, 34);
		add(usertest);
	}
	
	public CommonPlant getPlantData(String plant, ArrayList<CommonPlant> data){
		boolean found = false;
		CommonPlant reply = new CommonPlant("Fail", false, 0, 0);
		for(int i = 0; i < data.size(); i++){
			if(plant.equalsIgnoreCase(data.get(i).getName())){
				reply = data.get(i);
				found = true;
			}
		}
		if(!found){
			System.out.println("Didn't find plant");
		}
		return reply;
	}
	public boolean checkDate(String date){
		Calendar c = Calendar.getInstance();
		String format = "MM/dd/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			c.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;	
	}
	public String currentDate(){
		Calendar c = Calendar.getInstance();
		String format = "MM/dd/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(c.getTime());		
	}
	public boolean toVegge(String b){
		if(b.equalsIgnoreCase("vegetable")){
			return true;
		}
		else {
			return false;
		}
	}

	}

