import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Observable, Subject} from 'rxjs';
import {getValidators} from './validators';
import {AppInjector, NotificationService, SelectSearchService} from 'common';
import moment from 'moment';
import {take, takeUntil} from 'rxjs/operators';
import {WarnControl} from 'common';
import {TrackerService} from '@shared/services/tracker.service';
import {OrganizationURL, PeopleURL} from '@shared/util/url.domain';

@Component({
    selector: 'tracker-customizable-attributes',
    templateUrl: './customizable-attributes.component.html',
    styleUrls: ['./customizable-attributes.component.scss']
})

export class CustomizableAttributesComponent implements OnInit, OnDestroy {

    private notification: NotificationService = AppInjector.get(NotificationService);

    attributes: Array<any> = [];

    @Input() set setLitAttributes(list) {
        if (list) {
            this.attributes = list.filter(item => item.deleted === null || item.deleted === false);
        }
    }

    @Input() attributeValues: any;

    @Input() transition: any;

    @Input() isPreview: any;

    @Input() set draftValue(value) {
        if (value) {
            this.attributeValues = value;
            this.setDraftValue(value);
        }
    }

    @Output() attributeAdded: EventEmitter<any> = new EventEmitter<any>();

    @Output() formStatusChanged: EventEmitter<any> = new EventEmitter<any>();

    private onDestroySubject = new Subject();

    currencyUnits = [];
    currencyUnitsBuffer = [];
    bufferSize = 5;
    loadingItems = false;

    form: FormGroup;
    private numberOfItemsFromEndBeforeFetchingMore = 3;

    public peopleSearchMap: Map<number, { loading: boolean, subjects: Subject<string>, searchItems: Observable<any[]> }> = new Map();

    public searchItemsOrganization: Observable<any[]>;
    public loadingOrganizationSearch = false;
    public subjectsOrganization = new Subject<string>();

    constructor(
        private formBuilder: FormBuilder,
        private searchService: SelectSearchService,
        private trackerService: TrackerService
    ) {

    }

    ngOnInit(): void {
        this.form = this.formBuilder.group({
            attributesValue: this.formBuilder.array([]),
        });
        if (this.isPreview) {
            this.form.disable();
        }
        this.initForm();
    }

    allowedAttribute(item) {
        if (this.transition) {
            const attributes = this.transition.attributes.split(';');
            return attributes.includes(item);
        }
        return true;
    }

    ngOnDestroy(): void {
        this.onDestroySubject.next();
        this.onDestroySubject.unsubscribe();
    }

    get attributesValue(): FormArray {
        return this.form.get('attributesValue') as FormArray;
    }

    getColumnClass(item) {
        if (item?.value.attribute.numberOfColumns) {
            return 'col-sm-' + item?.value.attribute.numberOfColumns;
        } else {
            return 'col-sm-3';
        }
    }

