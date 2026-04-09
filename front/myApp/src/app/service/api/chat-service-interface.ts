import { Observable } from "rxjs"
import { ResponseIa } from "../../interfaces/IA/responseIa"
import { HttpClient } from "@angular/common/http"
import { Injectable } from "@angular/core"
import { RequestIa } from "../../interfaces/IA/requestIa"

@Injectable()
export abstract class ChatIaService {

    protected constructor(protected http: HttpClient){}

    public abstract chat(message: RequestIa):  Observable<ResponseIa>

    public abstract getGreeting():  Observable<string>
}