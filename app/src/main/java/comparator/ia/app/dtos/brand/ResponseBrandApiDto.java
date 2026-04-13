package comparator.ia.app.dtos.brand;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseBrandApiDto {
	
	@JsonProperty("Count")
	private Integer count;
	
	@JsonProperty("Results")
	private List<Data> results;
	
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Data> getResults() {
		return results;
	}

	public void setResults(List<Data> results) {
		this.results = results;
	}

	public static class Data{
		@JsonProperty("MakeId")
		private Long brandId;
		
		@JsonProperty("MakeName")
		private String brandName;

		public Long getBrandId() {
			return brandId;
		}

		public void setBrandId(Long brandId) {
			this.brandId = brandId;
		}

		public String getBrandName() {
			return brandName;
		}

		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}
		
	}
	
}
