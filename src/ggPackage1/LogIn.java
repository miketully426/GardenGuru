package ggPackage1;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPasswordField;
import java.awt.Color;

public class LogIn extends JFrame {

	private JPanel contentPane;
	public JFrame frame;
	private GardenDB database = new GardenDB();
	private JComboBox usersel;
	private JPasswordField passwordtxt;
	private JTextField newUsertxt;
	private JButton btnExistingUser;
	private ArrayList<String> users = database.getUsers();
	private JLabel lblUsernameAlert;
	private JButton newUserbtn;
	private JPanel Login;
	private JButton btnCreateUser;
	private JButton btnLogin;
	private String user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogIn() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 723, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel MainMenu = new JPanel();

		contentPane.add(MainMenu, "name_1226007808784330");
		MainMenu.setLayout(null);
		
		JPanel Login = new JPanel();
		Login.setLayout(null);
		contentPane.add(Login, "name_241266752808218");
		MainMenu.setVisible(false);
		Login.setVisible(true);
		

		

		
		
		
		//main menu
		
		JLabel lblGardenGuru = new JLabel("Garden Guru");
		lblGardenGuru.setHorizontalAlignment(SwingConstants.CENTER);
		lblGardenGuru.setFont(new Font("Georgia", Font.PLAIN, 30));
		lblGardenGuru.setBounds(10, 11, 604, 59);
		MainMenu.add(lblGardenGuru);
		
		JButton btnGardenPlanner = new JButton("Garden Planner");
		btnGardenPlanner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Planner planno = new Planner(user);
				contentPane.add(planno, "name_232332");
				planno.setVisible(true);
				MainMenu.setVisible(false);
				//planno.addComponentListener(new ComponentAdapter() {
					//@Override
					//public void componentHidden(ComponentEvent arg0) {
						//MainMenu.setVisible(true);
					//}
					//});
			}
			});
			
				
		btnGardenPlanner.setFont(new Font("Georgia", Font.PLAIN, 20));
		btnGardenPlanner.setBounds(214, 200, 188, 59);
		MainMenu.add(btnGardenPlanner);
		

		
		JButton btnInfoCenter = new JButton("Info Center");
		btnInfoCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Encyclopedia info = new Encyclopedia();
				contentPane.add(info, "name_651651");
				info.setVisible(true);
				MainMenu.setVisible(false);
				info.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentHidden(ComponentEvent arg0) {
						MainMenu.setVisible(true);
						
					}
				});

			}
		});
		
		
		
		btnInfoCenter.setFont(new Font("Georgia", Font.PLAIN, 20));
		btnInfoCenter.setBounds(214, 270, 188, 59);
		MainMenu.add(btnInfoCenter);
		
		JLabel lblMikeTully = new JLabel("\u00A9 Mike Tully 2016");
		lblMikeTully.setBounds(265, 391, 117, 25);
		MainMenu.add(lblMikeTully);
		
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(265, 340, 106, 39);
		MainMenu.add(btnNewButton);
		
		JLabel lblHello = new JLabel("Hello, ");
		lblHello.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHello.setBounds(214, 102, 57, 39);
		MainMenu.add(lblHello);
		
		JLabel userDisplay = new JLabel("");
		userDisplay.setFont(new Font("Tahoma", Font.PLAIN, 16));
		userDisplay.setBounds(265, 102, 76, 39);
		MainMenu.add(userDisplay);
		

		

		
		JLabel label = new JLabel("Garden Guru");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Georgia", Font.PLAIN, 30));
		label.setBounds(10, 11, 604, 59);
		Login.add(label);
		
		JLabel label_1 = new JLabel("User: ");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(184, 82, 55, 26);
		Login.add(label_1);
		
		JLabel label_2 = new JLabel("Password:");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_2.setBounds(158, 119, 81, 14);
		Login.add(label_2);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				user = (String) usersel.getSelectedItem();
				if(database.checkPW(user).equals(passwordtxt.getText())){
				MainMenu.setVisible(true);
				Login.setVisible(false);
				userDisplay.setText(user);
				}
				else {
					JOptionPane.showMessageDialog(frame, "Invalid Password");
				}
			}
		});
		btnLogin.setBounds(265, 148, 117, 26);
		Login.add(btnLogin);
		

		
		btnCreateUser = new JButton("Create User");
		btnCreateUser.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				if(!checkUser(newUsertxt.getText())){
				database.addUser(newUsertxt.getText(), passwordtxt.getText());
				usersel.addItem(newUsertxt.getText());
				reset();
				}
				else{
					lblUsernameAlert.setVisible(true);
					Login.repaint();
					Login.revalidate();
				}
			}
		});
		btnCreateUser.setBounds(265, 148, 117, 26);
		Login.add(btnCreateUser);
		btnCreateUser.setVisible(false);
		
		newUsertxt = new JTextField();
		newUsertxt.setColumns(10);
		newUsertxt.setBounds(265, 81, 117, 20);
		Login.add(newUsertxt);
		newUsertxt.setVisible(false);
		
		newUserbtn = new JButton("New User");
		newUserbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				usersel.setVisible(false);
				newUsertxt.setVisible(true);
				newUserbtn.setVisible(false);
				btnExistingUser.setVisible(true);
				btnCreateUser.setVisible(true);
				btnLogin.setVisible(false);
				Login.repaint();
				Login.revalidate();
			}
		});
		newUserbtn.setBounds(392, 81, 106, 23);
		Login.add(newUserbtn);
		
		JLabel label_3 = new JLabel("\u00A9 Mike Tully 2016");
		label_3.setBounds(265, 391, 117, 25);
		Login.add(label_3);
		
		JButton loginExit = new JButton("Exit");
		loginExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		loginExit.setBounds(265, 340, 106, 39);
		Login.add(loginExit);
		
		
		
		
		usersel = new JComboBox();
		usersel.setBounds(265, 81, 117, 20);
		users = database.getUsers();
		for(int i = 0; i < users.size(); i++){
			usersel.addItem(users.get(i));
		}
		Login.add(usersel);
		
		passwordtxt = new JPasswordField();
		passwordtxt.setColumns(10);
		passwordtxt.setBounds(265, 117, 117, 20);
		Login.add(passwordtxt);
		

		
		btnExistingUser = new JButton("Existing User");
		btnExistingUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnExistingUser.setBounds(392, 117, 143, 23);
		Login.add(btnExistingUser);
		btnExistingUser.setVisible(false);
		
		lblUsernameAlert = new JLabel("Username already in use. Please try again.");
		lblUsernameAlert.setForeground(Color.RED);
		lblUsernameAlert.setFont(new Font("Arial", Font.PLAIN, 14));
		lblUsernameAlert.setBounds(184, 185, 314, 26);
		Login.add(lblUsernameAlert);
		lblUsernameAlert.setVisible(false);
		
	}	
	
	private boolean checkUser(String user){
		for(int i = 0; i < users.size(); i++){
			if(user.equals(users.get(i))){
				return true;
			}
		}
		return false;
	}
	private void reset(){
		lblUsernameAlert.setVisible(false);
		usersel.setVisible(true);
		newUsertxt.setVisible(false);
		newUserbtn.setVisible(true);
		btnExistingUser.setVisible(false);
		btnCreateUser.setVisible(false);
		btnLogin.setVisible(true);
		passwordtxt.setText("");
		repaint();
		revalidate();
	}
}
		

