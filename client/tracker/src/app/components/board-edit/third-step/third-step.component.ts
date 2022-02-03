import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BaseComponent} from 'common';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {ModalToAddStatusComponent} from './modal-to-add-status/modal-to-add-status.component';
import {ModalSettingStatusAttributesComponent} from './modal-setting-status-attributes/modal-setting-status-attributes.component';

@Component({
    selector: 'tracker-third-step',
    templateUrl: './third-step.component.html',
    styleUrls: ['./third-step.component.scss']
})
export class ThirdStepComponent extends BaseComponent implements OnInit {

    @Input() set setTransitions(value) {
        if (value) {
            this.transitions = value;
        }
    }

    @Input() set attributes(list) {
        if (list) {
            this.attributesList = list.filter(item => !item.deleted);
        }
    }

    @Input()
    set setStatusList(list) {
        if (list) {
            this.statusList = list.sort((a, b) => b.position - a.position ? 1 : -1);
        }
    }

    @Input() nextStep = false;

    @Output() statusListChange: EventEmitter<any> = new EventEmitter<any>();

    @Output() changeStatusOrder: EventEmitter<any> = new EventEmitter<any>();

    @Output() transitionsListChange: EventEmitter<any> = new EventEmitter<any>();

    @Output() goToFourthStep: EventEmitter<any> = new EventEmitter<any>();

    public transitions = [];

    public attributesList = [];

    public statusList = [];

    public selectedStatus = null;

    private selectedStatusIndex = null;

    public selectedTransition = null;

    public openChangePosition = false;

    modalRef: BsModalRef;

    constructor(private modalService: BsModalService) {
        super();
    }

    openSettingStatusAttributesModal() {
        this.modalRef = this.modalService.show(ModalSettingStatusAttributesComponent,
            {
                initialState: {setTransition: {transition: this.selectedTransition, attributes: this.attributesList}},
                backdrop: 'static',
                keyboard: false,
                class: 'modal-dialog-centered'
            });
        this.modalRef.content.saveSettings.subscribe(result => {
            this.saveSettingStatus(result);
        });

        this.modalRef.content.closeModal.subscribe(() => {
            this.closeModal();
        });
    }

    ngOnInit() {
        this.orderStatusByPosition();
        this.updateStatusList(this.statusList);
    }

    createStatusByName(value: any) {
        let newPosition;
        if (this.statusList.length === 0) {
            newPosition = 0;
        } else {
            newPosition = this.statusList[this.statusList.length - 1].position + 1;
        }
        return {name: value, deleted: false, position: newPosition};
    }

    checkIfStatusNameAlreadyExists(status: any) {
        const statusFoundList = this.statusList.find(item => {
            return item.name === status.name &&
                (item.id !== status.id || (item.id === undefined && status.id === undefined)) &&
                (item.deleted === false || item.deleted === undefined);
        });
        return statusFoundList !== undefined;
    }

    openEditStatusModal() {
        this.modalRef = this.modalService.show(ModalToAddStatusComponent,
            {
                initialState: {setStatus: this.selectedStatus ? this.selectedStatus : null},
                backdrop: 'static',
                keyboard: false,
                class: 'modal-dialog-centered'
            });
        this.modalRef.content.submitStatus.subscribe(result => {
            this.saveOrEdit(result);
        });
        this.modalRef.content.cancel.subscribe(result => {
            this.cancelEditAndAdd();
            this.modalRef.hide();
        });
    }


    changeSelectedItems(status, index) {
        this.selectedStatusIndex = index;
        this.selectedStatus = status;
    }

    returnAmountOfValidStatus() {
        const amountOfValidStatus = this.statusList.filter(item => item.deleted === false);
        return amountOfValidStatus.length;
    }

    sendStatusListChange() {
        if (this.returnAmountOfValidStatus() >= 2) {
            this.statusListChange.emit(this.statusList);
        } else {
            this.statusListChange.emit(null);
        }
    }

