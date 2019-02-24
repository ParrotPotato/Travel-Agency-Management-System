package frames;
import javax.swing.*;


import java.util.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.*;

import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import resources.*;
import source.*;

public class BookingWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtyear;
	private int vehicleID;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
				            UIManager.getSystemLookAndFeelClassName());
					BookingWindow frame = new BookingWindow(new Employee());
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
	public BookingWindow(Employee employee) {
		vehicleID = -1 ;
		setTitle("Booking Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblVechicleModel = new JLabel("Vechicle Model");
		lblVechicleModel.setBounds(12, 46, 135, 24);
		contentPane.add(lblVechicleModel);
		
		JLabel lblExpectedReturn = new JLabel("Expected Return ");
		lblExpectedReturn.setBounds(12, 122, 140, 24);
		contentPane.add(lblExpectedReturn);
		
		JLabel lblDate = new JLabel("Date : ");
		lblDate.setBounds(12, 169, 53, 17);
		contentPane.add(lblDate);
		
		JLabel lblMonth = new JLabel("Month :");
		lblMonth.setBounds(162, 169, 53, 17);
		contentPane.add(lblMonth);
		
		JLabel lblYear = new JLabel("Year :");
		lblYear.setBounds(351, 169, 53, 17);
		contentPane.add(lblYear);
		String[] dates = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		String[] months = {"January","February","March","April","May","June","July","August","Septmber","October","November","December"};
		String[] models = getModels();
		
		JLabel label1 = new JLabel("");
		label1.setBounds(12, 223, 355, 17);
		contentPane.add(label1);
		
		JLabel label2 = new JLabel("");
		label2.setBounds(12, 252, 355, 17);
		contentPane.add(label2);
		
		JLabel label3 = new JLabel("");
		label3.setBounds(12, 281, 355, 17);
		contentPane.add(label3);


		JLabel label4 = new JLabel("");
		label4.setBounds(12, 315, 355, 17);
		contentPane.add(label4);
		
		JComboBox vehicleModel = new JComboBox(models);
		vehicleModel.setBounds(12, 70, 168, 33);
		contentPane.add(vehicleModel);
		
		JComboBox date = new JComboBox(dates);
		date.setBounds(66, 161, 86, 33);
		contentPane.add(date);
		
		JComboBox month = new JComboBox(months);
		month.setBounds(227, 161, 112, 33);
		contentPane.add(month);
		
		JCheckBox ac = new JCheckBox("AC");
		ac.setBounds(213, 73, 135, 26);
		contentPane.add(ac);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setEnabled(false);
		JButton btnGetResults = new JButton("Get Results");
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
				employeeMenu.setVisible(true);
				close();
			}
		});
		
		
		btnGetResults.setBounds(506, 212, 112, 39);
		btnGetResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label1.setText("");
				label2.setText("");
				label3.setText("");
				label4.setText("");
				int year_value = Integer.parseInt(txtyear.getText());
				String month_value = months[month.getSelectedIndex()];
				int date_value = Integer.parseInt(dates[date.getSelectedIndex()]);
				String model_value = models[vehicleModel.getSelectedIndex()];
				boolean ac_value = ac.isSelected();
				
				String isAC = null;
				if(ac_value == true)
					isAC = "Y";
				else 
					isAC = "N";
				//String IDLE = "IDLE";
				try {
					int id = -1;
					Connection connection = ConnectionHandler.createConnection();
					String request = "select id from vehicle \n"
							+ "where vehicle.vehicle_model = \"" + model_value + "\"and vehicle.ac = \"" +isAC+  "\" and vehicle.current_state = \"IDLE\"";
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(request);
					while(resultSet.next()) {
						id = resultSet.getInt("id");
					}
					if(id == -1) {
						label1.setText("No such result found !!");
					}
					else {
						vehicleID = id;
						label1.setText("Vehicle ID            : " + id);
						resultSet.close();
						statement.close();
						connection.close();
						System.out.println("Reached upto label2 ");
						label3.setText("Expected Date: " + Integer.toString(date_value) + "-" + month_value + "-" + Integer.toString(year_value));
						if(ac_value == true)
							label2.setText("Vehicle Model  : " + model_value + "(AC)"  );
						else 
							label2.setText("Vehicle Model  : " + model_value + "(N-AC)");
						label4.setText("Expected Fare : " + Integer.toString(calculateFare(model_value,ac_value)));
						btnConfirm.setEnabled(true);
					}
				}catch(SQLException sq) {
					System.out.println("Error : SQLException occured in BookingWindow.getReport()");
					sq.printStackTrace();
				}
			}
			
		});
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year_value = Integer.parseInt(txtyear.getText());
				String month_value = months[month.getSelectedIndex()];
				int date_value = Integer.parseInt(dates[date.getSelectedIndex()]);
				String model_value = models[vehicleModel.getSelectedIndex()];
				boolean ac_value = ac.isSelected();
				
				String todaysDate = Integer.toString(Calendar.getInstance().get(Calendar.DATE)) + "-" + 
						months[Calendar.getInstance().get(Calendar.MONTH)] + "-" + 
						Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
				confirmBooking(year_value,month_value,date_value,todaysDate,model_value,ac_value,Employee.INSTANCE);
				EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
				employeeMenu.setVisible(true);
				close();
			}
		});
		
		contentPane.add(btnGetResults);
		
		btnConfirm.setEnabled(false);
		btnConfirm.setBounds(382, 315, 112, 39);
		contentPane.add(btnConfirm);
		
		
		btnCancel.setBounds(506, 315, 112, 39);
		contentPane.add(btnCancel);
		
		txtyear = new JTextField();
		txtyear.setBounds(397, 161, 65, 33);
		contentPane.add(txtyear);
		txtyear.setText(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
		txtyear.setColumns(10);
		
		JButton btnAdd = new JButton("+");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int value  = Integer.parseInt(txtyear.getText());
				value++;
				txtyear.setText(Integer.toString(value));
			}
		});
		btnAdd.setBounds(468, 161, 36, 33);
		contentPane.add(btnAdd);
		
		JButton btnSub = new JButton("-");
		btnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int value  = Integer.parseInt(txtyear.getText());
				value--;
				txtyear.setText(Integer.toString(value));
			}
		});
		btnSub.setBounds(502, 161, 36, 33);
		contentPane.add(btnSub);
		
		
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
		try{
			this.setVisible(false);
			this.dispose();
		}catch(Exception e) {
			System.out.println("Error : Exception occured in BookingWindow.close()");
		}
	}
	
	public int calculateFare(String model_value,boolean ac_value) {
		try {
			int fare = 0 ; 
			Connection connection = ConnectionHandler.createConnection();
			Statement statement = connection.createStatement();
			String sql = "select * from vehicle_model where vehicle_model.model = \"" + model_value + "\"";
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				int value = 0 ; 
				value = resultSet.getInt("base_hr_price");
				if(value*4 < resultSet.getInt("base_km_price"))
					fare = resultSet.getInt("base_km_price");
				else 
					fare = value * 4 ; 
			}
			resultSet.close();
			statement.close();
			connection.close();
			if(ac_value == true) {
				fare += fare/2.0;
			}
			return fare;
		}catch(SQLException sq) {
			sq.printStackTrace();
			System.out.println("Error : SQLException error occured");
			return 0;
		}
	}
	
	public boolean confirmBooking (int year_value,String month_value,int date_value,String todaysDate,String model_value,boolean ac_value ,Employee employee) {
		try {
			//Creating Connection
			Connection connection = ConnectionHandler.createConnection();
			
			int fare = calculateFare(model_value,ac_value);
			
			String requestString  = "insert into transaction (employee_id,vehicle_id,date_lended,date_return,advance_payment,type) value(?,?,?,?,?,?)";
			String returnDate = Integer.toString(date_value) + "-" + month_value +"-"+Integer.toString(year_value);
			PreparedStatement transactionStatement = connection.prepareStatement(requestString);
			transactionStatement.setInt(1, Employee.INSTANCE.id);
			transactionStatement.setInt(2, vehicleID);
			transactionStatement.setString(3, todaysDate);
			transactionStatement.setString(4, returnDate);
			transactionStatement.setString(6, "BOOKING");
			transactionStatement.setInt(5, fare);
			transactionStatement.execute();
			transactionStatement.close();
			connection.close();
			connection = ConnectionHandler.createConnection();
			int transaction_id = 0 ;
			//getting transaction ID 
			{
				String query = "select s_no from transaction where vehicle_id = " + Integer.toString(vehicleID) + " and  type = \"BOOKING\" and total_amount IS NULL " ;
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				while(resultSet.next()) {
					transaction_id = resultSet.getInt("s_no");
				}
				resultSet.close();
				statement.close();
			}
			
			String request = "insert into booking (vehicle_model,vehicle_id,date,fare,status,transaction_id) values (?,?,?,?,?,?)" ;
			PreparedStatement bookingStatement = connection.prepareStatement(request);
			bookingStatement.setString(1,model_value);
			bookingStatement.setInt(2, vehicleID);
			bookingStatement.setString(3,Integer.toString(date_value) + "-" + month_value +"-"+Integer.toString(year_value));
			bookingStatement.setInt(4,fare);
			bookingStatement.setString(5, "PENDING");
			bookingStatement.setInt(6, transaction_id);
			bookingStatement.execute();
			bookingStatement.close();
			
			//Updating vehicle database 
			String vehicleUpdate = "update vehicle "
					+ "set current_state = ? ,return_date = ? "
					+ "where id =  ? "; 
			PreparedStatement vehicleStatement = connection.prepareStatement(vehicleUpdate);
			vehicleStatement.setString(1, "ACTIVE");
			vehicleStatement.setString(2, Integer.toString(date_value) + "-" + month_value +"-"+Integer.toString(year_value));
			vehicleStatement.setInt(3, vehicleID);
			vehicleStatement.execute();
			vehicleStatement.close();
			
			//transaction 	
			
			JOptionPane.showMessageDialog(null, "Transaction Successfull !!! ");
			connection.close();
			return true;
		}catch(SQLException sq) {
			System.out.println("Error : SQLException throws in Emplooye.Employee().ActionListner");
			sq.printStackTrace();
			return false;
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
	
}
