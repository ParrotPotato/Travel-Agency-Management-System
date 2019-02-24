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
public class CustomerMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
					CustomerMenu frame = new CustomerMenu();
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
	public CustomerMenu() {
		setTitle("Customer Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnViewStatus = new JButton("View Status ");
		btnViewStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewStatus viewStatus = new ViewStatus();
				viewStatus.setVisible(true);
				close();
			}
		});
		btnViewStatus.setBounds(224, 123, 167, 25);
		contentPane.add(btnViewStatus);
		
		JButton btnViewAvaliability = new JButton("View Avaliability ");
		btnViewAvaliability.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewAvaliability viewAvailability = new ViewAvaliability();
				viewAvailability.setVisible(true);
				close();
			}
		});
		btnViewAvaliability.setBounds(224, 188, 167, 25);
		contentPane.add(btnViewAvaliability);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPage mainPage = new MainPage();
				mainPage.setVisible(true);
				close();
			}
		});
		btnNewButton.setBounds(508, 329, 110, 25);
		contentPane.add(btnNewButton);
	}
	public void close() {
		try {
			this.setVisible(false);
			this.dispose();
		}catch(Exception t) {
			t.printStackTrace();
		}
	}
}
