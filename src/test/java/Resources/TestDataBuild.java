package Resources;

import java.util.ArrayList;
import java.util.List;

import pojo.GetCourse;
import pojo.location_new;

public class TestDataBuild {

	public GetCourse AddPLacePayload(String name, String language, String address) {
		
	GetCourse getc = new GetCourse();
	getc.setAccuracy(50);
	getc.setAddress(address);
	getc.setLanguage(language);
	getc.setPhone_number("9050909709");
	getc.setWebsite("sourabhsharma@qainfotech.com");
	getc.setName(name);
	
	List<String> typlst = new ArrayList<String>();
	typlst.add("ShoePark");
	typlst.add("shop");
	
	getc.setTypes(typlst);
	
	location_new loc = new location_new();
	loc.setLat(-33.4848);
	loc.setLng(534.32342);
	
	getc.setLocation(loc);
	
	return getc;
	
	}
	
}
