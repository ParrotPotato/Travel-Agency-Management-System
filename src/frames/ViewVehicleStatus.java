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
public class ViewVehicleStatus extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
					ViewVehicleStatus frame = new ViewVehicleStatus();
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
	public ViewVehicleStatus() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 952, 624);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 92, 926, 445);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		btnBack.setBounds(826, 544, 112, 39);
		contentPane.add(btnBack);
		
		JLabel lblNewLabel = new JLabel("Model ");
		lblNewLabel.setBounds(12, 12, 70, 17);
		contentPane.add(lblNewLabel);
		String models[] = getModels();
		JComboBox comboBox = new JComboBox(models);
		comboBox.setBounds(12, 41, 224, 33);
		contentPane.add(comboBox);
		
		JButton btnGetReport = new JButton("Get Report");
		btnGetReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int getIndex = comboBox.getSelectedIndex();
				if(models[getIndex].equals("") == true) {
					try {
						String query = "" ; 
						String selected_model= models[comboBox.getSelectedIndex()];
						connection = ConnectionHandler.createConnection();
						query = "select * from vehicle  " ;
						statement = connection.createStatement();
						resultSet = statement.executeQuery(query);
						table.setModel(DbUtils.resultSetToTableModel(resultSet));
						resultSet.close();
						statement.close();
						connection.close();
					}catch(SQLException sq) {
						sq.printStackTrace();
					}
				}
				else {
					try {
						String query = "" ; 
						String selected_model= models[comboBox.getSelectedIndex()];
						connection = ConnectionHandler.createConnection();
							query = "select * from vehicle where vehicle_model = \"" + selected_model + "\" " ;
						statement = connection.createStatement();
						resultSet = statement.executeQuery(query);
						table.setModel(DbUtils.resultSetToTableModel(resultSet));
						resultSet.close();
						statement.close();
						connection.close();
					}catch(SQLException sq) {
						sq.printStackTrace();
					}
				}
			}
		});
		btnGetReport.setBounds(826, 38, 112, 39);
		contentPane.add(btnGetReport);
	}
	public String[] getModels() {
		try {
			Connection connection = ConnectionHandler.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select model from vehicle_model");
			List<String> models  = new ArrayList<String>();
			models.add("");
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
		try {
			this.setVisible(false);
			this.dispose();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
