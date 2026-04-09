package epp.tgf.app.util.strategy.stadistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.chain.price.PriceRange;
import comparator.ia.app.dtos.logic.VehicleDto;
import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.dtos.stadistics.buy.PriceDifferenceAboveMarketDTO;
import comparator.ia.app.dtos.stadistics.buy.PriceIncreaseNeededToReachMarketDTO;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.service.manager.objUseInManager.Price;
import comparator.ia.app.service.stadistic.chat.buy.StadisticsBuyApi;
import comparator.ia.app.util.strategy.stadistics.BuyStadisticsStrategy;
import comparator.ia.app.util.strategy.stadistics.StadisticsInput;

@ExtendWith(MockitoExtension.class)
class BuyStadisticsStrategyTest {
	
	private static final double PRICE_MODE_SMALLER_THAN_PRICE_BUY = 10000.0;
	private static final double PRICE_MODE_HIGHER_THAN_PRICE_BUY = 30000.0;
	
	@InjectMocks
	private BuyStadisticsStrategy strategy;
	
	@Mock
	private StadisticsBuyApi serviceMock;
	
	private StadisticsInput inputWithoutPrice;
	
	private StadisticsInput inputWithPrice;
	
	@BeforeEach
	void setUp() {
		configurationMockService();
	}
	
	@Test
	@DisplayName("Comprueba que se calcule el metodo correcto si priceUser >= mode")
	void testCalculateWithPriceBuyHigherThanModePrice() {
		createInputWithPrice();
		when(serviceMock.getModePrice(any())).thenReturn(PRICE_MODE_SMALLER_THAN_PRICE_BUY);
		StadisticsVehicleChatDto dto = strategy.calculate(inputWithPrice);
		
		verify(serviceMock).getMinPrice(any());
		verify(serviceMock).getModePrice(any());
		verify(serviceMock).getMaxPrice(any());
		verify(serviceMock).getCarsSellForThisPrice(any());
		
		verify(serviceMock, times(1)).getPercentageToSave(any(), any());
		verify(serviceMock, never()).getPercentageToPain(any(), any());
		
		assertNotNull(dto.getBuyStadisticsDTO().getPriceDifferenceAboveMarket());
		assertNotNull(dto.getBuyStadisticsDTO());
		
		assertNull(dto.getBuyStadisticsDTO().getPriceIncreaseNeededToReachMarket());
		assertNull(dto.getBothStadisticsDTO());
		assertNull(dto.getSellStadisticsDTO());
	}
	
	@Test
	@DisplayName("Comprueba que se calcule el metodo correcto si priceUser <= mode")
	void testCalculateWithPriceBuySmallerThanModePrice() {
		createInputWithPrice();
		when(serviceMock.getModePrice(any())).thenReturn(PRICE_MODE_HIGHER_THAN_PRICE_BUY);
		StadisticsVehicleChatDto dto = strategy.calculate(this.inputWithPrice);
		
		verify(serviceMock).getMinPrice(any());
		verify(serviceMock).getModePrice(any());
		verify(serviceMock).getMaxPrice(any());
		verify(serviceMock).getCarsSellForThisPrice(any());
		
		verify(serviceMock, never()).getPercentageToSave(any(), any());
		verify(serviceMock, times(1)).getPercentageToPain(any(), any());
		
		assertNotNull(dto.getBuyStadisticsDTO().getPriceIncreaseNeededToReachMarket());
		assertNotNull(dto.getBuyStadisticsDTO());
		
		assertNull(dto.getBuyStadisticsDTO().getPriceDifferenceAboveMarket());
		assertNull(dto.getBothStadisticsDTO());
		assertNull(dto.getSellStadisticsDTO());
	}
	
	@Test
	@DisplayName("Verifica que si el precio no se encuentra realiza los metodos oportunos")
	void testCalculateWithoutPrice() {
		createInputWithoutPrice();
		
		StadisticsVehicleChatDto dto = strategy.calculate(this.inputWithoutPrice);
		
		verify(serviceMock).getMinPrice(any());
		verify(serviceMock).getModePrice(any());
		verify(serviceMock).getMaxPrice(any());
		
		
		verify(serviceMock, never()).getCarsSellForThisPrice(any());
		verify(serviceMock, never()).getPercentageToSave(any(), any());
		verify(serviceMock, never()).getPercentageToPain(any(), any());
		
		assertNotNull(dto.getBuyStadisticsDTO());
		assertNotNull(dto.getBuyStadisticsDTO().getMarketMaxPrice());
		assertNotNull(dto.getBuyStadisticsDTO().getMarketMinPrice());
		assertNotNull(dto.getBuyStadisticsDTO().getMarketMostFrequentPrice());
		
		assertNull(dto.getBothStadisticsDTO());
		assertNull(dto.getSellStadisticsDTO());
		assertNull(dto.getBuyStadisticsDTO().getMarketVehicleCountAtUserBuyPrice());
		assertNull(dto.getBuyStadisticsDTO().getPriceIncreaseNeededToReachMarket());
		assertNull(dto.getBuyStadisticsDTO().getPriceDifferenceAboveMarket());
	}
	
	@Test
	@DisplayName("Comprueba que la operacion a realizar sea la de BUY.")
	void testGetOperation() {
		assertEquals(Operation.BUY, strategy.getOperation());
	}

	private void createInputWithPrice() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		Price price = new Price();
		price.setRangeBuy(new PriceRange(20000.0, 20000.0));
		dto.setPrice(price);
		inputWithPrice = new StadisticsInput(dto);
		
	}
	
	private void createInputWithoutPrice() {
		SimilarCarOperationDTO dto = new SimilarCarOperationDTO();
		Price price = new Price();
		price.setRangeBuy(null);
		price.setRangeSell(new PriceRange(3131313131.3, 13131.0));
		dto.setPrice(price);
		inputWithoutPrice = new StadisticsInput(dto);
	}
	
	
	private void configurationMockService() {
		lenient().when(serviceMock.getMaxPrice(any())).thenReturn(1.0);
		lenient().when(serviceMock.getMinPrice(any())).thenReturn(1.0);
		lenient().when(serviceMock.getCarsSellForThisPrice(any())).thenReturn(1);
		lenient().when(serviceMock.getPercentageToPain(any(), any())).thenReturn(new PriceIncreaseNeededToReachMarketDTO());
		lenient().when(serviceMock.getPercentageToSave(any(), any())).thenReturn(new PriceDifferenceAboveMarketDTO());
	}

}
