import { Nullable } from "primeng/ts-helpers"
import { Kilometers } from "./kilometers"
import { Price } from "./Price"
import { HorsePower } from "./horsePower"
import { Operation } from "../../enums/Operation"

export interface VehicleForm {
    model: string | Nullable,
    brand: string | Nullable,
    price: Price | Nullable,
    year: number | Nullable,
    fuelType: string,
    hp: HorsePower,
    km: Kilometers
    operation: Operation | string
}
