package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import resources.*;
import source.*;
public class EmployeeSignUp extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField username;
	private JPasswordField password;
	private JPasswordField confirmationPasword;
	private JTextField id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
					EmployeeSignUp frame = new EmployeeSignUp(null);
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
	public EmployeeSignUp(Employee employee) {
		setTitle("Employee SignUp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name                                       :");
		lblNewLabel.setBounds(63, 34, 169, 17);
		contentPane.add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("Username                             :");
		lblUsername.setBounds(63, 96, 169, 17);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password                              :");
		lblPassword.setBounds(63, 202, 169, 17);
		contentPane.add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password          :");
		lblConfirmPassword.setBounds(63, 266, 169, 17);
		contentPane.add(lblConfirmPassword);
		
		id = new JTextField();
		id.setColumns(10);
		id.setBounds(280, 133, 241, 33);
		contentPane.add(id);
		
		JLabel error = new JLabel("");
		error.setBounds(280, 274, 241, 17);
		contentPane.add(error);
		
		name = new JTextField();
		name.setBounds(280, 26, 241, 33);
		contentPane.add(name);
		name.setColumns(10);
		
		username = new JTextField();
		username.setColumns(10);
		username.setBounds(280, 88, 241, 33);
		contentPane.add(username);
		
		password = new JPasswordField();
		password.setBounds(280, 194, 241, 33);
		contentPane.add(password);
		
		confirmationPasword = new JPasswordField();
		confirmationPasword.setBounds(280, 258, 241, 33);
		contentPane.add(confirmationPasword);
		
		JButton createEmployee = new JButton("Create Employee ");
		createEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName_data = username.getText();
				String name_data = name.getText();
				if(password.getText().equals(confirmationPasword.getText()) == false || 
						userName_data.equals("") == true ||
						name_data.equals("") == true || 
						password.getText().equals("") == true || 
						confirmationPasword.getText().equals("") == true) {
					error.setText("Password entered does not match ");
					return;
				}
				String password_data = password.getText();
				boolean test = Employee.createEmployee(name_data,userName_data,password_data,Integer.parseInt(id.getText()));
				if(test == false) {
					error.setText("ID already exit ");
					username.setText("");
					id.setText("");
					password.setText("");
					confirmationPasword.setText("");
					name.setText("");
				}
				error.setText("New employee created !! ");
				username.setText("");
				id.setText("");
				password.setText("");
				confirmationPasword.setText("");
				name.setText("");
			}
		});
		createEmployee.setBounds(39, 315, 152, 39);
		contentPane.add(createEmployee);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
				employeeMenu.setVisible(true);
				close();
			}
		});
		cancel.setBounds(421, 315, 169, 39);
		contentPane.add(cancel);
		
		JButton clear = new JButton("Clear ");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username.setText("");
				password.setText("");
				id.setText("");
				confirmationPasword.setText("");
				name.setText("");
				error.setText("");
			}
		});
		clear.setBounds(230, 315, 152, 39);
		contentPane.add(clear);
		
		JLabel lblId = new JLabel("ID                                      : ");
		lblId.setBounds(63, 145, 169, 17);
		contentPane.add(lblId);
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
