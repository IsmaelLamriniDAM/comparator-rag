import { Operation } from "../../../../enums/Operation";
import { DataResponseCompare } from "../../../../interfaces/DataResponseCompare";
import { favouriteVehicle } from "../../../../interfaces/favouriteVehicle";
import { AbstractSearchFav } from "../abstractSearchFav";

export class SearchFavBuyChain extends AbstractSearchFav {

    public override createSearchFav(statistics: DataResponseCompare): favouriteVehicle {
        if(statistics.buyStadisticsDTO) {
            const favVehicle : favouriteVehicle = {
                brand: statistics.buyStadisticsDTO.vehicleFormSent.brand,
                model: statistics.buyStadisticsDTO.vehicleFormSent.model,
                price: statistics.buyStadisticsDTO.vehicleFormSent.price,
                year: statistics.buyStadisticsDTO.vehicleFormSent.year,
                fuelType: statistics.buyStadisticsDTO.vehicleFormSent.fuelType,
                km: statistics.buyStadisticsDTO.vehicleFormSent.km,
                hp: statistics.buyStadisticsDTO.vehicleFormSent.hp,
                recommendation: statistics.buyStadisticsDTO.recommendation,
                operation: Operation.BUY
            };
            return favVehicle;
        }
        return this.next!.createSearchFav(statistics);
    }
    
}