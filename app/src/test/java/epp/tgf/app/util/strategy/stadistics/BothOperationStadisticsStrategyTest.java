package epp.tgf.app.util.strategy.stadistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.dtos.stadistics.both.SaleProfitDTO;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.service.stadistic.chat.both.StadisticsBothApi;
import comparator.ia.app.util.strategy.stadistics.BothOperationStadisticsStrategy;
import comparator.ia.app.util.strategy.stadistics.StadisticsInput;

@ExtendWith(MockitoExtension.class)
class BothOperationStadisticsStrategyTest {
	
	@InjectMocks
	private BothOperationStadisticsStrategy bothOperationStadisticsStrategy;

	@Mock
	private StadisticsBothApi serviceMock;
	
	private StadisticsInput inputWithoutPrice;
	
	private StadisticsInput inputWithPrice;
	
	@BeforeEach
	void setUp() {
		configurationMockService();
	}

	@Test
	@DisplayName("Comprueba el calculo sin especificar el precio de compra")
	void testCalculateWithoutPrice() {
		createInputWithoutPrice();
		StadisticsVehicleChatDto dto = bothOperationStadisticsStrategy.calculate(this.inputWithoutPrice);
		
		verify(serviceMock).calculateMinPriceBuy(any());
		verify(serviceMock).calculateProfabilitySell(any());
		verify(serviceMock).calculateTimeAverageSell(any());
		verify(serviceMock, never()).calculateSaleProfit(any(), any());
		verify(serviceMock, never()).calculateCountCarsSell(any());
		
		assertNull(dto.getBuyStadisticsDTO());
		assertNull(dto.getSellStadisticsDTO());
		
		assertNull(dto.getBothStadisticsDTO().getEstimatedSaleProfit());
		assertNull(dto.getBothStadisticsDTO().getMarketSellComparableVehicleCount());

		assertNotNull(dto.getBothStadisticsDTO().getAverageDaysToSell());
		assertNotNull(dto.getBothStadisticsDTO().getSellSuccessProbability());
		assertNotNull(dto.getBothStadisticsDTO().getMarketMinBuyPrice());
		assertNotNull(dto.getBothStadisticsDTO());
	}
	
	@Test
	@DisplayName("Comprueba el calculo especificando el precio de compra")
	void testCalculateWithPrice() {
		createInputWithPrice();
		StadisticsVehicleChatDto dto = bothOperationStadisticsStrategy.calculate(this.inputWithPrice);
		
		verify(serviceMock).calculateMinPriceBuy(any());
		verify(serviceMock).calculateProfabilitySell(any());
		verify(serviceMock).calculateTimeAverageSell(any());
		verify(serviceMock).calculateSaleProfit(any(), any());
		verify(serviceMock).calculateCountCarsSell(any());
		
		assertNull(dto.getBuyStadisticsDTO());
		assertNull(dto.getSellStadisticsDTO());

		assertNotNull(dto.getBothStadisticsDTO().getMarketSellComparableVehicleCount());
		assertNotNull(dto.getBothStadisticsDTO().getEstimatedSaleProfit());
		assertNotNull(dto.getBothStadisticsDTO().getAverageDaysToSell());
		assertNotNull(dto.getBothStadisticsDTO().getSellSuccessProbability());
		assertNotNull(dto.getBothStadisticsDTO().getMarketMinBuyPrice());
		assertNotNull(dto.getBothStadisticsDTO());
	}
	
	@Test
	@DisplayName("Comprueba que la operacion a realizar sea la de BOTH.")
	void testGetOperation() {
		assertEquals(Operation.BOTH, bothOperationStadisticsStrategy.getOperation());
	}
	
	private void createInputWithPrice() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		Price price = new Price();
		price.setRangeBuy(new PriceRange(null, null));
		dto.setPrice(price);
		inputWithPrice = new StadisticsInput(dto);
		
	}
	
	private void createInputWithoutPrice() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		Price price = new Price();
		dto.setPrice(price);
		inputWithoutPrice = new StadisticsInput(dto);
	}
	
	private void configurationMockService() {
		lenient().when(serviceMock.calculateMinPriceBuy(any())).thenReturn(10.0);
		lenient().when(serviceMock.calculateCountCarsSell(any())).thenReturn(2);
		lenient().when(serviceMock.calculateSaleProfit(any(), any())).thenReturn(new SaleProfitDTO());
		lenient().when(serviceMock.calculateProfabilitySell(any())).thenReturn(10.0);
		lenient().when(serviceMock.calculateTimeAverageSell(any())).thenReturn(5.5);
		
	}

}
