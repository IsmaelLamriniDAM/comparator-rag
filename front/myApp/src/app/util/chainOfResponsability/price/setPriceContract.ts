import { Operation } from "../../../enums/Operation"
import { Price } from "../../../interfaces/dataVehicleForm/Price"

/**
 * Abstract base class that implements the Chain of Responsibility design pattern
 * for setting prices based on different operation strategies.
 * 
 * @abstract
 * @class ManagerSetPrice
 * @description This class defines the structure for a chain of price managers,
 * where each manager can handle price setting logic or delegate to the next manager in the chain.
 */
export abstract class SetPriceContract {

    protected next: SetPriceContract | null = null

    setNext(next: SetPriceContract) {
        this.next = next
    }

    abstract setPrice(opt: Operation, rangeValueSell: number[], rangeValueBuy: number[]) : Price | null;


}