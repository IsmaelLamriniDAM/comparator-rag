import { Nullable } from "primeng/ts-helpers"

export interface Price{
    rangeBuy: PriceRange | Nullable
    rangeSell: PriceRange  | Nullable
}

export interface PriceRange{
    max: number 
    min: number
}