import { Component, Input, signal } from '@angular/core';
import {InfoUserComponent} from './info-user/info-user.component'
import {ChangePasswordUserComponent} from './change-password-user/change-password-user.component'
import { LogoutUserComponent } from './logout-user/logout-user.component'
import { Router } from '@angular/router';
import { AuthService } from '../../../service/auth-service.service';

@Component({
  selector: 'app-profile-user',
  standalone: true,
  imports: [InfoUserComponent, LogoutUserComponent, ChangePasswordUserComponent],
  templateUrl: './profile-user.html',
})
export class ProfileUser {

  constructor(private route: Router, private authService: AuthService){}

  recieveInput() {
    this.logout()
  }

  private logout() {
    this.authService.logout().subscribe({
      complete: () => {
        this.authService.setLoggedIn(false);
        this.authService.clearUser();
        this.route.navigate(['logout/succes'])
      },
      error: (err) =>{ 
       this.route.navigate(['logout/error'], {
        state:{errorCode: err.status, errorMessage: 'Error Inesperado.'}
       })
      }
    })
  }
}
