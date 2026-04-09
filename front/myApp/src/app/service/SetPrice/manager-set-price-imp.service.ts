import { Injectable } from '@angular/core';
import { ManagerSetPrice } from '../../service/SetPrice/manager-set-price';

import { Price } from '../../interfaces/dataVehicleForm/Price';
import { SetPriceBuy } from '../../util/chainOfResponsability/price/chains/setPriceBuy';
import { SetPriceSell } from '../../util/chainOfResponsability/price/chains/setPriceSell';
import { SetPriceBoth } from '../../util/chainOfResponsability/price/chains/setPriceBoth';
import { Operation } from '../../enums/Operation';

@Injectable({
  providedIn: 'root',
})
export class ManagerSetPriceImp extends ManagerSetPrice {

  constructor() {
    super();
    this.handlerSetPrice = new SetPriceBuy();
    let priceSell = new SetPriceSell();
    let priceBoth = new SetPriceBoth();

    this.handlerSetPrice.setNext(priceSell);
    priceSell.setNext(priceBoth);
  }

  
  set(opt: Operation, rangeValueSell: number[], rangeValueBuy: number[]): Price | null {
    return this.handlerSetPrice!.setPrice(opt, rangeValueSell, rangeValueBuy);
  }

}
