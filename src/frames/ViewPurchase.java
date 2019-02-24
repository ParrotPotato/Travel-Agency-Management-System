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

public class ViewPurchase extends JFrame {

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
					ViewPurchase frame = new ViewPurchase();
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
	public ViewPurchase() {
		setTitle("Purchase");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 952, 624);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 50, 926, 475);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		try {
			Connection connection = ConnectionHandler.createConnection();
			Statement statement = connection.createStatement();
			String query = "select * from transaction where type = \"PURCHASE\""; 
			ResultSet resultSet = statement.executeQuery(query);
			table.setModel(DbUtils.resultSetToTableModel(resultSet));
			resultSet.close();
			statement.close();
			connection.close();
		}catch(SQLException sq) {
			sq.printStackTrace();
		}
		
		JLabel lblPurcahse = new JLabel("Purcahse : ");
		lblPurcahse.setBounds(12, 12, 123, 17);
		contentPane.add(lblPurcahse);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		btnBack.setBounds(826, 544, 112, 39);
		contentPane.add(btnBack);
	}
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
}
