package ggPackage1;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JSeparator;

public class Planner extends JPanel {

	private JTable table_1;
	public String username;
	private GardenDB database = new GardenDB();
	private JComboBox bedSelect;
	private double bedsize;
	private JLabel lblNewLabel_1;
	private double takenspace;
	private double spaceremain;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private JLabel lblSpaceRemaining;
	

	/**
	 * Create the panel.
	 */
	public Planner(String username) {
		setLayout(null);
		
		String[] columnNames = {"Plant", "Common", "Number", "Space", "Planted", "Est. Mature"}; 

		
		JLabel userselect = new JLabel(username);
		userselect.setBounds(43, 15, 125, 20);
		ArrayList<Bed> beds = database.getBeds(username);
		
		
		
		bedSelect = new JComboBox();
		bedSelect.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bedSelect.setBounds(46, 33, 128, 23);
		fillBedSelect(username);
		add(bedSelect);
		bedSelect.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ArrayList<Bed> bed2 = database.getBeds((String)userselect.getText());
				bedsize=getBedSize(bed2,(String)bedSelect.getSelectedItem());
				lblNewLabel_1.setText("Total size: "+bedsize+"sqft");
				takenspace = 0;
				tableModel.setRowCount(0);
				ArrayList<Plant> tabledata2 = database.returnBedPlants((String)userselect.getText(),(String)bedSelect.getSelectedItem());
				fillTable(tabledata2);
				setSpaceRemain();
			
			}
		});
		tableModel = new DefaultTableModel(columnNames, 0); 
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(223, 11, 450, 392);
		add(scrollPane);
		
		ArrayList<Plant> tabledata = database.returnBedPlants((String)userselect.getText(),(String)bedSelect.getSelectedItem());
		fillTable(tabledata);
		
		table_1 = new JTable(tableModel);
		table_1.setFillsViewportHeight(true);
		scrollPane.setViewportView(table_1);
		

		
		
		
		JLabel lblBed = new JLabel("Bed:");
		lblBed.setFont(new Font("Georgia", Font.PLAIN, 14));
		lblBed.setBounds(10, 37, 61, 17);
		add(lblBed);
		
		JButton createNewBed = new JButton("Create New Bed");
		createNewBed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewBed addBed = new NewBed(username);
				scrollPane.setViewportView(addBed);
				addBed.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentHidden(ComponentEvent arg0) {
						scrollPane.setViewportView(table_1);	
						bedSelect.removeAllItems();
						fillBedSelect(username);
						ArrayList<Bed> beds = database.getBeds(username);
						bedSelect.setSelectedIndex(beds.size() - 1);
						addBed.invalidate();
					}
				});
			}
				//if(bednameinput.getText().length() > 1){
					//double size = (Double)size1.getValue() * (Double)size2.getValue();
					//database.addBed((String)userselect.getText(), bednameinput.getText(), size);
					//bedSelect.removeAllItems();
					//fillBedSelect(username);
					//bedSelect.setSelectedItem((Object) bednameinput.getText());
					//bednameinput.setText("");
					
				//}
				//else {
					//JOptionPane.showMessageDialog(scrollPane, "Please name your bed.");
				//}
				
						
			
		});
		createNewBed.setBounds(10, 77, 158, 23);
		add(createNewBed);
		
		JButton addNewPlant = new JButton("Add Plant to Bed");
		addNewPlant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewPlant addPlant = new NewPlant(username, (String) bedSelect.getSelectedItem());
				scrollPane.setViewportView(addPlant);
				addPlant.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentHidden(ComponentEvent arg0) {
						scrollPane.setViewportView(table_1);	
						ArrayList<Plant> tabledata = database.returnBedPlants((String)userselect.getText(),(String)bedSelect.getSelectedItem());
						fillTable(tabledata);
						addPlant.invalidate();
						setSpaceRemain();
					}
				});
			}
		});
		addNewPlant.setBounds(10, 168, 192, 52);
		add(addNewPlant);
		
		JButton menu = new JButton("Return to Main Menu");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
			}
		});
		menu.setBounds(10, 351, 192, 52);
		add(menu);
		
		JLabel lblUsername = new JLabel(username);
		lblUsername.setBounds(46, 12, 125, 14);
		add(lblUsername);
		
		JLabel lblUser = new JLabel("User: ");
		lblUser.setBounds(10, 12, 46, 14);
		add(lblUser);
		
		bedsize = getBedSize(beds,(String)bedSelect.getSelectedItem());
		lblNewLabel_1 = new JLabel("Total size: "+bedsize+"sqft");
		lblNewLabel_1.setBounds(10, 111, 192, 23);
		add(lblNewLabel_1);
		
		
		lblSpaceRemaining = new JLabel();
		lblSpaceRemaining.setBounds(10, 134, 192, 23);
		setSpaceRemain();
		add(lblSpaceRemaining);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 64, 154, 2);
		add(separator);
		
		JButton btnDeleteCurrentBed = new JButton("Delete Bed");
		btnDeleteCurrentBed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[] options = { "OK", "CANCEL" };
				Object answer = JOptionPane.showOptionDialog(null, "Are you sure you want to delete "+ (String) bedSelect.getSelectedItem() , "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				null, options, options[0]);
				
				if(answer == (Object) 0){
					database.removeBed(username, (String) bedSelect.getSelectedItem());
					bedSelect.removeAllItems();
					fillBedSelect(username);
					refresh();
				}
				
			}
		});
		btnDeleteCurrentBed.setBounds(10, 265, 128, 23);
		add(btnDeleteCurrentBed);
		
		JButton btnDeletePlant = new JButton("Delete Plant");
		btnDeletePlant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object veg = table_1.getValueAt(table_1.getSelectedRow(), 0);
				String vegName = veg.toString();
				Object[] options = { "OK", "CANCEL" };
				Object answer = JOptionPane.showOptionDialog(null, "Are you sure you want to delete "+ vegName , "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				null, options, options[0]);
				
				if(answer == (Object) 0){
					database.removePlant(username, (String) bedSelect.getSelectedItem(), (String) table_1.getValueAt(table_1.getSelectedRow(), 0));
					ArrayList<Plant> tabledata = database.returnBedPlants((String)userselect.getText(),(String)bedSelect.getSelectedItem());
					fillTable(tabledata);
					setSpaceRemain();
				}
				
			}
		});
		btnDeletePlant.setBounds(10, 231, 128, 23);
		add(btnDeletePlant);
		
	}
		public Object[] dataPlant(Plant temp){
			String name = temp.getName();
			String type = "V";
			int number = 4;
			double space = temp.getSpace();
			String planted = temp.getPlanted();
			String mature = temp.getMature();
			System.out.println(username);
			Object[] data2 = {name, type, number, space, planted, mature};
			return data2;
		}
		public void refresh(){
			revalidate();
			repaint();
		}
	public void setUsername(String user){
		username = user;
	}
	private void setSpaceRemain(){
		spaceremain = bedsize - takenspace;
		lblSpaceRemaining.setText("Space remaining: "+spaceremain+"sqft");
		if(spaceremain < 0){
			lblSpaceRemaining.setForeground(Color.red);
		}
		else {
			lblSpaceRemaining.setForeground(Color.black);
		}
	}
	public double getBedSize(ArrayList<Bed> beds, String bedname){
		double size = 0;
		for(int i = 0; i < beds.size(); i++){
			Bed temp = beds.get(i);
			if(bedname == null || temp.getName() == null){
				return 0;
			}
			if(bedname.equalsIgnoreCase(temp.getName())){
				size = temp.getSize();
			}
		}
		return size;
	}
	
	private void fillBedSelect(String username){
		ArrayList<Bed> beds = database.getBeds(username);
		for(int i = 0; i < beds.size(); i++){
			bedSelect.addItem(beds.get(i).getName());
		}
	}
	
	private void fillTable(ArrayList<Plant> plants){
		tableModel.setRowCount(0);
		takenspace = 0;
		for(int i = 0; i < plants.size(); i++){
			Plant temp = plants.get(i);
			
			String name = temp.getName();
			String common = temp.getFamily();
			int numplanted = temp.getNumberPlanted();
			String planted = temp.getPlanted();
			double space = (1/temp.getSpace()) * numplanted;
			String mature = temp.getMature();
			Object[] data2 = {name, common, numplanted, space, planted, mature};
			tableModel.addRow(data2);
			takenspace = takenspace + space;
	}
	}
}
