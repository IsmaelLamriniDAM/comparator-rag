import { Operation } from "../../../../enums/Operation";
import { DataResponseCompare } from "../../../../interfaces/DataResponseCompare";
import { favouriteVehicle } from "../../../../interfaces/favouriteVehicle";
import { AbstractSearchFav } from "../abstractSearchFav";

export class SearchFavBothChain extends AbstractSearchFav {

    public override createSearchFav(statistics: DataResponseCompare): favouriteVehicle {
        const favVehicle : favouriteVehicle = {
            brand: statistics.bothStadisticsDTO.vehicleFormSent.brand,  
            model: statistics.bothStadisticsDTO.vehicleFormSent.model,
            price: statistics.bothStadisticsDTO.vehicleFormSent.price,
            year: statistics.bothStadisticsDTO.vehicleFormSent.year,
            fuelType: statistics.bothStadisticsDTO.vehicleFormSent.fuelType,
            km: statistics.bothStadisticsDTO.vehicleFormSent.km,
            hp: statistics.bothStadisticsDTO.vehicleFormSent.hp,
            recommendation: statistics.bothStadisticsDTO.recommendation,
            operation: Operation.BOTH
        };
        return favVehicle;
    }
}