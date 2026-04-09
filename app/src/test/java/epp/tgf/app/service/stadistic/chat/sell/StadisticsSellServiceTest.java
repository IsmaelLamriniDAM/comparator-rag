package epp.tgf.app.service.stadistic.chat.sell;

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
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.repository.VehicleRepository;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.service.stadistic.chat.sell.StadisticsSellService;

@DataJpaTest
@Import({StadisticsSellService.class})
class StadisticsSellServiceTest {
	
	private static final int YEAR = 2022;
	private static final List<String> BRANDS = List.of("MERCEDES BENZ", "MERCEDES", "MERCEDES-BENZ");
    private static final List<String> MODELS = List.of("M2", "M1");

    @Autowired
    private StadisticsSellService service;
    @Autowired
    private VehicleRepository repository;
    
    private SimilarCarOperationDTO dto;

    @BeforeEach
	void setUp() {
		createdSimilarCar();
		List<VehicleEntity> entities = new ArrayList<>();

		// <Precio minimo , dentro de los varemos>
		VehicleEntity v1 = new VehicleEntity();
		v1.setBrand("MERCEDES BENZ");
		v1.setModel("M2");
		v1.setPrice(25000.0);
		v1.setYear(YEAR);
		v1.setKilometers(18000);
		v1.setHorsePower(160.0);
		v1.setPublishedDate(LocalDateTime.now().minusDays(24));
		v1.setRemovalDate(LocalDateTime.now());
		entities.add(v1);

		// <Precio maximo , dentro de los varemos>
		VehicleEntity v2 = new VehicleEntity();
		v2.setBrand("MERCEDES BENZ");
		v2.setModel("M2");
		v2.setPrice(36000.0);
		v2.setYear(YEAR);
		v2.setKilometers(24000);
		v2.setHorsePower(240.0);
		v2.setPublishedDate(LocalDateTime.now().minusDays(21));
		v2.setRemovalDate(LocalDateTime.now());
		entities.add(v2);
		
		// <Precio moda, dentro de los varemos>
		VehicleEntity v3 = new VehicleEntity();
		v3.setBrand("MERCEDES BENZ");
		v3.setModel("M2");
		v3.setPrice(31000.0);
		v3.setYear(YEAR);
		v3.setKilometers(21000);
		v3.setHorsePower(201.0);
		v3.setPublishedDate(LocalDateTime.now().minusDays(22));
		v3.setRemovalDate(LocalDateTime.now());
		entities.add(v3);
		
		VehicleEntity v4 = new VehicleEntity();
		v4.setBrand("MERCEDES BENZ");
		v4.setModel("M1");
		v4.setPrice(31000.0);
		v4.setYear(YEAR);
		v4.setKilometers(21000);
		v4.setHorsePower(201.0);
		v4.setPublishedDate(LocalDateTime.now().minusDays(22));
		v4.setRemovalDate(LocalDateTime.now());
		entities.add(v4);
		
		// <Dentro de los varemos pero no esta vendido>
		VehicleEntity v5 = new VehicleEntity();
		v5.setBrand("MERCEDES BENZ");
		v5.setModel("M1");
		v5.setPrice(36000.0);
		v5.setYear(YEAR);
		v5.setKilometers(21000);
		v5.setHorsePower(201.0);
		v5.setPublishedDate(LocalDateTime.now().minusDays(22));
		entities.add(v5);
		
		// dentro de lo varemos pero con diferente marca y modelo
		VehicleEntity v6 = new VehicleEntity();
		v6.setBrand("BMW");
		v6.setModel("SERIE 3");
		v6.setPrice(34000.0);
		v6.setYear(YEAR);
		v6.setKilometers(20000);
		v6.setHorsePower(203.0);
		v6.setPublishedDate(LocalDateTime.now().minusDays(26));
		v6.setRemovalDate(LocalDateTime.now());
		entities.add(v6);
		
		VehicleEntity v7 = new VehicleEntity();
		v7.setBrand("BMW");
		v7.setModel("SERIE 2");
		v7.setPrice(40000.0);
		v7.setYear(YEAR);
		v7.setKilometers(20000);
		v7.setHorsePower(203.0);
		v7.setPublishedDate(LocalDateTime.now().minusDays(24));
		v7.setRemovalDate(LocalDateTime.now());
		entities.add(v7);
	    
		repository.saveAll(entities);
    }

	@Test
    @DisplayName("Comprueba que el porcentaje de venta sea el correcto.")
    void TestCalculatePercentage() {
		dto.setBrand(null);
    	dto.setModel(null);
        Double percentageActual = service.calculatePercentageSell(dto);
        assertEquals(83.33 , percentageActual, "Porcentaje actual sin tener en cuenta ni la marca y el modelo.");
    }

    @Test
    @DisplayName("Testea el tiempo medio que tarda en venderse en dias un vehiculo.")
    void TestCalculateTimeSell() {
    	dto.setBrand(null);
    	dto.setModel(null);
    	Price price = new Price();
    	price.setRangeSell(new PriceRange(null, null));
    	dto.setPrice(price);
    	
        Double actual = service.calculateTimeAverageSell(dto);
        assertEquals(23.17, actual, "El porcentaje debe ser de 2 decimales y no se especifica la marca, modelo y precio");
    }

    @Test
    @DisplayName("Comprueba el calculo de la moda del mercado.")
    void testCalculateModePrice() {
        Double actual = service.getModePrice(dto);
        assertEquals(31000.0, actual, "La moda del mercado");
    }

    @Test
    @DisplayName("Verifica el precio minimo.")
    void testCalculateMinPrice(){
    	dto.setBrand(null);
    	dto.setModel("M2");
        Double actual = service.getMinPrice(dto);
        assertEquals(25000.0, actual, "El precio minimo de los modelo M2");
    }

    @Test
    @DisplayName("Comprueba el precio maximo, al que se vendio un vehiculo, segun las caracteristicas del coche ofrecido.")
    void testCalculateMaxPrice(){
    	dto.setBrand("BMW");
    	dto.setModel(null);
        Double actual = service.getMaxPrice(dto);
        assertEquals(40000.0, actual, "EL precio maximo de cualquier modelo de bmws");
    }
    
    private void createdSimilarCar() {
		dto = new SimilarCarOperationDTO();
		dto.setBrand("MERCEDES BENZ");
		dto.setModel("M1");
		dto.setKm(new Kilometers(20000, 20000));
		dto.setHp(new HorsePower(200, 200));
		dto.setYear(2022);
		Price price = new Price();
		price.setRangeSell(new PriceRange(30000.0, 30000.0));
		dto.setPrice(price);
		dto.setFuelType(FuelType.OTHER);
		dto.setOperation(Operation.SELL);
	}
}
