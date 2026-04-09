import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-error-page.component',
  imports: [],
  templateUrl: './error-page.component.html',
})
export class ErrorPageComponent {
  error: any

  constructor(private router: Router) {
  
    this.error = history.state
  }

  navigate() {
    this.router.navigate(['dashboard'])
  }
}
