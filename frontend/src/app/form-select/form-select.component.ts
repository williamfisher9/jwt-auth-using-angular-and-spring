import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-form-select',
  standalone: true,
  imports: [],
  templateUrl: './form-select.component.html',
  styleUrl: './form-select.component.css'
})
export class FormSelectComponent {
  @Input() items : string[] = [];
  @Output() selectedValueEvent = new EventEmitter<string>();

  displaySelect : boolean = false;
  selectedValue : string = '';

  toggleSelectDisplay() {
    this.displaySelect = !this.displaySelect;
  }

  handleSelectedValue(val : string) {
    this.selectedValue = val;
    this.selectedValueEvent.emit(val);
  }

}
