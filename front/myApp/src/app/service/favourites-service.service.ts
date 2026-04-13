import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { favouriteVehicle } from '../interfaces/favouriteVehicle';

@Injectable({
  providedIn: 'root',
})
export class FavouritesService {
  private WEB_URL = 'http://localhost:8091/api/v1/fav';
  constructor(private http: HttpClient) {}

  add(form: favouriteVehicle): Observable<any> {
    return this.http.post(this.WEB_URL + '/add', form, { withCredentials: true });
  }
  remove(id: number | null): Observable<any> {
    return this.http.delete(this.WEB_URL + `/remove/${id}`, { withCredentials: true });
  }
  getAll(): Observable<any> {
    return this.http.get(this.WEB_URL + '/all', { withCredentials: true });
  }
  removeAll(): Observable<any> {
    return this.http.delete(this.WEB_URL + '/removeAll', { withCredentials: true });
  }
}
