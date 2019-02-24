package frames;

import java.awt.BorderLayout;



import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.border.EmptyBorder;

import resources.ConnectionHandler;

import java.sql.*;
import javax.swing.*;
import resources.*;
import source.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
public class PurchaseVehicle extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	JLabel label;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
					PurchaseVehicle frame = new PurchaseVehicle(null);
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
	public PurchaseVehicle(Employee employee) {
		
		setTitle("Pruchase Vehicle ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Model : ");
		lblNewLabel.setBounds(31, 46, 112, 17);
		contentPane.add(lblNewLabel);
		label = new JLabel("");
		label.setBounds(31, 217, 161, 39);
		JLabel lblVehicleId = new JLabel("Vehicle ID :");
		lblVehicleId.setBounds(31, 120, 161, 17);
		contentPane.add(lblVehicleId);
		String models[] = getModels();
		JComboBox model = new JComboBox(models);
		model.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					Connection connection = ConnectionHandler.createConnection();
					String modelName = models[model.getSelectedIndex()];
					String query = "select cost from vehicle_model where model = \"" + modelName + "\"" ;
					PreparedStatement insertVehicle = connection.prepareStatement(query);
					ResultSet resultSet = insertVehicle.executeQuery(query);
					int cost  = 0 ; 
					while(resultSet.next()) {
						cost = resultSet.getInt("cost");
					}
					label.setText("Cost : " + Integer.toString(cost));
					resultSet.close();
					insertVehicle.close();
					connection.close();
				}catch(Exception eq) {
					eq.printStackTrace();
				}
			}
		});
		contentPane.add(label);
		model.setBounds(31, 75, 161, 33);
		contentPane.add(model);
		
		id = new JTextField();
		id.setBounds(31, 149, 161, 33);
		contentPane.add(id);
		id.setColumns(10);
		
		JCheckBox ac = new JCheckBox("AC");
		ac.setBounds(229, 78, 135, 26);
		contentPane.add(ac);
		
		JButton addVehicleModel = new JButton("New Vehicle Model");
		addVehicleModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNewModel addNewModel = new AddNewModel(Employee.INSTANCE);
				addNewModel.setVisible(true);
				close();
			}
		});
		addVehicleModel.setBounds(420, 72, 178, 39);
		contentPane.add(addVehicleModel);
		
		JButton purchase = new JButton("Puchase ");
		purchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String modelName = models[model.getSelectedIndex()];
				
				if(id.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter Valid ID");
					return ;
				}
				int vehicleID = Integer.parseInt(id.getText());
				boolean isAC = ac.isSelected();
				
				int errpr = addVehicle(modelName,vehicleID,isAC,Employee.INSTANCE);
				if(errpr == -1 ) {
					id.setText("");
					JOptionPane.showMessageDialog(null, "Vehicle ID already exist !!");
				}
				else if(errpr == -2 ) {
					id.setText("");
					JOptionPane.showMessageDialog(null, "SQLException Occured !! ");
				}
				else if(errpr == 0) {
					id.setText("");
					System.out.println("Ghar pauch gaye hai hum");
					JOptionPane.showMessageDialog(null, "Vehicle Added !! ");
					PurchseSellVehicle purchaseSellVehicle = new PurchseSellVehicle(Employee.INSTANCE);
					purchaseSellVehicle.setVisible(true);
					close();
				}
			}
		});
		purchase.setBounds(348, 315, 112, 39);
		contentPane.add(purchase);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PurchseSellVehicle purchaseSellVehicle = new PurchseSellVehicle(Employee.INSTANCE);
				purchaseSellVehicle.setVisible(true);
				close();
			}
		});
		back.setBounds(486, 315, 112, 39);
		contentPane.add(back);
		
		
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
	public int addVehicle(String modelName,int vehicleID,boolean ac,Employee employee) {
		try {
			Connection connection = ConnectionHandler.createConnection();
			Statement statement = connection.createStatement();
			String query  = "select id from vehicle" ; 
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				int getId = resultSet.getInt("id");
				if(getId == vehicleID) {
					resultSet.close();
					statement.close();
					connection.close();
					return -1 ;
				}
			}
			resultSet.close();
			statement.close();
			String[] months = {"January","February","March","April","May","June","July","August","Septmber","October","November","December"};
			query = "insert into vehicle (id,vehicle_model,current_state,ac,return_date,dist_travled,amount_spent) values(?,?,?,?,NULL,NULL,NULL)";
			PreparedStatement insertVehicle = connection.prepareStatement(query);
			insertVehicle.setInt(1, vehicleID);
			insertVehicle.setString(2,modelName);
			insertVehicle.setString(3, "IDLE");
			if(ac == true)
				insertVehicle.setString(4, "Y");
			else 
				insertVehicle.setString(4, "N");
			insertVehicle.execute();
			insertVehicle.close();
			
			query = "select cost from vehicle_model where model = \"" + modelName + "\"" ;
			insertVehicle = connection.prepareStatement(query);
			resultSet = insertVehicle.executeQuery(query);
			int cost  = 0 ; 
			while(resultSet.next()) {
				cost = resultSet.getInt("cost");
			}
			resultSet.close();
			insertVehicle.close();
			
			query = "insert into transaction (employee_id,vehicle_id,advance_payment,total_amount,date_lended,date_return,type) value (?,?,0,?,?,NULL,?)";
			insertVehicle = connection.prepareStatement(query);
			insertVehicle.setInt(1, Employee.INSTANCE.getID());
			insertVehicle.setInt(2, vehicleID);
			insertVehicle.setInt(3, cost);
			insertVehicle.setString(4, Integer.toString(Calendar.getInstance().get(Calendar.DATE)) + "-" + 
						months[Calendar.getInstance().get(Calendar.MONTH)] + "-" + 
						Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
			insertVehicle.setString(5,"PURCHASE");
			insertVehicle.execute();
			insertVehicle.close();
			
			if(ac == true) {
				query = "update vehicle_model set ac = ac + 1, number = number + 1 where model = ? ";
				insertVehicle = connection.prepareStatement(query);
				insertVehicle.setString(1, modelName);
				insertVehicle.execute();
				insertVehicle.close();
			}
			else {
				query = "update vehicle_model set nac = nac + 1 , number = number + 1 where model = ? ";
				insertVehicle = connection.prepareStatement(query);
				insertVehicle.setString(1, modelName);
				insertVehicle.execute();
				insertVehicle.close();
			}
			connection.close();
			return 0;
		}catch(SQLException sqlException) {
			sqlException.printStackTrace();
			return -2 ;	
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
