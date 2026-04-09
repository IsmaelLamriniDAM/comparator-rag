package epp.tgf.app.stadistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.logic.CarCaractersiticsDto;
import comparator.ia.app.dtos.logic.DataDto;
import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.enums.FuelType;
import comparator.ia.app.service.stadistic.StatisticsServiceImpl;
import comparator.ia.app.service.vehicle.VehicleService;

@ExtendWith(MockitoExtension.class)
class StatisticsImplTest {

    @Mock
    private VehicleService vehicleService; 

    @InjectMocks
    private StatisticsServiceImpl statisticsImpl;

}