import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BrandModelService {
  
  private URL_BASE_ENDPOINT: string = 'http://localhost:8091/api/v1/catalog'

  constructor(private http: HttpClient) {}

  getAllBrandHisModels(): Observable<any> {
    return this.http.get(this.URL_BASE_ENDPOINT + '/brandModels', { withCredentials: true });
  }


}
