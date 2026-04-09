import { Operation } from "../../../../enums/Operation";
import { Price, PriceRange } from "../../../../interfaces/dataVehicleForm/Price";
import { SetPriceContract } from "../setPriceContract";

export class SetPriceBoth extends SetPriceContract{
     override setPrice(opt: Operation, rangeValueSell: number[], rangeValueBuy: number[]): Price | null {
          const rangeBuy : PriceRange = {
               max: rangeValueBuy[1],
               min: rangeValueBuy[0]
          }

          const rangeSell : PriceRange = {
               max: rangeValueSell[1],
               min: rangeValueSell[0]
          }

          const price : Price = {
               rangeBuy: rangeBuy,
               rangeSell: rangeSell
          }
          return price
     }

}