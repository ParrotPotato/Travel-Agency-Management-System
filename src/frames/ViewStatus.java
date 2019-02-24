package frames;

import java.awt.BorderLayout;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import resources.*;
import source.*;


public class ViewStatus extends JFrame {

	private JPanel contentPane;
	private JTextField vehicleID;
	public int id_value ;
	public String vehicle_model;
	public String current_state;
	public String ac_value ; 
	public String return_date_value;
	public int dist_travled;
	public int amount_spent;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
					ViewStatus frame = new ViewStatus();
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
	public ViewStatus() {
		setTitle("View Status ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Vehicle ID :");
		lblNewLabel.setBounds(69, 106, 97, 15);
		contentPane.add(lblNewLabel);
		
		JLabel id = new JLabel("");
		id.setBounds(296, 78, 183, 15);
		contentPane.add(id);
		
		JLabel vehicleModel = new JLabel("");
		vehicleModel.setBounds(296, 105, 183, 15);
		contentPane.add(vehicleModel);
		
		JLabel currentState = new JLabel("");
		currentState.setBounds(296, 131, 183, 15);
		contentPane.add(currentState);
		
		JLabel ac = new JLabel("");
		ac.setBounds(296, 158, 183, 15);
		contentPane.add(ac);
		
		JLabel distTravled = new JLabel("");
		distTravled.setBounds(296, 212, 183, 15);
		contentPane.add(distTravled);
		
		JLabel amountSpent = new JLabel("");
		amountSpent.setBounds(296, 239, 183, 15);
		contentPane.add(amountSpent);
		
		JLabel return_date = new JLabel("");
		return_date.setBounds(296, 185, 183, 15);
		contentPane.add(return_date);
		
		vehicleID = new JTextField();
		vehicleID.setBounds(69, 130, 127, 25);
		contentPane.add(vehicleID);
		vehicleID.setColumns(10);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerMenu customerMenu = new CustomerMenu();
				customerMenu.setVisible(true);
				close();
			}
		});
		btnBack.setBounds(482, 315, 114, 39);
		contentPane.add(btnBack);
		JButton btnGetResutl = new JButton("Get Resutl");
		btnGetResutl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					id.setText("");
					id.setText("");
					vehicleModel.setText("");
					currentState.setText("");
					ac.setText("");
					return_date.setText("");
					distTravled.setText("");
					amountSpent.setText("");
					boolean check = false;
					String vehicle_id = vehicleID.getText();
					Connection connection = ConnectionHandler.createConnection();
					Statement statement = connection.createStatement();
					String query = "select * from vehicle where id = " + vehicle_id + " ";
					ResultSet resultSet = statement.executeQuery(query);
					while(resultSet.next()) {
						  id_value  = resultSet.getInt("id");
						  vehicle_model = resultSet.getString("vehicle_model");
						  current_state = resultSet.getString("current_state");
						  ac_value  = resultSet.getString("ac"); 
						  return_date_value = resultSet.getString("return_date");
						  dist_travled = resultSet.getInt("dist_travled");
						  amount_spent = resultSet.getInt("amount_spent");
						  check = true ; 
					}
					if(check == true) {
						id.setText("Vehicle ID : " + Integer.toString(id_value));
						vehicleModel.setText("Vehicle Model :" + vehicle_model);
						currentState.setText("Current State :" + current_state);
						ac.setText("Ac Present : " + ac_value);
						return_date.setText("Return Date : " + return_date_value);
						distTravled.setText("Distance Travled : " + Integer.toString(dist_travled));
						amountSpent.setText("Amount Spend : " + Integer.toString(amount_spent));
					}
					else {
						id.setText("Error : Given ID Not found ");
						id.setText("");
						vehicleModel.setText("");
						currentState.setText("");
						ac.setText("");
						return_date.setText("");
						distTravled.setText("");
						amountSpent.setText("");
					}
					resultSet.close();
					statement.close();
					connection.close();
				}catch(Exception eq) {
					eq.printStackTrace();
				}
			}
		});
		btnGetResutl.setBounds(56, 315, 127, 39);
		contentPane.add(btnGetResutl);
	}
	public void close() {
		try {
			this.setVisible(false);
			this.dispose();
		}catch(Exception rq) {
			rq.printStackTrace();
		}
	}
}
