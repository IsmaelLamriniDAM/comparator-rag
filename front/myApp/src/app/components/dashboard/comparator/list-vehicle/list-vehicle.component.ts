import { Component, computed, Input, input, signal } from '@angular/core';
import type { Vehicle } from '../../../../interfaces/Vehicle';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-list-vehicle-component',
  imports: [DatePipe],
  templateUrl: './list-vehicle.component.html',
})
export class ListVehicleComponent {
  vehicleList = input<Vehicle[]>([]);
  listLength = computed(() => this.vehicleList().length);
  collapsed = signal(false);
  isLoading = input(false);


  toggleCollapse() {
    this.collapsed.update(collapse => !collapse)
  }
}