import { Component, Input, input, model, output, Signal, signal, SimpleChanges } from '@angular/core';
import { AiChatMesssages } from './ai-chat-messsages/ai-chat-messsages';
import { AiHistoryChat } from './ai-history-chat/ai-history-chat';
import { AiZoneWriteComponent } from './ai-zone-write.component/ai-zone-write.component';
import { HistoryConversation } from '../../../service/history-conversation.service';
import { ConfirmOperationData } from '../../../interfaces/pop-up/confirmOperationData';
import { PopUp } from '../common/pop-up/pop-up';
import { single } from 'rxjs';
import { ChatIaService } from '../../../service/api/chat-service-interface';
import { ChatIaServiceImp } from '../../../service/chat-ia.service';
import { ResponseIa } from '../../../interfaces/IA/responseIa';
import { RequestIa } from '../../../interfaces/IA/requestIa';
import { HistoryDto } from '../../../interfaces/IA/historyDto';
import { RolChat } from '../../../enums/RolChat';
import { MessageAndRol } from '../../../interfaces/IA/messageAndRol';

@Component({
  selector: 'app-ai',
  imports: [AiChatMesssages, AiZoneWriteComponent, PopUp, AiHistoryChat],
  templateUrl: './ia.html',
  providers: [
  {
    provide: ChatIaService,   
    useClass: ChatIaServiceImp
  }
]
})
export class AiComponent {

  title = signal<string | null>(null)

  listHistories = signal<HistoryDto[]>([])

  messagesConversation = signal<MessageAndRol[]>([])

  idHistory = signal<string | null> (null)

  dataDelete= signal<ConfirmOperationData | null>(null)

  messagesTemporaly = signal<MessageAndRol[] | null>(null)

  constructor(private historyService : HistoryConversation, private chatService: ChatIaService) {}

  ngOnInit() {
    this.historyService.getAllHistories().subscribe({
      next: (data) => this.listHistories.set(data),
      error: ()=> console.log("error al darme todos los historiales")
    })
  }

  callIa(message: string) {
    const request: RequestIa = {
      messageUser: message,
      idHistory: this.idHistory()
    }
    this.messagesTemporaly.set([
      ...this.messagesConversation(),
      {message: message, rol: RolChat.USER},  {message: "Cargando", rol: RolChat.IA}

    ])
    this.chatService.chat(request).subscribe({

      next: (responseIa) => {
        this.messagesTemporaly.set(null)
        this.setElementsMessagesConversation(responseIa.messagesAndRol)
        this.title.set(responseIa.title)
        if(this.idHistory() != responseIa.idHistory) {
          this.listHistories.update(list => {
            if(this.idHistory() == null) {

              return [...list, {idShare: responseIa.idHistory, title: responseIa.title} ]
            }
            return list
          }
        )
          }
        this.idHistory.set(responseIa.idHistory)
        
      }, 
      error: () => console.log("LA RESPUESTA DE LA IA HA FALLADO")
    })
  }

  private setElementsMessagesConversation(messages: MessageAndRol[]){
    this.messagesConversation.update(list => { 
      return [...list, ...messages]
    })
  }

  createNewChat(wantCreate: boolean) {
    if(wantCreate) {
      this.idHistory.set(null)
      this.messagesConversation.set([])
      this.title.set(null)
    }
  }

  deleteOneHistory(id: string) {
    this.dataDelete.set({
      message: "Si eliminas esto, no podrás volver a recuperar el historial eliminado.",
      type: "ONE",
      id: id,
      confirmAction: () => this.historyService.deleteOneHistory(id)
    })
  }

  deleteElementList(id: string | null) {
    if(id != null) {
      this.listHistories.update(list => list.filter(element => element.idShare != id))
      this.createNewChat(true)
    } else {
      this.listHistories.set([])
      this.createNewChat(true)
    }
  }

  searchHistotyView(id: string) {
    this.historyService.getAllMessagesForHisHistory(id).subscribe({
      next: (listMessages) => this.messagesConversation.set(listMessages),
      error:() => console.log("ALGO HA PASADO CON LOS MENSAHES DE ESTA HISTORIA")
    })
  }

  deleteAllHistories(wantDelete: boolean) {
    if(wantDelete) {
      this.dataDelete.set({
      message: "Si eliminas esto, no podrás volver a recuperar los historiales eliminados.",
      type: "ALL",
      confirmAction: () => this.historyService.deleteAllHistory()
      })
    }

  }

}