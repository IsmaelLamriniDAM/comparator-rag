import { Component, input } from '@angular/core';

@Component({
  selector: 'app-message-user',
  imports: [],
  templateUrl: './message-user.html',
})
export class MessageUser {
  messageUser = input<string>()
}
