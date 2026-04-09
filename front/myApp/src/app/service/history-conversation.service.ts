import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HistoryDto } from '../interfaces/IA/historyDto';
import { MessageAndRol } from '../interfaces/IA/messageAndRol';

@Injectable({
  providedIn: 'root',
})
export class HistoryConversation {

  private readonly BASE_URL: string = "http://localhost:8091/api/v1/history-chat"

  private readonly GET_ALL: string = "/all"
  private readonly GET_MESSAGES_HISTORY: string = "/history"
  private readonly DELETE_HISTORY: string = "/delete/history"
  private readonly DELETE_ALL_HISTORIES: string = "/delete/all"


  constructor(private http: HttpClient){}
  
  deleteOneHistory(id: string): Observable<any> {
    return this.http.delete(this.BASE_URL + this.DELETE_HISTORY, {params: {id}, withCredentials: true })
  }
  
  deleteAllHistory(): Observable<any>{
    return this.http.delete(this.BASE_URL + this.DELETE_ALL_HISTORIES, {withCredentials: true})
  }

  getAllMessagesForHisHistory(id: string): Observable<MessageAndRol[]> {
    return this.http.get<MessageAndRol[]>(this.BASE_URL + this.GET_MESSAGES_HISTORY , {params: {id}, withCredentials: true })
  }

  getAllHistories(): Observable<HistoryDto[]> {
    return this.http.get<HistoryDto[]>(this.BASE_URL + this.GET_ALL , { withCredentials: true })
  }
}
