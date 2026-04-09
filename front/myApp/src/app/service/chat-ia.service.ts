import { Injectable } from '@angular/core';
import { ChatIaService } from './api/chat-service-interface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponseIa } from '../interfaces/IA/responseIa';
import { RequestIa } from '../interfaces/IA/requestIa';

@Injectable({
  providedIn: 'root',
})
export class ChatIaServiceImp extends ChatIaService{

  private readonly BASE_URL: string = 'http://localhost:8091/api/v1/ia'
  private readonly KEY_CHAT: string = "/chat"
  private readonly KEY_GREETING: string = "/greeting"

  constructor(http : HttpClient) {
    super(http);
  }

  chat(dataDto: RequestIa): Observable<ResponseIa> {
    return this.http.post<ResponseIa>(this.BASE_URL + this.KEY_CHAT, dataDto, {withCredentials: true})
  }
  getGreeting(): Observable<string> {
    return this.http.get<string>(this.BASE_URL + this.KEY_GREETING, {withCredentials: true})
  }
  
}
