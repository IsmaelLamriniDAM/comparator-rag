import { Observable } from "rxjs";

export interface ConfirmOperationData {
    message: string;
    confirmAction: () => Observable<any>;
    id?: any
    type: string
    }