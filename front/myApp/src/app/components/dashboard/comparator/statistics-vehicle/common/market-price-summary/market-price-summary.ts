import { Component, Input } from '@angular/core';
import { WindowInfo } from '../../../../common/window-info/window-info';

@Component({
  selector: 'app-market-price-summary',
  imports: [],
  templateUrl: './market-price-summary.html',
})
export class MarketPriceSummary {

  @Input() maxPrice!: number;
  @Input() minPrice!: number;
  @Input() modePrice!: number;
  


}
