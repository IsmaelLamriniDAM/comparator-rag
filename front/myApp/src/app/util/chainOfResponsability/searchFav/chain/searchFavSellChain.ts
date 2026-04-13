import { Operation } from "../../../../enums/Operation";
import { DataResponseCompare } from "../../../../interfaces/DataResponseCompare";
import { favouriteVehicle } from "../../../../interfaces/favouriteVehicle";
import { AbstractSearchFav } from "../abstractSearchFav";

export class SearchFavSellChain extends AbstractSearchFav {
    public override createSearchFav(statistics: DataResponseCompare): favouriteVehicle {
        if(statistics.sellStadisticsDTO) {
            const favVehicle : favouriteVehicle = {
                brand: statistics.sellStadisticsDTO.vehicleFormSent.brand,
                model: statistics.sellStadisticsDTO.vehicleFormSent.model,
                price: statistics.sellStadisticsDTO.vehicleFormSent.price,
                year: statistics.sellStadisticsDTO.vehicleFormSent.year,
                fuelType: statistics.sellStadisticsDTO.vehicleFormSent.fuelType,
                km: statistics.sellStadisticsDTO.vehicleFormSent.km,
                recommendation: statistics.sellStadisticsDTO.recommendation,
                hp: statistics.sellStadisticsDTO.vehicleFormSent.hp,
                operation: Operation.SELL
            };
            return favVehicle;

        }
        return this.next!.createSearchFav(statistics);
    }

   

}
