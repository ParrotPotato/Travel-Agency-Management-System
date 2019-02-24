package frames;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.sql.*;
import javax.swing.*;
import resources.*;
import source.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class PurchseSellVehicle extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
					PurchseSellVehicle frame = new PurchseSellVehicle(null);
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
	public PurchseSellVehicle(Employee employee) {
		setTitle("Purchase Sell Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnPurchaseVehicle = new JButton("Purchase Vehicle");
		btnPurchaseVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PurchaseVehicle purchaseVehicle = new PurchaseVehicle(Employee.INSTANCE);
				purchaseVehicle.setVisible(true);
				close();
			}
		});
		btnPurchaseVehicle.setBounds(108, 46, 171, 39);
		contentPane.add(btnPurchaseVehicle);
		
		JButton btnSellVehicle = new JButton("Sell Vehicle");
		btnSellVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SellVehicle sellVehicle = new SellVehicle(Employee.INSTANCE);
				sellVehicle.setVisible(true);
				close();
			}
		});
		btnSellVehicle.setBounds(108, 120, 171, 39);
		contentPane.add(btnSellVehicle);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
				employeeMenu.setVisible(true);
				close();
			}
		});
		btnBack.setBounds(447, 315, 171, 39);
		contentPane.add(btnBack);
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
