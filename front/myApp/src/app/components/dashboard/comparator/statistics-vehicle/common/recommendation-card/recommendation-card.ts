import { NgClass } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Recommendation } from '../../../../../../enums/Recommendation';
import { WindowInfo } from "../../../../common/window-info/window-info";

@Component({
  selector: 'app-recommendation-card',
  imports: [NgClass, WindowInfo],
  templateUrl: './recommendation-card.html',
})
export class RecommendationCard {
  @Input() recommendation: Recommendation = Recommendation.NOT_RECOMMENDED;
  infoRecommendation: string = "Recomendación basada en el análisis de los datos del mercado y las características del vehículo. Ten en cuenta que esta recomendación es solo una guía y no garantiza resultados específicos. Siempre es importante realizar una investigación adicional y considerar otros factores antes de tomar una decisión de compra o venta.";
  styles = {
    RECOMMENDED: {
      bg: "bg-green-500/20",
      border: "border-green-500/30",
      textColor: "text-green-400",
      text: "RECOMENDADO",
      desc: "Alto potencial de beneficio"
    },
    CAUTION: {
      bg: "bg-yellow-500/20",
      border: "border-yellow-500/30",
      textColor: "text-yellow-400",
      text: "PRECAUCIÓN",
      desc: "Margen ajustado"
    },
    NOT_RECOMMENDED: {
      bg: "bg-red-500/20",
      border: "border-red-500/30",
      textColor: "text-red-400",
      text: "EVITAR",
      desc: "Riesgo de pérdida"
    }
  }
}
