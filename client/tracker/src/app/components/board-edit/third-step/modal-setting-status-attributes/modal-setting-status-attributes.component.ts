import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'tracker-modal-setting-status-attributes',
  templateUrl: './modal-setting-status-attributes.component.html',
  styleUrls: ['./modal-setting-status-attributes.component.scss']
})
export class ModalSettingStatusAttributesComponent  implements OnInit {

  @Input() set setTransition(value) {
      if (value) {
          this.transitions = value.transition;
          if (value.transition.attributes) {
              this.selectedAttributes = value.transition.attributes.split(';');
          }
          this.attributesList = value.attributes;
          this.addCheckboxes();
      }
  }

  @Output() saveSettings: EventEmitter<any> = new EventEmitter<any>();
  @Output() closeModal: EventEmitter<any> = new EventEmitter<any>();

  public transitions = [];

  public selectedAttributes = [];

  public attributesList = [];

  form: FormGroup;

  constructor(private formBuilder: FormBuilder) {
      this.form = this.formBuilder.group({
          orders: new FormArray([])
      });
  }

  ngOnInit(): void {
  }

  get ordersFormArray() {
      return this.form.controls.orders as FormArray;
  }

  private addCheckboxes() {
      this.attributesList.forEach(i => this.ordersFormArray.push(new FormControl(this.returnValue(i))));
  }

  returnValue(value) {
      if (this.selectedAttributes) {
          for (const item of this.selectedAttributes) {
              if (value.title === item) {
                  return true;
              }
          }
      }
      return false;
  }

  convertListInString(list) {
      let listString = '';
      for (let i = 0; i < list.length; i++) {
          if (i + 1 < list.length) {
              listString = listString + list[i] + ';';
          } else {
              listString = listString + list[i];
          }
      }
      return listString;
  }

  submit() {
      const selectedOrderIds = this.form.value.orders
          .map((checked, i) => checked ? this.attributesList[i].title : null)
          .filter(v => v !== null);
      this.saveSettings.emit(this.convertListInString(selectedOrderIds));
  }

}
