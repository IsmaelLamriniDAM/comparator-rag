import { Component, computed, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PasswordUpdateUser } from '../../../../interfaces/PasswordUpdateUser';
import { Router } from '@angular/router';
import { AuthService } from '../../../../service/auth-service.service';

@Component({
  selector: 'app-change-password-user-component',
  imports: [FormsModule],
  templateUrl: './change-password-user.component.html',
})
export class ChangePasswordUserComponent {
  collapsed = signal(true);

  pwdCurrent = signal('')
  pwdNew = signal('')
  pwdConfirm = signal(``)
  messageErrorCurrentPwd = signal('')
  messageErrorPwd = signal('')
  messageErrorPwdConfirm= signal('')
  messagePwdNotValid = signal('')
  messageErrorResponse = signal('')
  messageSuccesfullResponse = signal('')

  constructor(private authService: AuthService, private router: Router) {}


  navigate() {
    this.router.navigate(['/forgot-password'])
  }
  pwdRepeat() {
    if(this.pwdCurrent() === this.pwdNew()) {
      return this.messageErrorPwd.set("Contraseña no puede ser igual a la actual.")
    }
    this.messageErrorPwd.set('')
  }

  pwdConfirmIncorrect() {
     if(this.pwdNew() !== this.pwdConfirm()) {
      return this.messageErrorPwdConfirm.set("Contraseña no coincide con la nueva.")
    }
    this.messageErrorPwdConfirm.set('')
  }

  isPwdCurrentValid(pwdCurrent: string) {
      const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
    if(pwdCurrent.match(regex)) {
      return this.messageErrorCurrentPwd.set('')
    }
    this.messageErrorCurrentPwd.set('La contraseña debe tener mínimo 8 caracteres, incluir mayúsculas, minúsculas, números y un símbolo.')
  }

  isPwdValid(pwd: string){
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
    if(pwd.match(regex)) {
      return this.messagePwdNotValid.set('')
    }
    this.messagePwdNotValid.set('La contraseña debe tener mínimo 8 caracteres, incluir mayúsculas, minúsculas, números y un símbolo.')
  }

  toggleCollapse() {
    this.collapsed.update(collapse => !collapse)
  }

  // LAMADA AL ENDPOINT
  updatePwd() {
    const pwdUpdate: PasswordUpdateUser = {
      pwdCurrent: this.pwdCurrent(),
      pwdNew: this.pwdNew(),
      pwdNewConfirm: this.pwdConfirm()
    }

    this.authService.updatePassword(pwdUpdate).subscribe({
      complete: () => {
        this.pwdCurrent.set('')
        this.pwdNew.set('')
        this.pwdConfirm.set(``)
        this.messageSuccesfullResponse.set('Contraseña actualizada con exito')
        setTimeout(() => {
          this.messageSuccesfullResponse.set('')
        }, 5000);
      },
      error: (err) => {
        if(err.status == 401) {
          this.messageErrorResponse.set(err.error)
        } else {
          this.messageErrorResponse.set('Error al actulizar la contraseña.');
        }
        setTimeout(() => {
          this.messageErrorResponse.set('')
        }, 3000)
      }
    })
  }
}
