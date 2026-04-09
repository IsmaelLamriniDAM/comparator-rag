package comparator.ia.app.dtos.scrapper.response.brandModel.cochesNet;

import java.util.List;

public class ResponseBrandsCochesNet {

	private List<DataBrand> items;
	
	public List<DataBrand> getItems() {
		return items;
	}

	public void setItems(List<DataBrand> items) {
		this.items = items;
	}

	public static class DataBrand {
		private String makeId;
		private String name;
		public String getMakeId() {
			return makeId;
		}
		public void setMakeId(String makeId) {
			this.makeId = makeId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	} 
	
	
	
}