    newAttribute(attribute: any, attributeValue): FormGroup {
        const valueControl = new WarnControl(this.getValueByType(attributeValue, attribute.type), getValidators(attribute, attributeValue));
        const wasDeleted = attributeValue?.value?.deleted;
        if (wasDeleted) {
            valueControl.reset();
        }
        const attributeForm = this.formBuilder.group({
            id: new FormControl(attributeValue ? attributeValue.id : undefined),
            value: valueControl,
            valueComplement: new FormControl(attributeValue?.valueComplement ? attributeValue.valueComplement : undefined),
            time: this.setTimeForm(attribute, attributeValue),
            attribute: this.formBuilder.group({
                id: new FormControl(attribute.id ? attribute.id : undefined),
                title: new FormControl(attribute.title ? attribute.title : undefined),
                showOnCard: new FormControl(attribute.showOnCard ? attribute.showOnCard : undefined),
                type: new FormControl(attribute.type ? attribute.type : undefined),
                required: new FormControl(attribute.required ? attribute.required : undefined),
                minValue: new FormControl(attribute.minValue !== null ? attribute.minValue : undefined),
                maxValue: new FormControl(attribute.maxValue !== null ? attribute.maxValue : undefined),
                maxLength: new FormControl(attribute.maxLength ? attribute.maxLength : undefined),
                listOptions: new FormControl(attribute.listValues ? attribute.listValues.split(';') : undefined),
                needsValueComplement: new FormControl(attribute.needsValueComplement),
                minDate: new FormControl(attribute.minDate !== null ? attribute.minDate : null),
                maxDate: new FormControl(attribute.maxDate !== null ? attribute.maxDate : null),
                deleted: new FormControl(attribute.deleted ? attribute.deleted : false),
                numberOfColumns: new FormControl(attribute.numberOfColumns !== null ? attribute.numberOfColumns : undefined),
                position: new FormControl(attribute.position !== null ? attribute.position : undefined),
                positionX: new FormControl(attribute.positionX !== null ? attribute.positionX : undefined),
                positionY: new FormControl(attribute.positionY !== null ? attribute.positionY : undefined),
                currency: new FormControl(attribute.currency !== null ? attribute.currency : undefined)
            })
        });
        if (this.isPreview) {
            attributeForm.disable();
        }
        return attributeForm;
    }

    setTimeForm(attribute: any, attributeValue: any) {
        if (attribute.type === 'DATE_TIME') {
            if (attributeValue && attributeValue.value) {
                const auxTime = new Date(attributeValue.value);
                const time = `${auxTime.getHours().toString().padStart(2, '0')}:${auxTime.getMinutes().toString().padStart(2, '0')}`;
                return new FormControl(time, attribute.required ? [Validators.required] : []);
            } else {
                return new FormControl(undefined, attribute.required ? [Validators.required] : []);
            }
        } else {
            return new FormControl(undefined, []);
        }
    }

    addAttributes(attribute: any, position) {
        this.attributesValue.insert(position, attribute);
    }

    private async initPeopleOrOrganizationForm(attributeValue, item, attributePosition, attributeType) {
        try {
            if (attributeType === 'PEOPLE') {
                await this.initPeopleAttribute(attributeValue);
            } else if (attributeType === 'ORGANIZATION') {
                await this.initOrganizationAttribute(attributeValue);
            }
            this.addAttributes(this.newAttribute(item, attributeValue), attributePosition);
        } catch (error) {
            this.notification.error(error.error.message);
        }
    }

    async initPeopleAttribute(attributeValue) {
        const people = await this.trackerService.get(`${PeopleURL.BASE}/${attributeValue.value}`).toPromise() as any;
        attributeValue.value = {
            id: people.id,
            name: people.name,
            deleted: people.deleted
        };
    }

    async initOrganizationAttribute(attributeValue) {
        const organization = await this.trackerService
            .get(`${OrganizationURL.BASE}/${attributeValue.value}`).toPromise() as any;
        attributeValue.value = {
            id: organization.id,
            fantasyName: organization.fantasyName,
            deleted: organization.deleted
        };
    }

    async initForm() {
        for (const item of this.attributes) {
            if (this.allowedAttribute(item.title)) {
                const attributePosition = (item.position - 1);
                const attributeValue = this.getAttributeValue(item.id);
                if (attributeValue && attributeValue.value && item.type === 'PEOPLE') {
                    await this.initPeopleOrOrganizationForm(attributeValue, item, attributePosition, 'PEOPLE');
                } else if (attributeValue && attributeValue.value && item.type === 'ORGANIZATION') {
                    await this.initPeopleOrOrganizationForm(attributeValue, item, attributePosition, 'ORGANIZATION');
                } else {
                    this.addAttributes(this.newAttribute(item, attributeValue), attributePosition);
                }
            }
        }
        this.changeForm();
        if (!this.isPreview) {
            this.initSearchOrganization();
            this.initAttributesIndividualSearch();
            this.listenForRelatedAttributesChanges();
            this.form.statusChanges.pipe(takeUntil(this.onDestroySubject)).subscribe(
                (status) => {
                    this.formStatusChanged.emit(status);
                }
            );
        }
    }

