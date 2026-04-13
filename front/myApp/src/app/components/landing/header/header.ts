import { Component, inject, signal } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../service/auth-service.service';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header.html',
})
export class Header {
  menuOpen = signal(false);
  authService = inject(AuthService)
  toggleOpen() {
    const newValue = !this.menuOpen();
    this.menuOpen.set(newValue);
  }
}
