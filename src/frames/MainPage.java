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
import java.awt.Color;
public class MainPage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
					MainPage frame = new MainPage();
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
	public MainPage() {
		setTitle("Travel Agency Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("TRAVEL AGENCY MANAGEMENT SYSTEM");
		lblNewLabel.setBounds(156, 12, 391, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblUseAs = new JLabel("Use As :");
		lblUseAs.setBounds(36, 112, 66, 15);
		contentPane.add(lblUseAs);
		
		JButton btnNewButton = new JButton("Customer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerMenu customerMenu = new CustomerMenu();
				customerMenu.setVisible(true);
				close();	
			}
		});
		btnNewButton.setBounds(233, 235, 110, 25);
		contentPane.add(btnNewButton);
		
		JButton btnEmployee = new JButton("Employee");
		btnEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeLogin employeeLogin = new EmployeeLogin();
				employeeLogin.setVisible(true);
				close();
			}
		});
		btnEmployee.setBounds(233, 105, 110, 25);
		contentPane.add(btnEmployee);
	}
	public void close() {
		try {
			this.setVisible(false);
			this.dispose();
		}catch(Exception eq) {
			eq.printStackTrace();
		}
	}
}
