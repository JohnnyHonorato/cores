import {Component, OnInit} from '@angular/core';

import {BaseModelComponent} from './base-model.component';

/**
 * The 'BaseItemComponent' class provides the common API for all the components
 * that works with items.
 *
 * All components that uses models MUST extend this class.
 *
 * @extends BaseComponent
 */
@Component({
    template: ''
})
export abstract class BaseItemComponent extends BaseModelComponent implements OnInit {

  /**
   * Item which is being edited or view.
   */
  public item: any = new Object();

  /**
   * Constructor.
   */
  constructor() {
    super();
  }

  /**
   * Defines whether the request is over
   */
  public loading = false;

  /**
   * On Init of the components.
   */
  ngOnInit(): void {
    super.ngOnInit();

    this.getItem();
  }

  /**
   * Gets the item for edition if is an edit mode.
   *
   * If insert mode, by pass this.
   */
  protected getItem(): void {
    const id = this.getParamId();
    if (id) {
      this.loading = true;
      this.getService().getOne(this.getServiceURL(), id).subscribe(result => {
        this.item = result;
        this.loading = false;
        this.postGetItem();
      });
    }
  }

  /**
   * Navigates back to the list components.
   */
  public backToList(): void {
    this.navigate([this.getRouterURL()]);
  }

  /**
   * Executes post the execution of Get Item.
   */
  protected postGetItem(): void {}

  /**
   * Gets the id in the parameters list.
   *
   * @returns The id param.
   */
  protected getParamId(): string {
    return this.getParam(this.getItemIdKey());
  }

  /**
   * Gets the Key of Item ID.
   *
   * @returns The Key value of item id.
   */
  protected getItemIdKey(): string {
    return 'id';
  }
}
