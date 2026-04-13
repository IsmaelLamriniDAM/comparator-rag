import { Component, signal } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { registerData } from '../../../interfaces/registerData';
import { AuthService } from '../../../service/auth-service.service';

@Component({
  selector: 'app-register',
  imports: [RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './register.html',
})
export class Register {
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
    const name = form.value.name;
    const email = form.value.email;
    const password = form.value.password;
    const phoneNumber = form.value.phoneNumber;

    if(String(name).length < 3) {
      this.errorMessage = "El nombre debe tener mínimo 3 caracteres.";
      return;
    }

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

    const registerData: registerData = {
      name,
      email,
      password,
      phone: phoneNumber
    }

    this.loading.set(true);
    this.authService.register(registerData).subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
        this.loading.set(false);    
      },
      error: err => {
        const serverError = err.error;
  
        if (err.status === 400) {
          this.errorMessage = serverError?.message || "Las credenciales son incorrectas o los datos son inválidos.";
        } 
        else {
          this.errorMessage = err.error
        }

        this.loading.set(false);
      }
    })
  }
}
