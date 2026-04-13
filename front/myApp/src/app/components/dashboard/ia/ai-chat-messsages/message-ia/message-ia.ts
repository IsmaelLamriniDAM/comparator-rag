import { Component, input } from '@angular/core';

@Component({
  selector: 'app-message-ia',
  imports: [],
  templateUrl: './message-ia.html',
})
export class MessageIa {

  message = input<string>()

  normalizedMessage(message: string | undefined): string {
    return message?.replace(/\*/g, "") || "";
  }
}
