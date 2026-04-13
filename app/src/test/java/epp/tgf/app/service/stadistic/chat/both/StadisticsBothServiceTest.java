package epp.tgf.app.service.stadistic.chat.both;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import comparator.ia.app.dtos.chain.HorsePower;
import comparator.ia.app.dtos.chain.Kilometers;
import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.stadistics.both.SaleProfitDTO;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.repository.VehicleRepository;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.service.stadistic.chat.both.StadisticsBothService;

@DataJpaTest
@Import(StadisticsBothService.class)
class StadisticsBothServiceTest {
	
    private static final List<String> BRANDS = List.of("MERCEDES BENZ", "MERCEDES", "MERCEDES-BENZ");
    private static final List<String> MODELS = List.of("M2", "M1");
	
	@Autowired
	private StadisticsBothService stadisticsBothService; 
	@Autowired
	private VehicleRepository vehicleRepo;
	
	private SimilarCarOperationDTO caseProofSimilirCar = new SimilarCarOperationDTO();
	
	@BeforeEach
	void setUp() {
		persistenceVehiclesTemporarily();
		caseProofSetFieldsSimilarCarList();
	}

	@Test
	@DisplayName("Comprueba el precio minimo al que se se vende un coche segun los parametros especificados.")
	void testCalculateMinPriceBuy() {
		Double actual = stadisticsBothService.calculateMinPriceBuy(caseProofSimilirCar);
		assertEquals(13000.0, actual, "El minimo debe ser igual al esperado teniendo en cuenta todos los campos.");
	}
	
	@Test
	@DisplayName("Verifica las veces que se repite un precio dentro del rango de venta indicado por el usuario con los filtros impuestos.")
	void testCalculateCountCarsSell() {
		caseProofSimilirCar.setKm(new Kilometers(null, null));
		Integer actual = stadisticsBothService.calculateCountCarsSell(caseProofSimilirCar);
		assertEquals(0, actual, "El contador de N de coches que se vende dentro del rango aplicado debe ser igual al esperado sin tener en cuenta los kilometros.");
	}
	
	@Test 
	@DisplayName("Comprueba la probabilidad de venta teniendo en cuenta los parametros aplicados menos el año.")
	void testCalculateProfabilitySell() {
		caseProofSimilirCar.setYear(null);
		Double actual = stadisticsBothService.calculateProfabilitySell(caseProofSimilirCar);
		assertEquals(100.0, actual, "La probabilidad de venta dado debe tener dos decimales y si es >= 5 debe redondearse hacia arriba.");
	}
	
	@Test
	@DisplayName("Verifica el beneficio de la compra y venta respecto a la moda, teniendo en cuenta los parametros aplicados menos las marcas ni el modelo.")
	void testCalculateSaleProfit() {
		caseProofSimilirCar.setBrand(null);
		caseProofSimilirCar.setModel(null);
		SaleProfitDTO actualDto = stadisticsBothService.calculateSaleProfit(10000.0, 60000.0);
		
		assertEquals(5000, actualDto.getPrice(), "La diferencia se calcula con la mitad del rango de venta recibido - la moda actual del mercado.");
		assertEquals(50.0, actualDto.getPercentage(), "El porcentaje de venta debe ser de dos decimales.");
		
		SaleProfitDTO actualLossProfitDto = stadisticsBothService.calculateSaleProfit(40000.0, 60000.0);
		assertEquals(-25000.0, actualLossProfitDto.getPrice(), "La diferencia se calcula con la mitad del rango de venta recibido - la moda actual del mercado.");
		assertEquals(-62.5, actualLossProfitDto.getPercentage(), "El porcentaje de venta debe ser de dos decimales.");
	}
	
	@Test
	@DisplayName("Verifica el tiempo medio de venta segun las especificaciones del coche sin tener en cuenta la potencia.")
	void testCalculateTimeAverageSell() {
		caseProofSimilirCar.setHp(new HorsePower(null, null));
		Double averageActual = stadisticsBothService.calculateTimeAverageSell(caseProofSimilirCar);
		assertEquals(27.5, averageActual, "La media de tiempo que tarda en verderse un coche debe ser igual al esperado.");
	}
	
