import { Component, effect, input, model, output } from '@angular/core';
import { DataHistoryMessage } from '../../../../interfaces/IA/dataHistoryMessage';
import { DataConversation } from '../../../../interfaces/IA/dataConversation';
import { RolChat } from '../../../../enums/RolChat';
import { MessageIa } from './message-ia/message-ia';
import { MessageUser } from './message-user/message-user';
import { MessageAndRol } from '../../../../interfaces/IA/messageAndRol';

@Component({
  selector: 'app-ai-chat-messsages',
  imports: [MessageIa, MessageUser],
  templateUrl: './ai-chat-messsages.html',
})
export class AiChatMesssages {

  listMessages =  model<MessageAndRol[]>()

  title = input<string |null>()

  messageTempo = input<MessageAndRol[]| null>()
  


  isRolIa(rol: RolChat): boolean {
    return rol === RolChat.IA
  }


}
