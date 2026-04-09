import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { RouterLink, RouterLinkActive } from '@angular/router';
@Component({
  selector: 'app-hero',
  imports: [ButtonModule, RouterLink, RouterLinkActive],
  templateUrl: './hero.html',
})
export class Hero {

}
