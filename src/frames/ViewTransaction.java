package frames;
import resources.*;


import javax.swing.*;
import java.awt.BorderLayout;



import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import resources.ConnectionHandler;

import java.sql.*;
import javax.swing.*;
import resources.*;
import source.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import source.*;
public class ViewTransaction extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
					ViewTransaction frame = new ViewTransaction();
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
	public ViewTransaction() {
		setTitle("Transactions");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 952, 624);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 33, 926, 499);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		try {
			Connection connection = ConnectionHandler.createConnection();
			Statement statement = connection.createStatement();
			String query = "select * from transaction;"; 
			ResultSet resultSet = statement.executeQuery(query);
			table.setModel(DbUtils.resultSetToTableModel(resultSet));
			resultSet.close();
			statement.close();
			connection.close();
		}catch(SQLException sq) {
			sq.printStackTrace();
		}
		JLabel lblNewLabel = new JLabel("Transactions : ");
		lblNewLabel.setBounds(12, 4, 293, 17);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		btnNewButton.setBounds(826, 544, 112, 39);
		contentPane.add(btnNewButton);
	}
	public void close() {
		try {
			this.setVisible(true);
			this.dispose();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
