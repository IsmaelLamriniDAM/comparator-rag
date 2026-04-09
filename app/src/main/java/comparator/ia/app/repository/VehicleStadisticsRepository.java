package comparator.ia.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.service.manager.objUseInManager.Price;

public interface VehicleStadisticsRepository extends JpaRepository<VehicleEntity, Long> {
	
	// <<< STATISTICS BUY >>>
	@Query(value =
		    "SELECT MAX(price) " +
		    "FROM vehicles " +
		    "WHERE removal_date IS NULL " +
		    "AND (:model IS NULL OR model =  :model) " +
		    "AND (:brand IS NULL OR brand = :brand) " +
		    "AND (:minVh IS NULL OR horsepower >= :minVh) " +
		    "AND (:maxVh IS NULL OR horsepower <= :maxVh) " +
		    "AND (:minKm IS NULL OR kilometers >= :minKm) " +
		    "AND (:maxKm IS NULL OR kilometers <= :maxKm) " +
		    "AND (:year IS NULL OR vehicle_year = :year) " +
		    "AND (:fuelType IS NULL OR :fuelType = 'OTHER' OR fuel_type = :fuelType)",
		    nativeQuery = true)
	Double getMaxPriceBuy(@Param("model") String model, 
		    @Param("brand") String brand, 
		    @Param("minVh") Integer minVh, 
		    @Param("maxVh") Integer maxVh, 
		    @Param("minKm") Integer minKm, 
		    @Param("maxKm") Integer maxKm,
		    @Param("year") Integer year, 
		    @Param("fuelType") String fuelType
		    );
	
	@Query(value =
		    "SELECT MIN(price) " +
		    "FROM vehicles " +
		    "WHERE removal_date IS NULL " +
		    "AND (:model IS NULL OR model = :model) " +
		    "AND (:brand IS NULL OR brand = :brand) " +
		    "AND (:minVh IS NULL OR horsepower >= :minVh) " +
		    "AND (:maxVh IS NULL OR horsepower <= :maxVh) " +
		    "AND (:minKm IS NULL OR kilometers >= :minKm) " +
		    "AND (:maxKm IS NULL OR kilometers <= :maxKm) " +
		    "AND (:year IS NULL OR vehicle_year = :year) " +
		    "AND (:fuelType IS NULL OR :fuelType = 'OTHER' OR fuel_type = :fuelType)",
		    nativeQuery = true)
		Double getMinPriceBuy(
		    @Param("model") String model,
		    @Param("brand") String brand,
		    @Param("minVh") Integer minVh,
		    @Param("maxVh") Integer maxVh,
		    @Param("minKm") Integer minKm,
		    @Param("maxKm") Integer maxKm,
		    @Param("year") Integer year,
		    @Param("fuelType") String fuelType
		);
	
	@Query(value = "SELECT price FROM vehicles WHERE "
			+ "removal_date IS NULL "
			+  "AND (:model IS NULL OR model = :model) " 
		    + "AND (:brand IS NULL OR brand = :brand) " 
			+ "AND (:minVh IS NULL OR horsepower >= :minVh) "
			+ "AND (:maxVh IS NULL OR horsepower <= :maxVh) "
			+ "AND (:minKm IS NULL OR kilometers >= :minKm) "
			+ "AND (:maxKm IS NULL OR kilometers <= :maxKm) "
			+ "AND (:year IS NULL OR vehicle_year = :year) "
			+ "AND (:fuelType IS NULL OR :fuelType = 'OTHER' OR fuel_type = :fuelType) "
			+ "GROUP BY price "
			+ "ORDER BY COUNT(price) DESC "
			+ "LIMIT 1"
			, nativeQuery = true)
		Double getModePriceBuy(
				@Param("model") String model, 
			    @Param("brand") String brand, 
		    @Param("minVh") Integer minVh, 
		    @Param("maxVh") Integer maxVh, 
		    @Param("minKm") Integer minKm, 
		    @Param("maxKm") Integer maxKm,
		    @Param("year") Integer year, 
		    @Param("fuelType")  String fuelType
		);
	
	// <<< STATISTICS SELL >>>
	@Query("SELECT COUNT(v) FROM VehicleEntity v WHERE "
		    + "v.price BETWEEN :minPrice AND :maxPrice "
		    + "AND v.removalDate IS NULL "
		    + "AND (:model IS NULL OR model = :model) " 
		    + "AND (:brand IS NULL OR brand = :brand) " 
		    + "AND (:minVh IS NULL OR v.horsePower >= :minVh) "
		    + "AND (:maxVh IS NULL OR v.horsePower <= :maxVh) "
		    + "AND (:minKm IS NULL OR v.kilometers >= :minKm) "
		    + "AND (:maxKm IS NULL OR v.kilometers <= :maxKm) "
		    + "AND (:year IS NULL OR v.year = :year) "
		    + "AND (:fuelType IS NULL OR :fuelType = epp.tgf.app.enums.FuelType.OTHER OR v.fuelType = :fuelType)")
		Integer getCountVehiclesSell(
				@Param("model") String model, 
			    @Param("brand") String brand, 
		    @Param("minVh") Integer minVh, 
		    @Param("maxVh") Integer maxVh, 
		    @Param("minKm") Integer minKm, 
		    @Param("maxKm") Integer maxKm,
		    @Param("year") Integer year, 
		    @Param("fuelType") FuelType fuelType,
		    @Param("minPrice") Double minPrice,
		    @Param("maxPrice") Double maxPrice
		);
	
