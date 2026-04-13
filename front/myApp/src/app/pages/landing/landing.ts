import { Component } from '@angular/core';
import { Header } from "../../components/landing/header/header";
import { Hero } from "../../components/landing/hero/hero";
import { Footer } from "../../components/landing/footer/footer";
import { ComparatorFeatures } from "../../components/landing/comparator-features/comparator-features";
import { AboutUs } from "../../components/landing/about-us/about-us";
import { CTA } from '../../components/landing/cta/cta';

@Component({
  selector: 'app-landing',
  imports: [Header, Hero, Footer, AboutUs, CTA, ComparatorFeatures ],
  templateUrl: './landing.html',
})
export class Landing {

}
