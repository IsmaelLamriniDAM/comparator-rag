import { Operation } from "../enums/Operation";
import { Recommendation } from "../enums/Recommendation";
import { HorsePower } from "./dataVehicleForm/horsePower";
import { Kilometers } from "./dataVehicleForm/kilometers";
import { Price } from "./dataVehicleForm/Price";

export interface favouriteVehicle {
    brand: string | null,
    model: string | null,
    price: Price | null,
    year: number | null,
    fuelType: string | null,
    km: Kilometers | null,
    hp: HorsePower | null,
    operation: Operation,
    recommendation: Recommendation
    
}