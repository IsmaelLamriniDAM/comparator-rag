import { Recommendation } from "../enums/Recommendation"
import { CarThatHasBeenSent } from "./dataVehicleForm/carThatHasBeenSent"
import { PriceRange } from "./dataVehicleForm/Price"
import { Vehicle } from "./Vehicle"

export interface DataResponseCompare{
    buyStadisticsDTO: BuyStadisticsDTO,
    sellStadisticsDTO: SellStadisticsDTO,
    bothStadisticsDTO: BothOperationStadisticsDTO,
    question: QuestionUser
}

export interface BuyStadisticsDTO{
    marketMaxPrice: number,
    marketMinPrice: number,
    marketMostFrequentPrice: number,
    marketVehicleCountAtUserBuyPrice: number,
    priceIncreaseNeededToReachMarket: PriceIncreaseNeededToReachMarketDTO,
    priceDifferenceAboveMarket: PriceDifferenceAboveMarketDTO
    vehicleFormSent: CarThatHasBeenSent
    listVehiclesCompared: Vehicle[]
    recommendation: Recommendation
    countVehiclesHasBeenCompared: number;
};

    export interface PriceIncreaseNeededToReachMarketDTO{
        percentage: number,
        money: number
    }

    export interface PriceDifferenceAboveMarketDTO{
        percentage: number,
        money: number
    }

export interface SellStadisticsDTO{
    marketMaxSellPrice: number,
    marketMinSellPrice: number,
    marketMostFrequentSellPrice: number,
    sellProbability: number,
    averageDaysToSell: number,
    vehicleFormSent: CarThatHasBeenSent,
    listVehiclesCompared: Vehicle[]
    recommendation: Recommendation
    countVehiclesHasBeenCompared: number;
}

export interface BothOperationStadisticsDTO{
    marketSellComparableVehicleCount: number,
    sellSuccessProbability: number,
    estimatedSaleProfit: SaleProfitDTO,
    averageDaysToSell: number,
    marketMinBuyPrice: number,
    vehicleFormSent: CarThatHasBeenSent,
    listVehiclesCompared: Vehicle[]
    recommendation: Recommendation
    countVehiclesHasBeenCompared: number;
}

    export interface SaleProfitDTO{
        percentage: number,
        price: number
    }  
    
export interface QuestionUser{
    question: string,
    priceSell: PriceRange,
    priceBuy: PriceRange
}
