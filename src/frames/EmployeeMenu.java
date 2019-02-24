package frames;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import source.*;
public class EmployeeMenu extends JFrame {

	private JPanel contentPane;
	private Employee employee;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
				            UIManager.getSystemLookAndFeelClassName());
					EmployeeMenu frame = new EmployeeMenu(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public EmployeeMenu(Employee employee) {
		this.employee = employee ; 
		//System.out.println("MENU recieved :: " + employee.getUsername());
		setTitle("EmployeeMenu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnBookVehicle = new JButton("Book Vehicle");
		btnBookVehicle.setBounds(41, 57, 239, 38);
		btnBookVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookingWindow bookingWindow = new BookingWindow(Employee.INSTANCE);
				bookingWindow.setVisible(true);
				close();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnBookVehicle);
		
		JButton btnViewvehiclestatus = new JButton("View Vehicle Status");
		btnViewvehiclestatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewVehicleStatus viewVehicleStatus = new ViewVehicleStatus();
				viewVehicleStatus.setVisible(true);
				
			}
		});
		btnViewvehiclestatus.setBounds(349, 157, 239, 38);
		contentPane.add(btnViewvehiclestatus);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(41, 323, 155, 31);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 EmployeeLogin employeeLogin  = new EmployeeLogin();
				 employeeLogin.setVisible(true);
				 close();
			}
		});
		contentPane.add(btnLogout);
		
		JButton btnAddEmployee = new JButton("Add Employee");
		btnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(employee.getUsername().equals("niteshmeena") == true) {
					EmployeeSignUp employeeSignUp = new EmployeeSignUp(Employee.INSTANCE);
					employeeSignUp.setVisible(true);
					close();
				}
				else 
					JOptionPane.showMessageDialog(null, "Only administrator is allowed to add or delete employeee");
					
			}
		});
		btnAddEmployee.setBounds(237, 323, 155, 31);
		contentPane.add(btnAddEmployee);
		
		JButton btnRemoveEmployee = new JButton("Remove Employee");
		btnRemoveEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(employee.getUsername().equals("niteshmeena") == true ) {
					DeleteEmployee deleteEmployee = new DeleteEmployee(Employee.INSTANCE);
					deleteEmployee.setVisible(true);
					close();
				}
				else {
					JOptionPane.showMessageDialog(null, "Only administrator is allowed to add or delete employeee");
				}
			}
		});
		btnRemoveEmployee.setBounds(433, 326, 155, 31);
		contentPane.add(btnRemoveEmployee);
		
		JButton btnViewTransactions = new JButton("View Transactions");
		btnViewTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewTransaction viewTransaction = new ViewTransaction();
				viewTransaction.setVisible(true);
				//add 
			}
		});
		btnViewTransactions.setBounds(41, 157, 239, 38);
		contentPane.add(btnViewTransactions);
		
		JButton btnViewPurchases = new JButton("View Purchases");
		btnViewPurchases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewPurchase viewPerchase = new ViewPurchase();
				viewPerchase.setVisible(true);
			}
		});
		btnViewPurchases.setBounds(349, 107, 239, 38);
		contentPane.add(btnViewPurchases);
		
		JButton vehicleReturn = new JButton("Vehicle Return ");
		vehicleReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehicleReturn vehicleReturn = new VehicleReturn(Employee.INSTANCE);
				vehicleReturn.setVisible(true);
				close();
			}
		});
		vehicleReturn.setBounds(349, 57, 239, 38);
		contentPane.add(vehicleReturn);
		
		JButton btnpurchaseSellMenu = new JButton("Purchase Sell Menu");
		btnpurchaseSellMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PurchseSellVehicle purchaseSellVehicle = new PurchseSellVehicle(Employee.INSTANCE);
				purchaseSellVehicle.setVisible(true);
				close();
			}
		});
		btnpurchaseSellMenu.setBounds(41, 107, 239, 38);
		contentPane.add(btnpurchaseSellMenu);
		
		JButton btnRepairVehicle = new JButton("Repair Vehicle");
		btnRepairVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RepairVehicle repairVehicle = new RepairVehicle(Employee.INSTANCE);
				repairVehicle.setVisible(true);
				close();
			}
		});
		btnRepairVehicle.setBounds(41, 207, 239, 38);
		contentPane.add(btnRepairVehicle);
		
		JButton btnReturnRepairVehicle = new JButton("Return Repair Vehicle");
		btnReturnRepairVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReturnRepairVehicle returnRepairVehicle = new ReturnRepairVehicle(Employee.INSTANCE);
				returnRepairVehicle.setVisible(true);
				close();
			}
		});
		btnReturnRepairVehicle.setBounds(349, 207, 239, 38);
		contentPane.add(btnReturnRepairVehicle);
	}
	public void close() {
		try {
			this.setVisible(false);
			this.dispose();
		}catch (Exception e) {
			System.out.println("Error : Exception occured in EmployeeMenu.close()");
		}
	}
}
