import { VehicleForm } from '../interfaces/dataVehicleForm/vehicleForm';
import { DataResponseCompare } from '../interfaces/DataResponseCompare';
import { Operation } from '../enums/Operation';

export const EMPTY_VEHICLE: VehicleForm = {
    brand: "",
    model: "",
    price: {
        rangeBuy: {max: 0, min: 0},
        rangeSell: {max: 0, min: 0}
     },
    year: 0,
    fuelType: "",
    km: { min: 0, max: 0 },
    hp: { min: 0, max: 0 },
    operation: Operation.BUY
}

