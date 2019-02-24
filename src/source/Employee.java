package source;
import resources.*;
import java.sql.*;
public class Employee {
	public int id;
	private String name;
	private String password;
	private String username;
	private static final String EMPLOYEE_TABLE =  "employee" ;
	public static Employee INSTANCE ;
	
	public Employee(){
		id = 0 ; 
		name = null;
		password = null;
	}
	public Employee(int id,String name,String password,String username){
		this.id = id;
		this.name = name;
		this.password = password;
		this.username = username;
	}
	//getters for the Employee
	public int getID() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return this.password;
	}
	public String getUsername() {
		return this.username;
	}
	public void setID(int id) {
		this.id = id ;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username ;
	}
	
	
	public static Employee validateEmployee(String username,String password) {
		try {
			Connection connection = ConnectionHandler.createConnection();
			Statement statement = null;
			statement = connection.createStatement();
			String request = "select * from " + EMPLOYEE_TABLE ;
			ResultSet resultSet = statement.executeQuery(request);
			while(resultSet.next()) {
				int emp_id = resultSet.getInt("emp_id");
				String emp_name = resultSet.getString("emp_name");
				String emp_username = resultSet.getString("emp_username");
				String emp_password = resultSet.getString("emp_password");
				if(password.equals(emp_password) == true && username.equals(emp_username) == true) {
					resultSet.close();
					statement.close();
					connection.close();
					INSTANCE = new Employee();
					INSTANCE.setName(emp_name);
					INSTANCE.setPassword(emp_password);
					INSTANCE.setID(emp_id);
					INSTANCE.setUsername(emp_username);;
					resultSet.close();
					statement.close();
					connection.close();
					return INSTANCE;
				}
			}
			resultSet.close();
			statement.close();
			connection.close();
		}catch(SQLException eq) {
			System.out.println("SQLException Occured in validateEmployee ... ");
		}catch(Exception e) {
			System.out.println("Exception Occured in validateEmployee ... ");
		}
		return null;
	}
	public static boolean createEmployee(String name,String username,String password,int id) {
		try{
			if(name.isEmpty() == true || username.isEmpty() == true || password.isEmpty() == true || id == 0) {
				return false;
			}
			Connection connection = ConnectionHandler.createConnection();
			String request = "select emp_id from employee where emp_id = " + Integer.toString(id);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(request);
			while(rs.next()) {
				return false;
			}
			rs.close();
			statement.close();
			request = "insert into " + EMPLOYEE_TABLE + " values(?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			// TODO : Implement auto increment in the ID
			// Till then hard coding 
			
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, password);
			preparedStatement.setString(4, username);
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
			
			return true;
		}catch(SQLException eq) {
			eq.printStackTrace();
			System.out.println("Error : SQLException occured in EmployeeSignUp.CreateEmployee()");
			return false ;
		}
	}
	public static boolean deleteEmployee(String condition) {
		try {
			Connection connection = ConnectionHandler.createConnection();
			String testing = "select emp_id from employee where " + condition + " ";
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery(testing);
			boolean returnvalue = rs.next();
			if(returnvalue == false) {
				rs.close();
				stat.close();
				connection.close();
				return false;
			}
			rs.close();
			stat.close();
			String deleteRequest = "DELETE from " + EMPLOYEE_TABLE + " where " + condition + ";";
			Statement statement  = connection.createStatement();
			statement.execute(deleteRequest);
			statement.close();
			connection.close();
			return true;
		}catch(SQLException eq) {
			eq.printStackTrace();
			System.out.println("Error : SQLException occured in Employee.deleteEmployee()");
			return false;
		}finally {
			//TODO : Check if the close function has failed in the try section and then implement its execution in this block 
		}
	}
}
