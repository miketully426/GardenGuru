package ggPackage1;

import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.SwingConstants;

public class Encyclopedia extends JPanel {
	
	public JFrame frame;
	private JTable table;
	private JTextField name;
	private JTextField vegge;
	private DefaultTableModel tm;
	private JFormattedTextField days;
	private JFormattedTextField size;
	private JTextArea companions;
	private JTextArea anticompanions;
	private JButton btnMainMenu;
	private JButton btnAddNew;
	private ArrayList<CommonPlant> data;
	private JButton btnDeleteSelectedPlant;
	private GardenDB database;
	private JButton btnUpdate;

	/**
	 * Create the panel.
	 */
	public Encyclopedia() {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				tm.setRowCount(0);
				database = new GardenDB();
				ArrayList<CommonPlant> data = database.returnCommonPlants();
				createTable(data);
				table.repaint();
				table.revalidate();
			}
		});

		setLayout(null);

		
		database = new GardenDB();
		String columnname[] = {"Plants"};
		tm = new DefaultTableModel(columnname, 0);
		data = database.returnCommonPlants();
		createTable(data);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 133, 323);
		add(scrollPane);
		
		table = new JTable(tm);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		JButton btnSelect = new JButton("Display");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object veg = table.getValueAt(table.getSelectedRow(), 0);
				displayInfo(veg.toString(), data);
			}
		});
		btnSelect.setBounds(153, 119, 89, 40);
		add(btnSelect);
		
		name = new JTextField();
		name.setEditable(false);
		name.setText("Name");
		name.setBounds(243, 53, 86, 20);
		add(name);
		name.setColumns(10);
		
		vegge = new JTextField();
		vegge.setEditable(false);
		vegge.setBounds(356, 53, 86, 20);
		add(vegge);
		vegge.setColumns(10);
		
		JLabel lblTypicalDaysTo = new JLabel("Typical days to harvest: ");
		lblTypicalDaysTo.setBounds(272, 102, 170, 23);
		add(lblTypicalDaysTo);
		
		JLabel lblTypicalPlantsPer = new JLabel("Typical plants per sq/ft: ");
		lblTypicalPlantsPer.setBounds(272, 136, 170, 23);
		add(lblTypicalPlantsPer);
		
		JLabel lblCompanionPlants = new JLabel("Companion Plants: ");
		lblCompanionPlants.setBounds(153, 169, 109, 23);
		add(lblCompanionPlants);
		
		JLabel lblPlantsToAvoid = new JLabel("Plants to avoid:");
		lblPlantsToAvoid.setBounds(153, 258, 109, 23);
		add(lblPlantsToAvoid);
		
		days = new JFormattedTextField();
		days.setEditable(false);
		days.setBounds(456, 103, 48, 20);
		add(days);
		
		size = new JFormattedTextField();
		size.setEditable(false);
		size.setBounds(456, 139, 48, 20);
		add(size);
		
		companions = new JTextArea();
		companions.setEditable(false);
		companions.setBounds(163, 203, 300, 53);
		add(companions);
		
		anticompanions = new JTextArea();
		anticompanions.setEditable(false);
		anticompanions.setBounds(163, 281, 300, 53);
		add(anticompanions);
		
		btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

				
				
			}
		});
		btnMainMenu.setBounds(446, 0, 126, 23);
		add(btnMainMenu);
		
		btnAddNew = new JButton("Add New");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewCommonPlant newPlant = new NewCommonPlant();
				newPlant.setVisible(true);
				newPlant.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentHidden(ComponentEvent arg0) {
						tm.setRowCount(0);
						data = database.returnCommonPlants();
						createTable(data);
						table.repaint();
						table.revalidate();
						newPlant.dispose();
						
					}
				});
			}
		});
		btnAddNew.setBounds(153, 14, 89, 23);
		add(btnAddNew);
		
		JCheckBox chckbxEditEntry = new JCheckBox("Edit Entry");
		chckbxEditEntry.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(chckbxEditEntry.isSelected()){
				anticompanions.setEditable(true);
				companions.setEditable(true);
				name.setEditable(true);
				size.setEditable(true);
				days.setEditable(true);
				vegge.setEditable(true);
				btnDeleteSelectedPlant.setEnabled(true);
				btnUpdate.setEnabled(true);
				}
				else {
					anticompanions.setEditable(false);
					companions.setEditable(false);
					name.setEditable(false);
					size.setEditable(false);
					days.setEditable(false);
					vegge.setEditable(false);
					btnDeleteSelectedPlant.setEnabled(false);
					btnUpdate.setEnabled(false);
				}
			}
		});
		chckbxEditEntry.setBounds(272, 345, 97, 23);
		add(chckbxEditEntry);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CommonPlant newCommon = new CommonPlant(name.getText(), true, (double)size.getValue(), (int)days.getValue());
				newCommon.addCompanion(companions.getText());
				newCommon.addAnticompanion(anticompanions.getText());
				database.updateCommonPlant(newCommon);
				tm.setRowCount(0);
				data = database.returnCommonPlants();
				createTable(data);
				table.repaint();
				table.revalidate();
			}
		});
		btnUpdate.setBounds(378, 345, 89, 23);
		btnUpdate.setEnabled(false);
		add(btnUpdate);
		
		btnDeleteSelectedPlant = new JButton("<html><center>Delete<br> Selected<br> Plant");
		btnDeleteSelectedPlant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String itemName = (String) table.getValueAt(table.getSelectedRow(), 0);
				Object[] options = { "OK", "CANCEL" };
				Object answer = JOptionPane.showOptionDialog(null, "Are you sure you want to delete "+ itemName , "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				null, options, options[0]);
				
				if(answer == (Object) 0){
				database.removeCommon(itemName);
				tm.setRowCount(0);
				data = database.returnCommonPlants();
				createTable(data);
				table.repaint();
				table.revalidate();
				}
			}
		});
		btnDeleteSelectedPlant.setBounds(483, 233, 89, 70);
		btnDeleteSelectedPlant.setEnabled(false);
		add(btnDeleteSelectedPlant);
		
	}

	public void createTable(ArrayList<CommonPlant> plants){
		for(int i = 0; i < plants.size(); i++){
			String name = plants.get(i).getName();
			Object temp[] = {name};
			tm.addRow(temp);
		}
	}
	public void displayInfo(String plant, ArrayList<CommonPlant> data){
		boolean found = false;
		for(int i = 0; i < data.size(); i++){
			if(plant.equalsIgnoreCase(data.get(i).getName())){
				name.setText(data.get(i).getName());
				vegge.setText(data.get(i).vegeOrHerb());
				days.setValue((int) data.get(i).getDays());
				size.setValue((Double) data.get(i).getSize());
				companions.setText(data.get(i).getCompanion());
				anticompanions.setText(data.get(i).getAnticompanion());
				found = true;
			}
		}
		//for debugging
		if(!found){
			System.out.println("Plant not found");
		}
		
	}
	}


