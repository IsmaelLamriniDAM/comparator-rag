import { Injectable } from '@angular/core';
import { AbstractSearchFav } from '../../util/chainOfResponsability/searchFav/abstractSearchFav';
import { Data } from '@angular/router';
import { DataResponseCompare } from '../../interfaces/DataResponseCompare';
import { favouriteVehicle } from '../../interfaces/favouriteVehicle';
import { SearchFavBuyChain } from '../../util/chainOfResponsability/searchFav/chain/searchFavBuyChain';
import { SearchFavBothChain } from '../../util/chainOfResponsability/searchFav/chain/searchFavBothChain';
import { SearchFavSellChain } from '../../util/chainOfResponsability/searchFav/chain/searchFavSellChain';

@Injectable({
  providedIn: 'root',
  
})
export class SearchFavService {

  
  private abstractSearchFav: AbstractSearchFav;

  constructor() {
    const searchFavBuyChain = new SearchFavBuyChain();
    const searchFavSellChain = new SearchFavSellChain();
    const searchFavBothChain = new SearchFavBothChain();

    searchFavBuyChain.setNext(searchFavSellChain);
    searchFavSellChain.setNext(searchFavBothChain);

    this.abstractSearchFav = searchFavBuyChain;
  }

  buildSearchFav(statistics: DataResponseCompare): favouriteVehicle {
    return this.abstractSearchFav.createSearchFav(statistics);
  }

}
