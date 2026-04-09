package epp.tgf.app.util.strategy.stadistics;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;
import comparator.ia.app.enums.Operation;
import comparator.ia.app.util.strategy.stadistics.BothOperationStadisticsStrategy;
import comparator.ia.app.util.strategy.stadistics.BuyStadisticsStrategy;
import comparator.ia.app.util.strategy.stadistics.CalculateStadistic;
import comparator.ia.app.util.strategy.stadistics.SellStadisticsStrategy;
import comparator.ia.app.util.strategy.stadistics.StadisticsInput;

@ExtendWith(MockitoExtension.class)
class CalculateStadisticTest {
	
	private CalculateStadistic calculateStadistics;
	
	@Mock
	private BuyStadisticsStrategy strategyBuy;
	
	@Mock
	private SellStadisticsStrategy strategySell;
	
	@Mock
	private BothOperationStadisticsStrategy strategyBoth;
	
	@Mock
	private StadisticsInput input;
	
	@Mock
	private SimilarCarOperationDTO similarCarOperationDTOMock;
	
	private StadisticsVehicleChatDto expectDTO;
	
	@BeforeEach
	void setUp() {
		when(strategyBuy.getOperation()).thenReturn(Operation.BUY);
		when(strategySell.getOperation()).thenReturn(Operation.SELL);
		when(strategyBoth.getOperation()).thenReturn(Operation.BOTH);
		when(input.getSimilarVehicle()).thenReturn(similarCarOperationDTOMock);
		
		calculateStadistics = new CalculateStadistic(List.of(strategyBuy, strategySell, strategyBoth));
		expectDTO = new StadisticsVehicleChatDto();
	}
	
	@Test
	@DisplayName("Check that not executed method calculate() because strategy is unknown.")
	void testNotCalculate() {
		when(input.getSimilarVehicle().getOperation()).thenReturn(null);
		
		assertThrows(RuntimeException.class,() -> calculateStadistics.calculate(input));
		
		verify(strategyBuy, never()).calculate(input);
		verify(strategySell, never()).calculate(input);
		verify(strategyBoth, never()).calculate(input);
	}
	
	@Test
	@DisplayName("Verify that buy strategy executed to form correct.")
	void testCalculateStadisticsBuyStrategy() {
		when(input.getSimilarVehicle().getOperation()).thenReturn(Operation.BUY);
		when(strategyBuy.calculate(input)).thenReturn(expectDTO);

		StadisticsVehicleChatDto resultActual = calculateStadistics.calculate(input);
		assertSame(expectDTO, resultActual);
		verify(strategyBuy).calculate(input);
	}
	
	@Test
	@DisplayName("Verify that sell strategy executed to form correct.")
	void testCalculateStadisticsSellStrategy() {
		when(input.getSimilarVehicle().getOperation()).thenReturn(Operation.SELL);
		when(strategySell.calculate(input)).thenReturn(expectDTO);
	
		StadisticsVehicleChatDto resultActual = calculateStadistics.calculate(input);
		
		assertSame(expectDTO, resultActual);
		verify(strategySell).calculate(input);
	}
	
	@Test
	@DisplayName("Verify that sale strategy executed to form correct.")
	void testCalculateStadisticsBothStrategy() {
		when(input.getSimilarVehicle().getOperation()).thenReturn(Operation.BOTH);
		when(strategyBoth.calculate(input)).thenReturn(expectDTO);
		
		StadisticsVehicleChatDto resultActual = calculateStadistics.calculate(input);
		
		assertSame(expectDTO, resultActual);
		verify(strategyBoth).calculate(input);
	}

}
