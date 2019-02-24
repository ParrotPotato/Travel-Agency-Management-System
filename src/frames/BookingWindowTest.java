package frames;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BookingWindowTest {
	
	@Test
	void testGetModels() {
		BookingWindow bookingWindow = new BookingWindow(null);
		String[] model = {"Ambassador","Audi","Fortuner","Mahindra Armada","Maruti Esteem","Maruti Omno","Mercedes ","Swift","Tata Sumo"};
		String[] testmodel = bookingWindow.getModels();
		for(int i=0 ; i < model.length ; i++) {
			assertEquals(model[i],testmodel[i]);
		}
	}
	@Test
	void testConfirmBooking() {
		// not a way to test this 
	}
	@Test
	void testIsIDLE() {
		BookingWindow bookingWindow = new BookingWindow(null);
		int id = 111;
		assertEquals(true,bookingWindow.isIDLE(id));
		
		id = 6223;
		assertEquals(false,bookingWindow.isIDLE(id));
		
		id = 71232;
		assertEquals(false,bookingWindow.isIDLE(id));
	}
}
