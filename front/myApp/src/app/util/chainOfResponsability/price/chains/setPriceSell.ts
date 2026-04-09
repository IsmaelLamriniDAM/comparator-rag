import { Operation } from "../../../../enums/Operation";
import { Price, PriceRange } from "../../../../interfaces/dataVehicleForm/Price";
import { SetPriceContract } from "../setPriceContract";

export class SetPriceSell extends SetPriceContract{

    override setPrice(opt: Operation, rangeValueSell: number[], rangeValueBuy: number[]): Price | null {
        if(opt.toString() === Operation.SELL.toString()) {
            console.log("RAMA DE VENTA", rangeValueSell)
            const range : PriceRange = {
                max: rangeValueSell[1],
                min: rangeValueSell[0]
            }


            const price : Price = {
                rangeBuy: null,
                rangeSell: range
            }
             console.log("PRECIO DE VENTA", price)
            return price;
        }
        return this.next!.setPrice(opt, rangeValueBuy, rangeValueSell);
    }
    
}