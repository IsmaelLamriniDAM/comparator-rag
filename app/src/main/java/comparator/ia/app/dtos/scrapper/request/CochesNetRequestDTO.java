package comparator.ia.app.dtos.scrapper.request;

import java.util.List;

public class CochesNetRequestDTO {
	public Pagination pagination;
	public Sort sort;
	public Filters filters;
	
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	public Filters getFilters() {
		return filters;
	}
	public void setFilters(Filters filters) {
		this.filters = filters;
	}
	
	public static class Pagination {
		public int page;
		public int size;
		
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		
		
	}
	
	public static class Sort {
		public String order;
        public String term;
        
		public String getOrder() {
			return order;
		}
		public void setOrder(String order) {
			this.order = order;
		}
		public String getTerm() {
			return term;
		}
		public void setTerm(String term) {
			this.term = term;
		}
        
	}
	
	public static class Filters {
        public Range price;
        public List<Integer> bodyTypeIds;
        public Categories categories;
        public List<Integer> drivenWheelsIds;
        public String entry;
        public List<Integer> environmentalLabels;
        public List<Integer> equipments;
        public List<Integer> fuelTypeIds;
        public boolean hasPhoto;
        public boolean hasWarranty;
        public Range hp;
        public boolean isCertified;
        public Range km;
        public Range luggageCapacity;
        public String maxTerms;
        public boolean onlyPeninsula;
        public List<Integer> offerTypeIds;
        public List<Integer> provinceIds;
        public String searchText;
        public int sellerTypeId;
        public Integer transmissionTypeId;
        public Range year;
       
        
        public Range getPrice() {
			return price;
		}

		public void setPrice(Range price) {
			this.price = price;
		}

		public List<Integer> getBodyTypeIds() {
			return bodyTypeIds;
		}

		public void setBodyTypeIds(List<Integer> bodyTypeIds) {
			this.bodyTypeIds = bodyTypeIds;
		}

		public Categories getCategories() {
			return categories;
		}

		public void setCategories(Categories categories) {
			this.categories = categories;
		}

		public List<Integer> getDrivenWheelsIds() {
			return drivenWheelsIds;
		}

		public void setDrivenWheelsIds(List<Integer> drivenWheelsIds) {
			this.drivenWheelsIds = drivenWheelsIds;
		}

		public String getEntry() {
			return entry;
		}

		public void setEntry(String entry) {
			this.entry = entry;
		}

		public List<Integer> getEnvironmentalLabels() {
			return environmentalLabels;
		}

		public void setEnvironmentalLabels(List<Integer> environmentalLabels) {
			this.environmentalLabels = environmentalLabels;
		}

		public List<Integer> getEquipments() {
			return equipments;
		}

		public void setEquipments(List<Integer> equipments) {
			this.equipments = equipments;
		}

		public List<Integer> getFuelTypeIds() {
			return fuelTypeIds;
		}

		public void setFuelTypeIds(List<Integer> fuelTypeIds) {
			this.fuelTypeIds = fuelTypeIds;
		}

		public boolean isHasPhoto() {
			return hasPhoto;
		}

		public void setHasPhoto(boolean hasPhoto) {
			this.hasPhoto = hasPhoto;
		}

		public boolean isHasWarranty() {
			return hasWarranty;
		}

		public void setHasWarranty(boolean hasWarranty) {
			this.hasWarranty = hasWarranty;
		}

		public Range getHp() {
			return hp;
		}

		public void setHp(Range hp) {
			this.hp = hp;
		}

		public boolean isCertified() {
			return isCertified;
		}

		public void setCertified(boolean isCertified) {
			this.isCertified = isCertified;
		}

		public Range getKm() {
			return km;
		}

		public void setKm(Range km) {
			this.km = km;
		}

		public Range getLuggageCapacity() {
			return luggageCapacity;
		}

		public void setLuggageCapacity(Range luggageCapacity) {
			this.luggageCapacity = luggageCapacity;
		}

		public String getMaxTerms() {
			return maxTerms;
		}

		public void setMaxTerms(String maxTerms) {
			this.maxTerms = maxTerms;
		}

		public boolean isOnlyPeninsula() {
			return onlyPeninsula;
		}

		public void setOnlyPeninsula(boolean onlyPeninsula) {
			this.onlyPeninsula = onlyPeninsula;
		}

		public List<Integer> getOfferTypeIds() {
			return offerTypeIds;
		}

		public void setOfferTypeIds(List<Integer> offerTypeIds) {
			this.offerTypeIds = offerTypeIds;
		}

		public List<Integer> getProvinceIds() {
			return provinceIds;
		}

		public void setProvinceIds(List<Integer> provinceIds) {
			this.provinceIds = provinceIds;
		}

		public String getSearchText() {
			return searchText;
		}

		public void setSearchText(String searchText) {
			this.searchText = searchText;
		}

		public int getSellerTypeId() {
			return sellerTypeId;
		}

		public void setSellerTypeId(int sellerTypeId) {
			this.sellerTypeId = sellerTypeId;
		}

		public Integer getTransmissionTypeId() {
			return transmissionTypeId;
		}

		public void setTransmissionTypeId(Integer transmissionTypeId) {
			this.transmissionTypeId = transmissionTypeId;
		}

		public Range getYear() {
			return year;
		}

		public void setYear(Range year) {
			this.year = year;
		}

		public static class Range {
            public Integer from;
            public Integer to;
            
			public Integer getFrom() {
				return from;
			}
			public void setFrom(Integer from) {
				this.from = from;
			}
			public Integer getTo() {
				return to;
			}
			public void setTo(Integer to) {
				this.to = to;
			}
        }
        
        public static class Categories {
        	public List<Integer> category1Ids;

			public List<Integer> getCategory1Ids() {
				return category1Ids;
			}

			public void setCategory1Ids(List<Integer> category1Ids) {
				this.category1Ids = category1Ids;
			}
        	
        }
        
        public static class VehicleFilter {
        	public Integer makeId;
        	public Integer modelId;
        	public String model;
        	public String version;
        	
			public Integer getMakeId() {
				return makeId;
			}
			public void setMakeId(Integer makeId) {
				this.makeId = makeId;
			}
			public Integer getModelId() {
				return modelId;
			}
			public void setModelId(Integer modelId) {
				this.modelId = modelId;
			}
			public String getModel() {
				return model;
			}
			public void setModel(String model) {
				this.model = model;
			}
			public String getVersion() {
				return version;
			}
			public void setVersion(String version) {
				this.version = version;
			}
        }
        
	}
}
