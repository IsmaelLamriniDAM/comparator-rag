package comparator.ia.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.enums.WebName;
import comparator.ia.app.service.manager.objUseInManager.Price;
import jakarta.transaction.Transactional;


@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long>{
	List<VehicleEntity> findAllByModelAndBrandAndYearAndFuelTypeAndHorsePowerAndKilometersBetween(
			String model,
			String brand,
			Integer year,
			FuelType fuelType,
			Double horsePower,
			Integer minKilometers,
			Integer maxKilometers
			);
	@Query("SELECT v.webVehicleId FROM VehicleEntity v WHERE v.webName = :webName")
	List<String> findAllWebVehicleIdsByWebName(@Param("webName") WebName webName);

	@Modifying
	@Transactional
	@Query("UPDATE VehicleEntity v SET v.removalDate = :removalDate WHERE v.webName = :webName AND v.webVehicleId NOT IN :ids")
	void updateRemovalDateByWebNameAndWebVehicleIdNotIn(@Param("webName") WebName webName, @Param("ids") List<String> ids, @Param("removalDate") LocalDateTime removalDate);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM VehicleEntity v WHERE v.webName = :webName AND v.removalDate IS NULL")
	void deleteByWebNameAndRemovalDateIsNull(@Param("webName") WebName webName);
	
	@Query("SELECT COUNT(v) > 0 FROM VehicleEntity v WHERE v.brand = :brand AND v.model = :model AND v.year = :year AND v.kilometers = :kilometers AND v.fuelType = :fuelType AND v.webName != :webName AND v.removalDate IS NULL")
	boolean existsDuplicateCrossWeb(@Param("brand") String brand, @Param("model") String model, @Param("year") Integer year, @Param("kilometers") Integer kilometers, @Param("fuelType") FuelType fuelType, @Param("webName") WebName webName);
	
	@Query("SELECT v FROM VehicleEntity v WHERE "
		    + "v.publishedDate >= :ninetyDaysAgo "
		    + "AND (:model IS NULL OR model = :model) " 
		    + "AND (:brand IS NULL OR brand = :brand) " 
		    + "AND (:minVh IS NULL OR v.horsePower >= :minVh) "
		    + "AND (:maxVh IS NULL OR v.horsePower <= :maxVh) "
		    + "AND (:minKm IS NULL OR v.kilometers >= :minKm) "
		    + "AND (:maxKm IS NULL OR v.kilometers <= :maxKm) "
		    + "AND (:year IS NULL OR v.year = :year) "
		    + "AND (:fuelType IS NULL OR :fuelType = comparator.ia.app.enums.FuelType.OTHER OR v.fuelType = :fuelType) ")
		List<VehicleEntity> getVechilesForThisParameters(
		    @Param("model") String model,
		    @Param("brand") String brand,
		    @Param("minVh") Integer minVh,
		    @Param("maxVh") Integer maxVh,
		    @Param("minKm") Integer minKm,
		    @Param("maxKm") Integer maxKm,
		    @Param("year") Integer year,
		    @Param("fuelType") FuelType fuelType,
		    @Param("ninetyDaysAgo") LocalDateTime ninetyDaysAgo
		);
	
	@Query(value = "SELECT COUNT(*) FROM vehicles WHERE "
	        + "(:model IS NULL OR model = :model) "
	        + "AND (:brand IS NULL OR brand = :brand) "
	        + "AND (:minVh IS NULL OR horsepower >= :minVh) "
	        + "AND (:maxVh IS NULL OR horsepower <= :maxVh) "
	        + "AND (:minKm IS NULL OR kilometers >= :minKm) "
	        + "AND (:maxKm IS NULL OR kilometers <= :maxKm) "
	        + "AND (:year IS NULL OR vehicle_year = :year) "
	        + "AND (:fuelType IS NULL OR :fuelType = 'OTHER' OR fuel_type = :fuelType)",
	        nativeQuery = true)
	Long getCountVehiclesCompared(
	        @Param("model") String model,
	        @Param("brand") String brand,
	        @Param("minVh") Integer minVh,
	        @Param("maxVh") Integer maxVh,
	        @Param("minKm") Integer minKm,
	        @Param("maxKm") Integer maxKm,
	        @Param("year") Integer year,
	        @Param("fuelType") String fuelType
	);
	
	@Modifying
    @Transactional
    @Query("UPDATE VehicleEntity n SET n.removalDate = :fechaH WHERE n.webName = :webName AND n.updatedAt < :fechaH AND n.removalDate IS NULL")
    int updateSoldVehicles(@Param("fechaH") LocalDateTime fechaH,@Param("webName") WebName screapeda);
	
	 @Query(value = "SELECT * FROM vehicles WHERE web_vehicle_url IN(:urls)", nativeQuery = true)
	List<VehicleEntity> findAllByWebVehicleUrl(@Param("urls") List<String> urls);
	 
	 @Query(value = "SELECT * FROM vehicles WHERE MIN(price)"
			 + "AND (:model IS NULL OR model = :model) " 
			    + "AND (:brand IS NULL OR brand = :brand) " 
	 		+ "AND (:#{#vh == null} = true OR (horsePower >= :#{#vh?.min} AND horsePower <= :#{#vh?.max}))"
		    + "AND (:#{#km == null} = true OR (kilometers >= :#{#km?.min} AND kilometers <= :#{#km?.max}))"
		    + "AND (:year IS NULL OR vehicle_year = :year)"
		    + "AND (:fuelType IS NULL OR :fuelType = 'OTHER' OR fuel_Type = :fuelType)",
	 		nativeQuery = true)
	 VehicleEntity findVehicleMinPrice(
			 @Param("model") String model, 
			    @Param("brand") String brand, 
			    @Param("vh") HorsePower vh, 
			    @Param("km") Kilometers km,
			    @Param("year") Integer year, 
			    @Param("fuelType") FuelType fuelType);
	 
	 @Query(value = "SELECT * FROM vehicles WHERE MAX(price)"
			 + "AND (:model IS NULL OR model = :model) " 
			    + "AND (:brand IS NULL OR brand = :brand) " 
		 		+ "AND (:#{#vh == null} = true OR (horsePower >= :#{#vh?.min} AND horsePower <= :#{#vh?.max}))"
			    + "AND (:#{#km == null} = true OR (kilometers >= :#{#km?.min} AND kilometers <= :#{#km?.max}))"
			    + "AND (:year IS NULL OR vehicle_year = :year)"
			    + "AND (:fuelType IS NULL OR :fuelType = 'OTHER' OR fuel_Type = :fuelType)",
		 		nativeQuery = true)
	 VehicleEntity findVehicleMaxPrice(
			 @Param("model") String model, 
			    @Param("brand") String brand, 
			    @Param("vh") HorsePower vh, 
			    @Param("km") Kilometers km,
			    @Param("year") Integer year, 
			    @Param("fuelType") FuelType fuelType);
	 
	 boolean existsBy();
	 
	 @Query(value = "SELECT * FROM vehicles WHERE "
		        + "(:model IS NULL OR model = :model) "
		        + "AND (:brand IS NULL OR brand = :brand) "
		        + "AND (:minVh IS NULL OR horsepower >= :minVh) "
		        + "AND (:maxVh IS NULL OR horsepower <= :maxVh) "
		        + "AND (:minKm IS NULL OR kilometers >= :minKm) "
		        + "AND (:maxKm IS NULL OR kilometers <= :maxKm) "
		        + "AND (:year IS NULL OR vehicle_year = :year) "
		        + "AND (:fuelType IS NULL OR :fuelType = 'OTHER' OR fuel_type = :fuelType)",
		        nativeQuery = true)
	 List<VehicleEntity> findAllCarsThatHasBeenCompared(
			 	@Param("model") String model, 
			    @Param("brand") String brand, 
			    @Param("minVh") Integer minVh,
		        @Param("maxVh") Integer maxVh,
		        @Param("minKm") Integer minKm,
		        @Param("maxKm") Integer maxKm,
			    @Param("year") Integer year, 
			    @Param("fuelType") String fuelType
			    );
	 
	 
}
