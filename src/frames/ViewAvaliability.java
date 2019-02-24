package frames;

import java.awt.BorderLayout;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import resources.*;
import source.*;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewAvaliability extends JFrame {

	private JPanel contentPane;
	public int id_value ;
	public String vehicle_model;
	public String current_state;
	public String ac_value ; 
	public String return_date_value;
	public int dist_travled;
	public int amount_spent;
	ResultSet resultSet ; 
	Connection connection;
	Statement statement;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
					ViewAvaliability frame = new ViewAvaliability();
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
	public ViewAvaliability() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		String models[] = getModels();
		JLabel lblVehicleModel = new JLabel("Vehicle Model : ");
		lblVehicleModel.setBounds(12, 12, 114, 17);
		contentPane.add(lblVehicleModel);
		JComboBox modelValue = new JComboBox(models);
		modelValue.setBounds(12, 41, 168, 33);
		contentPane.add(modelValue);
		
		JCheckBox chckbxAc = new JCheckBox("AC");
		chckbxAc.setBounds(247, 44, 135, 26);
		contentPane.add(chckbxAc);
		

		
		
		
		JButton btnNewButton = new JButton("Get Results ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "" ; 
					String selected_model= models[modelValue.getSelectedIndex()];
					boolean isAC = chckbxAc.isSelected();
					connection = ConnectionHandler.createConnection();
					if(isAC == true)
						query = "select * from vehicle where vehicle_model = \"" + selected_model + "\" and ac = \"Y\"" ;
					else 
						query = "select * from vehicle where vehicle_model = \"" + selected_model + "\" and ac = \"N\"" ;
					statement = connection.createStatement();
					resultSet = statement.executeQuery(query);
					table.setModel(DbUtils.resultSetToTableModel(resultSet));
					resultSet.close();
					statement.close();
					connection.close();
				}catch(SQLException eq) {
					eq.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(506, 38, 112, 39);
		contentPane.add(btnNewButton); 
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CustomerMenu customerMenu = new CustomerMenu();
					customerMenu.setVisible(true);
					close();
				}catch(Exception tq) {
					tq.printStackTrace();
				}
			}
		});
		btnBack.setBounds(506, 315, 112, 39);
		contentPane.add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 86, 606, 225);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
	}
	public String[] getModels() {
		try {
			Connection connection = ConnectionHandler.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select model from vehicle_model");
			List<String> models  = new ArrayList<String>();
			while(resultSet.next()) {
				String temp = resultSet.getString("model");
				models.add(temp);
			}
			String[] model = models.stream().toArray(String[]::new);
			resultSet.close();
			statement.close();
			connection.close();
			return model;
		}catch(SQLException e) {
			System.out.println("Error : SQLException occured ");
			return null;
		}
		
	}
	public void close() {
		try {
			this.setVisible(false);
			this.dispose();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
