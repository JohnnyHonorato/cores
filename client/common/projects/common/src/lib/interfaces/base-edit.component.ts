import {Component, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {BaseItemComponent} from './base-item.component';

/**
 * The 'BaseEditComponent' provides the common API for edit components.
 *
 * Service, operations, forms, validations are all available.
 *
 * All edit components MUST extend this class.
 *
 * @extends BaseItemComponent
 *
 */
@Component({
    template: ''
})
export abstract class BaseEditComponent extends BaseItemComponent implements OnInit {


    /**
     * Form for edition.
     */
    public editForm: FormGroup;

    /**
     * Defines if it is edit mode.
     */
    public isEditMode = false;


    /**
     * If is submitting.
     *
     */
    public submitting = false;
    public submittingSaveAndContinue = false;

    /**
     * Constructor.
     */
    protected constructor() {
        super();
    }

    /**
     * On Init of the components.
     */
    ngOnInit(): void {
        super.ngOnInit();

        this.isEditMode = !this.isNullOrUndefined(this.getParamId());

        this.initForm();
    }

    /**
     * Initialize the edit form.
     */
    initForm(): void {
        this.editForm = new FormGroup(this.getFormControls(), this.getFormOptions());
    }

    /**
     * Invoked when user submits the form.
     *
     * Checks if the item's id exists:
     * - No:  Inserts the item.
     * - Yes: Updates the item.
     *
     * Notifies by a 'toast' at the end: Success or Error.
     *
     * If success, go back to the list components.
     */
     onSubmit(saveAndContinue = false): void {
        this.submittingBtn(saveAndContinue, true);
        if (!this.item.id) {
            this.preInsert();
            this.unmaskFields();
            this.insert(saveAndContinue);
        } else {
            this.preUpdate();
            this.unmaskFields();
            if (this.isUpdatePartial()) {
                this.updatePartial();
            } else {
                this.update();
            }
        }
    }

    private submittingBtn(saveAndContinue = false, submitting: boolean) {
        if (saveAndContinue) {
            this.submittingSaveAndContinue = submitting;
        } else {
            this.submitting = submitting;
        }
    }


    /**
     * Gets the form value.
     *
     */
    protected getFormValue() {
        return this.editForm.value;
    }

    /**
     * Inserts the item.
     */
     protected insert(saveAndContinue?: boolean): void {
        this.getService().insert(this.getServiceURL(), this.getFormValue()).subscribe(
            () => {
                this.postInsert();
                this.notification.insertSuccess();
                this.submittingBtn(saveAndContinue, false);
                if (!saveAndContinue) {
                    this.backToList();
                }
            },
            error => {
                this.submittingBtn(saveAndContinue, false);
                this.notification.error(error.error ? error.error.message : error.message);
            }
        );
    }



    /**
     * Updates the item.
     */
    protected update(): void {
        this.getService()
            .update(this.getServiceURL(), this.item.id, this.getFormValue())
            .subscribe(
                result => {
                    this.handleUpdate(result);
                },
                error => {
                    this.submitting = false;
                    this.notification.error(
                        error.error ? error.error.message : error.message
                    );
                }
            );
    }

    /**
     * Updates the item partially.
     */
    protected updatePartial(): void {
        this.getService()
            .updatePartial(this.getServiceURL(), this.item.id, this.getFormValue())
            .subscribe(
                result => {
                    this.handleUpdate(result);
                },
                error => {
                    this.submitting = false;
                    this.notification.error(error.error ? error.error.message : error.message);
                }
            );
    }

    /**
     * Handles the update result.
     *
     */
    protected handleUpdate(result): void {
        this.postUpdate();
        this.notification.updateSuccess();
        this.submitting = false;
        this.backToList();
    }

    /**
     * Fills the form with the item retrieved.
     *
     */
    protected postGetItem(): void {
        const formItem = {};
        Object.keys(this.getFormControls()).forEach(key => {
            formItem[key] = this.item.hasOwnProperty(key) ? this.item[key] : null;
        });
        this.editForm.patchValue(formItem);
    }

    /**
     * Executes before insert operation.
     */
    protected preInsert(): void {
    }

    /**
     * Executes post successful insert.
     */
    protected postInsert(): void {
    }

    /**
     * Executes before update operation.
     */
    protected preUpdate(): void {
    }

    /**
     * Executes post successful update.
     */
    protected postUpdate(): void {
    }

    /**
     * Flag to control if the update is full or partial.
     * By default: Full.
     *
     */
    protected isUpdatePartial(): boolean {
        return false;
    }

    /**
     * Gets the form controls.
     *
     */
    abstract getFormControls(): { };

    protected getFormOptions(): object {
        return {};
    }

    /**
     * Helper from select in template
     *
     */
    public equalsSelect(objOne: any, objTwo: any): boolean {
        return objTwo ? objOne.id === objTwo.id : false;
    }

    protected unmaskFields(): void {
    }

    private isNullOrUndefined(value: any) {
        return value === null || value === undefined;
    }
}