    changeForm() {
        if (this.form.valid) {
            const attributes = this.form.get('attributesValue').value.map((item, index) => {
                if (item.attribute.type === 'PEOPLE' && item.value) {
                    item.value = item.value.id ? item.value.id : item.value;
                }
                if (item.attribute.type === 'ORGANIZATION' && item.value) {
                    item.value = item.value.id ? item.value.id : item.value;
                }
                if (item.attribute.type === 'DATE_TIME' && item.value) {
                    let time = (((this.form.get('attributesValue') as FormArray).at(index)) as FormGroup).controls.time.value;
                    if (time?.length > 0) {
                        time = time.split(':');
                        item.value = moment(item.value).hour(time[0]).minute(time[1]).toISOString();
                    }
                }
                return item;
            });
            this.attributeAdded.emit(attributes);
        }
        this.formStatusChanged.emit(this.form.status);

    }

    returnDateFromTimestamp(timestamp) {
        if (timestamp) {
            return new Date(timestamp);
        }
        return null;
    }

    onScroll(event: { start: number; end: number }) {
        if (this.loadingItems || this.currencyUnits.length <= this.currencyUnitsBuffer.length) {
            return;
        }
        if (event.end + this.numberOfItemsFromEndBeforeFetchingMore >= this.currencyUnitsBuffer.length) {
            this.fetchMore();
        }
    }

    onScrollToEnd() {
        this.fetchMore();
    }

    initSearchOrganization() {
        if (this.form.enabled) {
            this.searchService.search('', OrganizationURL.BASE, 5, 'tracker.organization.no-permission').subscribe((result) => {
                const organizations = result.map((item) => {
                    return {
                        id: item.id,
                        fantasyName: item.fantasyName
                    };
                });
                this.searchItemsOrganization = this.searchService
                    .startSubject(organizations, this.subjectsOrganization, OrganizationURL.BASE, this.loadingOrganizationSearch);
            });
        }
    }

    initAttributesIndividualSearch() {
        const attributeValuesFormArray = this.form.controls.attributesValue as FormArray;
        this.attributes.forEach(attribute => {
            const attributeValues = attributeValuesFormArray.controls.map(control => control.value);
            const relatedAttributeValue = attributeValues
                .filter(attributeValue => attribute.relatedAttribute &&
                    attribute.relatedAttribute.id === attributeValue.attribute.id)[0];
            this.startSearchSubscription(attribute, relatedAttributeValue);
        });
    }

    listenForRelatedAttributesChanges() {
        const attributeValuesFormArray = this.form.controls.attributesValue as FormArray;
        attributeValuesFormArray.controls.forEach(control =>
            control.valueChanges.subscribe(relatedAttributeValue => {
                this.attributes
                    .filter(attribute =>
                        attribute.relatedAttribute &&
                        attribute.relatedAttribute.id === relatedAttributeValue.attribute.id)
                    .forEach(attribute => {
                        this.startSearchSubscription(attribute, relatedAttributeValue);
                        const attributeValueControl = attributeValuesFormArray.controls.find(attributeValue =>
                            attributeValue.value && attributeValue.value.attribute
                            && attributeValue.value.attribute.id === attribute.id);
                        const newAttributeValue = attributeValueControl.value;
                        newAttributeValue.value = null;
                        attributeValueControl.setValue(newAttributeValue);
                    });
            })
        );
    }

    startSearchSubscription(attribute: any, relatedAttributeValue: any) {
        if (attribute.type === 'PEOPLE') {
            this.startSearchPeople(attribute, relatedAttributeValue);
        }
    }

