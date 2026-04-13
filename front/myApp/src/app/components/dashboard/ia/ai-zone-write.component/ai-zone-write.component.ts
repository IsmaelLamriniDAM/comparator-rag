import { Component, output, signal } from '@angular/core';
import { FormsModule } from "@angular/forms";

@Component({
  selector: 'app-ai-zone-write',
  imports: [FormsModule],
  templateUrl: './ai-zone-write.component.html',
})
export class AiZoneWriteComponent {

  messageUser = signal<string>("")

  questionUserSend = output<string>()

  sendQuestionIa() {
    this.questionUserSend.emit(this.messageUser())
    this.messageUser.set("")
  }
}
