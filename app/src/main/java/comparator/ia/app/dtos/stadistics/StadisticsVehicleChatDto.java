package comparator.ia.app.dtos.stadistics;

import comparator.ia.app.dtos.chat.CarSendRagDTO;
import comparator.ia.app.dtos.chat.QuestionUser;
import comparator.ia.app.dtos.stadistics.sendView.SendCarView;
import comparator.ia.app.dtos.vector.SimilarCarOperationDTO;

public class StadisticsVehicleChatDto {
	private BuyStadisticsDTO buyStadisticsDTO;
	private SellStadisticsDTO sellStadisticsDTO;
	private BothOperationStadisticsDTO bothStadisticsDTO;
	

	
	private QuestionUser question;
	private Long marketComparedVehicleCount;
	
	public Long getMarketComparedVehicleCount() {
		return marketComparedVehicleCount;
	}
	public void setMarketComparedVehicleCount(Long marketComparedVehicleCount) {
		this.marketComparedVehicleCount = marketComparedVehicleCount;
	}
	public QuestionUser getQuestion() {
		return question;
	}
	public void setQuestion(QuestionUser question) {
		this.question = question;
	}
	public BuyStadisticsDTO getBuyStadisticsDTO() {
		return buyStadisticsDTO;
	}
	public void setBuyStadisticsDTO(BuyStadisticsDTO buyStadisticsDTO) {
		this.buyStadisticsDTO = buyStadisticsDTO;
	}
	
	public SellStadisticsDTO getSellStadisticsDTO() {
		return sellStadisticsDTO;
	}
	public void setSellStadisticsDTO(SellStadisticsDTO sellStadisticsDTO) {
		this.sellStadisticsDTO = sellStadisticsDTO;
	}
	public BothOperationStadisticsDTO getBothStadisticsDTO() {
		return bothStadisticsDTO;
	}
	public void setBothStadisticsDTO(BothOperationStadisticsDTO bothStadisticsDTO) {
		this.bothStadisticsDTO = bothStadisticsDTO;
	}
	
	
}
