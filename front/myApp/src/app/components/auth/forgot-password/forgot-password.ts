import { Component, signal } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../service/auth-service.service';


@Component({
  selector: 'app-forgot-password',
  imports: [RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './forgot-password.html',
})
export class ForgotPassword {


  constructor(private auth: AuthService) { }

  errorMessage = "";
  counter = signal<number>(0);
  intervalId: any;

  onSubmit(form: NgForm) {
    if(this.counter() > 0) {
      return;
    }
    const email = form.value.email;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      this.errorMessage = "El email no es válido.";
      return;
    }

    this.errorMessage = "";

    this.auth.forgottPwd(email).subscribe({
      next: (response) => {
        this.startCountdown();
      },
      error: (error) => {
        if (error.status === 400) {
          this.errorMessage = error.error;
        } else {
          this.errorMessage = "Ocurrió un error. Por favor, inténtalo de nuevo.";
        }
      }
    });

  }

  startCountdown() {
    this.counter.set(15);

    this.intervalId = setInterval(() => {
      this.counter.update(counter => counter - 1);
      if (this.counter() <= 0) {
        clearInterval(this.intervalId);
      }
    }, 1000);
  }

}
