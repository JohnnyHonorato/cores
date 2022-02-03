import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {FilterService} from '@shared/services/filter.service';
import {BaseEditComponent} from 'common';

@Component({
    selector: 'tracker-filter-edit',
    templateUrl: './filter-edit.component.html',
    styleUrls: ['./filter-edit.component.scss']
})

export class FilterEditComponent extends BaseEditComponent implements OnInit {

    @Input()
    public set setFilter(item) {
        if (item?.assignees?.length > 0 || item?.tags?.length > 0) {
            this.view = false;
            this.filters = item?.assignees.concat(item.tags);
            this.setForm(item);
            this.formatNameSuggestion(item);
        }
    }

    @Input() public trackerModelId;

    @Output() public cleanFilter: EventEmitter<any> = new EventEmitter();

    @Output() public saveFilter: EventEmitter<any> = new EventEmitter();

    @Output() removeItemFilter: EventEmitter<any> = new EventEmitter();

    view = false;

    filters = [];

    numberFilters;

    constructor(private filterService: FilterService) {
        super();
    }

    ngOnInit(): void {
        super.ngOnInit();
    }

    removeFilterItem(filterItem) {
        if (this.filters.length === 1) {
            this.clean();
        } else {
            const index = this.filters.indexOf(filterItem);
            this.filters.splice(index, 1);
            this.removeItemFilter.emit(filterItem);
        }
    }

    clean() {
        this.resetFilter();
        this.cleanFilter.emit();
    }

    resetFilter() {
        this.service.get(`tracker/v1/tracker-models/${this.trackerModelId}/filters/count`).subscribe((result: any) => {
            this.view = false;
            this.filters = [];
            this.editForm.reset();
        });
    }

    isEdit() {
        return this.editForm.controls.id.value !== undefined && this.editForm.controls.id.value !== null;
    }

    save() {
        if (this.isEdit()) {
            this.update();
        } else {
            this.insert();
        }
    }

    insert() {
        this.filterService.createFilter(this.trackerModelId, this.editForm.value).subscribe(result => {
            this.setForm(result);
            this.view = true;
            this.notification.insertSuccess();
            this.saveFilter.emit(result);
        }, error => {
            this.notificationService.error(error.error.message);
        });
    }

    update() {
        this.filterService.updateFilter(this.trackerModelId, this.editForm.value).subscribe(result => {
            this.notification.updateSuccess();
            this.saveFilter.emit(result);
        }, error => {
            this.notificationService.error(error.error.message);
        });
    }

    setForm(item) {
        this.editForm.controls.id.setValue(item.id ? item.id : this.editForm.controls.id.value);
        this.editForm.controls.name.setValue(item.name ? item.name : this.editForm.controls.name.value);
        this.editForm.controls.createdBy.setValue(item.createdBy ? item.createdBy : this.editForm.controls.createdBy.value);
        this.editForm.controls.isFavourite.setValue(item.isFavourite ? item.isFavourite : false);
        this.editForm.controls.numberOfAttributes.setValue(item.numberOfAttributes ? item.numberOfAttributes : this.filters.length);
        this.editForm.controls.trackerModel.setValue(item.trackerModel ? item.trackerModel : {id: this.trackerModelId});
        this.editForm.controls.tags.setValue(item.tags ? item.tags : []);
        this.editForm.controls.assignees.setValue(item.assignees ? item.assignees : []);
        this.editForm.controls.deleted.setValue(item.deleted ? item.deleted : false);
    }

    getFormControls(): {} {
        return {
            id: new FormControl(undefined, []),
            name: new FormControl(undefined, [Validators.required, Validators.maxLength(255)]),
            createdBy: new FormControl(undefined, []),
            isFavourite: new FormControl(false, []),
            numberOfAttributes: new FormControl(undefined, []),
            trackerModel: new FormControl(undefined, []),
            tags: new FormControl(undefined, []),
            assignees: new FormControl(undefined, []),
            deleted: new FormControl(undefined, [])
        };
    }

    private formatNameSuggestion(item) {
        if (!item.name && !this.editForm.controls.name.value) {
            this.service.get(`tracker/v1/tracker-models/${this.trackerModelId}/filters/count`).subscribe((result: any) => {
                this.numberFilters = parseInt(result, 10);
                this.editForm.controls.name.setValue('Filtro Personalizado ' + (this.numberFilters + 1));
            });
        }
    }

    getServiceURL(): string {
        throw new Error('Method not implemented.');
    }

    getRouterURL(): string {
        return 'tracker';
    }
}
