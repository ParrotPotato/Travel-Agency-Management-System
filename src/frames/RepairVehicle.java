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
public class RepairVehicle extends JFrame {

	private JPanel contentPane;
	private boolean acval = false;
	private String modelName = "";
	private boolean taskComplete = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
					RepairVehicle frame = new RepairVehicle(Employee.INSTANCE);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public boolean getTaskStatus() {
		return taskComplete;
	}
	/**
	 * Create the frame.
	 */
	public RepairVehicle(Employee employee) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		String[] ids = getID();
		
		JComboBox id = new JComboBox(ids);
		id.setBounds(30, 101, 187, 24);
		contentPane.add(id);
		
		
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
		JLabel lblVehicleId = new JLabel("Vehicle ID :");
		lblVehicleId.setBounds(30, 76, 171, 15);
		contentPane.add(lblVehicleId);
		JButton btnRepair = new JButton("Repair");btnRepair.setEnabled(false);

		JButton btnGetDetails = new JButton("Get Details");
		btnGetDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String idValue = (ids[id.getSelectedIndex()]);
					Connection connection = ConnectionHandler.createConnection();
					Statement statement = connection.createStatement();
					boolean status = false ;
					ResultSet resultSet = statement.executeQuery("select * from vehicle where id = " + idValue + " and current_state = \"IDLE\"");
					//System.out.println("Getting in the getDetails");
					while(resultSet.next()) {
						status = true ;
						btnRepair.setEnabled(true);
						idVal.setText("Vehicle ID : " + Integer.toString(resultSet.getInt("id")));
						model.setText("Vehicle Model : " + resultSet.getString("vehicle_model"));
						modelName =  resultSet.getString("vehicle_model");
						currentState.setText("Vehicle Current State : " + resultSet.getString("current_state"));
						ac.setText("Vehicle AC : " + resultSet.getString("ac"));
						if(resultSet.getString("ac").equals("Y") == true) {
							acval = true; 
						}
						else 
							acval = false;
						retrun_date.setText("Vehicle Return Date : " + resultSet.getString("return_date"));
						distanceTraveled.setText("Vehicle Distance Travelled : " + Integer.toString(resultSet.getInt("dist_travled")));
						amountSpent.setText("Amount Spend : " + Integer.toString(resultSet.getInt("amount_spent")));
						//btnSell.setEnabled(false);
					}
					resultSet.close();
					statement.close();
					connection.close();
					if(status == false) {
						JOptionPane.showMessageDialog(null, "Vehicle ID : " + idValue + " not in idle state ");
					}
				}catch(Exception eq) {
					eq.printStackTrace();
				}
				
			}
		});
		btnGetDetails.setBounds(480, 101, 110, 25);
		contentPane.add(btnGetDetails);
		
		btnRepair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String idValue = (ids[id.getSelectedIndex()]);
					Connection connection = ConnectionHandler.createConnection();
					String request = "update vehicle set current_state = \"REPAIR\" where id = " + idValue + " "; 
					PreparedStatement pre = connection.prepareStatement(request);
					pre.execute();
					pre.close();
					
					request = "select cost from vehicle_model where model =\"" + modelName + "\"";
					
					//int costValue = Integer.parseInt(cost.getText()); 
					String[] months = {"January","February","March","April","May","June","July","August","Septmber","October","November","December"};  //   | to be changed 
					String query = "insert into transaction (employee_id,vehicle_id,advance_payment,total_amount,date_lended,date_return,type) value (?,?,0,0,?,NULL,?)";
					PreparedStatement insertVehicle = connection.prepareStatement(query);
					insertVehicle.setInt(1, Employee.INSTANCE.getID());
					insertVehicle.setInt(2, Integer.parseInt(idValue));
					insertVehicle.setString(3, Integer.toString(Calendar.getInstance().get(Calendar.DATE)) + "-" + 
											months[Calendar.getInstance().get(Calendar.MONTH)] + "-" + 
											Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
					insertVehicle.setString(4,"REPAIR");
					insertVehicle.execute();
					insertVehicle.close();
					connection.close();
					JOptionPane.showMessageDialog(null, "Sent for Repair!!");
					PurchseSellVehicle purchseSellVehicle = new PurchseSellVehicle(Employee.INSTANCE);
					purchseSellVehicle.setVisible(true);
					EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
					employeeMenu.setVisible(true);
					taskComplete  = true;
					close();
				}catch(Exception eq) {
					eq.printStackTrace();
				}
			}
		});
		btnRepair.setBounds(373, 329, 110, 25);
		contentPane.add(btnRepair);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
				employeeMenu.setVisible(true);
				close();
			}
		});
		btnCancel.setBounds(508, 329, 110, 25);
		contentPane.add(btnCancel);
		
		
	}
	public String[] getID() {
		try {
			System.out.println("Entering Repair function!!");
			Connection connection = ConnectionHandler.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select id from vehicle");
			List<String> models  = new ArrayList<String>();
			while(resultSet.next()) {
				String temp = Integer.toString(resultSet.getInt("id"));
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
		}catch (Exception eq) {
			System.out.println("Error : SQLException occured in EmployeeeSignUp.close()");
		}
		
	}
}
