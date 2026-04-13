import { Component, signal, effect, input, Output, EventEmitter } from '@angular/core';
import { ComparatorFormComponent } from "./comparator-form/comparator-form.component";
import { StatisticsVehicleComponent } from "./statistics-vehicle/statistics-vehicle.component";
import { ListVehicleComponent } from "./list-vehicle/list-vehicle.component";
import { ComparatorFormService } from '../../../service/comparator-form.service';
import { VehicleForm } from '../../../interfaces/dataVehicleForm/vehicleForm';
import { BothOperationStadisticsDTO, BuyStadisticsDTO, DataResponseCompare, SellStadisticsDTO } from '../../../interfaces/DataResponseCompare';
import {  EMPTY_VEHICLE } from '../../../constants/constants';
import { Vehicle } from '../../../interfaces/Vehicle';
import { AuthService } from '../../../service/auth-service.service';

@Component({
  selector: 'app-comparator',
  imports: [ComparatorFormComponent, StatisticsVehicleComponent, ListVehicleComponent],
  templateUrl: './comparator.html',
})



export class Comparator {

  vehicleData = EMPTY_VEHICLE;
  vehicleFromFavourites = input<VehicleForm>(this.vehicleData);
  submittedVehicleForm = signal<VehicleForm>(this.vehicleData);

  buyStatistics: BuyStadisticsDTO | null = null;
  sellStatistics: SellStadisticsDTO | null = null;
  bothStatistics: BothOperationStadisticsDTO | null = null;

  
  dataResponse = signal<DataResponseCompare | null>(null);

  listVehiclesCompared = signal<Vehicle[]>([]);

 

  constructor(private comparatorFormService: ComparatorFormService, private authService: AuthService) {
   
  }

  isLoading = signal(false);
  errorMessage = signal<string>("");
  formCollapsed = signal(false);
  recommendation: "buy" | "caution" | "notbuy" = "buy";
  marginMaxPrice: "buy" | "caution" | "notbuy" = "buy";
  marginModePrice: "buy" | "caution" | "notbuy"  = "buy";
  marginAveragePrice: "buy" | "caution" | "notbuy"  = "buy";

  
  sendComparatorForm(vehicleForm: VehicleForm) {

      this.isLoading.set(true);
      this.errorMessage.set("");

      this.comparatorFormService.compare(vehicleForm).subscribe({
        next: (statistics: DataResponseCompare) => {
          this.formCollapsed.set(true);

          
          this.dataResponse.set(statistics);
          this.listVehiclesCompared.set(this.getListVehiclesCompared(statistics));
          this.authService.addCompare()

          this.isLoading.set(false);
        },
        error: err => {
          this.isLoading.set(false);
          const fieldErrors = err?.error?.fieldErrors;
          const fieldError = fieldErrors ? Object.values(fieldErrors)[0] : "";
          this.errorMessage.set(err.error.userMessage + " " + fieldError);
          this.formCollapsed.set(false);
        }
      });
  }



  private getListVehiclesCompared(statistics: DataResponseCompare): Vehicle[] {
    const buyVehicles = statistics.buyStadisticsDTO?.listVehiclesCompared;
    const sellVehicles = statistics.sellStadisticsDTO?.listVehiclesCompared;
    const bothVehicles = statistics.bothStadisticsDTO?.listVehiclesCompared;

    let allVehicles: Vehicle[] = [];
     if (buyVehicles) allVehicles = allVehicles.concat(buyVehicles);
     if (sellVehicles) allVehicles = allVehicles.concat(sellVehicles);
     if (bothVehicles) allVehicles = allVehicles.concat(bothVehicles);

    const uniqueVehicles = new Map<string, Vehicle>();  
    allVehicles.forEach(vehicle => {
      uniqueVehicles.set(vehicle.webVehicleUrl, vehicle);
    });
    return Array.from(uniqueVehicles.values());
  }

  fuelTypes = new Map<string, string>([
    ['GASOLINE', 'Gasolina'],
    ['DIESEL', 'Diésel'],
    ['ELECTRIC', 'Eléctrico'],
    ['HYBRID', 'Híbrido'],
    ['GNC', 'GNC'],
    ['GLP', 'GLP'],
    ['OTHER', 'Otro'],
  ])
}
