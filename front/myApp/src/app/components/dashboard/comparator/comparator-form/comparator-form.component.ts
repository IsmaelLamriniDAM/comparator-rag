import { Component, EventEmitter, Output, Input, signal, SimpleChange, SimpleChanges } from '@angular/core';
import { SelectModule } from 'primeng/select';
import { SliderModule } from 'primeng/slider';
import { FormsModule, NgForm } from '@angular/forms';
import { VehicleForm } from '../../../../interfaces/dataVehicleForm/vehicleForm';
import { EMPTY_VEHICLE } from '../../../../constants/constants';
import { ComparatorFormService } from '../../../../service/comparator-form.service';
import { ChangeDetectorRef } from '@angular/core';
import { AuthService } from '../../../../service/auth-service.service';
import { BrandModelService } from '../../../../service/brand-model.service';
import { Price, PriceRange } from '../../../../interfaces/dataVehicleForm/Price';
import { Nullable } from 'primeng/ts-helpers';
import { ManagerSetPrice } from '../../../../service/SetPrice/manager-set-price';
import { ManagerSetPriceImp } from '../../../../service/SetPrice/manager-set-price-imp.service';
import { Operation } from '../../../../enums/Operation';
import { WindowInfo } from "../../common/window-info/window-info";

@Component({
  selector: 'app-comparator-form',
  imports: [SelectModule, SliderModule, FormsModule, WindowInfo],
  templateUrl: './comparator-form.component.html',
  providers: [
    { provide: ManagerSetPrice, useClass: ManagerSetPriceImp }
  ]
})
export class ComparatorFormComponent {

  infoBrand: string = "Selector de marcas.";
  infoModel: string = "Selector de modelos según la marca seleccionada.";
  infoPriceBuy: string = "Rango de precios para la compra del vehículo.";
  infoPriceSell: string = "Rango de precios para la venta del vehículo.";
  infoYear: string = "Año de fabricación del vehículo."
  infoFuel: string = "Tipo de combustible del vehículo.";
  infoHp: string = "Rango de potencia del vehículo (en caballos de fuerza)."
  infokM: string = "Rango de kilometraje del vehículo (en kilómetros)."


  brandsHisModels: any[] = [];
  brandsAndModelsMap = new Map<string, string[]>;

  brandOptions: { value: string}[] = [];
  modelOptions: string [] = [];

  brand: string = "";
  model: string = "";
  year: number = 2000;

  rangeValues: number[] = [0, 1500000];
  rangeValueHp : number[] = [60, 400]
  rangeValuePriceBuy: number[] = [1000, 1000000]
  rangeValuePriceSell: number[] = [1000, 1000000]


  selectedFuel: string = "";

  buyOpt =  signal<boolean>(true)
  sellOpt = signal<boolean>(false)
  bothOpt = signal<boolean>(false)

   @Output() clickSendForm = new EventEmitter<VehicleForm>();


  constructor(private comparatorFormService: ComparatorFormService, private cdr: ChangeDetectorRef,
     private authService: AuthService, private brandModelService: BrandModelService, private managerSetPrice: ManagerSetPrice) {
    this.brandModelService.getAllBrandHisModels().subscribe({
      next: (data) => {
        this.brandsHisModels = data;

        this.brandsHisModels.forEach(b => this.brandsAndModelsMap.set(b.brand, b.models));
        this.brandOptions = this.brandsHisModels.map(bm => bm.brand)
      },
      error: (err) => console.error('Error loading catalog', err)
    });
  }

  onHpRangeChange(value: number[]): void {
  this.rangeValueHp = [...value];
  }

  optBuyChoosed() {
    this.buyOpt.set(true)
    this.sellOpt.set(false)
    this.bothOpt.set(false)
  }

  optSellChoosed() {
    this.buyOpt.set(false)
    this.sellOpt.set(true)
    this.bothOpt.set(false)
  }

  optbothChoosed(){
    this.buyOpt.set(false)
    this.sellOpt.set(false)
    this.bothOpt.set(true)
  }

    // typeOperationsSS = [
    //   {label: 'COMPRA', valueE: Operation.BUY},
    //   {label: 'VENTA', valueE: Operation.SELL},
    //   {label: 'AMBOS', valueE: Operation.BOTH}
    // ];


  fuelOptions = [
    { label: 'Gasolina', value: 'GASOLINE' },
    { label: 'Diésel', value: 'DIESEL' },
    { label: 'Eléctrico', value: 'ELECTRIC' },
    { label: 'Híbrido', value: 'HYBRID' },
    { label: 'GNC', value: 'GNC' },
    { label: 'GLP', value: 'GLP' },
    { label: 'Otro', value: 'OTHER' },
    {label: 'Todos', value: 'ALL'}
  ];

