import { DataResponseCompare } from "../../../interfaces/DataResponseCompare";
import { favouriteVehicle } from "../../../interfaces/favouriteVehicle";

export abstract class AbstractSearchFav {

    protected next: AbstractSearchFav | null = null;

    public setNext(next: AbstractSearchFav) {
        this.next = next;
    }

    public abstract createSearchFav(statistics: DataResponseCompare): favouriteVehicle;
}