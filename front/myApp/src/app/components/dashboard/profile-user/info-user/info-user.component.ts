import { booleanAttribute, Component, computed, signal, effect, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {ProfileUpdate} from '../../../../interfaces/ProfileUpdate'
import { single } from 'rxjs';
import { NgClass } from '@angular/common';
import { AuthService } from '../../../../service/auth-service.service';
import { UserRemain } from '../../../../interfaces/userRemain';


@Component({
  selector: 'app-info-user-component',
  imports: [FormsModule, NgClass],
  templateUrl: './info-user.component.html',
})
export class InfoUserComponent {

  constructor(private authService: AuthService) {
    effect(() => {
      const user = this.authService.user();

      if (!user) return;

      this.name.set(user.name);
      this.phone.set(user.phoneNumber);
      this.numCochesBuscados.set(user.numComparinsons + this.incrementComparison);
    });
  }

  @Input() incrementComparison: number = 0;

  userCurrent: UserRemain | null = null;
  name = signal('');
  phone = signal('')
  numCochesBuscados = signal(0);
  enable = signal(false);
  listErrors = signal<Set<string>>(new Set());
  messageErroResponse = signal('');
  messageErroResponseComputed = computed(() => this.messageErroResponse())
  collapsed = signal(true);
  disableName = signal(true);
  disablePhone = signal(true);
  disableNameComputed = computed(() => this.disableName());
  disablePhoneComputed = computed(() => this.disablePhone());
  changeName = signal(this.name());
  changePhone = signal(this.phone());
  messageErrorName= signal('');
  messageErrorPhone = signal('');
  messageSuccessfulResponse = signal('');
  messageSuccessfulResponseCmt = computed(() => this.messageSuccessfulResponse())
  openTab = signal(false)

  changeStatusInputName(){
    this.disableName.update(currentEnable => !currentEnable);
  }

  changeStatusInputPhone() {
      this.disablePhone.update(currentEnable => !currentEnable);
  }

  openTabBtn() {
    this.openTab.update(currentStatus => !currentStatus)
  }


  toggleCollapse() {
    this.collapsed.update(collapse => !collapse)
  }

  // LLAMADA AL ENPOINT
   updateProfile() {
    let profileUpdate: ProfileUpdate = {
      name: this.changeName(),
      phoneNumber: this.changePhone()
    }
    if(this.disableNameComputed()) {
      profileUpdate.name = this.name()
    }
    if(this.disablePhoneComputed()) {
      profileUpdate.phoneNumber = this.phone()
    }

    
    this.authService.updateProfile(profileUpdate).subscribe({
      complete:() => {
        this.messageSuccessfulResponse.set('Datos actualizados con éxito.');
        if(this.changeName()) {
          this.name.set(this.changeName())
        }
        if(this.changePhone()) {
          this.phone.set(this.changePhone())
        }
        setTimeout(() => {
          this.messageSuccessfulResponse.set('')
        }, 5000);
      },
      error:() => {
        this.messageErroResponse.set('Nose ha podido actualizar los datos.');
        setTimeout(() => {
          this.messageErroResponse.set('')
        }, 3000);
      }
    });
   }

   validNameInput() {
    if(this.changeName().length < 3) {
      return this.messageErrorName.set('El nombre debe superar los 2 caracteres.');
    }    
    if(this.name() === this.changeName()) {
      return this.messageErrorName.set(`Nombre ya exitente.`);
    }
    return this.messageErrorName.set('');
   }

   validPhoneInput() {
    if(!this.changePhone().match('^[0-9]{9}$')) {
      return this.messageErrorPhone.set('Patrón de telefono incorrecto.');
    }    
    if(this.phone() === this.changePhone()) {
      return this.messageErrorPhone.set(`Telefono ya exitente.`);
    }
    return this.messageErrorPhone.set('');
   }

}
