import {CurrencyService} from '../../../shared/services/currency.service';
import {AfterViewChecked, ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {BaseComponent, CustomValidators} from 'common';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {ModalConfirmationMonetaryComponent} from './modal-confirmation-monetary/modal-confirmation-monetary.component';

@Component({
    selector: 'tracker-custom-att-edit',
    templateUrl: './custom-att-edit.component.html',
    styleUrls: ['./custom-att-edit.component.scss']
})
export class CustomAttEditComponent extends BaseComponent implements OnInit, AfterViewChecked {

    @Input() attributeToEdit: any;

    @Input()
    set setFilter(value) {
        if (value) {
            this.organizationFields = value.filter(a => !a.deleted && a.type === 'ORGANIZATION');
        }
    }

    @Output()
    private closeModal: EventEmitter<string> = new EventEmitter();

    modalRefMonetary: BsModalRef;

    private readonly STRING_MIN_LENGTH = 1;
    private readonly STRING_MAX_LENGTH = 255;

    currencyUnits = [];
    currencyUnitsBuffer = [];
    bufferSize = 5;
    loadingItems = false;
    currentCurrency: any;
    changeMonetary = false;
    private numberOfItemsFromEndBeforeFetchingMore = 3;

    public formControlWithValidator: any;
    public loadingComponent = false;
    public addingItem: boolean;

    public listValidators = [Validators.pattern('[^;]+'), Validators.required];
    public listValidatorsMsg = {
        pattern: this.translate('tracker-manager.attribute.validation.type.list.options-invalid'),
        required: this.translate('tracker-manager.attribute.validation.type.list.options-empty')
    };

    public attributeTypes = [
        {value: 'INTEGER', label: 'tracker-manager.attribute.type.integer'},
        {value: 'DECIMAL', label: 'tracker-manager.attribute.type.decimal'},
        {value: 'STRING', label: 'tracker-manager.attribute.type.string'},
        {value: 'BOOLEAN', label: 'tracker-manager.attribute.type.boolean'},
        {value: 'PEOPLE', label: 'tracker-manager.attribute.type.people'},
        {value: 'LIST', label: 'tracker-manager.attribute.type.list'},
        {value: 'DATE', label: 'tracker-manager.attribute.type.date'},
        {value: 'DATE_TIME', label: 'tracker-manager.attribute.type.datetime'},
        {value: 'CURRENCY', label: 'tracker-manager.attribute.type.currency'},
        {value: 'ORGANIZATION', label: 'tracker-manager.attribute.type.organization'}
    ];

    public editForm: FormGroup;
    public isEditMode: boolean;

    public organizationFields = [];

    constructor(
        private cdRef: ChangeDetectorRef,
        private currencyService: CurrencyService,
        private modalService: BsModalService
    ) {
        super();
    }

    ngOnInit(): void {
        this.isEditMode = !!this.attributeToEdit;
        super.ngOnInit();
        this.initForm();
        this.getCurrencyCodes();
        if (this.attributeToEdit) {
            this.formControlWithValidator = this.attributeToEdit.type;
        }
        this.setRelatedAttributeValidators();
        this.setRequired('STRING', 'maxLength');
        this.setRequired('CURRENCY', 'currency');
        this.currentCurrency = this.editForm.get('currency')?.value;
    }

    private getCurrencyCodes() {
        this.currencyService.getAllCurrencies().subscribe((list: any) => {
            this.currencyUnits = list.currencies;
            this.currencyUnitsBuffer = this.currencyUnits.slice(0, this.bufferSize);
        }, (error) => {
        });
    }

    ngAfterViewChecked() {
        this.cdRef.detectChanges();
    }

    onScroll(event: { start: number; end: number }) {
        if (this.loadingItems || this.currencyUnits.length <= this.currencyUnitsBuffer.length) {
            return;
        }

        if (
            event.end + this.numberOfItemsFromEndBeforeFetchingMore >=
            this.currencyUnitsBuffer.length
        ) {
            this.fetchMore();
        }

    }

    private fetchMore() {
        this.loadingItems = true;
        const len = this.currencyUnitsBuffer.length;
        const more = this.currencyUnits.slice(len, this.bufferSize + len);
        this.currencyUnitsBuffer = this.currencyUnitsBuffer.concat(more);
        this.loadingItems = false;
    }

    onScrollToEnd() {
        this.fetchMore();
    }

    getFormControls(): {} {
        return {
            id: new FormControl(this.attributeToEdit?.id ? this.attributeToEdit.id : undefined, []),
            title: new FormControl(this.attributeToEdit ? this.attributeToEdit.title : undefined, [Validators.required,
                Validators.maxLength(255), CustomValidators.noWhitespaceValidator]),
            type: new FormControl(this.attributeToEdit ? this.attributeToEdit.type : undefined,
                [Validators.required]),
            trackerModel: new FormControl(undefined, []),
            required: new FormControl(this.attributeToEdit?.required ? this.attributeToEdit.required : false, []),
            showOnCard: new FormControl(this.attributeToEdit?.showOnCard ? this.attributeToEdit.showOnCard : false, []),
            position: new FormControl(this.attributeToEdit ? this.attributeToEdit.position : undefined, []),
            maxValue: new FormControl(this.attributeToEdit?.maxValue ? this.attributeToEdit.maxValue : undefined, []),
            minValue: new FormControl(this.attributeToEdit?.minValue ? this.attributeToEdit.minValue : undefined, []),
            maxLength: new FormControl(this.attributeToEdit?.maxLength ? this.attributeToEdit.maxLength : undefined,
                [Validators.min(1), Validators.max(255)]),
            listValues: new FormControl(this.attributeToEdit?.listValues ? this.attributeToEdit.listValues.split(';') : undefined, []),
            needsValueComplement: new FormControl(
                this.attributeToEdit?.needsValueComplement ? this.attributeToEdit.needsValueComplement : false,
                []),
            currency: new FormControl(this.attributeToEdit?.currency ? this.attributeToEdit.currency : undefined, [Validators.required]),
            maxDate: new FormControl(this.attributeToEdit?.maxDate ? new Date(this.attributeToEdit.maxDate) : undefined,
                [CustomValidators.dateValidator('#min-date')]),
            minDate: new FormControl(this.attributeToEdit?.minDate ? new Date(this.attributeToEdit.minDate) : undefined,
                [CustomValidators.dateValidator('#max-date')]),
            deleted: new FormControl(undefined, []),
            relatedAttribute: new FormControl(
                this.attributeToEdit?.relatedAttribute ? this.attributeToEdit.relatedAttribute : undefined, [])
        };
    }

    setRequired(type, field) {
        if (this.editForm.get('type').value === type) {
            this.editForm.get(field).setValidators(Validators.required);
        } else {
            this.editForm.get(field).setErrors(null);
            this.editForm.get(field).clearValidators();
        }
    }

    setRelatedAttributeValidators() {
        if (this.editForm.get('type').value === 'PEOPLE' && this.editForm.get('needsValueComplement').value) {
            this.editForm.get('relatedAttribute').setValidators(Validators.required);
        } else {
            this.editForm.get('relatedAttribute').setErrors(null);
            this.editForm.get('relatedAttribute').clearValidators();
        }
    }

    private initForm() {
        this.editForm = new FormGroup(this.getFormControls(), {});
        this.editForm.setValidators([CustomValidators.checkIfMinValueIsBiggerThanMaxValue(),
            CustomValidators.dateLessThan('minDate', 'maxDate')]);
    }

    add() {
        this.addingItem = true;
        if (!this.isEditMode && this.editForm.get('type').value === 'CURRENCY') {
            this.editForm.get('needsValueComplement').setValue(true);
            this.changeMonetary = false;
        }
        const value = this.editForm.value;
        this.editForm.get('minValue').setValue(parseFloat(value.minValue));
        this.editForm.get('maxValue').setValue(parseFloat(value.maxValue));
        if (
            this.validateRequiredFields(value) &&
            this.validateAttributeType(value)
        ) {
            this.normalizeListOptions(value);
            if (this.editForm.get('type').value === 'DECIMAL') {
                this.closeModal.emit(this.editForm.getRawValue());
            } else {
                this.closeModal.emit(value);
            }
            this.close();
        }
        this.addingItem = false;
    }

    private validateRequiredFields(value) {
        if (!value?.title) {
            this.notification.warning(this.translate('tracker-manager.attribute.validation.name.empty'));
            return false;
        }
        if (!value?.type) {
            this.notification.warning(this.translate('tracker-manager.attribute.validation.type.empty'));
            return false;
        }
        return true;
    }

    private validateAttributeType(value) {
        switch (value.type) {
            case 'INTEGER':
            case 'DECIMAL':
                return this.validateNumberType(value);
            case 'STRING':
                return this.validateStringType(value);
            case 'LIST':
                return this.validateListType(value);
            default:
                break;
        }
        return true;
    }

    private validateNumberType(value): boolean {
        try {
            if (parseFloat(value.minValue) > parseFloat(value.maxValue)) {
                this.notification.warning(this.translate('tracker-manager.attribute.validation.type.number.min-greater-than-max'));
                return false;
            }
        } catch (error) {
            this.notification.warning(this.translate('tracker-manager.attribute.validation.type.number.NaN'));
            return false;
        }
        return true;
    }

    private validateStringType(value): boolean {
        if (value.maxLength < this.STRING_MIN_LENGTH) {
            this.notification.warning(this.translate('tracker-manager.attribute.validation.type.string.min-length'));
            return false;
        }
        if (value.maxLength > this.STRING_MAX_LENGTH) {
            this.notification.warning(this.translate('tracker-manager.attribute.validation.type.string.max-length'));
            return false;
        }
        return true;
    }

    private validateListType(value): boolean {
        if (!value.listValues?.length) {
            this.notification.warning(this.translate('tracker-manager.attribute.validation.type.list.options-empty'));
            return false;
        }
        return true;
    }

    private normalizeListOptions(value) {
        const values = value.listValues as Array<any>;
        if (values?.length) {
            value.listValues = values.join(';');
        }
    }

    close(): any {
        this.editForm.reset();
        this.closeModal.emit();
    }

    isRequiredField(checked: boolean) {
        this.editForm.get('required').setValue(checked);
    }

    isNeedsValue(value: boolean) {
        this.editForm.get('needsValueComplement').setValue(value);
        if (!value) {
            this.editForm.get('relatedAttribute').setValue(null);
        }
        this.setRelatedAttributeValidators();
    }

    isShowOnCardField(checked: boolean) {
        this.editForm.get('showOnCard').setValue(checked);
    }

    showInput(type: string) {
        return this.editForm.controls.type.value === type;
    }

    resetForm() {
        this.editForm.controls.maxValue.reset();
        this.editForm.controls.minValue.reset();
        this.editForm.controls.maxLength.reset();
        this.editForm.controls.listValues.reset();
        this.editForm.controls.maxDate.reset();
        this.editForm.controls.minDate.reset();
        this.editForm.controls.currency.reset();
        this.editForm.get('needsValueComplement').setValue(false);
        this.editForm.get('relatedAttribute').setValue(undefined);
        if (this.editForm.get('type').value === 'DATE') {
            this.applyMasks();
        }
        this.setRelatedAttributeValidators();
        this.setRequired('STRING', 'maxLength');
        this.setRequired('CURRENCY', 'currency');
    }

    setDecimalField(event, value) {
        if (event) {
            this.editForm.get(value).setValue('0,');
        }
    }

    openModalMonetary() {
        this.modalRefMonetary = this.modalService.show(ModalConfirmationMonetaryComponent,
            {
                backdrop: 'static',
                keyboard: false,
                class: 'modal-sm modal-dialog-centered'
            });
        this.modalRefMonetary.content.closeModal.subscribe(result => {
            if (result) {
                this.clickYes();
            } else {
                this.clickNo();
            }
        });

    }

    onChangeMonetary() {
        if (this.attributeToEdit && this.changeMonetary === false) {
            this.openModalMonetary();
        }
        this.changeMonetary = true;
    }

    clickYes() {
        this.currentCurrency = this.editForm.get('currency')?.value;
        this.modalRefMonetary.hide();
    }

    clickNo() {
        this.editForm.get('currency').setValue(this.currentCurrency);
        this.changeMonetary = false;
        this.modalRefMonetary.hide();
    }

}

