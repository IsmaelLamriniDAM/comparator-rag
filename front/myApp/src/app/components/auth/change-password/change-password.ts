import { Component, signal } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../service/auth-service.service';
import { DataChangeForgottPwd } from '../../../interfaces/DataChangeForgottPwd';

@Component({
  selector: 'app-change-password',
  imports: [RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './change-password.html',
})
export class ChangePassword {

  constructor(private authService: AuthService, private activatedRoute: ActivatedRoute) {}

  errorMessage = "";
  showPassword = signal(false);
  validating = signal(false);
  tokenValid = signal(true);
  changed = signal(false);

  toggleShowPassword() {
    this.showPassword.update(value => !value);
  }

  onSubmit(form: NgForm) {
    const password = form.value.password;
    const repeatedPassword = form.value.repeatedPassword;

    const passwordRegex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;

    if (!passwordRegex.test(password)) {
      this.errorMessage =
        "La contraseña debe tener mínimo 8 caracteres, incluir mayúsculas, minúsculas, números y un símbolo.";
      return;
    }

    if(password != repeatedPassword) {
      this.errorMessage =
        "Las contraseñas no coinciden.";
      return;
    }

    this.errorMessage = "";

    const changePassword: DataChangeForgottPwd = {
      token: this.activatedRoute.snapshot.queryParamMap.get('token') || '',
      newPwd: password,
      checkPwd: repeatedPassword
    }

    this.authService.updatePasswordForgot(changePassword).subscribe({
      next: () => {
        this.changed.set(true);
      },
      error: (error) => {
        console.error('Error updating password:', error);
        this.errorMessage = error.error || 'Error al cambiar la contraseña. Por favor, inténtalo de nuevo.';
      }
    });

  }
}
