import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-estimated-time-card',
  imports: [],
  templateUrl: './estimated-time-card.html',
})
export class EstimatedTimeCard {
  @Input() estimatedTime!: number;
  @Input() mostFrequentClosingPrice!: number;

  format(n: number) {
    return Number(n).toLocaleString("es-ES");
  }
}
