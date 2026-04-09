import { Component, signal, input, Type, computed } from '@angular/core';
import { Sidebar } from "../../components/dashboard/sidebar/sidebar";
import { Comparator } from '../../components/dashboard/comparator/comparator';
import { ProfileUser } from '../../components/dashboard/profile-user/profile-user';
import { Favourites } from '../../components/dashboard/favourites/favourites';
import { VehicleForm } from '../../interfaces/dataVehicleForm/vehicleForm';
import { EMPTY_VEHICLE } from '../../constants/constants';
import { AiComponent } from '../../components/dashboard/ia/ia';
import { AiHistoryChat } from "../../components/dashboard/ia/ai-history-chat/ai-history-chat";
import { ResponseIa } from '../../interfaces/IA/responseIa';
import { HistoryDto } from '../../interfaces/IA/historyDto';

@Component({
  selector: 'app-dashboard',
  imports: [Sidebar, ProfileUser, Comparator, Favourites, AiComponent],
  templateUrl: './dashboard.html',
})
export class Dashboard {
  collapsedSidebar = signal(false);
  activeView = signal('comparator');


  incrementComparisons = 0;
  
  vehicleData = EMPTY_VEHICLE;

  vehicleFromFavourites = signal<VehicleForm>(this.vehicleData);
  onAnalyseFromFavourites(vehicle: VehicleForm) {
    this.vehicleFromFavourites.set(vehicle);
    this.activeView.set("comparator");
  }
 

}
