package resources;
import java.sql.*;

public class ConnectionHandler {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/tams?useSSL=false";
	static final String USER = "root";
	static final String PASS = "unknown-Man12";
	
	public static Connection createConnection(){
		Connection conn = null;
		try {
			//System.out.println("Connecting to" + DB_URL);
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			//System.out.println("Connecting to" + DB_URL);
		}catch (SQLException eq) {
			System.out.println("SQLException Occured in createConnections ... ");
			eq.printStackTrace();
			conn =null;
		} catch (ClassNotFoundException ec) {
			System.out.println("ClassNotFoundException Occured in createConnections ... ");
			ec.printStackTrace();
			conn =null;
		}
		return conn;
	}
}
