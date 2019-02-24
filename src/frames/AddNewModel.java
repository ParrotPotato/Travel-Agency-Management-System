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
public class AddNewModel extends JFrame {

	private JPanel contentPane;
	private JTextField modelName;
	private JTextField basehr;
	private JTextField basekm;
	private JTextField cost;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
					AddNewModel frame = new AddNewModel(null);
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
	public AddNewModel(Employee employee) {
		setTitle("Add New Model ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Model Name");
		lblNewLabel.setBounds(112, 86, 141, 17);
		contentPane.add(lblNewLabel);
		
		JLabel lblBasePerHour = new JLabel("Base Per Hour");
		lblBasePerHour.setBounds(346, 192, 141, 17);
		contentPane.add(lblBasePerHour);
		
		JLabel lblBasePerKm = new JLabel("Base Per km");
		lblBasePerKm.setBounds(112, 192, 141, 17);
		contentPane.add(lblBasePerKm);
		
		JLabel lblCost = new JLabel("Model Cost");
		lblCost.setBounds(346, 86, 141, 17);
		contentPane.add(lblCost);
		
		cost = new JTextField();
		cost.setColumns(10);
		cost.setBounds(346, 115, 152, 33);
		contentPane.add(cost);
		
		modelName = new JTextField();
		modelName.setBounds(112, 115, 152, 33);
		contentPane.add(modelName);
		modelName.setColumns(10);
		
		basehr = new JTextField();
		basehr.setColumns(10);
		basehr.setBounds(346, 221, 152, 33);
		contentPane.add(basehr);
		
		basekm = new JTextField();
		basekm.setColumns(10);
		basekm.setBounds(112, 221, 152, 33);
		contentPane.add(basekm);
		
		JButton btnAddNewModel = new JButton("Add New Model");
		btnAddNewModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name = modelName.getText();
					int baseperhr = Integer.parseInt(basehr.getText());
					int baseprekm = Integer.parseInt(basekm.getText());
					int costvalue = Integer.parseInt(cost.getText());
					boolean test = addNewModel(name,baseperhr,baseprekm,costvalue);
					if(test == false) {
						return ;
					}
//					Connection connection = ConnectionHandler.createConnection();
//					String query = "insert into vehicle_model value(?,0,0,0,?,?,0,?)";
//					PreparedStatement pre = connection.prepareStatement(query);
//					pre.setInt(2, baseperhr);
//					pre.setInt(3, baseprekm);
//					pre.setString(1, name);
//					pre.setInt(4, costvalue);
//					pre.execute();
//					connection.close();
					JOptionPane.showMessageDialog(null, "New Model Added !! ");
					PurchaseVehicle purchaseVehicle = new PurchaseVehicle(Employee.INSTANCE);
					purchaseVehicle.setVisible(true);
					close();
				}catch(Exception eq) {
					eq.printStackTrace();
				}
			}
		});
		btnAddNewModel.setBounds(324, 315, 141, 39);
		contentPane.add(btnAddNewModel);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PurchaseVehicle purchaseVehicle = new PurchaseVehicle(Employee.INSTANCE);
				purchaseVehicle.setVisible(true);
				close();
			}
		});
		back.setBounds(477, 315, 141, 39);
		contentPane.add(back);
	}
	public void close() {
		try {
			this.setVisible(false);
			this.dispose();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean addNewModel(String name,int baseperhr,int baseperkm, int cost) {
		try {
			Connection connection = ConnectionHandler.createConnection();
			String query = "insert into vehicle_model value(?,0,0,0,?,?,0,?)";
			PreparedStatement pre = connection.prepareStatement(query);
			pre.setInt(2, baseperhr);
			pre.setInt(3, baseperkm);
			pre.setString(1, name);
			pre.setInt(4, cost);
			pre.execute();
			connection.close();
			return true;
		}catch(Exception eq) {
			eq.printStackTrace();
			return false;
		}
	}
}
