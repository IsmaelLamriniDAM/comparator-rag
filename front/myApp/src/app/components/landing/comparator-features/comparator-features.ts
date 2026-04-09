import { Component } from '@angular/core';
import { feature } from '../../../interfaces/features';

@Component({
  selector: 'app-comparator-features',
  imports: [],
  templateUrl: './comparator-features.html',
})
export class ComparatorFeatures {
  features: feature[] =  [
    {
      icon: "pi-search",
      title: "Búsqueda inteligente",
      description: "A diario se analizan las páginas de venta pública para encontrar nuevos vehículos y actualizar estadísticas de venta."
    },
    {
      icon: "pi-calculator",
      title: "Cálculo de rentabilidad",
      description: "Calcula el margen de beneficio estimado incluyendo costes adicionales como preparación o impuestos de matirculación."
    },
    {
      icon: "pi-history",
      title: "Historial de búsquedas",
      description: "Guarda y consulta tus búsquedas anteriores para tomar mejores decisiones en un futuro comparando con el mercado actual."
    },

  ]
}
