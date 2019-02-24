package frames;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AddNewModelTest {

	@Test
	void testAddNewModel() {
		AddNewModel addnewModel = new AddNewModel(null);
		String name = "Testing";
		int baseperhr = 100;
		int baseperkm = 5;
		int costvalue = 4000;
		assertEquals(true,addnewModel.addNewModel(name, baseperhr, baseperkm, costvalue));
	}

}