    startSearchPeople(attribute: any, relatedAttributeValue: any) {
        let peopleSearch = this.peopleSearchMap.get(attribute.id);
        if (peopleSearch) {
            peopleSearch.subjects.unsubscribe();
            peopleSearch.subjects = new Subject();
        } else {
            peopleSearch = {
                loading: false,
                subjects: new Subject<string>(),
                searchItems: new Observable<any[]>()
            };
        }
        let searchEndpoint = PeopleURL.BASE;
        if (attribute.relatedAttribute) {
            if (attribute.relatedAttribute.type === 'ORGANIZATION') {
                const selectedOrganizationId = relatedAttributeValue.value?.id || relatedAttributeValue.value;
                searchEndpoint = `${PeopleURL.BY_ORGANIZATION}/${selectedOrganizationId ? selectedOrganizationId : 0}`;
            }
        }
        this.searchService.search('', searchEndpoint, 5, 'tracker.people.no-permission').pipe(take(1)).subscribe((result) => {
            let people = [];
            if (result) {
                people = result.map((item) => {
                    return {
                        id: item.id,
                        name: item.name
                    };
                });
            }
            peopleSearch.searchItems = this.searchService
                .startSubject(people, peopleSearch.subjects, searchEndpoint, peopleSearch.loading);
            this.peopleSearchMap.set(attribute.id, peopleSearch);
        });
    }

    private fetchMore() {
        this.loadingItems = true;
        const len = this.currencyUnitsBuffer.length;
        const more = this.currencyUnits.slice(len, this.bufferSize + len);
        this.currencyUnitsBuffer = this.currencyUnitsBuffer.concat(more);
        this.loadingItems = false;
    }

    private getAttributeValue(attId) {
        if (!this.attributeValues) {
            return undefined;
        }
        const index = this.attributeValues.findIndex((att) => {
            return att.attribute.id === attId;
        });
        return index > -1 ? this.attributeValues[index] : undefined;
    }

    private getValueByType(attributeValue, type) {
        if (attributeValue) {
            switch (type) {
                case 'DECIMAL':
                    return +attributeValue.value;
                case 'BOOLEAN':
                    return attributeValue.value === 'true';
                default:
                    return attributeValue.value;
            }
        } else {
            if (type === 'BOOLEAN') {
                return false;
            }
        }
        return undefined;
    }

    setDecimalField(event, index) {
        if (event) {
            this.attributesValue.at(index).get('value').setValue('0,');
        }
    }

    async setDraftValue(value) {
        for (let index = 0; index < value.length; index++) {
            if (value[index].attribute.type === 'DATE_TIME') {
                this.attributesValue.controls[index].get('time').setValue(value[index].time);
            } else if (value[index].attribute.type === 'PEOPLE' && value[index].value) {
                await this.initPeopleAttribute(value[index]);
            } else if (value[index].attribute.type === 'ORGANIZATION' && value[index].value) {
                await this.initOrganizationAttribute(value[index]);
            }
            this.attributesValue.controls[index].get('value').setValue(value[index].value);
        }
        this.changeForm();
    }

    lineItems(line) {
        return this.attributesValue.controls.filter(item => item.value.attribute.positionY === line);
    }

    indexItem(item) {
        return this.attributesValue.controls.indexOf(item);
    }

    arrayTotalLine() {
        const attributeQuantity = this.attributesValue.controls.length;
        const lastAttribute = this.attributesValue.controls[attributeQuantity - 1];
        const maxPositionY = attributeQuantity ? (lastAttribute.value.attribute.positionY + 1) : 0;
        return new Array(maxPositionY);
    }

    resetForm() {
        this.attributesValue.controls.forEach(element => {
            element.get('valueComplement').setValue(null);
            element.get('time').setValue(null);
            if (element.get('attribute').get('type').value === 'BOOLEAN') {
                element.get('value').setValue(false);
            } else {
                element.get('value').setValue(null);
            }
        });
        this.attributeAdded.emit(this.form.get('attributesValue').value);
    }
}