	@Query(value = "SELECT AVG(EXTRACT(EPOCH FROM (removal_date - published_date)) / 86400.0) "
		    + "FROM vehicles WHERE "
		    + "removal_date IS NOT NULL "
		    + "AND published_date >= :fromDate "
		    + "AND (:model IS NULL OR model = :model) "
		    + "AND (:brand IS NULL OR brand = :brand) "
		    +  "AND (:minVh IS NULL OR horsePower >= :minVh) "
		    +  "AND (:maxVh IS NULL OR horsePower <= :maxVh) "
		    + "AND (:minKm IS NULL OR kilometers >= :minKm) "
		    + "AND (:maxKm IS NULL OR kilometers <= :maxKm) "
		    + "AND (:year IS NULL OR vehicle_year = :year) "
		    + "AND (:fuelType IS NULL OR :fuelType = 'OTHER' OR fuel_type = :fuelType) "
		    + "AND (:minPrice IS NULL OR price >= :minPrice) " 
		    + "AND (:maxPrice IS NULL OR price <= :maxPrice) ", 
		    nativeQuery = true)
		Double getAverageTimeSell(
				@Param("model") String model, 
			    @Param("brand") String brand,
		    @Param("minVh") Integer minVh, 
		    @Param("maxVh") Integer maxVh, 
		    @Param("minKm") Integer minKm, 
		    @Param("maxKm") Integer maxKm,
		    @Param("year") Integer year, 
		    @Param("fuelType") String fuelType,
		    @Param("minPrice") Double minPrice,
		    @Param("maxPrice") Double maxPrice,
		    @Param("fromDate") LocalDateTime fromDate
		);

	@Query(value = "SELECT MAX(price) FROM vehicles WHERE "
		    + "removal_date IS NOT NULL "
		    + "AND published_date >= :fromDate "
		    + "AND (:model IS NULL OR model =  :model) " 
		    + "AND (:brand IS NULL OR brand = :brand) " 
		    + "AND (:minVh IS NULL OR horsepower >= :minVh) "
		    + "AND (:maxVh IS NULL OR horsepower <= :maxVh) "
		    + "AND (:minKm IS NULL OR kilometers >= :minKm) "
		    + "AND (:maxKm IS NULL OR kilometers <= :maxKm) "
		    + "AND (:year IS NULL OR vehicle_year = :year) "
		    + "AND (:fuelType IS NULL OR :fuelType = 'OTHER' OR fuel_type = :fuelType)", 
		    nativeQuery = true)
		Double getMaxPriceSell(
				@Param("model") String model, 
			    @Param("brand") String brand,
		    @Param("minVh") Integer minVh, 
		    @Param("maxVh") Integer maxVh, 
		    @Param("minKm") Integer minKm,
		    @Param("maxKm") Integer maxKm,
		    @Param("year") Integer year, 
		    @Param("fuelType") String fuelType,
		    @Param("fromDate") LocalDateTime fromDate
		);

	@Query(value = "SELECT MIN(price) FROM vehicles WHERE "
		    + "removal_date IS NOT NULL "
		    + "AND published_date >= :fromDate " 
		    + "AND (:model IS NULL OR model =  :model) " 
		    + "AND (:brand IS NULL OR brand = :brand) " 
		    + "AND (:minVh IS NULL OR horsepower >= :minVh) "
		    + "AND (:maxVh IS NULL OR horsepower <= :maxVh) "
		    + "AND (:minKm IS NULL OR kilometers >= :minKm) "
		    + "AND (:maxKm IS NULL OR kilometers <= :maxKm) "
		    + "AND (:year IS NULL OR vehicle_year = :year) "
		    + "AND (:fuelType IS NULL OR :fuelType = 'OTHER' OR fuel_type = :fuelType)", 
		    nativeQuery = true)
		Double getMinPriceSell(
				@Param("model") String model, 
			    @Param("brand") String brand, 
		    @Param("minVh") Integer minVh, 
		    @Param("maxVh") Integer maxVh, 
		    @Param("minKm") Integer minKm,
		    @Param("maxKm") Integer maxKm,
		    @Param("year") Integer year, 
		    @Param("fuelType") String fuelType,
		    @Param("fromDate") LocalDateTime fromDate
		);

	@Query(value = "SELECT v.price FROM vehicles v WHERE "
		    + "v.published_date >= :fromDate "
		    + "AND v.removal_date IS NOT NULL "
		    + "AND (:model IS NULL OR model =  :model) " 
		    + "AND (:brand IS NULL OR brand = :brand) " 
		    + "AND (:minVh IS NULL OR v.horsepower >= :minVh) "
		    + "AND (:maxVh IS NULL OR v.horsepower <= :maxVh) "
		    + "AND (:minKm IS NULL OR v.kilometers >= :minKm) "
		    + "AND (:maxKm IS NULL OR v.kilometers <= :maxKm) "
		    + "AND (:year IS NULL OR v.vehicle_year = :year) "
		    + "AND (:fuelType IS NULL OR :fuelType = 'OTHER' OR v.fuel_type = :fuelType) "
		    + "GROUP BY v.price "
		    + "ORDER BY COUNT(*) DESC "
		    + "LIMIT 1", nativeQuery = true)
		Double getModePriceSell(
				@Param("model") String model, 
			    @Param("brand") String brand, 
		    @Param("minVh") Integer minVh, 
		    @Param("maxVh") Integer maxVh, 
		    @Param("minKm") Integer minKm, 
		    @Param("maxKm") Integer maxKm,
		    @Param("year") Integer year, 
		    @Param("fuelType") String fuelType,
		    @Param("fromDate") LocalDateTime fromDate
		);

}
