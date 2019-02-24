package frames;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import source.Employee;

class PurchaseVehicleTest {

	@Test
	void testGetModels() {
		PurchaseVehicle instance = new PurchaseVehicle(null);
		String[] model = {"Ambassador","Audi","Fortuner","Mahindra Armada","Maruti Esteem","Maruti Omno","Mercedes ","Swift","Tata Sumo"};
		String[] testmodel = instance.getModels();
		for(int i=0 ; i < model.length ; i++) {
			assertEquals(model[i],testmodel[i]);
		}
	}

	@Test
	void testAddVehicle() {
		Employee testEmployee = new Employee(30023,"Nitesh Meena","password","niteshmeena");
		Employee.INSTANCE = testEmployee;
		PurchaseVehicle instance = new PurchaseVehicle(testEmployee);
		int result = 0 ; // 0 errors occured 
		int actualResult = instance.addVehicle("Testing",453678,false,testEmployee);
		assertEquals(result,actualResult);
		
		//as the vehicle with the given ID is already created we should get an error code of -1 
		result = -1 ; 
		actualResult = instance.addVehicle("Testing",453678,false,testEmployee);
		assertEquals(result,actualResult);
	}

}
