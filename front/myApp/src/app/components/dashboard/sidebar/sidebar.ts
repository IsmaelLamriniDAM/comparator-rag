import { Component, EventEmitter, input, Output, signal, effect, computed, Input } from '@angular/core';
import { sidebarItems } from '../../../interfaces/sidebarItems';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../service/auth-service.service';
@Component({
  selector: 'app-sidebar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar.html',
})
export class Sidebar {
  collapsed = signal<boolean>(false);
  @Output() collapsedChange = new EventEmitter<boolean>();
  @Output() changeView = new EventEmitter<string>();
  activeView = input<string>('comparator');
  @Input() numAddComparisons = 0;
  
  constructor(private authService: AuthService) {
    effect(() => {
      const id = this.activeView();
      this.setActiveFromOutside(id);
    });
  }

  // maxComparationsNumber = computed(() =>
  //   this.authService.getMaxNumberCompares()
  // );

  

  comparationsNumber = computed(() =>
    this.authService.getNumberCompares()
  );

  percentageComparationsUsed = computed(() => {
  const max = 200;
  const used = this.comparationsNumber();
  return used * 100 / max;
});

  sidebarItems = signal<sidebarItems[]>([
    {
      id: "comparator",
      icon: "pi-truck",
      title: "Comparador",
      active: true
    },
    {
      id: "favourites",
      icon: "pi-bookmark",
      title: "Búsquedas favoritas",
      active: false
    },
    {
      id: "ia",
      icon: "pi-microchip-ai",
      title: "IA",
      active: false
    },
    {
      id: "settings",
      icon: "pi-cog",
      title: "Ajustes",
      active: false
    }
    
  ]);

  changeCollapsed() {
    const newValue = !this.collapsed();
    this.collapsed.set(newValue);
    this.collapsedChange.emit(newValue);
  }

  setActiveFromOutside(id: string) {
    this.sidebarItems.update(items => items.map(item => item.id === id ? {...item, active: true} : {...item, active: false}))
  }

  changeActive(id: string) {
    this.setActiveFromOutside(id);
    this.changeView.emit(id);
  }
}
