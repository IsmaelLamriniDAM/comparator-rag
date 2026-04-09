import { Component, Input, input } from '@angular/core';

@Component({
  selector: 'app-window-info',
  imports: [],
  templateUrl: './window-info.html',
})
export class WindowInfo {
  @Input() infoText: string = "Información adicional sobre el campo.";
}
