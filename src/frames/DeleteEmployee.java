package frames;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import java.util.*;
import resources.ConnectionHandler;
import source.Employee;
import resources.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
public class DeleteEmployee extends JFrame {

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
					DeleteEmployee frame = new DeleteEmployee(null);
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
	public DeleteEmployee(Employee employee) {
		setTitle("Remove Employee");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("EmployeeName           : ");
		lblName.setBounds(79, 58, 166, 17);
		contentPane.add(lblName);
		
		JLabel lblNewLabel = new JLabel("Username                        :");
		lblNewLabel.setBounds(79, 120, 166, 17);
		contentPane.add(lblNewLabel);
		
		JLabel lblEmployeeid = new JLabel("EmployeeID                     :");
		lblEmployeeid.setBounds(79, 180, 166, 17);
		contentPane.add(lblEmployeeid);
		
		List <List <String>> lists = getEmployeeData();
		String names[] = lists.get(0).stream().toArray(String[]::new);
		String usernames[] = lists.get(1).stream().toArray(String[]::new);
		String ids[] = lists.get(2).stream().toArray(String[]::new);
		
		JComboBox nameBox = new JComboBox(names);
		JComboBox usernameBox = new JComboBox(usernames);
		JComboBox idBox = new JComboBox(ids);
		
		idBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					int id = idBox.getSelectedIndex();
					nameBox.setSelectedIndex(id);
					usernameBox.setSelectedIndex(id);
				}
			}
		});

		usernameBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					int id = usernameBox.getSelectedIndex();
					nameBox.setSelectedIndex(id);
					idBox.setSelectedIndex(id);
				}
			}
		});
		nameBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					int id = nameBox.getSelectedIndex();
					usernameBox.setSelectedIndex(id);
					idBox.setSelectedIndex(id);
				}
			}
		});
		nameBox.setBounds(257, 50, 247, 33);
		contentPane.add(nameBox);
		
		
		usernameBox.setBounds(257, 112, 247, 33);
		contentPane.add(usernameBox);
		
		
		idBox.setBounds(257, 172, 247, 33);
		contentPane.add(idBox);
		
		JButton remove = new JButton("Remove ");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = ids[idBox.getSelectedIndex()];
				String condition = "emp_id = " + id ;
				boolean test = Employee.deleteEmployee(condition);
				if(test == false) {
					JOptionPane.showMessageDialog(null, "Employee deletetion from database failed !! ");
					return;
				}
				JOptionPane.showMessageDialog(null, "Employee deleted from the database successfully !! ");
				EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
				employeeMenu.setVisible(true);
				close();
			}
		});
		remove.setBounds(79, 315, 112, 39);
		contentPane.add(remove);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeMenu employeeMenu = new EmployeeMenu(Employee.INSTANCE);
				employeeMenu.setVisible(true);
				close();
			}
		});
		cancel.setBounds(506, 315, 112, 39);
		contentPane.add(cancel);
	}
	
	public List < List <String> > getEmployeeData() {
		try {
			Connection connection = ConnectionHandler.createConnection();
			Statement statement = connection.createStatement();
			String getallresult = "Select * from employee";
			ResultSet resultSet = statement.executeQuery(getallresult);
			List <String> names = new ArrayList<String>();
			List <String> usernames = new ArrayList<String>();
			List <String> ids = new ArrayList<String>();
			names.add("");
			usernames.add("");
			ids.add("");
			while(resultSet.next() == true) {
				names.add(resultSet.getString("emp_name"));
				usernames.add(resultSet.getString("emp_username"));
				ids.add(Integer.toString(resultSet.getInt("emp_id")));
			}
			List <List <String> > arrayOfArray	 = new ArrayList<List <String> >();
			arrayOfArray.add(names);
			arrayOfArray.add(usernames);
			arrayOfArray.add(ids);
			
			return arrayOfArray;
		}catch(SQLException eq) {
			eq.printStackTrace();
			System.out.println("Error : SQLEXception occured in DeleteEMployee.getEmployeeData()");
			return null;
		}
		
	}
	public void close() {
		try {
			this.setVisible(false);
			this.dispose();
		}catch(Exception e) {
			System.out.println("Error : Exception occured in delete");
		}
	}
}
