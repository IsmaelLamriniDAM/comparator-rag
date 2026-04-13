package comparator.ia.app.util.chainOfResponsability.messageRag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;

import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;

public class MessageBothRag extends MessageStatisticsCreatorRag{
	
	private final Logger logger = LoggerFactory.getLogger(MessageBothRag.class);
	
	private static final String PROMPT_BOTH = """
			Eres un analista experto en operaciones de compra y reventa de vehículos de segunda mano.

			Tu tarea es analizar una operación completa en la que un usuario quiere comprar un vehículo y posteriormente venderlo, usando estadísticas reales obtenidas desde anuncios de múltiples páginas web.

			Debes razonar con criterio de mercado. No debes limitarte a repetir números. No debes inventar información. No debes responder como si estuvieras leyendo estructuras técnicas.

			OBJETIVO:
			Determinar si la operación de comprar y después vender el vehículo parece favorable, razonable o poco recomendable según el beneficio estimado, la probabilidad de venta, el tiempo medio de venta y la base estadística disponible.

			SIGNIFICADO DE LOS DATOS DE LA CONSULTA DEL USUARIO:
			- question: texto literal de la pregunta del usuario. Debes usarlo para entender la intención real de la consulta.
			- priceBuy: rango de precio de compra detectado en la pregunta del usuario.
			- priceSell: rango de precio de venta detectado en la pregunta del usuario.
			- PriceRange.max: valor máximo del rango detectado.
			- PriceRange.min: valor mínimo del rango detectado.
			- Si priceBuy.max y priceBuy.min son null, significa que el usuario no indicó un precio de compra claro.
			- Si priceSell.max y priceSell.min son null, significa que el usuario no indicó un precio de venta claro.
			- Si los valores min y max de un rango son iguales, interpreta que el usuario indicó un único precio concreto.
			- Si min y max son distintos, interpreta que el usuario indicó un rango.
			- Si solo uno de los dos valores tiene valor, úsalo como referencia aproximada.
			- No inventes precios que no estén presentes en la consulta.

			SIGNIFICADO DE LOS DATOS DE MERCADO:
			- marketSellComparableVehicleCount: número de vehículos del mercado encontrados en venta que coinciden con las características propuestas por el usuario.
			- sellSuccessProbability: probabilidad estimada de vender el vehículo en la operación planteada por el usuario. Debe interpretarse como probabilidad de éxito de venta, no como rentabilidad.
			- estimatedSaleProfit: beneficio estimado de la operación completa de comprar y después vender.
			- estimatedSaleProfit.percentage: beneficio porcentual estimado de la operación.
			- estimatedSaleProfit.price: beneficio monetario estimado de la operación.
			- averageDaysToSell: número medio de días que tardaría en venderse un vehículo comparable.
			- marketMinBuyPrice: precio mínimo de compra detectado en el mercado para vehículos comparables.
			- marketComparedVehicleCount: número total de vehículos con los que realmente se han comparado las estadísticas. Si este valor es 0, significa que no existe base estadística suficiente para valorar la operación.

			REGLAS OBLIGATORIAS:
			1. Usa el beneficio estimado, la probabilidad de venta y el tiempo medio de venta como ejes principales del análisis cuando exista base estadística suficiente.
			2. Si marketComparedVehicleCount es 0:
			   - indica claramente que no se ha podido generar una estadística válida,
			   - explica que no existen suficientes vehículos comparables en el mercado para valorar la operación,
			   - no intentes concluir que la operación es buena o mala con seguridad,
			   - responde igualmente con una conclusión prudente basada en la ausencia de comparables.
			3. Si estimatedSaleProfit es positivo:
			   - explica que la operación podría generar beneficio,
			   - cuantifica el beneficio en dinero y porcentaje si ambos datos están disponibles.
			4. Si estimatedSaleProfit es cero o cercano a cero:
			   - explica que la operación tendría un margen muy ajustado o escaso.
			5. Si estimatedSaleProfit es negativo:
			   - explica que la operación sería desfavorable económicamente o tendría pérdidas.
			6. Usa sellSuccessProbability para valorar la facilidad esperada de venta:
			   - una probabilidad más alta implica mayor facilidad para cerrar la venta,
			   - una probabilidad más baja implica mayor dificultad o más incertidumbre.
			7. Usa averageDaysToSell para valorar la rapidez esperada de salida:
			   - menos días implica una venta potencialmente más rápida,
			   - más días implica una venta potencialmente más lenta.
			8. Usa marketMinBuyPrice como contexto para valorar si el precio de compra del usuario parece competitivo, pero no como única base de decisión.
			9. Usa marketSellComparableVehicleCount y marketComparedVehicleCount para matizar la confianza del análisis.
			10. Si faltan datos importantes o hay valores null relevantes, dilo claramente y limita la conclusión.
			11. No inventes información no presente en los datos.
			12. Responde a la intención real de la pregunta del usuario.

			FORMATO DE SALIDA (OBLIGATORIO):
			1. Respuesta directa a la pregunta del usuario.
			2. Evaluación económica de la operación de compra y posterior venta.
			3. Evaluación de la probabilidad de venta y del tiempo medio de salida.
			4. Conclusión breve y práctica.

			DIAGNÓSTICO Y DECISIÓN:
			- Etiqueta final: OPERACIÓN MUY FAVORABLE / OPERACIÓN INTERESANTE / OPERACIÓN AJUSTADA / OPERACIÓN POCO RECOMENDABLE / SIN BASE ESTADÍSTICA SUFICIENTE
			- Justificación basada únicamente en los datos disponibles.
			- Confianza: Alta / Media / Baja.
			  - Alta si marketComparedVehicleCount es representativo y los datos clave no son null.
			  - Media si hay base estadística pero limitada.
			  - Baja si faltan datos importantes o la muestra es escasa.

			Datos de la consulta del usuario:
			%s

			Datos de estadísticas de compra y posterior venta:
			%s
			
			Numero de coches con los que se ha comparado:
			%s

			Responde ahora en español, de forma natural, profesional y coherente.
			""";

	public MessageBothRag(ChatModel chatModel, ChatClient chatClient) {
		super(chatModel, chatClient);
	}

	@Override
	public String buildMessage(StadisticsVehicleChatDto dto) {
		if(dto.getBothStadisticsDTO() != null) {
			String questionJson = buildQuestionJson(dto);
			String statistics = GSON.toJson(dto.getBothStadisticsDTO());
			
			logger.info("GERERATED MESSAGE AI");
			
			return sendMessage(PROMPT_BOTH.formatted(questionJson, statistics, buildNumCarComparedJson(dto.getMarketComparedVehicleCount())));
		}
		
		return next.buildMessage(dto);
	}

}
