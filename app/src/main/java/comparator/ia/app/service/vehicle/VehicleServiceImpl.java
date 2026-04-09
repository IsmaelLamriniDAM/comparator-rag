package comparator.ia.app.service.vehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comparator.ia.app.dtos.chat.CarSendRagDTO;
import comparator.ia.app.dtos.logic.CarCaractersiticsDto;
import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.WebName;
import comparator.ia.app.mapper.entityVehicle.EntityVehicleMapper;
import comparator.ia.app.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {

	private final VehicleRepository vehicleRepository;

	public VehicleServiceImpl(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}

	@Override
	public List<VehicleDto> findAllVehiclesMatchesWith(CarCaractersiticsDto vehicle) {
		return vehicleRepository
				.findAllByModelAndBrandAndYearAndFuelTypeAndHorsePowerAndKilometersBetween(vehicle.getModel().toLowerCase(),
						vehicle.getBrand().toLowerCase(), vehicle.getYear(), vehicle.getFuelType(), vehicle.getHorsePower(),
						vehicle.getMinKilometers(), vehicle.getMaxKilometers())
				.stream().map(t -> {
					VehicleDto v = new VehicleDto();
					v.setBrand(t.getBrand());
					v.setModel(t.getModel());
					v.setFuelType(t.getFuelType());
					v.setHorsePower(t.getHorsePower());
					v.setKilometers(t.getKilometers());
					v.setPrice(t.getPrice());
					v.setPublishedDate(t.getPublishedDate().toString());
					v.setRemoveData(t.getRemovalDate().toString());
					v.setWebVehicleUrl(t.getWebVehicleUrl());
					v.setYear(t.getYear());
					v.setWebName(t.getWebName());
					v.setImage(t.getWebVehicleImg());
					return v;
				}).toList();
	}

	@Override
	@Transactional
	public List<VehicleEntity> save(Collection<VehicleEntity> filteredVehicles) {
	    if (filteredVehicles.isEmpty()) {
	        return new ArrayList<>();
	    }
	    
	    Set<VehicleEntity> vehicles = filteredVehicles.stream().collect(Collectors.toCollection(HashSet::new)); 
	    WebName webName = vehicles.stream().findAny().get().getWebName();
	    LocalDateTime scrapDate = LocalDateTime.now();
	    
	    Set<String> existingIdsSet = new HashSet<>(vehicleRepository.findAllWebVehicleIdsByWebName(webName));
	    Set<String> scrapedIds = filteredVehicles.stream()
	            .map(VehicleEntity::getWebVehicleId)
	            .collect(Collectors.toSet());
	    
	    long newInsertions = scrapedIds.stream()
	            .filter(id -> !existingIdsSet.contains(id))
	            .count();
	    
	    
	    vehicleRepository.updateRemovalDateByWebNameAndWebVehicleIdNotIn(webName, new ArrayList<>(scrapedIds), scrapDate);
	    
	    int vehiclesToMarkAsRemoved = existingIdsSet.size() - scrapedIds.size();
	    vehicleRepository.deleteByWebNameAndRemovalDateIsNull(webName);
	    
	    vehicles = vehicles.stream()
	            .filter(v -> !vehicleRepository.existsDuplicateCrossWeb(v.getBrand(), v.getModel(), v.getYear(), v.getKilometers(), v.getFuelType(), webName))
	            .collect(Collectors.toCollection(HashSet::new));
	    vehicles.forEach(v -> v.setUpdatedAt(scrapDate));
	    return vehicleRepository.saveAll(vehicles);
	}

	@Override
	public void removeDate(LocalDateTime scrapDate, WebName webName) {
		vehicleRepository.updateSoldVehicles(scrapDate, webName);
		
	}

	@Override
	public List<VehicleDto> getAllCarsForUrls(List<String> urls) {
		return vehicleRepository
				.findAllByWebVehicleUrl(urls)
				.stream()
				.map(v -> {
					VehicleDto dto = new VehicleDto();
					dto.setBrand(v.getBrand());
					dto.setModel(v.getModel());
					dto.setFuelType(v.getFuelType());
					dto.setHorsePower(v.getHorsePower());
					dto.setKilometers(v.getKilometers());
					dto.setPrice(v.getPrice());
					dto.setPublishedDate(v.getPublishedDate().toString());
					dto.setRemoveData(v.getRemovalDate().toString());
					dto.setWebVehicleUrl(v.getWebVehicleUrl());
					dto.setYear(v.getYear());
					dto.setWebName(v.getWebName());
					return dto;
				}).toList();
	}

	@Override
	public CarSendRagDTO findVehicleMinPrice(SimilarCarOperationDTO vehicle) {
		VehicleEntity vehicleEntity = vehicleRepository.findVehicleMinPrice(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp()
				, vehicle.getKm(), vehicle.getYear(), vehicle.getFuelType());
		return new CarSendRagDTO(vehicleEntity.getBrand(), vehicleEntity.getModel(), vehicleEntity.getPrice(), vehicleEntity.getKilometers(), vehicleEntity.getHorsePower(),
				vehicleEntity.getFuelType(), vehicleEntity.getWebVehicleUrl(), vehicleEntity.getPublishedDate().toString(), vehicleEntity.getRemovalDate().toString());
	}

	@Override
	public CarSendRagDTO findVehicleMaxPrice(SimilarCarOperationDTO vehicle) {
		VehicleEntity vehicleEntity = vehicleRepository.findVehicleMaxPrice(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp()
				, vehicle.getKm(), vehicle.getYear(), vehicle.getFuelType());
		return new CarSendRagDTO(vehicleEntity.getBrand(), vehicleEntity.getModel(), vehicleEntity.getPrice(), vehicleEntity.getKilometers(), vehicleEntity.getHorsePower(),
				vehicleEntity.getFuelType(), vehicleEntity.getWebVehicleUrl(), vehicleEntity.getPublishedDate().toString(), vehicleEntity.getRemovalDate().toString());
	}

	@Override
	public Long getCountVehiclesCompared(SimilarCarOperationDTO vehicle) {
		return vehicleRepository.getCountVehiclesCompared(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(),  vehicle.getHp().max(), vehicle.getKm().min(),  vehicle.getKm().max(),
				vehicle.getYear(), vehicle.getFuelType().name());
	}

	@Override
	public boolean existsVehiclesInDB() {
		return vehicleRepository.existsBy();
	}

	@Override
	public List<VehicleEntity> findVehiclesCompared(SimilarCarOperationDTO vehicle) {
		return vehicleRepository.findAllCarsThatHasBeenCompared(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(), vehicle.getKm().max(), vehicle.getYear(), vehicle.getFuelType().name());
	}

	@Override
	public List<VehicleEntity> findVehiclesComparedSell(SimilarCarOperationDTO vehicle) {
		return vehicleRepository.getVechilesForThisParameters(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(), vehicle.getKm().max(), vehicle.getYear(), vehicle.getFuelType(), LocalDateTime.now().minusDays(90));
	}

	@Override
	public List<VehicleEntity> findVehiclesComparedBoth(SimilarCarOperationDTO vehicle) {
		return  vehicleRepository.getVechilesForThisParameters(vehicle.getModel(), vehicle.getBrand(), vehicle.getHp().min(), vehicle.getHp().max(), vehicle.getKm().min(), vehicle.getKm().max(), vehicle.getYear(), vehicle.getFuelType(), LocalDateTime.now().minusDays(90));
	}
}
