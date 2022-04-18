package petStoreTest;

import org.testng.annotations.DataProvider;

public class MyDataProvider {

	@DataProvider(name="testing")
	public Object[][] getData(){
		
		Object[][] data = new Object[3][1];
		
		data[0][0] = "available";
		data[1][0] = "pending";
		data[2][0] = "sold";
		
		return data;
	}
	
}