  @Input() collapsed = false;
  @Input() errorMessage: string = "";
  @Input() isLoading: boolean = false;
  @Output() submittedVehicleForm = new EventEmitter<VehicleForm>();
  
  @Input() vehicleFromFavourites: VehicleForm = EMPTY_VEHICLE;
  formatter = new Intl.NumberFormat('es-ES');
  @Output() collapseChange = new EventEmitter<boolean>();

  toggleCollapse() {
    this.collapseChange.emit(!this.collapsed);
  }

  // hasReachedMaxComparisons(): boolean {
  //   const max = this.authService.getMaxNumberCompares();
  //   const current = this.authService.getNumberCompares();
  //   return current >= max;
  // }


  onBrandChange() {
      this.modelOptions = [];

    const selectedBrand = this.brandsAndModelsMap.get(this.brand);
    if (!selectedBrand) return;

    this.brandsAndModelsMap.get(this.brand)?.map(m => m).forEach(m => this.modelOptions.push(m));
    this.brandsAndModelsMap.get(this.brand)?.forEach(m => this.modelOptions.push(m))
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['vehicleFromFavourites']) {
    this.brand = this.vehicleFromFavourites.brand || "";

    const hpMin = this.vehicleFromFavourites.hp?.min || 60; 
    const hpMax = this.vehicleFromFavourites.hp?.max || 400;
    this.rangeValueHp = [hpMin, hpMax];

    const kmMin = this.vehicleFromFavourites.km?.min || 0;
    const kmMax = this.vehicleFromFavourites.km?.max || 1500000;
    this.rangeValues = [kmMin, kmMax];


    this.rangeValuePriceBuy = [this.vehicleFromFavourites.price?.rangeBuy?.min || 1000, this.vehicleFromFavourites.price?.rangeBuy?.max || 1000000];
    this.rangeValuePriceSell = [this.vehicleFromFavourites.price?.rangeSell?.min || 1000, this.vehicleFromFavourites.price?.rangeSell?.max || 1000000];

    this.year = this.vehicleFromFavourites.year || 2000;
    this.selectedFuel = this.vehicleFromFavourites.fuelType || "";
    this.model = this.vehicleFromFavourites.model || "";
    
    if (this.vehicleFromFavourites.operation != null) {
      if (this.vehicleFromFavourites.operation == Operation.SELL) {
        this.optSellChoosed();
      } else if (this.vehicleFromFavourites.operation == Operation.BOTH) {
        this.optbothChoosed();
      } 
    }
  }
  }

  onSubmit(form: NgForm) {
     if(this.isLoading) return;
    this.errorMessage = "";
    const year = form.value.year != null && form.value.year !== '' ? Number(form.value.year) : null;
    const brand = form.value.brand != null && form.value.brand !== '' ? String(form.value.brand) : null;
    const model = form.value.model != null && form.value.model !== '' ? String(form.value.model) : null;

    const currentYear = new Date().getFullYear();
  
     
    if (year != null && year > currentYear) {
      this.errorMessage = `El año de fabricación no puede ser mayor que ${currentYear}.`;
      return;
    }
    if ( year != null && year <= 1900) {
      
      this.errorMessage = `El año de fabricación no puede ser mayor que ${currentYear}.`;
      return;
    }
    if (String(year).includes(".")) {
      this.errorMessage = `El año no puede contener un punto.`;
      return;
    }
  

    const rangePrice: Price |Nullable = this.setPrice()


    const vehicleData: VehicleForm = {
      brand,
      model,
      price: rangePrice,
      year,
      fuelType: this.selectedFuel ? this.selectedFuel === "ALL" ? 'OTHER' : 'OTHER' : this.selectedFuel.length > 0 ? this.selectedFuel : 'OTHER',
        hp: {
          min: this.rangeValueHp[0],
          max: this.rangeValueHp[1]
        },
      km: {
        min: this.rangeValues[0],
        max: this.rangeValues[1]
      },
      operation: this.buyOpt() ? Operation.BUY.toString() : this.sellOpt() ? Operation.SELL.toString() : Operation.BOTH.toString()
      
    }
    
    this.clickSendForm.emit(vehicleData);
  }

  setPrice(): Price | Nullable {
    return this.managerSetPrice.set(
      this.buyOpt() ? Operation.BUY : this.sellOpt() ? Operation.SELL : Operation.BOTH, 
      this.rangeValuePriceSell, 
      this.rangeValuePriceBuy
    )
  }
    
}


