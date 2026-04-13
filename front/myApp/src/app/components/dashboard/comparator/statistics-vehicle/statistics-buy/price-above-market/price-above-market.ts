import { Component, Input } from '@angular/core';
import { BuyStadisticsDTO, PriceDifferenceAboveMarketDTO } from '../../../../../../interfaces/DataResponseCompare';
import { WindowInfo } from '../../../../common/window-info/window-info';

@Component({
  selector: 'app-price-above-market',
  imports: [WindowInfo],
  templateUrl: './price-above-market.html',
})
export class PriceAboveMarket {

  @Input() statisticBuyChildren!: BuyStadisticsDTO;
  @Input() halfPriceUser!: string;

  infoPriceAboveMarket : string = "El precio de compra indicado por el usuario es superior al precio de mercado. Esto significa que el usuario podría estar pagando más de lo que debería por el vehículo. El calculo se hace con la mitad del rango propuesto por el usuario de compra, ya que es el punto medio entre el precio mínimo y máximo que el usuario está dispuesto a pagar. Si el precio de mercado es inferior a este punto medio, se considera que el usuario está pagando por encima del mercado."

  

}
