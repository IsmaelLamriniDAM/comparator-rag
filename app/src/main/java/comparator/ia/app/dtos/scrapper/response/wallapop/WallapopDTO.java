package comparator.ia.app.dtos.scrapper.response.wallapop;

import java.util.List;

public class WallapopDTO {
	public Data data;
    public Meta meta;
    	
    public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

    public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public static class Data {
        public Section section;

        public Section getSection() {
			return section;
		}

		public void setSection(Section section) {
			this.section = section;
		}

		public static class Section {
            public Payload payload;
  
            public Payload getPayload() {
				return payload;
			}

			public void setPayload(Payload payload) {
				this.payload = payload;
			}

			public static class Payload {
                public List<Item> items;

                public List<Item> getItems() {
					return items;
				}

				public void setItems(List<Item> items) {
					this.items = items;
				}

				public static class Item {
                    public String id;
                    public Price price;
                    public String web_slug;
                    public String created_at;
                    public TypeAttributes type_attributes;
                    public List<Images> images;


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

					public Price getPrice() {
						return price;
					}

					public void setPrice(Price price) {
						this.price = price;
					}

					public String getWeb_slug() {
						return web_slug;
					}

					public void setWeb_slug(String web_slug) {
						this.web_slug = web_slug;
					}

					public String getCreated_at() {
						return created_at;
					}

					public void setCreated_at(String created_at) {
						this.created_at = created_at;
					}

					public TypeAttributes getType_attributes() {
						return type_attributes;
					}

					public void setType_attributes(TypeAttributes type_attributes) {
						this.type_attributes = type_attributes;
					}

					public static class Price {
                        public Double amount;

                        public Double getAmount() {
							return amount;
						}

						public void setAmount(Double amount) {
							this.amount = amount;
						}

                    }
					
					public static class Images {
						public Urls urls;
						

						public Urls getUrls() {
							return urls;
						}


						public void setUrls(Urls urls) {
							this.urls = urls;
						}


						public class Urls {
							public String big;

							public String getBig() {
								return big;
							}

							public void setBig(String big) {
								this.big = big;
							}
						}
					}

					public static class TypeAttributes {
                        public String brand;
                        public String model;
                        public Long year;
                        public String engine;
                        public Long km;
                        public Double horsepower;

                        public String getBrand() {
							return brand;
						}

						public void setBrand(String brand) {
							this.brand = brand;
						}

						public String getModel() {
							return model;
						}

						public void setModel(String model) {
							this.model = model;
						}

						public Long getYear() {
							return year;
						}

						public void setYear(Long year) {
							this.year = year;
						}

						public String getEngine() {
							return engine;
						}

						public void setEngine(String engine) {
							this.engine = engine;
						}

						public Long getKm() {
							return km;
						}

						public void setKm(Long km) {
							this.km = km;
						}

						public Double getHorsepower() {
							return horsepower;
						}

						public void setHorsepower(Double horsepower) {
							this.horsepower = horsepower;
						}
                    }
                }
            }
        }
    }
	public static class Meta {
        public String next_page;

        public String getNext_page() {
			return next_page;
		}

		public void setNext_page(String next_page) {
			this.next_page = next_page;
		}
    }
}