    editTransitions(status, newStatus) {
        const sourceIndexes = this.transitions.map(
            (item, i) => item.source.name === status.name ? i : null).filter(i => i !== null);
        sourceIndexes.forEach(index => {
            this.transitions[index].source = newStatus;
        });
        const targetIndexes = this.transitions.map(
            (item, i) => item.target.name === status.name ? i : null).filter(i => i !== null);
        targetIndexes.forEach(index => {
            this.transitions[index].target = newStatus;
        });
        this.transitionsListChange.emit(this.transitions);
    }

    saveOrEdit(value) {
        const status = value.id ? value : this.createStatusByName(value.name);
        if (this.checkIfStatusNameAlreadyExists(status)) {
            this.notification.warning(this.translate('tracker-manager.status-already-exists'));
        } else {
            if (this.selectedStatusIndex !== null) {
                this.editTransitions(this.statusList[this.selectedStatusIndex], status);
                this.statusList.splice(this.selectedStatusIndex, 1, status);
            } else {
                this.statusList.push(status);
            }
            this.modalRef.hide();
            this.transitions.push(this.createTransition(status, status));
            this.transitionsListChange.emit(this.transitions);
            this.sendStatusListChange();
            this.changeSelectedItems(null, null);
        }
    }

    editStatus(item, index) {
        this.changeSelectedItems(item, index);
        this.openEditStatusModal();
    }

    deleteStatus(item, index) {
        this.selectedStatusIndex = index;
    }

    confirmDeletionStatus() {
        this.statusList[this.selectedStatusIndex].deleted = true;
        this.sendStatusListChange();
        this.changeSelectedItems(null, null);
    }

    cancelEditAndAdd() {
        this.changeSelectedItems(null, null);
    }

    getTransitionBySourceAndTarget(source, target) {
        return this.transitions.find(item => item.source.name === source.name && item.target.name === target.name);
    }

    reverseDelete(source, target) {
        const transition = this.getTransitionBySourceAndTarget(source, target);
        const index = this.transitions.findIndex(item => item.source.name === source.name && item.target.name === target.name);
        if (index >= 0) {
            this.transitions.splice(index, 1);
            if (transition.id != null) {
                transition.deleted = !transition.deleted;
                this.transitions.splice(index, 0, transition);
            }
        }
    }

    createTransition(source, target) {
        return {id: null, source, target, deleted: false, attributes: this.convertListInString(this.attributesList)};
    }

    changeTransition(source, target) {
        const transition = this.getTransitionBySourceAndTarget(source, target);
        if (transition === undefined) {
            this.transitions.push(this.createTransition(source, target));
        } else {
            this.reverseDelete(source, target);
        }
        this.transitionsListChange.emit(this.transitions);
    }

    selectClass(source, target) {
        if (this.transitions.length > 0) {
            const transition = this.getTransitionBySourceAndTarget(source, target);
            if (transition && transition.deleted === false) {
                return 'btn-add';
            }
        }
        return 'btn-remove';
    }

    selectTransition(source, target) {
        this.selectedTransition = this.getTransitionBySourceAndTarget(source, target);
        this.openSettingStatusAttributesModal();
    }

    closeModal() {
        this.selectedTransition = null;
        this.modalRef.hide();
    }

    saveSettingStatus(list) {
        const index = this.transitions.findIndex(
            item => item.source.name === this.selectedTransition.source.name
                && item.target.name === this.selectedTransition.target.name);
        this.transitions[index].attributes = list;
        this.transitionsListChange.emit(this.transitions);
        this.closeModal();
    }

    updateStatusList(statusList) {
        this.statusList = statusList;
        this.showChangePosition(false);
    }

    showChangePosition(value) {
        this.changeStatusOrder.emit(value);
        this.openChangePosition = value;
    }

    convertListInString(list) {
        let listString = '';
        for (let i = 0; i < list.length; i++) {
            if (i + 1 < list.length) {
                listString = listString + list[i].title + ';';
            } else {
                listString = listString + list[i].title;
            }
        }
        return listString;
    }

    private orderStatusByPosition() {
        this.statusList.sort((a, b) => (a.position > b.position) ? 1 : -1);
    }

}
