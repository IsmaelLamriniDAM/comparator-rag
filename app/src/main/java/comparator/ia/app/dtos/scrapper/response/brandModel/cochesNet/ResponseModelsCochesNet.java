package comparator.ia.app.dtos.scrapper.response.brandModel.cochesNet;

import java.util.List;


public class ResponseModelsCochesNet {
	
	private List<InfoModel> datalist;
	
	public List<InfoModel> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<InfoModel> datalist) {
		this.datalist = datalist;
	}

	public static class InfoModel {
		private String value;
		private String text;
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
	}
	
}
