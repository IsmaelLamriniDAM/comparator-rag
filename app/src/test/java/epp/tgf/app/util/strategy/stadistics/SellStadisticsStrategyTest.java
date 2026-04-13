package epp.tgf.app.util.strategy.stadistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.stadistic.chat.sell.StadisticsSellApi;
import comparator.ia.app.util.strategy.stadistics.SellStadisticsStrategy;
import comparator.ia.app.util.strategy.stadistics.StadisticsInput;

@ExtendWith(MockitoExtension.class)
class SellStadisticsStrategyTest {
	
	private static final Double[] PRICES = {10000.0, 20000.0, 15000.0, 15000.0};
	private static final Integer NUM_CREATE_CARS = PRICES.length; 
	private static final Double PRICE_BUY_USER = 12000.0;
	private static final Double PRICE_SELL_USER = 20000.0;
	
	@InjectMocks
	private SellStadisticsStrategy sellStadisticsStrategy;
	
	@Mock
	private StadisticsSellApi sellServiceMock;
	
	private StadisticsInput input;
	
	@BeforeEach
	void setUp() {
		configurationMockService();
		createInput();
	}

	@Test
	@DisplayName("Verify that method calculate() has been executed.")
	void testCalculate() {
		StadisticsVehicleChatDto dto = sellStadisticsStrategy.calculate(this.input);
		
		verify(sellServiceMock, times(1)).getModePrice(any());
		verify(sellServiceMock, times(1)).getMaxPrice(any());
		verify(sellServiceMock, times(1)).getMinPrice(any());
		verify(sellServiceMock, times(1)).calculatePercentageSell(any());
		verify(sellServiceMock, times(1)).calculateTimeAverageSell(any());
		
		assertNull(dto.getBothStadisticsDTO());
		assertNull(dto.getBuyStadisticsDTO());
		
		assertNotNull(dto.getSellStadisticsDTO().getMarketMaxSellPrice());
		assertNotNull(dto.getSellStadisticsDTO().getMarketMinSellPrice());
		assertNotNull(dto.getSellStadisticsDTO().getMarketMostFrequentSellPrice());
		assertNotNull(dto.getSellStadisticsDTO().getSellProbability());
		assertNotNull(dto.getSellStadisticsDTO().getAverageDaysToSell());
		
		assertNotNull(dto.getSellStadisticsDTO());
	}
	
	@Test
	@DisplayName("Verify that getOperation() is same to Operation Sell")
	void testGetOperation() {
		assertEquals(Operation.SELL, sellStadisticsStrategy.getOperation());
	}
	
	private void createInput() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		this.input = new StadisticsInput(dto);
	}
	
	
	private void configurationMockService() {
		lenient().when(sellServiceMock.getModePrice(any())).thenReturn(10.0);
		lenient().when(sellServiceMock.getMaxPrice(any())).thenReturn(20.0);
		lenient().when(sellServiceMock.getMinPrice(any())).thenReturn(5.0);
		lenient().when(sellServiceMock.calculatePercentageSell(any())).thenReturn(10.0);
		lenient().when(sellServiceMock.calculateTimeAverageSell(any())).thenReturn(5.5);
		
	}

}
