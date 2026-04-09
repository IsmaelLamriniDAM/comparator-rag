import { Component, Input } from '@angular/core';
import { SellStadisticsDTO } from '../../../../../interfaces/DataResponseCompare';
import { VehicleUserCriteria } from '../common/vehicle-user-criteria/vehicle-user-criteria';
import { MarketPriceSummary } from '../common/market-price-summary/market-price-summary';
import { Operation } from '../../../../../enums/Operation';
import { NgClass } from '@angular/common';
import { RecommendationCard } from '../common/recommendation-card/recommendation-card';
import { Recommendation } from '../../../../../enums/Recommendation';
import { WindowInfo } from "../../../common/window-info/window-info";

@Component({
  selector: 'app-statistics-sell',
  imports: [VehicleUserCriteria, MarketPriceSummary, NgClass, RecommendationCard, WindowInfo],
  templateUrl: './statistics-sell.html',
})
export class StatisticsSell {

  @Input() statisticSell!: SellStadisticsDTO;

  operationSell = Operation.SELL;

  infoProfability: string = "Probabilidad de venta del vehículo introducido por el usuario en base a los datos del mercado actual"
  infoMarketSumary : string = "Resumen de los precios de coches vendidos del mercado para vehículos similares introducidos por el usuario"
  infoAverageDaysToSell: string = "Tiempo medio que tardan en venderse los vehículos similares al introducido por el usuario"
  infoVehiclesCompared: string = "Número de vehículos similares al introducido por el usuario que se han comparado en el análisis"
  
   getRecommendation() {
    if(this.statisticSell.recommendation) {
      return this.statisticSell.recommendation
    }
    return Recommendation.NOT_RECOMMENDED
  }

  getCountVehiclesCompared(): string {
    return this.statisticSell.countVehiclesHasBeenCompared != null ? this.statisticSell.countVehiclesHasBeenCompared.toString() : "0";
  }

}
