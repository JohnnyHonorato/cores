import {Component, OnInit} from '@angular/core';
import {BaseItemComponent} from './base-item.component';

/**
 * The 'BaseViewComponent' provides the common API for model view components.
 *
 * All model view components MUST extend this class.
 *
 * @extends BaseItemComponent
 */
@Component({
    template: ''
})
export abstract class BaseViewComponent extends BaseItemComponent implements OnInit {

  /**
   * Constructor.
   */
  constructor() {
    super();
  }

  /**
   * On Init of the components.
   */
  ngOnInit(): void {
    super.ngOnInit();
  }
}
