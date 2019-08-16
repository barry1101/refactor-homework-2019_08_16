package sales;

import org.junit.Test;

public class SalesAppTest {

	@Test
	public void testGenerateSalesActivityReport() {
		
		SalesApp salesApp = new SalesApp();
		salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);
		
	}
}
