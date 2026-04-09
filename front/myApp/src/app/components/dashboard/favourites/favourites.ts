import { Component, EventEmitter, Output, effect, inject, signal } from '@angular/core';
import { FavouritesList } from "./favourites-list/favourites-list";
import { VehicleForm } from '../../../interfaces/dataVehicleForm/vehicleForm';
import { FavouritesService } from '../../../service/favourites-service.service';
import { receivedFavouriteVehicle } from '../../../interfaces/receivedFavouriteVehicle';

@Component({
  selector: 'app-favourites',
  imports: [FavouritesList],
  templateUrl: './favourites.html',
})
export class Favourites {
  favouritesService = inject(FavouritesService)
  vehiclesList = signal<receivedFavouriteVehicle[]>([]);
  @Output() analyse = new EventEmitter<VehicleForm>();

  constructor(){}

  ngOnInit() {
    this.favouritesService.getAll().subscribe({
      next: (data) => {
        this.vehiclesList.set(data);
      }
    })
  }

  removeFavourite(id: number) {
    this.favouritesService.remove(id).subscribe({
      next: () => {
        this.vehiclesList.update(list =>
          list.filter(vehicle => vehicle.id !== id)
        );
      }
    })
    this.vehiclesList()
  }
 
  onAnalyse(vehicle: VehicleForm) {
    this.analyse.emit(vehicle);
  }

}
