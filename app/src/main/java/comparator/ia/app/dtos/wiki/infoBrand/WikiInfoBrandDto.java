package comparator.ia.app.dtos.wiki.infoBrand;

import java.util.List;

public class WikiInfoBrandDto {
	
	private List<Page> pages;
	
	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public static class Page{
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
	}
	
}	
