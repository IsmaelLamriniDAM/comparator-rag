import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-succes-pages.component',
  imports: [],
  templateUrl: './succes-pages.component.html',
})
export class SuccesPagesComponent {
   constructor(private router: Router) {}

    navigate() {
    this.router.navigate([''])
  }
}
