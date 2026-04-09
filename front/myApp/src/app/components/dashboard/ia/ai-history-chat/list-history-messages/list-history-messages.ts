import { Component, effect, EventEmitter, input, Input, model, output, Output, SimpleChange, SimpleChanges } from '@angular/core';
import { PopUp } from "../../../common/pop-up/pop-up";
import { ConfirmOperationData } from '../../../../../interfaces/pop-up/confirmOperationData';
import { HistoryConversation } from '../../../../../service/history-conversation.service';
import { ResponseIa } from '../../../../../interfaces/IA/responseIa';
import { DataHistoryMessage } from '../../../../../interfaces/IA/dataHistoryMessage';
import { HistoryDto } from '../../../../../interfaces/IA/historyDto';

@Component({
  selector: 'app-list-history-messages',
  imports: [],
  templateUrl: './list-history-messages.html',
})
export class ListHistoryMessages {
  
  histories = model<HistoryDto[]>()
  
  viewMessagesEvent = output<HistoryDto>()

  deleteIdHistory = output<string>()

  clickViewMessages(data: HistoryDto) {
    this.viewMessagesEvent.emit(data)
  }

  deleteHistory(id: string) {
    this.deleteIdHistory.emit(id)
  }
}
