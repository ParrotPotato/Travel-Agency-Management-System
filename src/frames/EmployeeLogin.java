package frames;
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
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

import source.*;
public class EmployeeLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
					EmployeeLogin frame = new EmployeeLogin();
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
	public EmployeeLogin() {
		setTitle("Employee Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmployeeName = new JLabel("Employee Name              :");
		lblEmployeeName.setBounds(118, 76, 194, 24);
		contentPane.add(lblEmployeeName);
		
		JLabel lblPassword = new JLabel("Password                       :");
		lblPassword.setBounds(118, 132, 194, 24);
		contentPane.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(330, 73, 194, 31);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(330, 129, 194, 31);
		contentPane.add(passwordField);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean test = checking();
				if(test == true) {
					EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
					employeeMenu.setVisible(true);
					textField.setText("");
					passwordField.setText("");
					close();
				}else {
					textField.setText("");
					passwordField.setText("");
					JOptionPane.showMessageDialog(null, "Entered Credentials does not match !!");
				}
			}
		});
		btnSubmit.setBounds(118, 238, 110, 25);
		contentPane.add(btnSubmit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				passwordField.setText("");
			}
		});
		btnClear.setBounds(265, 238, 110, 25);
		contentPane.add(btnClear);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPage mainPage = new MainPage();
				mainPage.setVisible(true);
				close();
			}
		});
		back.setBounds(414, 238, 110, 25);
		contentPane.add(back);
	}
	public void close() {
		try{
			this.setVisible(false);
			this.dispose();
		}catch(Exception e) {
			System.out.println("Error : Exception occured in EmployeeLogin.close()");
		}
	}
	
	public boolean checking() {
		String name = textField.getText();
		String password = passwordField.getText();
		Employee employee = Employee.validateEmployee(name, password);
		if(employee != null) {
			return true;
		}
		return false ;
	}
}
