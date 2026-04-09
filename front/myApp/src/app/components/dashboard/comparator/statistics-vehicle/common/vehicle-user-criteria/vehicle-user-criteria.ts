import { Component, Input, input } from '@angular/core';
import { BuyStadisticsDTO } from '../../../../../../interfaces/DataResponseCompare';
import { VehicleForm } from '../../../../../../interfaces/dataVehicleForm/vehicleForm';
import { CarThatHasBeenSent } from '../../../../../../interfaces/dataVehicleForm/carThatHasBeenSent';
import { Operation } from '../../../../../../enums/Operation';
import { Price, PriceRange } from '../../../../../../interfaces/dataVehicleForm/Price';
import { WindowInfo } from "../../../../common/window-info/window-info";

@Component({
  selector: 'app-vehicle-user-criteria',
  imports: [WindowInfo],
  templateUrl: './vehicle-user-criteria.html',
})
export class VehicleUserCriteria {

  @Input() vehicleCriteria!: CarThatHasBeenSent;
  @Input() operation!: Operation
  infoCriteria : string = "Criterios del vehículo introducido por el usuario"


  
  
  isOperationBoth(): boolean {
    return this.operation === Operation.BOTH;
  }

  getBrand(): string {
    return this.vehicleCriteria?.brand ?? "Marca no especificado";
  }

  getModel(): string {
    return this.vehicleCriteria?.model ?? "Modelo no especificado";
  }

  getYear(): string {
    return this.vehicleCriteria?.year != null ? this.vehicleCriteria.year.toString() : "Año no especificado";
  }

  getPriceMin(): string {
    if(this.operation === Operation.BUY) {
      return this.vehicleCriteria.price?.rangeBuy?.min != null ? this.vehicleCriteria.price.rangeBuy.min.toString() + " €" : "No especificado";
    } else if(this.operation === Operation.SELL) {
      return this.vehicleCriteria.price?.rangeSell?.min != null ? this.vehicleCriteria.price.rangeSell.min.toString() + " €" : "No especificado";
    } else {
      const minBuy = this.vehicleCriteria.price?.rangeBuy?.min;
      const minSell = this.vehicleCriteria.price?.rangeSell?.min;
      if (minBuy != null && minSell != null) {
        return `compra: ${minBuy} €  -  venta: ${minSell} €`;
      }
    }
    return "No especificado";
  }

  getPriceMax(): string |Price{
    if(this.operation === Operation.BUY) {
      return this.vehicleCriteria.price?.rangeBuy?.max != null ? this.vehicleCriteria.price.rangeBuy.max.toString() + " €" : "No especificado";
    } else if(this.operation === Operation.SELL) {
      return this.vehicleCriteria.price?.rangeSell?.max != null ? this.vehicleCriteria.price.rangeSell.max.toString() + " €" : "No especificado";
    } else {
      const maxBuy = this.vehicleCriteria.price?.rangeBuy?.max;
      const maxSell = this.vehicleCriteria.price?.rangeSell?.max;
      if (maxBuy != null && maxSell != null) {
        return `compra: ${maxBuy} €  -  venta: ${maxSell} €`;
      }
    }
    return "No especificado";
  } 

  getKmMin(): string {
    return this.vehicleCriteria.km?.min != null ? this.vehicleCriteria.km.min.toString() + " km" : "No especificado";
  }

  getKmMax(): string {
    return this.vehicleCriteria.km?.max != null ? this.vehicleCriteria.km.max.toString() + " km" : "No especificado";
  }

  getHpMin(): string {
    return this.vehicleCriteria.hp?.min != null ? this.vehicleCriteria.hp.min.toString() + " CV" : "No especificado";
  }

  getHpMax(): string {
    return this.vehicleCriteria.hp?.max != null ? this.vehicleCriteria.hp.max.toString() + " CV" : "No especificado";
  }

  getFuelType(): string {
    return this.vehicleCriteria.fuelType ?? "No especificado";
  }
}