	private void persistenceVehiclesTemporarily() {
		List<VehicleEntity> listV = new ArrayList<VehicleEntity>();
		// DENTRO DE LOS VAREMOS
			VehicleEntity v1 = new VehicleEntity();
			 v1.setBrand("MERCEDES");
			 v1.setModel("M1");
	         v1.setPrice(13000.0);
	         v1.setYear(2025);
	         v1.setKilometers(20000);
	         v1.setPublishedDate(LocalDateTime.now().minusDays(21));
	         v1.setHorsePower(115.0);
	         v1.setFuelType(FuelType.ELECTRIC);
	         listV.add(v1);
	         
	         VehicleEntity v2 = new VehicleEntity();
			 v2.setBrand("MERCEDES BENZ");
			 v2.setModel("M2");
	         v2.setPrice(18050.0);
	         v2.setYear(2025);
	         v2.setKilometers(27000);
	         v2.setPublishedDate(LocalDateTime.now().minusDays(28));
	         v2.setHorsePower(190.0);
	         v2.setFuelType(FuelType.ELECTRIC);
	         listV.add(v2);
	         
	         VehicleEntity v3 = new VehicleEntity();
			 v3.setBrand("MERCEDES-BENZ");
			 v3.setModel("M2");
	         v3.setPrice(22000.0);
	         v3.setYear(2025);
	         v3.setKilometers(30000);
	         v3.setHorsePower(150.0);
	         v3.setPublishedDate(LocalDateTime.now().minusDays(33));
	         v3.setRemovalDate(LocalDateTime.now());
	         v3.setFuelType(FuelType.ELECTRIC);
	         listV.add(v3);
	         
	         VehicleEntity v4 = new VehicleEntity();
			 v4.setBrand("MERCEDES-BENZ");
			 v4.setModel("M1");
	         v4.setPrice(27334.0);
	         v4.setYear(2025);
	         v4.setKilometers(24500);
	         v4.setHorsePower(125.0);
	         v4.setPublishedDate(LocalDateTime.now().minusDays(22));
	         v4.setRemovalDate(LocalDateTime.now());
	         v4.setFuelType(FuelType.ELECTRIC);
	         listV.add(v4);
	         
	         VehicleEntity v9 = new VehicleEntity();
	         v9.setBrand("MERCEDES-BENZ");
			 v9.setModel("M1");
	         v9.setPrice(19334.0);
	         v9.setYear(2020);
	         v9.setKilometers(24500);
	         v9.setHorsePower(125.0);
	         v9.setPublishedDate(LocalDateTime.now().minusDays(19));
	         v9.setRemovalDate(LocalDateTime.now());
	         v9.setFuelType(FuelType.ELECTRIC);
	         listV.add(v9);
	         
	         // FUERA DE LOS VAREMOS
	         
	         VehicleEntity v5 = new VehicleEntity();
			 v5.setBrand("MERCEDES-BENZ");
			 v5.setModel("M1");
	         v5.setPrice(27334.0);
	         v5.setYear(2025);
	         v5.setKilometers(24500);
	         v5.setHorsePower(125.0);
	         v5.setPublishedDate(LocalDateTime.now().minusDays(22));
	         v5.setFuelType(FuelType.GLP);
	         listV.add(v5);
	         
	         VehicleEntity v6 = new VehicleEntity();
			 v6.setBrand("MERCEDES-BENZ");
			 v6.setModel("M1");
	         v6.setPrice(40000.0);
	         v6.setYear(2025);
	         v6.setKilometers(40000);
	         v6.setHorsePower(125.0);
	         v6.setPublishedDate(LocalDateTime.now().minusDays(22));
	         v6.setRemovalDate(LocalDateTime.now());
	         v6.setFuelType(FuelType.ELECTRIC);
	         listV.add(v6);
	         
	         VehicleEntity v7 = new VehicleEntity();
			 v7.setBrand("TOYOTA");
			 v7.setModel("T1");
	         v7.setPrice(15000.0);
	         v7.setYear(2025);
	         v7.setKilometers(30000);
	         v7.setHorsePower(115.0);
	         v7.setPublishedDate(LocalDateTime.now().minusDays(18));
	         v7.setRemovalDate(LocalDateTime.now());
	         v7.setFuelType(FuelType.ELECTRIC);
	         listV.add(v7);
	         
	         VehicleEntity v8 = new VehicleEntity();
			 v8.setBrand("TOYOTA");
			 v8.setModel("T2");
	         v8.setPrice(15000.0);
	         v8.setYear(2025);
	         v8.setKilometers(30000);
	         v8.setHorsePower(115.0);
	         v8.setPublishedDate(LocalDateTime.now().minusDays(30));
	         v8.setRemovalDate(LocalDateTime.now());
	         v8.setFuelType(FuelType.ELECTRIC);
	         listV.add(v8);
	         
	         vehicleRepo.saveAll(listV);
	}
	

	private void caseProofSetFieldsSimilarCarList() {
		caseProofSimilirCar.setBrand("MERCEDES BENZ");
		caseProofSimilirCar.setModel("M2");
		caseProofSimilirCar.setHp(new HorsePower(200, 100));
		caseProofSimilirCar.setKm(new Kilometers(30000, 20000));
		caseProofSimilirCar.setYear(2025);
		caseProofSimilirCar.setFuelType(FuelType.ELECTRIC);
		Price price1 = new Price();
		price1.setRangeBuy(new PriceRange(20000.0, 10000.0));
		price1.setRangeSell(new PriceRange(30000.0, 21000.0));
		caseProofSimilirCar.setPrice(price1);
		caseProofSimilirCar.setOperation(Operation.BOTH);
	}

}
