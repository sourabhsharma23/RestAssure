package Resources;

public enum APIresource {

	AddPlaceAPI("maps/api/place/add/json"),
	GetPlaceAPI("maps/api/place/get/json");
	
	private String resource;
	
	APIresource(String resource){
		
		this.resource = resource;
	}
	
	public String getResource() {
		
		return resource;
	}
	
}
