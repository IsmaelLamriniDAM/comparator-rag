import { Component, Input, input, signal, computed, inject} from '@angular/core';
import { DataResponseCompare } from '../../../../interfaces/DataResponseCompare';
import { FavouritesService } from '../../../../service/favourites-service.service';
import { favouriteVehicle } from '../../../../interfaces/favouriteVehicle';
import { MarginCard } from './margin-card/margin-card';
import { RecommendationCard } from './common/recommendation-card/recommendation-card';
import { EstimatedTimeCard } from "./estimated-time-card/estimated-time-card";
import { StatisticsBuy } from './statistics-buy/statistics-buy.component';
import { StatisticsSell } from './statistics-sell/statistics-sell';
import { StatisticsBoth } from './statistics-both/statistics-both';
import { NgClass } from '@angular/common';
import { SearchFavService } from '../../../../service/searchFav/search-fav.service';
import { AbstractSearchFav } from '../../../../util/chainOfResponsability/searchFav/abstractSearchFav';
import { SearchFavBuyChain } from '../../../../util/chainOfResponsability/searchFav/chain/searchFavBuyChain';

@Component({
  selector: 'app-statistics-vehicle-component',
  imports: [StatisticsBuy, StatisticsSell, StatisticsBoth, NgClass],
  templateUrl: './statistics-vehicle.component.html',
  
})
export class StatisticsVehicleComponent {
  @Input() isLoading: boolean = false;
  isFavourite = signal(false);
  collapsed = signal(false);
  favouritesService = inject(FavouritesService);
  favouriteId = signal<number | null>(null);
  
  statistic = input.required<DataResponseCompare | null >(); 

  constructor(private searchFavService: SearchFavService) {}

  toggleFavourite() {
    const isFav = this.isFavourite();
    const favId = this.favouriteId();

    console.log("entra al boton")

    if (isFav && favId != null) {
      this.favouritesService.remove(favId).subscribe();
    }

    if (!isFav) {
      const s = this.statistic();

      const favouriteVehicle = this.searchFavService.buildSearchFav(s!);
      
      this.favouritesService.add(favouriteVehicle).subscribe({
        next: id => this.favouriteId.set(id)
      });
    }

    this.isFavourite.update(f => !f);
  }

  toggleCollapse() {
    this.collapsed.update(collapse => !collapse)
  }

  hasStatisticsBuy = computed(() => this.statistic()?.buyStadisticsDTO != null);

  hasStatisticsSell = computed(() => this.statistic()?.sellStadisticsDTO != null);

  hasStatisticsBoth = computed(() => this.statistic()?.bothStadisticsDTO != null);

  fuelTypes = new Map<string, string>([
    ['Gasolina', 'GASOLINE'],
    ['Diésel', 'DIESEL'],
    ['Eléctrico', 'ELECTRIC'],
    ['Híbrido', 'HYBRID'],
    ['GNC', 'GNC'],
    ['GLP', 'GLP'],
    ['Otro', 'OTHER'],
  ])

  format(n: number) {
    return Number(n).toLocaleString("es-ES");
  }

  formatPercentage(n: number) {
    return n.toFixed(2);
  }

  round(n: number) {
    return Math.round(n);
  }
}
