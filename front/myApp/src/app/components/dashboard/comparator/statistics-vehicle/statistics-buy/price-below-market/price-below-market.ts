import { Component, Input } from '@angular/core';
import { BuyStadisticsDTO } from '../../../../../../interfaces/DataResponseCompare';
import { WindowInfo } from "../../../../common/window-info/window-info";

@Component({
  selector: 'app-price-below-market',
  imports: [WindowInfo],
  templateUrl: './price-below-market.html',
})
export class PriceBelowMarket {
  @Input() recivedStatistic!: BuyStadisticsDTO;
  @Input() halfPriceUser!: string;
  infoPriceBelowMarket : string = "Se muestra la cantidad de dinero que el usuario podría perder en comparación con el precio más frecuente del mercado para vehículos similares al introducido por el usuario. El calculo se realiza restando  la mitad del rango de precio de compra del usuario al precio más frecuente del mercado. "
}
