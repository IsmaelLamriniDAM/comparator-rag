import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { VehicleForm } from '../interfaces/dataVehicleForm/vehicleForm';
@Injectable({
  providedIn: 'root',
})
export class ComparatorFormService {
  private URL = 'http://localhost:8091/api/v1/vehicle';

  constructor(private http: HttpClient) {}

  compare(vehicleForm: VehicleForm): Observable<any> {
    console.log("entra aqui por la cara");
    return this.http.post(this.URL + "/compare", vehicleForm, { withCredentials: true });
  }
  getCatalog(): Observable<any> {
    return this.http.get(this.URL + "/catalog", { withCredentials: true });
  }

}