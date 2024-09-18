import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  selectedMetric: string = 'length';
  availableUnits: string[] = [];
  fromUnit: string = '';
  toUnit: string = '';
  inputValue: number = null;
  result: number = null;

  lengthUnits: string[] = ['Millimeter', 'Centimeter', 'Meter', 'Kilometer'];

  constructor() {}

  onMetricChange() {
    if (this.selectedMetric === 'length') {
      this.availableUnits = this.lengthUnits;
    }
  }

  convert() {
    if (this.fromUnit && this.toUnit && this.inputValue !== null) {
      const conversionRates = {
        Millimeter: 1,
        Centimeter: 100,
        Meter: 1000,
        Kilometer: 1000000,
      };

      const fromRate = conversionRates[this.fromUnit];
      const toRate = conversionRates[this.toUnit];
      this.result = (this.inputValue * fromRate) / toRate;
    } else {
      this.result = null;
    }
  }
}
