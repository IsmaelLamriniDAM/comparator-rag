import { Operation } from "../../enums/Operation";
import { Price } from "../../interfaces/dataVehicleForm/Price";
import { SetPriceBoth } from "../../util/chainOfResponsability/price/chains/setPriceBoth";
import { SetPriceBuy } from "../../util/chainOfResponsability/price/chains/setPriceBuy";
import { SetPriceSell } from "../../util/chainOfResponsability/price/chains/setPriceSell";
import { SetPriceContract } from "../../util/chainOfResponsability/price/setPriceContract";

export abstract class ManagerSetPrice{

    protected handlerSetPrice: SetPriceContract | null = null 
    
    constructor() {
       
    }

    abstract set(opt: Operation, rangeValueSell: number[], rangeValueBuy: number[]):Price | null;
}