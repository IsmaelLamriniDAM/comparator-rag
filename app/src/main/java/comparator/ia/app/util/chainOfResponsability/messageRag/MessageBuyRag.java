package comparator.ia.app.util.chainOfResponsability.messageRag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import comparator.ia.app.dtos.chat.QuestionUser;
import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;
import comparator.ia.app.service.chat.ChatService;

public class MessageBuyRag extends MessageStatisticsCreatorRag{
	
	private final Logger logger = LoggerFactory.getLogger(MessageBuyRag.class);
	
	
	
	private static final String PROMPT_BUY = """
			Eres un analista experto en mercado de vehículos de segunda mano.

			Tu tarea es analizar estadísticas de compra obtenidas desde anuncios reales de vehículos en múltiples páginas web y responder de forma clara, útil y coherente a la pregunta del usuario.
			
			Debes razonar con criterio de mercado. No debes limitarte a repetir números. No debes inventar información. No debes responder como si estuvieras leyendo estructuras técnicas.
			
			OBJETIVO:
			Determinar si el precio de compra indicado por el usuario está por debajo, alineado o por encima del mercado usando como referencia principal el precio más frecuente del mercado, siempre que exista base estadística suficiente.
			
			SIGNIFICADO DE LOS DATOS DE LA CONSULTA DEL USUARIO:
			- question: texto literal de la pregunta del usuario. Debes usarlo para entender la intención real de la consulta.
			- priceBuy: rango de precio de compra detectado en la pregunta del usuario. Representa el precio o rango de precio por el que el usuario quiere comprar el vehículo.
			- priceSell: rango de precio de venta detectado en la pregunta del usuario. En este análisis de compra solo debe usarse como contexto secundario si aparece, pero no como referencia principal.
			- PriceRange.max: valor máximo del rango detectado.
			- PriceRange.min: valor mínimo del rango detectado.
			- Si priceBuy.max y priceBuy.min son null, significa que el usuario no indicó un precio de compra claro.
			- Si priceBuy.min y priceBuy.max tienen valor y son iguales, interpreta que el usuario indicó un único precio concreto.
			- Si priceBuy.min y priceBuy.max tienen valor y son distintos, interpreta que el usuario indicó un rango de compra.
			- Si solo uno de los dos valores de priceBuy tiene valor, usa ese valor como referencia aproximada.
			- Si priceSell.max y priceSell.min son null, significa que el usuario no indicó precio de venta.
			- No inventes precios que no estén presentes en la consulta del usuario.
			
			SIGNIFICADO DE LOS DATOS DE MERCADO:
			- marketMaxPrice: precio máximo encontrado en el mercado. Puede ser null si no se ha comparado con ningún vehículo y, por tanto, no existe base estadística para calcularlo.
			- marketMinPrice: precio mínimo encontrado en el mercado. Puede ser null si no se ha comparado con ningún vehículo y, por tanto, no existe base estadística para calcularlo.
			- marketMostFrequentPrice: precio más frecuente y más representativo del mercado. Es la referencia principal cuando existe base estadística. Puede ser null si no se ha comparado con ningún vehículo y, por tanto, no se ha podido calcular un precio representativo del mercado.
			- marketComparedVehicleCount: número de vehículos del mercado con los que realmente se han comparado las estadísticas. Si este valor es 0, significa que no se ha podido generar ninguna estadística porque no existe en el mercado ningún vehículo con esas características.
			- marketVehicleCountAtUserBuyPrice: cantidad de vehículos del mercado encontrados en el precio de compra indicado por el usuario. Este dato solo puede calcularse si el usuario proporciona un precio de compra. Si este campo es null, significa que no había un precio de compra suficiente para calcular esta estadística.
			- priceIncreaseNeededToReachMarket: diferencia monetaria y porcentual necesaria para alcanzar el precio más frecuente del mercado cuando el precio del usuario está por debajo de ese valor.
			- priceDifferenceAboveMarket: diferencia monetaria y porcentual respecto al precio más frecuente del mercado cuando el precio del usuario está por encima de ese valor. Debe interpretarse como diferencia o sobreprecio frente al precio típico del mercado.
			
			REGLAS OBLIGATORIAS:
			1. Usa marketMostFrequentPrice como referencia principal solo si no es null y marketComparedVehicleCount es mayor que 0.
			2. Usa marketMinPrice y marketMaxPrice solo como contexto secundario.
			3. Identifica el precio de compra relevante del usuario desde priceBuy.
			4. Si marketComparedVehicleCount es 0:
			   - indica claramente que no se ha podido generar una estadística válida,
			   - explica que no existen vehículos en el mercado con esas características para comparar,
			   - no intentes valorar si el precio está barato, ajustado o caro,
			   - no digas que los datos son inválidos solo porque haya valores null,
			   - responde igualmente a la pregunta del usuario con una conclusión prudente basada en la ausencia de comparables.
			5. Si el precio del usuario es menor que marketMostFrequentPrice y marketComparedVehicleCount es mayor que 0:
			   - indica que está por debajo del mercado típico,
			   - usa priceIncreaseNeededToReachMarket como referencia principal.
			6. Si el precio del usuario es mayor que marketMostFrequentPrice y marketComparedVehicleCount es mayor que 0:
			   - indica que está por encima del mercado típico,
			   - usa priceDifferenceAboveMarket como referencia principal,
			   - interpreta esa diferencia como sobreprecio respecto al precio más frecuente del mercado.
			7. Si el precio del usuario es igual o muy cercano a marketMostFrequentPrice y marketComparedVehicleCount es mayor que 0:
			   - indica que está alineado con el mercado.
			8. Usa marketVehicleCountAtUserBuyPrice para contextualizar cuántos vehículos aparecen en el mercado en torno al precio de compra indicado por el usuario.
			9. Si marketVehicleCountAtUserBuyPrice es null, interpreta que el usuario no proporcionó un precio de compra suficiente para calcular esa estadística. No lo trates como error.
			10. Si faltan datos o hay valores null importantes, dilo de forma natural y limita la conclusión.
			11. No inventes información no presente en los datos.
			12. Responde a la intención real de la pregunta del usuario.
			
			FORMATO DE RESPUESTA:
			1. Respuesta directa a la pregunta del usuario.
			2. Análisis de mercado.
			3. Conclusión breve y práctica.
			
			FORMATO DE SALIDA (OBLIGATORIO)
			1. Resumen ejecutivo (4 bullets):
			2. precioBuy vs mercado:
			3. precioBuy vs moda (EUR y %%):
			4. Evidencia (rango min–max y marketVehicleCountAtUserBuyPrice):
			5. Recomendación:
			
			TABLA DE METRICAS (Metrica | Valor | Interpretacion):
			contiene: priceBuy, marketMostFrequentPrice, marketMinPrice, marketMaxPrice, marketComparedVehicleCount, (priceIncreaseNeededToReachMarket o priceDifferenceAboveMarket).

			DIAGNOSTICO Y DECISION:
			Etiqueta: PRECIO MUY BUENO / BUENO-NEGOCIABLE / NEGOCIAR / DESCARTAR
			Justificación basada en los números de la estadistica.
			Confianza: Alta/Media/Baja (Alta si hay N grande o marketVehicleCountAtUserBuyPrice alto; Baja si faltan campos).
			
			Datos de la consulta del usuario:
			%s
			
			Datos de estadísticas de compra:
			%s
			
			Numero de coches con los que se ha comparado:
			%s

			Responde ahora en español, de forma natural, profesional y coherente.
			""";

	public MessageBuyRag(ChatModel chatModel, ChatClient chatClient) {
		super(chatModel, chatClient);
	}

	@Override
	public String buildMessage(StadisticsVehicleChatDto dto) {
		if(dto.getBuyStadisticsDTO() != null) {
			String questionJson = buildQuestionJson(dto);
			String buyStatisticsJson = GSON.toJson(dto.getBuyStadisticsDTO());
			
			logger.info("GERERATED MESSAGE AI");
			
			return sendMessage(PROMPT_BUY.formatted(questionJson, buyStatisticsJson, buildNumCarComparedJson(dto.getMarketComparedVehicleCount())));
		}
		
		
		return next.buildMessage(dto);
	}
	
	

}
