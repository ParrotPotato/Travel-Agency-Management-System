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
public class SellVehicle extends JFrame {

	private JPanel contentPane;
	boolean acval = false;
	String modelName = "" ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
					SellVehicle frame = new SellVehicle(null);
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
	public SellVehicle(Employee employee) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblVehicleId = new JLabel("Vehicle Id : ");
		lblVehicleId.setBounds(51, 74, 155, 15);
		contentPane.add(lblVehicleId);
		
		JLabel vehicleid = new JLabel("");
		vehicleid.setBounds(50, 137, 291, 15);
		contentPane.add(vehicleid);
		
		JLabel model = new JLabel("");
		model.setBounds(50, 164, 291, 15);
		contentPane.add(model);
		
		JLabel currentstate = new JLabel("");
		currentstate.setBounds(50, 191, 291, 15);
		contentPane.add(currentstate);
		
		JLabel ac = new JLabel("");
		ac.setBounds(50, 222, 291, 15);
		contentPane.add(ac);
		
		JLabel returndate = new JLabel("");
		returndate.setBounds(50, 249, 291, 15);
		contentPane.add(returndate);
		
		JLabel distancetravaelled = new JLabel("");
		distancetravaelled.setBounds(50, 279, 291, 15);
		contentPane.add(distancetravaelled);
		
		JLabel amountspent = new JLabel("");
		amountspent.setBounds(50, 310, 291, 15);
		contentPane.add(amountspent);
		
		String ids[] = getID();
		
		JComboBox id = new JComboBox(ids);
		id.setMaximumRowCount(1000);
		id.setBounds(51, 101, 155, 24);
		contentPane.add(id);
		JButton btnSell = new JButton("Sell");
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String idValue = (ids[id.getSelectedIndex()]);
					Connection connection = ConnectionHandler.createConnection();
					if(isIDLE(Integer.parseInt(idValue)) == false) {
						JOptionPane.showMessageDialog(null, "Vehicle NOT Avaliable at the moment");
						connection.close();
						return ;
					}
					String request = "delete from vehicle where id = " + idValue + " "; 
					PreparedStatement pre = connection.prepareStatement(request);
					pre.execute();
					pre.close();
					if(acval == true) {
						request = "update vehicle_model	set number  = number-1 , ac = ac-1 where  model = ? " ;
						pre = connection.prepareStatement(request);
						pre.setString(1,modelName);
						pre.execute();
						pre.close();
					}
					else {
						request = "update vehicle_model	set number  = number-1 , nac = nac-1 where  model = ? " ;
						pre = connection.prepareStatement(request);
						pre.setString(1,modelName);
						pre.execute();
						pre.close();
					}
					request = "select cost from vehicle_model where model =\"" + modelName + "\"";
					Statement statement  = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(request);
					int cost = 0; 
					while(resultSet.next()) {
						cost = resultSet.getInt("cost");
					}
					statement.close();
					resultSet.close();
					String[] months = {"January","February","March","April","May","June","July","August","Septmber","October","November","December"};  //   | to be changed 
					String query = "insert into transaction (employee_id,vehicle_id,advance_payment,total_amount,date_lended,date_return,type) value (?,?,0,?,?,NULL,?)";
					PreparedStatement insertVehicle = connection.prepareStatement(query);
					insertVehicle.setInt(1, Employee.INSTANCE.getID());
					insertVehicle.setInt(2, Integer.parseInt(idValue));
					insertVehicle.setInt(3, cost);
					insertVehicle.setString(4, Integer.toString(Calendar.getInstance().get(Calendar.DATE)) + "-" + 
					months[Calendar.getInstance().get(Calendar.MONTH)] + "-" + 
					Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
					insertVehicle.setString(5,"SELL");
					insertVehicle.execute();
					insertVehicle.close();
					connection.close();
					JOptionPane.showMessageDialog(null, "Vehicle Sold !! ");
					PurchseSellVehicle purchseSellVehicle = new PurchseSellVehicle(Employee.INSTANCE);
					purchseSellVehicle.setVisible(true);
					close();
				}catch(Exception eq) {
					eq.printStackTrace();
				}
			}
		});btnSell.setEnabled(false);
		btnSell.setBounds(357, 329, 110, 25);
		contentPane.add(btnSell);
		
		JButton btnGetDetails = new JButton("Get Details ");
		btnGetDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String idValue = (ids[id.getSelectedIndex()]);
					Connection connection = ConnectionHandler.createConnection();
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("select * from vehicle where id = " + idValue + " ");
					while(resultSet.next()) {
						vehicleid.setText("Vehicle ID : " + Integer.toString(resultSet.getInt("id")));
						model.setText("Vehicle Model : " + resultSet.getString("vehicle_model"));
						modelName =  resultSet.getString("vehicle_model");
						currentstate.setText("Vehicle Current State : " + resultSet.getString("current_state"));
						ac.setText("Vehicle AC : " + resultSet.getString("ac"));
						if(resultSet.getString("ac").equals("Y") == true) {
							acval = true; 
						}
						else 
							acval = false;
						returndate.setText("Vehicle Return Date : " + resultSet.getString("return_date"));
						distancetravaelled.setText("Vehicle Distance Travelled : " + Integer.toString(resultSet.getInt("dist_travled")));
						amountspent.setText("Amount Spend : " + Integer.toString(resultSet.getInt("amount_spent")));
						btnSell.setEnabled(false);
					}
					resultSet.close();
					statement.close();
					connection.close();
					btnSell.setEnabled(true);
				}catch(Exception eq) {
					eq.printStackTrace();
				}
			}
		});
		btnGetDetails.setBounds(434, 101, 170, 25);
		contentPane.add(btnGetDetails);
		
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PurchseSellVehicle purchseSellVehicle = new PurchseSellVehicle(Employee.INSTANCE);
				purchseSellVehicle.setVisible(true);
				close();
			}
		});
		btnCancel.setBounds(508, 329, 110, 25);
		contentPane.add(btnCancel);
	}
	public String[] getID() {
		try {
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
		}catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	public boolean isIDLE(int id) {
		try {
			Connection connection = ConnectionHandler.createConnection();
			Statement statement = connection.createStatement();
			String sql = "select current_state from vehicle where id = " + Integer.toString(id) + " " ; 
			ResultSet rs = statement.executeQuery(sql);
			int count = 0 ;
			while(rs.next()) {
				if(rs.getString("current_state").equals("IDLE")) {
					count ++ ;
				}
			}
			rs.close();
			statement.close();
			connection.close();
			if(count == 0 ) {
				return false;
			}
			else {
				return true;
			}
		}catch(SQLException sq) {
			sq.printStackTrace();
			return false ;
		}
	}
	public boolean sellVehicle() {
		return false;
	}
}
