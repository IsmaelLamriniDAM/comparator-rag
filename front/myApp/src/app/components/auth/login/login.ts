import { Component, Inject, signal } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { loginData } from '../../../interfaces/loginData';
import { AuthService } from '../../../service/auth-service.service';

@Component({
  selector: 'app-login',
  imports: [RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './login.html',
})
export class Login {
  constructor(private authService: AuthService, private router: Router) {};
  errorMessage = "";
  showPassword = signal(false);
  loading = signal(false);

  toggleShowPassword() {
    this.showPassword.update(value => !value);
  }

  onSubmit(form: NgForm) {
    if(this.loading() == true) {
      return;
    }
    const email = form.value.email;
    const password = form.value.password;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      this.errorMessage = "El email no es válido.";
      return;
    }

    const passwordRegex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;

    if (!passwordRegex.test(password)) {
      this.errorMessage =
        "La contraseña debe tener mínimo 8 caracteres, incluir mayúsculas, minúsculas, números y un símbolo.";
      return;
    }

    this.errorMessage = "";

    const loginData: loginData = {
      email,
      password
    }

    this.loading.set(true)
    this.authService.login(loginData).subscribe({
      next: () => {
        this.router.navigate(['/dashboard'])
        this.loading.set(false)
      },
      error: err => {
        if (err.status === 400) {
          this.errorMessage = "Las credenciales son incorrectas o los datos son inválidos.";
        } else {
          this.errorMessage = err.error
        }
        this.loading.set(false)
      }
    })
  }
}
