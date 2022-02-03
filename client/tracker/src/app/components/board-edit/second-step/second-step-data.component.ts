import {Attribute, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {NotificationService} from 'common';
import {TrackerService} from '@shared/services/tracker.service';
import {BsModalService, BsModalRef} from 'ngx-bootstrap/modal';
import {AttachmentTypesComponent} from './attachment-types/attachment-types.component';
import {CustomAttEditComponent} from '../custom-att-edit/custom-att-edit.component';

@Component({
    selector: 'tracker-second-step-data',
    templateUrl: './second-step-data.component.html',
    styleUrls: ['./second-step-data.component.scss']
})
export class SecondStepDataComponent implements OnInit {

    public openChangeResizeAndReorder = false;

    modalRef: BsModalRef;

    @Input()
    set setAttributes(value) {
        if (value) {
            this.customAttributes = value;
        }
        this.indexToAddAttribute = this.customAttributes.length;
    }


    @Input()
    set setAttachments(value) {
        if (value) {
            this.attachmentTypes = value;
        }
    }

    @Input() set setTransitions(value) {
        if (value) {
            this.transitions = value;
        }
    }

    @Output() formIsValid: EventEmitter<any> = new EventEmitter<any>();

    @Output() attachmentTypesChange: EventEmitter<any> = new EventEmitter<any>();

    @Output() transitionsListChange: EventEmitter<any> = new EventEmitter<any>();

    public selectedAttribute: any;

    public customAttributes = [];

    public transitions = [];

    public index;

    public indexToAddAttribute: number;

    public attachmentTypes = '';

    constructor(
        private notification: NotificationService,
        private translateService: TranslateService,
        private modalService: BsModalService,
        protected trackerService: TrackerService) {
    }

    ngOnInit(): void {
    }

    openAttachmentTypesModal() {
        this.modalRef = this.modalService.show(AttachmentTypesComponent,
            {
                initialState: { types: this.attachmentTypes },
                backdrop: 'static',
                keyboard: false,
                class: 'modal-dialog-centered'
            });
        this.modalRef.content.submitAttachmentTypes.subscribe(result => {
            this.setAttachmentTypes(result);
        });
    }

    openCustomAttributeModal(item) {
        this.selectedAttribute = item ? item : undefined;
        this.modalRef = this.modalService.show(CustomAttEditComponent,
            {
                initialState: { attributeToEdit: this.selectedAttribute, setFilter: this.customAttributes },
                backdrop: 'static',
                keyboard: false,
                class: 'modal-dialog-centered'
            });
        this.modalRef.content.closeModal.subscribe(result => {
            if (result) {
                this.addCustomAttribute(result);
            } else {
                this.closeModal();
            }
        });
    }

    setAttachmentTypes(types) {
        this.attachmentTypes = types;
        this.attachmentTypesChange.emit(this.attachmentTypes);
        this.modalRef.hide();
    }

    updateCustomAttributes(changedCustomAttributes) {
        this.customAttributes = changedCustomAttributes;
        this.showChangeResizeAndReorder(false);
        this.updateCustomAttributesList();
    }

    closeModal() {
        this.selectedAttribute = null;
        this.modalRef.hide();
    }

    setRemoveIndex(index: number) {
        this.index = index;
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

    editTransitions(source, target) {
        if (this.transitions.length > 0) {
            let listAux = [];
            for (const row of this.transitions) {
                listAux = row.attributes.split(';');
                for (let j = 0; j < listAux.length; j++) {
                    if (listAux[j] === target.title) {
                        listAux[j] = source.title;
                    }
                }
                row.attributes = this.convertListInString(listAux);
            }
            this.transitionsListChange.emit(this.transitions);
        }
    }

    addCustomAttribute(attribute) {
        let position = 1;
        if (this.selectedAttribute && this.isValidTitle(attribute)) {
            attribute.position = this.selectedAttribute.position;
            attribute.trackerModel = this.selectedAttribute.trackerModel;
            attribute.numberOfColumns = this.selectedAttribute.numberOfColumns;
            attribute.positionX = this.selectedAttribute.positionX;
            attribute.positionY = this.selectedAttribute.positionY;
            const index = this.customAttributes.indexOf(this.selectedAttribute);
            this.customAttributes.splice(index, 1, attribute);
            this.editTransitions(attribute, this.selectedAttribute);
            this.closeModal();
        } else {
            if (this.isValidTitle(attribute)) {
                if (this.customAttributes && this.customAttributes.length > 0) {
                    position = this.customAttributes.length + 1;
                }
                attribute.position = position;
                attribute.cols = this.getColumnsNewCustomAtt(attribute.type);
                this.customAttributes.splice(this.indexToAddAttribute, 0, attribute);
                this.indexToAddAttribute++;
                this.closeModal();
                if (this.transitions.length > 0) {
                    this.transitions.forEach(item => {
                        item.attributes = item.attributes + ';' + attribute.title;
                    });
                    this.transitionsListChange.emit(this.transitions);
                }
            } else {
                this.notification.error(this.translateService.instant('tracker-manager.attribute-already-exists'));
            }
        }
        this.validForm();
    }

    editCustomAttribute(attribute) {
        this.addCustomAttribute({ attribute, selectedAttribute: this.selectedAttribute });
        this.closeModal();
    }

    updateCustomAttributesList() {
        this.customAttributes.forEach((att => {
            this.setListItemPosition(this.customAttributes, att);
        }));
        this.validForm();
    }

    setListItemPosition(list: any[], item) {
        const index = list.indexOf(item);
        item.position = index + 1;
    }

    validForm() {
        this.formIsValid.emit(this.customAttributes);
    }

    private isValidTitle(attribute): boolean {
        if (this.isFixedAttributeName(attribute.title)) {
            return false;
        } else {
            for (const customAttribute of this.customAttributes) {
                if (this.isEqualAttributes(customAttribute, attribute)) {
                    return false;
                }
            }
        }
        return true;
    }

    isEqualAttributes(customAttribute, attribute) {
        return (customAttribute.title.toUpperCase() === attribute.title.toUpperCase()
            && !customAttribute.deleted) && (attribute.position !== customAttribute.position || attribute.id !== customAttribute.id);
    }

    isFixedAttributeName(attributeName) {
        const fixedAttributeNames = ['Nome', 'Descrição', 'Prazo', 'Tags', 'Membros'];
        for (const name of fixedAttributeNames) {
            if (attributeName.toUpperCase() === name.toUpperCase()) {
                return true;
            }
        }
        return false;
    }

    deleteTransitions(item) {
        if (this.transitions.length > 0) {
            let listAux = [];
            for (const row of this.transitions) {
                listAux = row.attributes.split(';');
                for (let j = 0; j < listAux.length; j++) {
                    if (listAux[j] === item.title) {
                        listAux.splice(j, 1);
                    }
                }
                row.attributes = this.convertListInString(listAux);
            }
            this.transitionsListChange.emit(this.transitions);
        }
    }

    delete() {
        this.customAttributes[this.index].deleted = true;
        this.deleteTransitions(this.customAttributes[this.index]);
        this.removeRelatedAttributes(this.customAttributes[this.index]);
        this.validForm();
    }

    removeRelatedAttributes(item) {
        if (item.type === 'ORGANIZATION') {
            this.customAttributes.map(element => {
                if (element?.relatedAttribute?.title === item.title) {
                    element.relatedAttribute = undefined;
                    element.needsValueComplement = false;
                }
            });
        }
    }

    get hasCustomAttributes() {
        return this.customAttributes?.filter(a => !a.deleted).length;
    }

    getTypeToDisplayFormatted(item: string) {
        return `tracker-manager.attribute.type.${item.replace('_', '').toLowerCase()}`;
    }

    showChangeResizeAndReorder(value: boolean) {
        this.openChangeResizeAndReorder = value;
    }

    getColumnsNewCustomAtt(type) {
        switch (type) {
            case 'BOOLEAN':
                return 2;
            case 'STRING':
                return 12;
            case 'DATE_TIME':
                return 6;
            default:
                return 3;
        }
    }

}
