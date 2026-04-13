import { Operation } from "../enums/Operation";
import { Recommendation } from "../enums/Recommendation";
import { HorsePower } from "./dataVehicleForm/horsePower";
import { Kilometers } from "./dataVehicleForm/kilometers";
import { Price } from "./dataVehicleForm/Price";

export interface receivedFavouriteVehicle {
    id: number,
    brand: string,
    model: string,
    price: Price,
    year: number,
    fuelType: string,
    km: Kilometers,
    hp: HorsePower,
    operation: Operation,
    recommendation: Recommendation
}