package epp.tgf.app.service.stadistic.chat.buy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import comparator.ia.app.dtos.stadistics.buy.PriceDifferenceAboveMarketDTO;
import comparator.ia.app.dtos.stadistics.buy.PriceIncreaseNeededToReachMarketDTO;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.repository.VehicleRepository;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.service.stadistic.chat.buy.StadisticsBuyApi;
import comparator.ia.app.service.stadistic.chat.buy.StadisticsBuyService;

@DataJpaTest
@Import(StadisticsBuyService.class)
class StadisticsBuyServiceTest {
	
	 private static final List<String> BRANDS = List.of("MERCEDES BENZ", "MERCEDES", "MERCEDES-BENZ");
	 private static final List<String> MODELS = List.of("M2", "M1");
	 private static final int YEAR = 2022;
	
	@Autowired
	private VehicleRepository repo;
	@Autowired
	private StadisticsBuyApi service;
	
	private SimilarCarOperationDTO dto;
	
	@BeforeEach
	void setUp() {
		createdSimilarCar();
		List<VehicleEntity> entities = new ArrayList<VehicleEntity>();
		// PRECIO MINIMO DENTRO DE LOS VAREMOS
		VehicleEntity v = new VehicleEntity();
		v.setBrand("BMW");
		v.setModel("SERIE 3");
		v.setPrice(8000.0);
		v.setYear(YEAR);
		v.setKilometers(12000);
		v.setHorsePower(150.0);
		v.setPublishedDate(LocalDateTime.now());
		v.setFuelType(FuelType.GASOLINE);
		entities.add(v);
		
		// PRECIO MAXIMO DENTRO DE LOS VAREMOS
		VehicleEntity v1 = new VehicleEntity();
		v1.setBrand("MERCEDES-BENZ");
		v1.setModel("M1");
		v1.setPrice(20000.0);
		v1.setYear(YEAR);
		v1.setKilometers(20000);
		v1.setHorsePower(200.0);
		v1.setPublishedDate(LocalDateTime.now());
		v1.setFuelType(FuelType.GASOLINE);
		entities.add(v1);
		
		// PRECIO DE MODA DENTRO DE LOS VAREMOS PERO CON DISTINTO CONBUSTIBLE
		VehicleEntity v2 = new VehicleEntity();
		v2.setBrand("MERCEDES-BENZ");
		v2.setModel("M1");
		v2.setPrice(18000.0);
		v2.setYear(YEAR);
		v2.setKilometers(20000);
		v2.setHorsePower(200.0);
		v2.setPublishedDate(LocalDateTime.now());
		v2.setFuelType(FuelType.ELECTRIC);
		entities.add(v2);
		
		VehicleEntity v3 = new VehicleEntity();
		v3.setBrand("MERCEDES-BENZ");
		v3.setModel("M2");
		v3.setPrice(18000.0);
		v3.setYear(YEAR);
		v3.setKilometers(20000);
		v3.setHorsePower(200.0);
		v3.setPublishedDate(LocalDateTime.now());
		v3.setFuelType(FuelType.HYBRID);
		entities.add(v3);
		
		// DENTRO DE LOS VAREMOS MENOS EN EL HP
		VehicleEntity v4 = new VehicleEntity();
		v4.setBrand("MERCEDES-BENZ");
		v4.setModel("M1");
		v4.setPrice(13000.0);
		v4.setYear(YEAR);
		v4.setKilometers(20000);
		v4.setHorsePower(400.0);
		v4.setPublishedDate(LocalDateTime.now());
		v4.setFuelType(FuelType.GASOLINE);
		entities.add(v4);
		
		repo.saveAll(entities);
	}
	
	@Test()
	@DisplayName("Comprueba el precio maximo del mercado.")
	void testMaxPrice() {
		Double maxPriceActual = service.getMaxPrice(dto);
		assertEquals(20000.0, maxPriceActual);
	}
	
	@Test
	@DisplayName("Comprueba el precio minimo del mercado.")
	void TestMinPrice() {
		dto.setBrand(null);
		dto.setModel(null);
		Double minPriceActual = service.getMinPrice(dto);
		assertEquals(8000.0, minPriceActual, "El precio minimo del mercado de todas las marcas");
	}
	
	@Test
	@DisplayName("Comprueba el precio de moda del mercado.")
	void TestModePrice() {
		dto.setModel(null);
		dto.setFuelType(FuelType.OTHER);
		Double minPriceActual = service.getModePrice(dto);
		assertEquals(18000.0, minPriceActual, "El precio de moda ignorando el tipo de combustible de los coches y el modelo del coche");
	}

	@Test
	@DisplayName("Comprueba la cantidad de coches que se vende por el precio indicado")
	void testGetCarsSellForThisPrice() {
		dto.setModel("M1");
		Integer valueActual = service.getCarsSellForThisPrice(dto);
		assertEquals(0, valueActual, "Espero " + 0 + " coches pero es valor actual es de ->" + valueActual);
	}
	
	 @Test
	 @DisplayName("Compruebo que el porcentage a pagar sea correcto.")
	 void testGetPercentageToPay() {
		 PriceIncreaseNeededToReachMarketDTO result = service.getPercentageToPain(10000.0, 15000.0);
		 assertEquals(5000, result.getMoney());
		 assertEquals(33.33, result.getPercentage());
	 }
	 
	 @Test
	 @DisplayName("Comprueba que el porcentage de ahorro sea correcto.")
	 void testGetPercentageToSave() {
		 PriceDifferenceAboveMarketDTO result = service.getPercentageToSave(10000.0, 5000.0);
		 assertEquals(5000.0, result.getMoney());
		 assertEquals(50.0, result.getPercentage());
	 }
	 
	private void createdSimilarCar() {
		dto = new SimilarCarOperationDTO();
		dto.setBrand("MERCEDES BENZ");
		dto.setModel("M1");
		dto.setKm(new Kilometers(20000, 12000));
		dto.setHp(new HorsePower(200, 150));
		dto.setYear(YEAR);
		Price price = new Price();
		price.setRangeBuy(new PriceRange(10000.0, 10000.0));
		dto.setPrice(price);
		dto.setFuelType(FuelType.GASOLINE);
		dto.setOperation(Operation.BUY);
	}
	 

}
