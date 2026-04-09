import { Component, input, output , signal } from '@angular/core';
import { ConfirmOperationData } from '../../../../interfaces/pop-up/confirmOperationData';

@Component({
  selector: 'app-pop-up',
  imports: [],
  templateUrl: './pop-up.html',
})
export class PopUp {


  deleteElementList = output<string | null>()

  dataDeleteConfirm = input<ConfirmOperationData | null>()

  popupVisible = signal<boolean>(false);

  ngOnChanges() {
    this.popupVisible.set(this.dataDeleteConfirm() != null)
  }

  makeAction() {
    this.dataDeleteConfirm()!.confirmAction().subscribe({
      complete: () => {
        console.log("Operacion ejecutado con exito")
        this.deleteElementList.emit(this.dataDeleteConfirm()!.id)
        this.closePopup()
      },
      error: () =>{console.log("Error al realizar la operacion")} 
    }
      
    )
  }

  closePopup(){
    this.popupVisible.set(false)
  }

  
}
