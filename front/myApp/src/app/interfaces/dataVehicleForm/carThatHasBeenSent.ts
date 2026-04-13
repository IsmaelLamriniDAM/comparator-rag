import { HorsePower } from "./horsePower";
import { Kilometers } from "./kilometers";
import { Price } from "./Price";

export interface CarThatHasBeenSent {
    brand: string | null,
    model: string | null,
    year: number | null,
    hp: HorsePower | null,
    km: Kilometers | null
    price: Price | null,
    fuelType: string | null,
}