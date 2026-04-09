import { NgClass } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-margin-card',
  imports: [NgClass],
  templateUrl: './margin-card.html',
})
export class MarginCard {
  @Input() title!: string;
  @Input() money!: string;
  @Input() percentage!: string;
  @Input() state!: "buy" | "caution" | "notbuy";

  styles = {
    buy: {
      bg: "bg-green-500/20",
      text: "text-green-400",
    },
    caution: {
      bg: "bg-yellow-500/20",
      text: "text-yellow-400",
    },
    notbuy: {
      bg: "bg-red-500/20",
      text: "text-red-400",
    }
  }
}
