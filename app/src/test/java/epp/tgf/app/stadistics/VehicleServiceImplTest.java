package epp.tgf.app.stadistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.logic.CarCaractersiticsDto;
import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.entities.VehicleEntity;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.enums.WebName;
import comparator.ia.app.repository.VehicleRepository;
import comparator.ia.app.service.vehicle.VehicleServiceImpl;


@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {
  
    @Mock
    private VehicleRepository vehicleRepository; 

    @InjectMocks
    private VehicleServiceImpl vehicleService; 
    
    private VehicleEntity createVehicleEntity() {
    	VehicleEntity v = new VehicleEntity();
        v.setModel("A4");
        v.setBrand("Audi");
        v.setPrice(35000.0);
        v.setYear(2024);
        v.setKilometers(50000);
        v.setHorsePower(150.0);
        v.setFuelType(FuelType.DIESEL);
        v.setWebVehicleUrl("Coches.net");
        v.setPublishedDate(null);
        v.setWebName(WebName.OCASIONPLUS);
        return v;
    }
    
    private VehicleDto createVehicleDto() {
    	VehicleDto dto = new VehicleDto();
        dto.setBrand("Audi");
        dto.setModel("A4");
        dto.setYear(2024);
        dto.setFuelType(FuelType.DIESEL);
        dto.setHorsePower(150.0);
        dto.setKilometers(50000);
        dto.setWebVehicleUrl("Coches.net");
        dto.setPublishedDate(null);
        dto.setPrice(35000.0);
        dto.setWebName(WebName.OCASIONPLUS);
        return dto;
    }
    
    private CarCaractersiticsDto createInputDto() {
        CarCaractersiticsDto dto = new CarCaractersiticsDto();
        dto.setBrand("Audi");
        dto.setModel("A4");
        dto.setYear(2020);
        dto.setFuelType(FuelType.GASOLINE);
        dto.setHorsePower(150.0);
        dto.setMinKilometers(10000);
        dto.setMaxKilometers(60000);
        return dto;
    }

    @Test
    void findAllVehiclesMatchesWith_DeberiaLlamarAlRepositorioYMapearADto() {
        
        CarCaractersiticsDto carCaractersiticsDto = createInputDto();
        VehicleDto vehicleDto = createVehicleDto();
        List<VehicleEntity> entitiesFromDb = List.of(createVehicleEntity());
        
        when(vehicleRepository.findAllByModelAndBrandAndYearAndFuelTypeAndHorsePowerAndKilometersBetween(
            any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(entitiesFromDb);
        
        List<VehicleDto> resultList = vehicleService.findAllVehiclesMatchesWith(carCaractersiticsDto);

        verify(vehicleRepository).findAllByModelAndBrandAndYearAndFuelTypeAndHorsePowerAndKilometersBetween(
        		carCaractersiticsDto.getModel(),
        		carCaractersiticsDto.getBrand(), 
        		carCaractersiticsDto.getYear(), 
        		carCaractersiticsDto.getFuelType(), 
        		carCaractersiticsDto.getHorsePower(), 
        		carCaractersiticsDto.getMinKilometers(), 
        		carCaractersiticsDto.getMaxKilometers()
        );

        assertFalse(resultList.isEmpty(), "La lista de resultados no debe estar vacía.");
        assertEquals(1, resultList.size(), "Debe haber 1 elemento mapeado.");       

        assertEquals(vehicleDto.getBrand(), resultList.get(0).getBrand(), "Error en mapeo de Brand.");
        assertEquals(vehicleDto.getModel(), resultList.get(0).getModel(), "Error en mapeo de Model."); 
        assertEquals(vehicleDto.getPrice(), resultList.get(0).getPrice(), 0.001, "Error en mapeo de Price."); 
        assertEquals(vehicleDto.getKilometers(), resultList.get(0).getKilometers(), "Error en mapeo de Kilometers.");
        assertEquals(vehicleDto.getHorsePower(), resultList.get(0).getHorsePower(), 0.001, "Error en mapeo de HorsePower."); 
        assertEquals(vehicleDto.getFuelType(), resultList.get(0).getFuelType(), "Error en mapeo de FuelType.");
        assertEquals(vehicleDto.getWebName(), resultList.get(0).getWebName(), "Error en mapeo de la página Web.");
        assertEquals(vehicleDto.getWebVehicleUrl(), resultList.get(0).getWebVehicleUrl(), "Error en mapeo de WebVehicleUrl.");
        assertEquals(vehicleDto.getYear(), resultList.get(0).getYear(), "Error en mapeo de Year.");
        assertEquals(vehicleDto.getPublishedDate(), resultList.get(0).getPublishedDate(), "Error en mapeo de PublishedDate.");
        
    }
}
