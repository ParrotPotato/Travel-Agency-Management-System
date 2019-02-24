package frames;
import java.sql.*;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;


import resources.*;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import source.*;
public class VehicleReturn extends JFrame {

	private JPanel contentPane;
	private JTextField hrs;
	private JTextField days;
	private JTextField kms;
	private int advanced_fare ; 
	private int total_fare ; 
	private boolean ac ;  
	String id = null;
	String vehicle_model = null;
	String date_lended = null;
	String date_return = null;
	String fare = null;
	int transaction_id = 0 ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
				            UIManager.getSystemLookAndFeelClassName());
					VehicleReturn frame = new VehicleReturn(null);
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
	public VehicleReturn(Employee employee) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		advanced_fare = 0;
		total_fare = 0 ;
		
		JLabel lblTotalAmount = new JLabel("Total Amount :");
		lblTotalAmount.setBounds(145, 269, 289, 17);
		contentPane.add(lblTotalAmount);
		JLabel lblVehicleId = new JLabel("Vehicle ID :");
		lblVehicleId.setBounds(33, 68, 101, 17);
		contentPane.add(lblVehicleId);
		
		JLabel lblvehicleID = new JLabel("");
		lblvehicleID.setBounds(243, 77, 375, 17);
		contentPane.add(lblvehicleID);
		
		JLabel lblvehiclModel = new JLabel("");
		lblvehiclModel.setBounds(243, 119, 375, 17);
		contentPane.add(lblvehiclModel);
		
		JLabel lbllendedDate = new JLabel("");
		lbllendedDate.setBounds(243, 160, 375, 17);
		contentPane.add(lbllendedDate);
		
		JLabel lblreturnDate = new JLabel("");
		lblreturnDate.setBounds(243, 203, 375, 17);
		contentPane.add(lblreturnDate);
		
		JLabel lblfare = new JLabel("");
		lblfare.setBounds(243, 240, 375, 17);
		contentPane.add(lblfare);
		
		String activeVehicles[] = getActiveVehicleID();
		JComboBox vehicleID = new JComboBox(activeVehicles);
		
		JLabel lblDays = new JLabel("Days :");
		lblDays.setBounds(145, 166, 41, 17);
		contentPane.add(lblDays);
		
		JLabel lblHrs = new JLabel("Hrs : ");
		lblHrs.setBounds(27, 166, 41, 17);
		contentPane.add(lblHrs);
		
		JLabel lblKms = new JLabel("Kms: ");
		lblKms.setBounds(27, 218, 36, 17);
		contentPane.add(lblKms);
		
		JLabel totalFare = new JLabel("");
		totalFare.setBounds(319, 269, 205, 17);
		contentPane.add(totalFare);
		vehicleID.setBounds(27, 97, 168, 33);
		contentPane.add(vehicleID);
		
		hrs = new JTextField();
		hrs.setBounds(67, 158, 41, 33);
		contentPane.add(hrs);
		hrs.setColumns(10);
		
		days = new JTextField();
		days.setColumns(10);
		days.setBounds(190, 158, 41, 33);
		contentPane.add(days);
		
		kms = new JTextField();
		kms.setColumns(10);
		kms.setBounds(67, 210, 41, 33);
		contentPane.add(kms);
		JButton btnConfirm = new JButton("Confirm");btnConfirm.setEnabled(false);
		JButton btnCalculate = new JButton("Calculate");btnCalculate.setEnabled(false);
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int hours = Integer.parseInt(hrs.getText());
				int Days = Integer.parseInt(days.getText());
				int kilometers = Integer.parseInt(kms.getText());
				String model = vehicle_model;
				int netFare = calculatefare(hours,Days,kilometers,model);
				lblTotalAmount.setText("Total Amount :" + Integer.toString(netFare));
				total_fare = netFare;
				btnConfirm.setEnabled(true);
			}
		});
		btnCalculate.setBounds(33, 315, 112, 39);
		contentPane.add(btnCalculate);
		
		
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmReturn();
				JOptionPane.showMessageDialog(null, "Data Update Successfully ");
				EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
				employeeMenu.setVisible(true);
				close();
			}
		});
		btnConfirm.setBounds(382, 315, 112, 39);
		contentPane.add(btnConfirm);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
				employeeMenu.setVisible(true);
				close();
			}
		});
		btnCancel.setBounds(506, 315, 112, 39);
		contentPane.add(btnCancel);
		
		JButton btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection connection = ConnectionHandler.createConnection();
					Statement statement = connection.createStatement();
					String query = "select transaction.vehicle_id, booking.vehicle_model,transaction.date_lended,transaction.date_return,booking.fare,booking.transaction_id from booking, transaction "
							+ "where transaction.vehicle_id = " + activeVehicles[vehicleID.getSelectedIndex()] + " and booking.vehicle_id = " +
							activeVehicles[vehicleID.getSelectedIndex()] + " and booking.status = \"PENDING\" and booking.transaction_id = transaction.s_no" ; 
					ResultSet resultSet = statement.executeQuery(query);
					
					while(resultSet.next()) {
						id = Integer.toString(resultSet.getInt("vehicle_id"));
						vehicle_model = resultSet.getString("vehicle_model");
						date_lended = resultSet.getString("date_lended");
						date_return = resultSet.getString("date_return");
						fare = Integer.toString(resultSet.getInt("fare"));
						advanced_fare = Integer.parseInt(fare) ; 
						transaction_id = resultSet.getInt("transaction_id");
						lblvehicleID.setText("Vehicle ID : " + id);
						lblvehiclModel.setText("Vehicle Model : " + vehicle_model);
						lbllendedDate.setText("Issued  : " + date_lended);
						lblreturnDate.setText("Exepcted date : " + date_return);
						lblfare.setText("Advanced : " + fare);
					}
					
					resultSet.close();
					statement.close();
					
					statement = connection.createStatement();
					query = "select ac from vehicle where vehicle.ac = \"Y\" and vehicle.id=" + id +" " ;
					resultSet = statement.executeQuery(query);
					while(resultSet.next()) {
						if(resultSet.getString("ac").equals("Y"))
							ac = true;
						else 
							ac = false;
					}
					resultSet.close();
					statement.close();
					connection.close();
					btnCalculate.setEnabled(true);
				}catch(SQLException eq) {
					eq.printStackTrace();
					System.out.println("Error : SQLException occured in VehicleReturn.mouseClicked()");
				}
			}
		});
		btnFind.setBounds(167, 315, 112, 39);
		contentPane.add(btnFind);
		
		
	}
	
	public String[] getActiveVehicleID() {
		try {
			Connection connection = ConnectionHandler.createConnection();
			Statement statement = connection.createStatement();
			String request = "select id from vehicle where current_state = \"ACTIVE\"";
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
	
	public int calculatefare(int hours,int days,int kilometers,String model) {
		try {
			Connection connection = ConnectionHandler.createConnection();
			String query = "select * from vehicle_model "
					+ "where vehicle_model.model = \"" + model + "\" ";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			int perKm = 0 ;
			int perHr = 0 ;
			while(resultSet.next()) {
				perKm = resultSet.getInt("base_km_price");
				perHr = resultSet.getInt("base_hr_price");
			}
			resultSet.close();
			statement.close();
			connection.close();
			if(perKm*kilometers >= (days*150 + perHr*hours)) {
				if(ac == true) {
					int temp =0  ;
					temp = perKm*kilometers;
					temp += temp/2 ;
					return temp + advanced_fare;
				}
				else 
					return perKm*kilometers + advanced_fare;
			}
			else {
				if(ac == true) {
					int temp =0  ;
					temp = (days*150 + perHr*hours);
					temp += temp/2 ;
					return temp + advanced_fare;
				}
				else 
					return (days*150 + perHr*hours) + advanced_fare;
			}
		}catch(SQLException sq) {
			sq.printStackTrace();
			System.out.println("Error : SQLException occured at the calculateFare in vehicleReturn");
			return -1 ;
		}
	}
	
	public void confirmReturn() {
		try {
			Connection connection = ConnectionHandler.createConnection();
			String update = "update vehicle set current_state = ? , dist_travled = ? , return_date = NULL  where id = ?";
			PreparedStatement vehicleStatement = connection.prepareStatement(update);
			vehicleStatement.setString(1, "IDLE");
			vehicleStatement.setInt(2, Integer.parseInt(kms.getText()));
			vehicleStatement.setInt(3,Integer.parseInt(id));
			vehicleStatement.execute();
			
			vehicleStatement.close();
			
			update = "update booking set dist_traveled = ? , status =  ? where vehicle_id = ? and status = ? and transaction_id = ?";
			PreparedStatement bookingStatement = connection.prepareStatement(update);
			bookingStatement.setInt(1, Integer.parseInt(kms.getText()));
			bookingStatement.setString(2, "CONFIRMED");
			bookingStatement.setInt(3, Integer.parseInt(id));
			bookingStatement.setString(4, "PENDING");
			bookingStatement.setInt(5, transaction_id);
			bookingStatement.execute();
			
			bookingStatement.close();
			
			update = "update transaction set total_amount = ? where s_no = ? ";
			PreparedStatement transactionStatement = connection.prepareStatement(update);
			transactionStatement.setInt(1, total_fare);
			transactionStatement.setInt(2,transaction_id);
			transactionStatement.execute();
			
			update = "update vehicle_model set amount_earn = amount_earn + ? where model =  ? ";
			PreparedStatement vehicleModel = connection.prepareStatement(update);
			vehicleModel.setInt(1, total_fare);
			vehicleModel.setString(2, vehicle_model);
			vehicleModel.execute();
			vehicleModel.close();
			transactionStatement.close();
			connection.close();
		}catch(SQLException eq) {
			eq.printStackTrace();
		}
	}
	
	void close() {
		try {
			this.setVisible(false);
			this.dispose();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
