package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import resources.*;
import source.*;
public class ReturnRepairVehicle extends JFrame {

	private JPanel contentPane;
	private JTextField cost;
	private String modelName = "" ; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
					ReturnRepairVehicle frame = new ReturnRepairVehicle(Employee.INSTANCE);
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
	public ReturnRepairVehicle(Employee employee) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel idVal = new JLabel("");
		idVal.setBounds(30, 149, 233, 15);
		contentPane.add(idVal);
		
		JLabel model = new JLabel("");
		model.setBounds(30, 176, 233, 15);
		contentPane.add(model);
		
		JLabel currentState = new JLabel("");
		currentState.setBounds(30, 201, 233, 15);
		contentPane.add(currentState);
		
		JLabel ac = new JLabel("");
		ac.setBounds(30, 228, 233, 15);
		contentPane.add(ac);
		
		JLabel retrun_date = new JLabel("");
		retrun_date.setBounds(30, 255, 233, 15);
		contentPane.add(retrun_date);
		
		JLabel distanceTraveled = new JLabel("");
		distanceTraveled.setBounds(30, 282, 233, 15);
		contentPane.add(distanceTraveled);
		
		JLabel amountSpent = new JLabel("");
		amountSpent.setBounds(30, 309, 233, 15);
		contentPane.add(amountSpent);
		
		JLabel lblVehicleId = new JLabel("Vehicle ID");
		lblVehicleId.setBounds(12, 45, 143, 15);
		contentPane.add(lblVehicleId);
		
		
		cost = new JTextField();
		cost.setBounds(253, 69, 143, 32);
		contentPane.add(cost);
		cost.setColumns(10);
		String[] activeVehicles = getRepairVehicleID();
		JComboBox ids = new JComboBox(activeVehicles);
		ids.setBounds(12, 72, 171, 24);
		contentPane.add(ids);
		
		JButton btnGetInfo = new JButton("Get Info ");
		btnGetInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String idValue = (activeVehicles[ids.getSelectedIndex()]);
					Connection connection = ConnectionHandler.createConnection();
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("select * from vehicle where id = " + idValue + " ");
					while(resultSet.next()) {
						idVal.setText("Vehicle ID : " + Integer.toString(resultSet.getInt("id")));
						model.setText("Vehicle Model : " + resultSet.getString("vehicle_model"));
						modelName =  resultSet.getString("vehicle_model");
						currentState.setText("Vehicle Current State : " + resultSet.getString("current_state"));
						ac.setText("Vehicle AC : " + resultSet.getString("ac"));
//						if(resultSet.getString("ac").equals("Y") == true) {
//							acval = true; 
//						}
//						else 
//							acval = false;
						retrun_date.setText("Vehicle Return Date : " + resultSet.getString("return_date"));
						distanceTraveled.setText("Vehicle Distance Travelled : " + Integer.toString(resultSet.getInt("dist_travled")));
						amountSpent.setText("Amount Spend : " + Integer.toString(resultSet.getInt("amount_spent")));
						//btnSell.setEnabled(false);
					}
				}catch(Exception eq) {
					eq.printStackTrace();
				}
			}
		});
		btnGetInfo.setBounds(486, 72, 110, 25);
		contentPane.add(btnGetInfo);
		
		JButton btnReturned = new JButton("Returned ");
		btnReturned.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection connection = ConnectionHandler.createConnection();
					String update = "update vehicle set current_state = ? , amount_spent = amount_spent + ?   where id = ?";
					PreparedStatement vehicleStatement = connection.prepareStatement(update);
					vehicleStatement.setString(1, "IDLE");
					vehicleStatement.setInt(2, Integer.parseInt(cost.getText()));
					vehicleStatement.setInt(3,Integer.parseInt(activeVehicles[ids.getSelectedIndex()]));
					vehicleStatement.execute();
					
					vehicleStatement.close();
					
					update = "update transaction set total_amount = ? where total_amount = 0 and type = ? and vehicle_id = ? ";
					PreparedStatement transactionStatement = connection.prepareStatement(update);
					transactionStatement.setInt(1, Integer.parseInt(cost.getText()));
					transactionStatement.setString(2, "REPAIR");
					transactionStatement.setInt(3, Integer.parseInt(activeVehicles[ids.getSelectedIndex()]));
					transactionStatement.execute();
					JOptionPane.showMessageDialog(null, "Returned from Repair!!");
					transactionStatement.close();
					connection.close();
				}catch(SQLException eq) {
					eq.printStackTrace();
				}
				EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
				employeeMenu.setVisible(true);
				close();
			}
		});
		btnReturned.setBounds(341, 329, 110, 25);
		contentPane.add(btnReturned);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
				employeeMenu.setVisible(true);
				close();
			}
		});
		btnClose.setBounds(508, 329, 110, 25);
		contentPane.add(btnClose);
		
		JLabel lblCost = new JLabel("Cost");
		lblCost.setBounds(254, 45, 66, 15);
		contentPane.add(lblCost);
	}
	public String[] getRepairVehicleID() {
		try {
			Connection connection = ConnectionHandler.createConnection();
			Statement statement = connection.createStatement();
			String request = "select id from vehicle where current_state = \"REPAIR\"";
			ResultSet resultSet = statement.executeQuery(request);
			List <String> list = new ArrayList<String>();
			while(resultSet.next()) {
				list.add(Integer.toString(resultSet.getInt("id")));
			}
			String []ids = list.stream().toArray(String[]::new);
			resultSet.close();
			statement.close();
			return ids;
		}catch(SQLException eq) {
			eq.printStackTrace();
			System.out.println("Error : SQLException occured in VehicleReturn.getActiveVehicleID()");
			return null;
		}
	}
	public void close() {
		try {
			this.setVisible(false);
			this.dispose();
		}catch (Exception eq) {
			System.out.println("Error : SQLException occured in EmployeeeSignUp.close()");
		}
		
	}
	
}
