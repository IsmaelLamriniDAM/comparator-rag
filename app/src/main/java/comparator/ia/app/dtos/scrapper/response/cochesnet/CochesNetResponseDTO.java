package comparator.ia.app.dtos.scrapper.response.cochesnet;

import java.util.List;

public class CochesNetResponseDTO {
	public  List<Item> items;
	public Meta meta;
	
	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public static class Item {
		public String id;
		public String make;
		public String model;
		public String fuelType;
		public Double hp;
		public String url;
		public Long km;
		public Long year;
		public String publishedDate;
		public Price price;
		public List<Resources> resources;
		
		
		public List<Resources> getResources() {
			return resources;
		}

		public void setResources(List<Resources> resources) {
			this.resources = resources;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getMake() {
			return make;
		}
		
		public void setMake(String make) {
			this.make = make;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getFuelType() {
			return fuelType;
		}

		public void setFuelType(String fuelType) {
			this.fuelType = fuelType;
		}

		public Double getHp() {
			return hp;
		}

		public void setHp(Double hp) {
			this.hp = hp;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Long getKm() {
			return km;
		}

		public void setKm(Long km) {
			this.km = km;
		}

		public Long getYear() {
			return year;
		}

		public void setYear(Long year) {
			this.year = year;
		}

		public String getPublishedDate() {
			return publishedDate;
		}

		public void setPublishedDate(String publishedDate) {
			this.publishedDate = publishedDate;
		}

		public Price getPrice() {
			return price;
		}

		public void setPrice(Price price) {
			this.price = price;
		}
		
		public static class Resources {
			public String type;
			public String url;
			
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public String getUrl() {
				return url;
			}
			public void setUrl(String url) {
				this.url = url;
			}
		}

		public static class Price {
			Double amount;

			public Double getAmount() {
				return amount;
			}

			public void setAmount(Double amount) {
				this.amount = amount;
			}
			
		}
	}
	
	public static class Meta {
		Integer totalPages;

		public Integer getTotalPages() {
			return totalPages;
		}

		public void setTotalPages(Integer totalPages) {
			this.totalPages = totalPages;
		}
		
	}
	
}
