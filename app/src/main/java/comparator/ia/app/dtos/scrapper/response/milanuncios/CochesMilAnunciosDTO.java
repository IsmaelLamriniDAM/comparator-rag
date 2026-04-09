package comparator.ia.app.dtos.scrapper.response.milanuncios;

import java.util.ArrayList;
import java.util.List;

public class CochesMilAnunciosDTO {
	
	private List<Ads> ads;
	private List<Photos> photos;
	private Pagination pagination;
	
	
	

	@Override
	public String toString() {
		return "CochesMilAnunciosDTO [ads=" + ads + "]";
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public  List<Ads> getAds() {
		return ads;
	}
	
	public void setAds(List<Ads> ads) {
		this.ads = ads;
	}
	
	public List<Photos> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photos> photos) {
		this.photos = photos;
	}
	

	
	public static class Pagination {
		private String nextToken;
		private TotalHits totalHits;
		
		public String getNextToken() {
			return nextToken;
		}

		public void setNextToken(String nextToken) {
			this.nextToken = nextToken;
		}

		public TotalHits getTotalHits() {
			return totalHits;
		}

		public void setTotalHits(TotalHits totalHits) {
			this.totalHits = totalHits;
		}

		public static class TotalHits {
			private String value;

			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}
			
		}
	}

	public static class Photos  {
		private String adId;
		private List<String> imageUrls;
		
		public String getAdId() {
			return adId;
		}
		public void setAdId(String adId) {
			this.adId = adId;
		}
		public List<String> getImageUrls() {
			return imageUrls;
		}
		public void setImageUrls(List<String> imageUrls) {
			this.imageUrls = imageUrls;
		}
		
	}
	
	public static class Ads {
		
		private List<Attributes> attributes;
		private List<Category> categories; 
		private String id;
		private String publicationDate;
		private Price price;
		private String title;
//		private String description;
		private String updateDate;
		private String url;
		
		
		
		@Override
		public String toString() {
			return "Ads [atributtes=" + attributes + ", id=" + id + ", publicationDate=" + publicationDate + ", price="
					+ price + ", title=" + title + ", updateDate=" + updateDate + ", url=" + url + "]";
		}

		public String getUpdateDate() {
			return updateDate;
		}

		public void setUpdateDate(String updateDate) {
			this.updateDate = updateDate;
		}

		public  List<Attributes> getAttributes() {
			return attributes;
		}

		public void setAttributess(List<Attributes> attributes) {
			this.attributes = attributes;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPublicationDate() {
			return publicationDate;
		}

		public void setPublicationDate(String publicationDate) {
			this.publicationDate = publicationDate;
		}

		public Price getPrice() {
			return price;
		}

		public void setPrice(Price price) {
			this.price = price;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		public List<Category> getCategories() {
			return categories;
		}

		public void setCategories(List<Category> categories) {
			this.categories = categories;
		}

		public static class Category {
			private String name;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			@Override
			public String toString() {
				return "Category [name=" + name + "]";
			}
			
			
		}
		
		public static class Origin{
			private String name;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
			
		}
		
		public static class Price {
			
			private Cash cash;
			
			public Cash getCash() {
				return cash;
			}

			public void setCash(Cash cash) {
				this.cash = cash;
			}

			public static class Cash {
				private Double  value;
				
				@Override
				public String toString() {
					return "[value=" + value + "]";
				}
				
				public Double getValue() {
					return value;
				}
				
				public void setValue(Double value) {
					this.value = value;
				}
				
				
			}
		}

		public static class Attributes {
			private Field field;
			private Value value;
			
			public Field getField() {
				return field;
			}

			public void setFiel(Field field) {
				this.field = field;
			}

			public Value getValue() {
				return value;
			}

			public void setValue(Value value) {
				this.value = value;
			}
			
			public static class Field {
				public String raw;

				public String getRaw() {
					return raw;
				}

				public void setRaw(String raw) {
					this.raw = raw;
				}
			}
			
			public static class Value {
				private String raw;

				public String getRaw() {
					return raw;
				}

				public void setRaw(String raw) {
					this.raw = raw;
				}
				
			}
			
		}
		
	}
}
