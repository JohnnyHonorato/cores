import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, Validators} from '@angular/forms';
import {Observable, Subscription} from 'rxjs';
import {AppInjector, NotificationService, CustomValidators} from 'common';
import {ChecklistURL} from '@shared/util/url.domain';
import {TrackerService} from '@shared/services/tracker.service';
@Component({
    selector: 'tracker-checklist',
    templateUrl: './checklist.component.html',
    styleUrls: ['./checklist.component.scss']
})
export class ChecklistComponent implements OnInit, OnDestroy {

    @Input() checklist: any;

    @Input() checklistIndex: number;

    @Input() checklistItemRemovedEvent: Observable<void>;

    @Input() isView: boolean;

    @Output() checklistAdded: EventEmitter<any> = new EventEmitter<any>();

    @Output() checklistItemAdded: EventEmitter<any> = new EventEmitter<any>();

    @Output() checklistItemValueChangedAdded: EventEmitter<any> = new EventEmitter<any>();

    @Output() updateChecklistItem: EventEmitter<any> = new EventEmitter<any>();

    @Output() deleteChecklist: EventEmitter<any> = new EventEmitter<any>();

    @Output() deleteChecklistItem: EventEmitter<any> = new EventEmitter<any>();

    private notification: NotificationService = AppInjector.get(NotificationService);

    private checklistItemRemovedSubscription: Subscription;

    form: FormGroup;

    doneItemsQtd: number;

    doneItemsPercent: number;

    isUpdate = false;

    elementToUpdate: any;

    constructor(private trackerService: TrackerService) { }

    ngOnInit(): void {
        this.initForm();
        this.countDoneItems();
        this.setChangeChecklistItemSubscription();
    }

    ngOnDestroy(): void {
        this.checklistItemRemovedSubscription.unsubscribe();
    }

    initForm(): void {
        this.form = new FormGroup(this.getFormControls());
    }

    getFormControls(): {} {
        return {
            name: new FormControl(undefined, [Validators.required,
            Validators.maxLength(255), CustomValidators.noWhitespaceValidator])
        };
    }

    hasError(control: AbstractControl): string {
        return control && control.touched && control.invalid ? 'is-invalid state-invalid' : '';
    }

    updateChecklist(name: string) {
        this.checklist.name = name;
        this.checklist.index = this.checklistIndex;
        this.checklistAdded.emit(this.checklist);
    }

    addChecklistItem(): void {
        if (this.isUpdate) {
            this.elementToUpdate.name = this.form.controls.name.value;
            this.elementToUpdate.checklistIndex = this.checklistIndex;
            this.isUpdate = false;
            this.form.reset();
            this.updateChecklistItem.emit(this.elementToUpdate);
        } else {
            const checklistItem = { id: null, name: this.form.controls.name.value, done: false, checklistIndex: this.checklistIndex };
            this.checklist.items.push(checklistItem);
            this.form.reset();
            this.checklistItemAdded.emit(checklistItem);
            this.countDoneItems();
        }
    }

    itemValueChanged(itemIndex: number): void {
        if (this.isView) {
            this.trackerService.put(`${ChecklistURL.BASE}/${this.checklist.id}/item/${this.checklist.items[itemIndex].id}`,
                this.checklist.items[itemIndex]).subscribe(result => {
                    this.notification.updateSuccess();
                }, (error) => {
                    this.notification.error(error.error.message);
                });
        }
        this.checklistItemValueChangedAdded.emit({ checklistIndex: this.checklistIndex, itemIndex });
        this.countDoneItems();
    }

    countDoneItems(): void {
        this.doneItemsQtd = this.checklist.items.filter((item: any) => item.done).length;
        this.doneItemsPercent = this.doneItemsQtd / this.checklist.items.length * 100 || 0;
    }

    updateItem(element: any): void {
        this.isUpdate = true;
        this.elementToUpdate = element;
        this.form.controls.name.setValue(element.name);
    }

    delete(): void {
        this.deleteChecklist.emit(this.checklistIndex);
    }

    emitDeleteChecklistItemEvent(index: number): void {
        this.deleteChecklistItem.emit({ checklistIndex: this.checklistIndex, checklistItemIndex: index });
    }

    private setChangeChecklistItemSubscription(): void {
        this.checklistItemRemovedSubscription = this.checklistItemRemovedEvent
            .subscribe(() => {
                this.countDoneItems();
            });
    }

}
