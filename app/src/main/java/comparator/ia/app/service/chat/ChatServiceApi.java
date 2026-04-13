package comparator.ia.app.service.chat;

import comparator.ia.app.dtos.stadistics.StadisticsVehicleChatDto;

public interface ChatServiceApi {
    String getAnswer(StadisticsVehicleChatDto dto);
    String getGreeting();
    String getAnswerUnknownOperation();
}
