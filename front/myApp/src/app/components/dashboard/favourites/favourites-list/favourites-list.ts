import { Component, input, Output, EventEmitter, inject, signal, effect } from '@angular/core';
import { VehicleForm } from '../../../../interfaces/dataVehicleForm/vehicleForm';
import { FavouritesService } from '../../../../service/favourites-service.service';
import { receivedFavouriteVehicle } from '../../../../interfaces/receivedFavouriteVehicle';
import { NgClass } from '@angular/common';
import { PopUp } from '../../common/pop-up/pop-up';
import { ConfirmOperationData } from '../../../../interfaces/pop-up/confirmOperationData';
import { filter, single } from 'rxjs';

@Component({
  selector: 'app-favourites-list',
  imports: [NgClass, PopUp],
  templateUrl: './favourites-list.html',
})
export class FavouritesList {
  receivedFavouritesList = input<receivedFavouriteVehicle[]>([]);
  favouritesList = signal<receivedFavouriteVehicle[]>([]);
  @Output() remove = new EventEmitter<number>();
  @Output() analyse = new EventEmitter<VehicleForm>();

  isDelete = signal<boolean>(false)

  dataDeleteFavSearch = signal<ConfirmOperationData | null>(null)

  favouritesService = inject(FavouritesService)
  popupVisible = signal<"delete-one" | "delete-all" | "">("");
  selectedIndex = -1;

  constructor(){
    effect(() => {
      this.favouritesList.set(this.receivedFavouritesList());
    })
  }

  openDeleteAllPopup() {
    this.dataDeleteFavSearch.set({
      message: "Si eliminas esto, no podrás volver a recuperar las búsquedas favoritas eliminadas.",
      confirmAction: () =>  this.favouritesService.removeAll(),
      type: "ALL"
    })
  }

  openDeleteOnePopup(index: number) {
    this.dataDeleteFavSearch.set({
      message: "Si eliminas esto, no podrás volver a recupera la búsqueda favorita eliminada.",
      confirmAction: () =>  this.favouritesService.remove(index),
      id: index,
      type: "ONE"
    })
  }

  deleteFavInList(id: any | null): void {
    if(id != null) {
      this.favouritesList.update(list =>
                list.filter(vehicle => vehicle.id != id)
              );
    } else {
       this.favouritesList.set([])
    }
  }

  closePopup() {
    this.popupVisible.set("");
  }

  makeAction() {
    if(this.popupVisible() == "delete-all") {
      this.favouritesList.set([]);
      this.favouritesService.removeAll().subscribe({
        next: () => {}
      });
      this.popupVisible.set("");
    } else if(this.popupVisible() == "delete-one"){
      this.remove.emit(this.selectedIndex);
      this.popupVisible.set("");
    }
  }

  analyseFavourite(item: VehicleForm) {
    this.analyse.emit(item);
  }

  format(n: number) {
    return Number(n).toLocaleString("es-ES");
  }
}
