import { NgClass } from '@angular/common';
import { Component, input, model, output, signal } from '@angular/core';
import { ListHistoryMessages } from './list-history-messages/list-history-messages';
import { PopUp } from '../../common/pop-up/pop-up';
import { ConfirmOperationData } from '../../../../interfaces/pop-up/confirmOperationData';
import { HistoryConversation } from '../../../../service/history-conversation.service';
import { single } from 'rxjs';
import { ResponseIa } from '../../../../interfaces/IA/responseIa';
import { DataHistoryMessage } from '../../../../interfaces/IA/dataHistoryMessage';
import { HistoryDto } from '../../../../interfaces/IA/historyDto';
import { MessageAndRol } from '../../../../interfaces/IA/messageAndRol';

@Component({
  selector: 'app-ai-history-chat',
  imports: [NgClass, ListHistoryMessages],
  templateUrl: './ai-history-chat.html',
})
export class AiHistoryChat {

  listDataHistories = model<HistoryDto[]>([])

  titleHistory = model<string |null>()

  isOpenMenu = signal<boolean>(true)

  createNewConversation = output<boolean>()

  idDataDeleteHistory = output<string>()

  idViewHistory =  output<string>()

  wantDelete = output<boolean>()

  changeStatusMenu() {
    this.isOpenMenu.update(current => !current)
  }

  clickNewConversation() {
    this.createNewConversation.emit(true)
  }

  deleteOneHistory(id: string) {
    this.idDataDeleteHistory.emit(id)
  }

  viewMessagesHistory(data: HistoryDto) {
    this.idViewHistory.emit(data.idShare)
    this.titleHistory.set(data.title)
  }

  deleteAllHistories() {
    this.wantDelete.emit(true)
  }

}
