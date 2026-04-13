import { Operation } from "../../../../enums/Operation";
import { Price, PriceRange } from "../../../../interfaces/dataVehicleForm/Price";
import { SetPriceContract } from "../setPriceContract";

export class SetPriceBuy extends SetPriceContract{

    override setPrice(opt: Operation, rangeValueSell: number[], rangeValueBuy: number[]): Price |null{
    
        if(opt.toString() === Operation.BUY.toString()) {
            const range: PriceRange = {
                max: rangeValueBuy[1],
                min: rangeValueBuy[0]
            }

            const price : Price = {
                rangeBuy: range,
                rangeSell: null
            };
            return price
        }
        return this.next!.setPrice(opt, rangeValueBuy, rangeValueSell);
    }

}