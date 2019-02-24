package source;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmployeeTest {

	@Test
	void testValidateEmployee() {
		Employee employee = new Employee(30023,"Nitesh Meena","password","niteshmeena");
		Employee employee2 = Employee.validateEmployee("niteshmeena","password");
		assertEquals(employee.getName(),employee2.getName());
		assertEquals(employee.getID(),employee2.getID());
		assertEquals(employee.getUsername(),employee2.getUsername());
		assertEquals(employee.getPassword(),employee2.getPassword());
	}

	@Test
	void testCreateEmployee() {
		String name = "Chelsi Raheja";
		String password = "ttyl";	
		String username = "chelsi";
		int id = 10013;
		assertEquals(true,Employee.createEmployee(name, username, password, id));
		
		name = "Sudutt Harne";
		password = "bro";
		username = "sudo";
		id = 30036;
		assertEquals(true,Employee.createEmployee(name, username, password, id));
		
		name = "Nikhil Nayan Jha";
		password = "";
		username = "iamgreat";
		id = 30022;
		assertEquals(false,Employee.createEmployee(name, username, password, id));
	}

	@Test
	void testDeleteEmployee() {
		String condition = "emp_id = 10013";
		assertEquals(true,Employee.deleteEmployee(condition));
		
		condition = "emp_id = 30036";
		assertEquals(true,Employee.deleteEmployee(condition));
		
		condition = "emp_id = 30022";
		assertEquals(false,Employee.deleteEmployee(condition));

	}

}
