import {Component, EventEmitter, Input, Output} from '@angular/core';

/**
 * The Form Buttons components for navigates in the application.
 *
 */
@Component({
    selector: 'common-form-buttons',
    templateUrl: './form-buttons.component.html',
    styleUrls: ['./form-buttons.component.scss']
})
export class FormButtonsComponent {

    @Input()
    public disabledConditions: boolean;

    @Input()
    public loading: boolean;

    @Input()
    public showBackButton = true;

    @Input()
    public showSaveButton = true;

    @Output() backButton: EventEmitter<any> = new EventEmitter();

    back(): void {
        this.backButton.emit();
    }

}
