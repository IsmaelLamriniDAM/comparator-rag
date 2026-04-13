package comparator.ia.app.dtos.scrapper.response.ocasionplus;

import java.util.List;


public class OcasionPlusDTO {
    public List<Item> data;
    
    public List<Item> getData() {
		return data;
	}

	public void setData(List<Item> data) {
		this.data = data;
	}

	public static class Item {
    	String id;
    	String model;
    	String brand;
    	String slug;
    	Price price;
    	String publicationDate;
    	Characteristics characteristics;
    	List<Images> images;
    	
		public List<Images> getImages() {
			return images;
		}

		public void setImages(List<Images> images) {
			this.images = images;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}

		public Price getPrice() {
			return price;
		}

		public void setPrice(Price price) {
			this.price = price;
		}

		public Characteristics getCharacteristics() {
			return characteristics;
		}

		public void setCharacteristics(Characteristics characteristics) {
			this.characteristics = characteristics;
		}

		public String getSlug() {
			return slug;
		}

		public void setSlug(String slug) {
			this.slug = slug;
		}

		public String getPublicationDate() {
			return publicationDate;
		}

		public void setPublicationDate(String publicationDate) {
			this.publicationDate = publicationDate;
		}

		public static class Images {
			String thumb;

			public String getThumb() {
				return thumb;
			}

			public void setThumb(String thumb) {
				this.thumb = thumb;
			}		
		}

		public static class Price {
    		Double cash;

			public Double getCash() {
				return cash;
			}

			public void setCash(Double cash) {
				this.cash = cash;
			}
    		
    		
    	}
    	
    	public static class Characteristics {
    		Long kms;
    		Engine engine;
    		String registrationDate;
    		
    		public Long getKms() {
				return kms;
			}

			public void setKms(Long km) {
				this.kms = km;
			}

			public Engine getEngine() {
				return engine;
			}

			public void setEngine(Engine engine) {
				this.engine = engine;
			}

			public String getRegistrationDate() {
				return registrationDate;
			}

			public void setRegistrationDate(String registrationDate) {
				this.registrationDate = registrationDate;
			}



			public static class Engine {
    			Double cv;
    			String fuel;
    			
				public Double getCv() {
					return cv;
				}
				public void setCv(Double cv) {
					this.cv = cv;
				}
				public String getFuel() {
					return fuel;
				}
				public void setFuel(String fuel) {
					this.fuel = fuel;
				}
    			
    		}
    	}
    }
}