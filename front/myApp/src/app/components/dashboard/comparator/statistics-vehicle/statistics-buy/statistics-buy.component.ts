import { Component, Input, signal } from '@angular/core';
import { BuyStadisticsDTO, DataResponseCompare } from '../../../../../interfaces/DataResponseCompare';
import { PriceAboveMarket } from './price-above-market/price-above-market';
import { CommonModule } from '@angular/common';
import { PriceBelowMarket } from './price-below-market/price-below-market';
import { VehicleUserCriteria } from '../common/vehicle-user-criteria/vehicle-user-criteria';
import { MarketPriceSummary } from "../common/market-price-summary/market-price-summary";
import { Operation } from '../../../../../enums/Operation';
import { RecommendationCard } from '../common/recommendation-card/recommendation-card';
import { Recommendation } from '../../../../../enums/Recommendation';
import { WindowInfo } from "../../../common/window-info/window-info";

@Component({
  selector: 'app-statistics-buy',
  imports: [PriceAboveMarket, CommonModule, PriceBelowMarket, VehicleUserCriteria, MarketPriceSummary, RecommendationCard, WindowInfo],
  templateUrl: './statistics-buy.html',
})
export class StatisticsBuy {

  @Input() statisticBuy!: BuyStadisticsDTO;

  operationBuy = Operation.BUY;
  
  
  infoMarketSumary : string = "Resumen de los precios en venta para vehículos similares al introducido por el usuario"
  infoVehiclesSellForUserPrice : string = "Número de vehículos que se venden en el mercado por el rango de precio indicado por el usuario"
  infoVehiclesCompared: string = "Número de vehículos similares al introducido por el usuario que se han comparado en el análisis"
  priceBuyNotIndentify: string = "No se ha podido identificar el precio de compra del vehículo introducido por el usuario. Sin esta información, no se pueden realizar comparaciones precisas con los precios del mercado, lo que limita la capacidad de proporcionar recomendaciones y análisis detallados sobre la compra del vehículo."
  
  
  isAboveMaketPrice(): boolean {
    return this.statisticBuy.priceDifferenceAboveMarket != null;
  }

  loseMoney(): boolean {
    return this.statisticBuy.priceIncreaseNeededToReachMarket != null;
  }


  getPriceMin(): string {
    return this.statisticBuy.vehicleFormSent.price?.rangeBuy?.min != null ? this.statisticBuy.vehicleFormSent.price.rangeBuy.min.toString() + " €" : "No especificado";
  }

  getPriceMax(): string {
    return this.statisticBuy.vehicleFormSent.price?.rangeBuy?.max != null ? this.statisticBuy.vehicleFormSent.price.rangeBuy.max.toString() + " €" : "No especificado";
  } 

  getCountCarsSellForUserPrice(): string {
    return this.statisticBuy.marketVehicleCountAtUserBuyPrice != null ? this.statisticBuy.marketVehicleCountAtUserBuyPrice.toString() : "0";
  }

  getHaflPriceRangeUser(): string {
    return ((this.statisticBuy!.vehicleFormSent!.price!.rangeBuy!.min + this.statisticBuy!.vehicleFormSent!.price!.rangeBuy!.max) / 2).toString();
  }

  getCountVehiclesCompared(): string {
    console.log(this.statisticBuy)
    return this.statisticBuy.countVehiclesHasBeenCompared != null ? this.statisticBuy.countVehiclesHasBeenCompared.toString() : "0";
  }

  getRecommendation() {
    if(this.statisticBuy.recommendation){
      return this.statisticBuy.recommendation
    }
    return Recommendation.NOT_RECOMMENDED
  }

}
