import { Component, EventEmitter, Output, signal } from '@angular/core';

@Component({
  selector: 'app-logout-user-component',
  imports: [],
  templateUrl: './logout-user.component.html',
})
export class LogoutUserComponent {

  @Output() logoutClick = new EventEmitter<void>();
  
   collapsed = signal(true);
   isLoading = signal (false);

  clickEvent() {
    this.isLoading.update(currentStatus => !currentStatus)
    this.logoutClick.emit();
  }
  
  toggleCollapse() {
    this.collapsed.update(collapse => !collapse)
  }
}
