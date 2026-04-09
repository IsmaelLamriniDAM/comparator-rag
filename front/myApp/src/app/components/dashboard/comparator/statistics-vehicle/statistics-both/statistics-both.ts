import { Component, Input } from '@angular/core';
import { BothOperationStadisticsDTO } from '../../../../../interfaces/DataResponseCompare';
import { VehicleUserCriteria } from '../common/vehicle-user-criteria/vehicle-user-criteria';
import { Operation } from '../../../../../enums/Operation';
import { NgClass } from '@angular/common';
import { RecommendationCard } from '../common/recommendation-card/recommendation-card';
import { Recommendation } from '../../../../../enums/Recommendation';
import { WindowInfo } from '../../../common/window-info/window-info';

@Component({
  selector: 'app-statistics-both',
  imports: [VehicleUserCriteria, NgClass, RecommendationCard, WindowInfo],
  templateUrl: './statistics-both.html',
})
export class StatisticsBoth {
  @Input() statisticsBoth!: BothOperationStadisticsDTO
  operationBoth: Operation = Operation.BOTH

  infoMinBuyPrice: string = "Precio mínimo al que se vende en el mercado un vehículo."
  infoVehiclesInPriceRange: string = "Número de vehículos que se venden dentro del rango de precio indicado por el usuario."
  infoProfability: string = "Probabilidad de venta del vehículo introducido por el usuario en base a los datos del mercado actual"
  infoAverageDaysToSell: string = "Tiempo medio que tardan en venderse los vehículos similares al introducido por el usuario"
  infoProfitOperation: string = "Beneficio estimado de la operación del vehículo introducido por el usuario en base a los datos del mercado actual. Esto se calcula restanto la mitad del rango de venta menos la mitad del rango de venta. Si el resultado es positivo, se estima que se ganaría dinero con la operación, mientras que si es negativo, se estima que se perdería dinero con la operación."
  infoVehiclesCompared: string = "Número de vehículos similares al introducido por el usuario que se han comparado en el análisis"


  getCountVehiclesInPriceRange(): string {
    const rangeSell = this.statisticsBoth?.vehicleFormSent.price?.rangeSell;
    return rangeSell ? `${rangeSell.min} € - ${rangeSell.max} €` : '';
  }

  isWinMoney(): boolean {
    return this.statisticsBoth.estimatedSaleProfit.price >= 0;
  }

  getPriceSaleProfit(): string {
    const profit = this.statisticsBoth.estimatedSaleProfit.price;
    return `${profit} €`;
  }

    getRecommendation() {
       if(this.statisticsBoth.recommendation){
         return this.statisticsBoth.recommendation
       }
       return Recommendation.NOT_RECOMMENDED
     }

  getCountVehiclesCompared(): string {
    return this.statisticsBoth.countVehiclesHasBeenCompared != null ? this.statisticsBoth.countVehiclesHasBeenCompared.toString() : "0";
  }
}
